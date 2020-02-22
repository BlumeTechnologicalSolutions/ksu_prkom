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
                        "where 'usr.Login' = :userLogin and 'usr.Password' = :userPass ")
                        .addEntity(User.class)
                        .setParameter("userLogin", login)
                        .setParameter("userPass", password)
                        .list();
                transaction.commit();
                if(users !=null && users.size()>0) {
                    User user = users.get(0);
                    String tokenHash = Token.md5Custom(String.valueOf(new Date().getTime()));
                    user.setToken(tokenHash);
                    return new Response(true, user);
                }
            } catch (Exception ex){
                if(transaction!=null) transaction.rollback();
                logger.info("Exception in authorization: " ,ex.getLocalizedMessage(),ex);
            } finally {
                session.close();
            }
        }
        return new Response(false, "Неверно указан логин или пароль");
    }

    public Response registration(UserRegistration user){
        logger.info("Start user registration with login:"+user.getLogin());
        String login = user.getLogin();
        String password = user.getPassword();
        String controlAnswer = user.getControlAnswer();
        String controlQuestion = user.getControlQuestion();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String petronimic = user.getPatronymic();
        if(login == null || login == "") return new Response(false, "Логин недопустим");
        if(password == null || password == "") return new Response(false, "Пароль недопустим");
        if(user.getControlAnswer() == null || user.getControlAnswer() == "") return new Response(false, "Контрольный ответ недопустим");
        if(user.getControlQuestion() == null || user.getControlQuestion() == "") return new Response(false, "Контрольный вопрос недопустим");
        if(user.getFirstName() == null || user.getFirstName() == "") return new Response(false, "Имя недопустимо");
        if(user.getLastName() == null || user.getLastName() == "") return new Response(false, "Фамилия недопустима");

        /*MongoDatabase database = new MongoDbUtill().getDataBase();
        MongoCollection<Document> collection =  database.getCollection("Accounts");
        FindIterable<Document> findIt = collection.find(eq("login", login));
        Iterator iterator = findIt.iterator();
        if(!iterator.hasNext()) {
            Document accountInfo = new Document();
            accountInfo.put("login", login);
            accountInfo.put("password", password);
            accountInfo.put("secretquestion", controlQuestion);
            accountInfo.put("secretanswer", controlAnswer);
            collection.insertOne(accountInfo);
            //logger.info("Registration user with info:" + result.toString());
            return new Response(true, (Object) "Пользователь успешно зарегистрирован");
        }*/
        logger.info("User is already exist with login: " + login);
        return new Response(false, "Указанный логин уже существует");
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
