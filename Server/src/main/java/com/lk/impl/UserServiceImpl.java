package com.lk.impl;

import com.lk.entity.*;
import com.lk.persistence.Authentification;
import com.lk.persistence.HibernateUtil;
import com.lk.persistence.HtmlMailSenderAddressList;
import com.lk.service.UserService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;



@Named("userService")
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    List<EmailReceiver> emailReceivers = new ArrayList<>();

    @Override
    public Response getUserByToken(HttpServletRequest httpServletRequest) {
        logger.info("Start function getUserByToken, by token info: " + httpServletRequest);
        Authentification authentification = new Authentification();
        User user = authentification.getUserByCookie(httpServletRequest);
        if(user!=null) return new Response(true, user);
        else return new Response(false, "Токен не существует");
    }

    @Override
    public Response authorization (User userToAuthorize){
        if(userToAuthorize.getLogin() != null || userToAuthorize.getPassword() != null) {
            logger.info("User authorization start: " + userToAuthorize.getLogin());
            if(userToAuthorize.getLogin() == null || userToAuthorize.getLogin() == "") return new Response(false, "Логин недопустим");
            if(userToAuthorize.getPassword() == null || userToAuthorize.getPassword() == "") return new Response(false, "Пароль недопустим");
            String login = userToAuthorize.getLogin();
            String password = userToAuthorize.getPassword();
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<User> users = session.createSQLQuery("select * from public.users as usr " +
                        "where login = :userLogin and password = :userPass ")
                        .addEntity(User.class)
                        .setParameter("userLogin", login)
                        .setParameter("userPass", password)
                        .list();
                transaction.commit();
                if(users !=null && users.size()>0) {
                    User user = users.get(0);
                    Integer userId = user.getId();
                    String tokenHash = Token.md5Custom(String.valueOf(new Date().getTime()));
                    saveToken(tokenHash,userId);
                    user.setToken(tokenHash);
                    user = setUserToClient(user);
                    return new Response(true, user);
                }

            } catch (Exception ex){
                if(transaction!=null) transaction.rollback();
                logger.info("Exception in authorization: " ,ex.getLocalizedMessage(),ex);
                return new Response(false, "Ошибка при авторизации пользователя: "+ex.getLocalizedMessage());
            } finally {
                session.close();
            }
        }
        return new Response(false, "Неверно указан логин или пароль");
    }

    @Override
    public Response sendRegistrationCode(String toEmail){
        if(toEmail == null || toEmail == "") return new Response(false, "Указан пустой email");
        if(getUserByEmail(toEmail).size()>0){
            logger.info("User is already exist with email: " + toEmail);
            return new Response(false, "Указанный почтовый ящик уже зарегистрирован");
        }
        Integer minutesSend = 60;
        for (EmailReceiver emailReceiver : emailReceivers){
            if(emailReceiver.getEmail().equals(toEmail)){
                if(emailReceiver.getReceiveDate().getTime() + (1000 * 60 * minutesSend) > new Date().getTime()) {
                    return new Response(true, "Код подтверждения уже был отправлен на: " + toEmail);
                }
            }
        }
        if(createCode(toEmail)){
            return new Response(true, "Код подтверждения отправлен на: "+toEmail);
        } else {
            return new Response(false, "Ошибка при отправке письма на почту: "+toEmail);
        }

    }

    private boolean createCode(String email)  {
        String code = createCode(); //создаем код  из 5 рандоманых цифр
        String emailMessage = "Ваш код подтверждения <b>"+code+"</b>"; //, действует в течении 1 часа
        List<String> toEmails = new ArrayList<>();
        toEmails.add(email);
        //отправка кода на email
        HtmlMailSenderAddressList htmlMailSenderAddressList = new HtmlMailSenderAddressList();
        if(htmlMailSenderAddressList.send("Код подтверждения", emailMessage, toEmails)){
            logger.info("Email sended to: "+email+", with activation code: "+code);
            //saved receive
            EmailReceiver emailReceiver = new EmailReceiver();
            emailReceiver.setCode(code);
            emailReceiver.setEmail(email);
            emailReceiver.setReceiveDate(new Date());
            emailReceivers.add(emailReceiver);
            return true;
        } else {
            logger.error("Problems with email service, check debug");
            return false;
        }
    }

    public String createCode(){
        Random rnd = new Random();
        Integer digit = rnd.nextInt(10);
        String code = "";
        for (int i = 0; i < 5; i++) {
            code += digit.toString();
            digit = rnd.nextInt(9);
        }
        return code;
    }

    private void deleteCodeFromEmailReceivers(String email, String code){
        Iterator<EmailReceiver> iter = emailReceivers.iterator();
        while(iter.hasNext()) {
            EmailReceiver emailReceiver = iter.next();
            //тот который был подтвержден
            if (emailReceiver.getEmail().equals(email) && emailReceiver.getCode().equals(code)) {
                iter.remove();
            }
            //просроченные
            if(emailReceiver.getReceiveDate().getTime() + (1000 * 60 * 60) < new Date().getTime()){
                iter.remove();
            }
        }
    }

    @Override
    public Response registration(UserRegistration userToRegistration, String code){
        logger.info("Start user registration with login:"+userToRegistration.getLogin());
        String email = userToRegistration.getEmail();
        if(email == null || email == "") return new Response(false, "Email недопустим");
        if(getUserByEmail(email).size()>0){
            logger.info("User is already exist with email: " + email);
            return new Response(false, "Указанный почтовый ящик уже зарегистрирован");
        }
        boolean isApprovedCode = false;
        for(EmailReceiver emailReceiver: emailReceivers){
            if(emailReceiver.getEmail().equals(email) && emailReceiver.getCode().equals(code)){
                isApprovedCode = true;
            }
        }
        if(isApprovedCode) {
            deleteCodeFromEmailReceivers(email, code);
            String login = userToRegistration.getLogin();
            String password = userToRegistration.getPassword();
            String controlAnswer = userToRegistration.getControlAnswer();
            String controlQuestion = userToRegistration.getControlQuestion();
            String firstName = userToRegistration.getFirstName();
            String lastName = userToRegistration.getLastName();
            String petronimic = userToRegistration.getPatronymic();
            if (login == null || login == "") return new Response(false, "Логин недопустим");
            if (password == null || password == "") return new Response(false, "Пароль недопустим");
            if (controlAnswer == null || controlAnswer == "")
                return new Response(false, "Контрольный ответ недопустим");
            if (controlQuestion == null || controlQuestion == "")
                return new Response(false, "Контрольный вопрос недопустим");
            if (firstName == null || firstName == "") return new Response(false, "Имя недопустимо");
            if (lastName == null || lastName == "") return new Response(false, "Фамилия недопустима");
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            try {
                if (getUserByLogin(login)!=null) {
                    logger.info("User is already exist with login: " + login);
                    return new Response(false, "Указанный логин уже существует");
                }

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                /*User newUser = new User(null, firstName, lastName, petronimic,email,timestamp,login,password,controlQuestion, controlAnswer,false,false,false);*/
                transaction = session.beginTransaction();
                session.createSQLQuery("INSERT INTO public.users(login, password, creation_date, fist_name, last_name, patronymic, email, control_question, control_answer, is_admin, is_operator, is_deleted)\n" +
                        "VALUES ((:login),(:password),(:timestamp),(:firstName),(:lastName),(:petronimic),(:email),(:controlQuestion),(:controlAnswer),b'0',b'0',b'0')")
                        .setParameter("firstName", firstName)
                        .setParameter("lastName", lastName)
                        .setParameter("petronimic", petronimic)
                        .setParameter("email", email)
                        .setParameter("timestamp", timestamp)
                        .setParameter("login", login)
                        .setParameter("password", password)
                        .setParameter("controlQuestion", controlQuestion)
                        .setParameter("controlAnswer", controlAnswer)
                        .executeUpdate();
                User user = getUserByLogin(login);
                session.createSQLQuery("INSERT INTO public.private_user_affair(user_id)\n" +
                        "VALUES ((:userId))")
                        .setParameter("userId", user.getId())
                        .executeUpdate();
                transaction.commit();
                return new Response(true, (Object) "Пользователь успешно зарегистрирован");
            } catch (Exception ex) {
                if (transaction != null) transaction.rollback();
                logger.info("Exception in registration: ", ex.getLocalizedMessage(), ex);
                return new Response(false, "Ошибка при регистарции пользователя: " + ex.getLocalizedMessage());
            } finally {
                session.close();
            }
        } else
            return new Response(false, "Вы указали неверный код подтверждения");
    }

    public User setUserToClient(User user){
        user.setPassword(null);
        user.setControlAnswer(null);
        return user;
    }

    public List<User> getUserByEmail(String email){
        List<User> users = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            users = session.createSQLQuery("select * from users where email = :thisEmail")
                    .addEntity(User.class)
                    .setParameter("thisEmail", email)
                    .list();
            transaction.commit();
        } catch (Exception ex){
            if(transaction!=null) transaction.rollback();
            logger.error("Exception in getUserByEmail: " ,ex.getLocalizedMessage(),ex);
        } finally {
            session.close();
        }
        return users;
    }

    public User getUserByLogin(String login){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<User> users  = session.createSQLQuery("select * from users where login = :thisLogin")
                    .addEntity(User.class)
                    .setParameter("thisLogin", login)
                    .list();
            transaction.commit();
            if(users.size()>0) return  users.get(0);
        } catch (Exception ex){
            if(transaction!=null) transaction.rollback();
            logger.error("Exception in getUserByLogin: " ,ex.getLocalizedMessage(),ex);
        } finally {
            session.close();
        }
        return null;
    }

    public void saveToken(String token, Integer UserId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Date date = new Date();
            date.setDate(date.getDate()+14);
            session.createSQLQuery("INSERT INTO public.tokens(token, user_id, expiration)\n" +
                    "VALUES ((:token),(:userId),(:timestamp))")
                    .setParameter("token", token)
                    .setParameter("userId", UserId)
                    .setParameter("timestamp", new Timestamp(date.getTime()))
                    .executeUpdate();
            transaction.commit();
        } catch (Exception ex){
            if(transaction!=null) transaction.rollback();
            logger.error("Exception in saveToken: " ,ex.getLocalizedMessage(),ex);
        } finally {
            session.close();
        }

    }

    @Override
    public Response remember(String email){
        logger.info("Start user remember with email:"+email);
        List<User> users = getUserByEmail(email);
        if(users.size()>0){
            User user = users.get(0);
            String emailMessage = "Данные для авторизации в личный кабинет Приемной комиссии.<br> Ваш логин: <b>"+user.getLogin()+"</b><br>Ваш пароль: <b>"+user.getPassword()+"</b>";
            List<String> toEmails = new ArrayList<>();
            toEmails.add(email);
            HtmlMailSenderAddressList htmlMailSenderAddressList = new HtmlMailSenderAddressList();
            if(htmlMailSenderAddressList.send("Восстановление доступа к личному кабинету", emailMessage, toEmails)){
                return new Response(true, (Object)("Письмо было отправлено на почтовый ящик: "+ email));
            } else {
                return new Response(false, "Ошибка при отправке письма");
            }
        } else {
            return new Response(false, "Вы указали несуществующий email");
        }
    }

    public User getUserById(Integer userId) {
        logger.info("Start function getUserById, by userId: " + userId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            List<User> user = session.createSQLQuery("Select * from Users as usr where usr.Id = (:userId)")
                    .addEntity(User.class)
                    .setParameter("userId",userId)
                    .list();
            transaction.commit();
            return user.get(0);
        } catch (Exception ex){
            logger.error("Exception in getUserByToken with:",ex.getLocalizedMessage(),ex);
            if (transaction!=null) { transaction.rollback(); }
        }
        return null;
    }

    @Override
    public Response getRegistrationSecretQuestions() {
        logger.info("Start function getRegistrationSecretQuestions");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            List<RegistrationSecretQuestion> registrationSecretQuestions =
                    session.createSQLQuery("Select * from registration_secret_questions")
                            .addEntity(RegistrationSecretQuestion.class)
                            .list();
            transaction.commit();
            return new Response(true, registrationSecretQuestions);
        } catch (Exception ex){
            logger.error("Exception in getRegistrationSecretQuestions with:",ex.getLocalizedMessage(),ex);
            if (transaction!=null) { transaction.rollback(); }
            return new Response(false, "Ошибка получения секретных вопросов: "+ex.toString());
        }
    }



}
