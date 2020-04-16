package com.lk.persistence;


import com.lk.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Authentification {

    private static Logger logger = LoggerFactory.getLogger(Authentification.class);

    public User getUserByCookie(HttpServletRequest request) {
        String tokenInfo = null;
        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie: cookies){
                if(cookie.getName().equals("token")) {
                    tokenInfo = cookie.getValue();
                }
            }
        }
        if(tokenInfo!=null){
            user = getUserByTokenInfo(tokenInfo);
        }
        return user;
    }


    public User getUserByTokenInfo(String tokenInfo){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            if (tokenInfo != null) {
                transaction = session.beginTransaction();
                List<User> users = session.createSQLQuery("SELECT user.* FROM public.tokens as t \n" +
                        "join public.users as user on t.user_id = t.id\n" +
                        "join WHERE t.tokenInfo = :tokenINFO and !t.IsDeleted")
                        .addEntity(User.class)
                        .setParameter("tokenINFO", tokenInfo)
                        .list();
                transaction.commit();
                if(users!=null && users.size() > 0) return users.get(0);
            }
        } catch (Exception ex) {
            if(transaction!=null) transaction.rollback();
            logger.error("Error in getUserByTokenInfo function:"+ex.getLocalizedMessage(),ex);
        } finally {
            session.close();
        }
        return null;
    }


}
