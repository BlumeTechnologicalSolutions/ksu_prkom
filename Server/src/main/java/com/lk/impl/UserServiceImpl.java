package com.lk.impl;

import com.lk.entity.Response;
import com.lk.entity.User;
import com.lk.entity.UserRegistration;
import com.lk.persistence.HibernateUtil;
import com.lk.persistence.MongoDbUtill;
import com.lk.service.UserService;
import com.mongodb.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.List;

@Named("userService")
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User getUserByToken(String tokenInfo) {
        logger.info("Start function getUserByToken, by token info: " + tokenInfo);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            List<User> user = session.createSQLQuery("Select * from Users as usr where usr.Token = (:TokenInfo)")
                    .addEntity(User.class)
                    .setParameter("TokenInfo",tokenInfo)
                    .list();
            transaction.commit();
            return user.get(0);
        } catch (Exception ex){
            logger.error("Exception in getUserByToken with:",ex.getLocalizedMessage(),ex);
            if (transaction!=null) { transaction.rollback(); }
        }
        return null;
    }

    public Response authorization (User user){

        if(user.getLogin() != null || user.getPassword() != null) {
            logger.info("User authorization start: " + user.getLogin());
            DB database = new MongoDbUtill().getDataBase();
            DBCollection collection = database.getCollection("Accounts");
            BasicDBObject searchForAuthorizationCheck = new BasicDBObject();
            searchForAuthorizationCheck.put("login", user.getLogin());
            searchForAuthorizationCheck.put("password", user.getPassword());
            DBCursor cursor = collection.find(searchForAuthorizationCheck);
            if (cursor.size() > 0) {
                if (cursor.hasNext()) {
                    DBObject dbObject = cursor.next();
                    logger.info("Autorization user:" + dbObject);
                    return new Response(true, dbObject);
                }
                return new Response(false, "Неверно указан логин или пароль");
            }
        }
        return new Response(false, "Неверно указан логин или пароль");
    }

    public Response registration(UserRegistration user){
        logger.info("Start user registration with login:"+user.getLogin());
        if(user.getLogin() == null || user.getLogin() == "") return new Response(false, "Логин недопустим");
        if(user.getPassword() == null || user.getPassword() == "") return new Response(false, "Пароль недопустим");
        if(user.getControlAnswer() == null || user.getControlAnswer() == "") return new Response(false, "Контрольный ответ недопустим");
        if(user.getControlQuestion() == null || user.getControlQuestion() == "") return new Response(false, "Контрольный вопрос недопустим");
        if(user.getFirstName() == null || user.getFirstName() == "") return new Response(false, "Имя недопустимо");
        if(user.getLastName() == null || user.getLastName() == "") return new Response(false, "Фамилия недопустима");

        String login = user.getLogin();
        DB database = new MongoDbUtill().getDataBase();
        DBCollection collection = database.getCollection("Accounts");
        BasicDBObject searchForDublicate = new BasicDBObject();
        searchForDublicate.put("login", login);
        DBCursor cursor = collection.find(searchForDublicate);
        if(cursor.size()==0){
            logger.info("registration user:" + login);
           // BasicDBObject document = new BasicDBObject();
            //document.put("name", "Shubham");
            //document.put("company", "Baeldung");
            //.insert(document);
            return new Response(true, (Object) "Пользователь успешно зарегистрирован");
        }
        return new Response(false, "Указанный логин уже существует");
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
