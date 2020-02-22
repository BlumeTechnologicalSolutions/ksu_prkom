package com.lk.impl;

import com.lk.entity.Response;
import com.lk.entity.Token;
import com.lk.entity.User;
import com.lk.entity.UserRegistration;
import com.lk.persistence.HibernateUtil;
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

    @Override
    public Response getUserByToken(HttpServletRequest httpServletRequest) {
        logger.info("Start function getUserByToken, by token info: " + httpServletRequest);
        String token = httpServletRequest.getHeader("Authorization");
        try{

        } catch (Exception ex){
            logger.error("Exception in getUserByToken with:",ex.getLocalizedMessage(),ex);
            return new Response(false, "Ошибка:"+ex.getLocalizedMessage());
        }
        return new Response(false, "Токен не существует");
    }

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
                    String tokenHash = Token.md5Custom(String.valueOf(new Date().getTime()));

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

    public Response registration(UserRegistration userToRegistration){
        logger.info("Start user registration with login:"+userToRegistration.getLogin());
        String login = userToRegistration.getLogin();
        String password = userToRegistration.getPassword();
        String controlAnswer = userToRegistration.getControlAnswer();
        String controlQuestion = userToRegistration.getControlQuestion();
        String firstName = userToRegistration.getFirstName();
        String lastName = userToRegistration.getLastName();
        String petronimic = userToRegistration.getPatronymic();
        String email = userToRegistration.getEmail();
        if(login == null || login == "") return new Response(false, "Логин недопустим");
        if(password == null || password == "") return new Response(false, "Пароль недопустим");
        if(controlAnswer == null || controlAnswer == "") return new Response(false, "Контрольный ответ недопустим");
        if(controlQuestion == null || controlQuestion == "") return new Response(false, "Контрольный вопрос недопустим");
        if(firstName == null || firstName == "") return new Response(false, "Имя недопустимо");
        if(lastName == null || lastName == "") return new Response(false, "Фамилия недопустима");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            if(getUserByLogin(login).size()>0){
                logger.info("User is already exist with login: " + login);
                return new Response(false, "Указанный логин уже существует");
            }
            Timestamp timestamp =  new Timestamp(System.currentTimeMillis());
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
            transaction.commit();
            return new Response(true, (Object) "Пользователь успешно зарегистрирован");
        } catch (Exception ex){
            if(transaction!=null) transaction.rollback();
            logger.info("Exception in registration: " ,ex.getLocalizedMessage(),ex);
            return new Response(false, "Ошибка при регистарции пользователя: "+ex.getLocalizedMessage());
        } finally {
            session.close();
        }
    }

    public List<User> getUserByLogin(String login){
        List<User> users = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            users = session.createSQLQuery("select * from users where login = :thisLogin")
                    .addEntity(User.class)
                    .setParameter("thisLogin", login)
                    .list();
            transaction.commit();
        } catch (Exception ex){
            if(transaction!=null) transaction.rollback();
            logger.info("Exception in getUserByLogin: " ,ex.getLocalizedMessage(),ex);
        } finally {
            session.close();
        }
        return users;
    }

    public Response remember(User user){
        logger.info("Start user registration with login:"+user.getLogin());
        if(user.getLogin() == null || user.getLogin() == "") return new Response(false, "Логин не может быть пустым");
        if(user.getControlAnswer() == null || user.getControlAnswer() == "") return new Response(false, "Ответ на контрольный вопрос не может быть пустым");
        String login = user.getLogin();
        /*MongoDatabase database = new MongoDbUtill().getDataBase();
        MongoCollection<Document> collection =  database.getCollection("Accounts");
        FindIterable<Document> userList = collection.find(eq("login", login));
        for(Document doc: userList){
            if(doc.get("secretanswer") == user.getControlAnswer()){
                //to do token of restore pass
                return new Response(true, (Object) "OK");
            }
        }*/
        return new Response(false, "Логин не существует или контрольный ответ неверный");
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



}
