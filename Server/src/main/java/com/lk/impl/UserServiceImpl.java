package com.lk.impl;

import com.lk.entity.Response;
import com.lk.entity.User;
import com.lk.entity.UserRegistration;
import com.lk.persistence.HibernateUtil;
import com.lk.persistence.MongoDbUtill;
import com.lk.service.UserService;
import com.mongodb.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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
        logger.info("Start function authorization");
        DB database = new MongoDbUtill().getDataBase();
        DBCollection collection = database.getCollection("testCol");
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("login", user.getLogin());
        searchQuery.put("password", user.getPassword());
        DBCursor cursor = collection.find(searchQuery);
        if(cursor.size()>0){
            if(cursor.hasNext()) {
                DBObject dbObject = cursor.next();
                logger.info("Autorization user:" + dbObject);
                return new Response(true, dbObject);
            }
            return new Response(false, "Неверно указан логин или пароль");
        }
        return new Response(false, "Неверно указан логин или пароль");
    }

    public Response registration(UserRegistration user){

        return new Response(true, "Ok");
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
