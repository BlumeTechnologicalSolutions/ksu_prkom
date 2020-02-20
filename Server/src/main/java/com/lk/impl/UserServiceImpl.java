package com.lk.impl;

import com.lk.entity.Response;
import com.lk.entity.Token;
import com.lk.entity.User;
import com.lk.entity.UserRegistration;
import com.lk.persistence.HibernateUtil;
import com.lk.persistence.MongoDbUtill;
import com.lk.service.UserService;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Named("userService")
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Response getUserByToken(HttpServletRequest httpServletRequest) {
        logger.info("Start function getUserByToken, by token info: " + httpServletRequest);
        String token = httpServletRequest.getHeader("Authorization");
        try{
            MongoDatabase database = MongoDbUtill.getDataBase();
            MongoCollection<Document> collection =  database.getCollection("Tokens");
            FindIterable<Document> tokens = collection.find(eq("token", token));
            if(tokens.iterator().hasNext()){
                String login = (String) tokens.iterator().next().get("login");
                collection =  database.getCollection("Accounts");
                FindIterable<Document> user = collection.find(eq("login", login));
                return new Response(true, (Object) user.iterator().next().toJson() );
            }
        } catch (Exception ex){
            logger.error("Exception in getUserByToken with:",ex.getLocalizedMessage(),ex);
            return new Response(false, "Ошибка:"+ex.getLocalizedMessage());
        }
        return new Response(false, "Токен не существует");
    }

    public Response authorization (User user){
        if(user.getLogin() != null || user.getPassword() != null) {
            logger.info("User authorization start: " + user.getLogin());
            if(user.getLogin() == null || user.getLogin() == "") return new Response(false, "Логин недопустим");
            if(user.getPassword() == null || user.getPassword() == "") return new Response(false, "Пароль недопустим");
            String login = user.getLogin();
            String password = user.getPassword();
            MongoDatabase database = new MongoDbUtill().getDataBase();
            MongoCollection<Document> collection =  database.getCollection("Accounts");
            FindIterable<Document> userList = collection.find(and(eq("login", login), eq("password", password)));
            if(userList.iterator().hasNext()) {
                String json = userList.iterator().next().toJson();
                Date date = new Date();
                date.setDate(date.getDate()+14);
                String tokenHash = Token.md5Custom(String.valueOf(date.getTime()));
                //Token token = new Token(login,tokenHash, date);
                Document tokenDb = new Document();
                tokenDb.put("login", login);
                tokenDb.put("token", tokenHash);
                tokenDb.put("expiration", date);
                collection = database.getCollection("Tokens");
                collection.insertOne(tokenDb);
                json = json.substring(0,json.length()-1);
                json += ", \"token\":\""+tokenHash+"\"}";
                return new Response(true, (Object) json);
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

        MongoDatabase database = new MongoDbUtill().getDataBase();
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
        }
        logger.info("User is already exist with login: " + login);
        return new Response(false, "Указанный логин уже существует");
    }


    public Response remember(User user){
        logger.info("Start user registration with login:"+user.getLogin());
        if(user.getLogin() == null || user.getLogin() == "") return new Response(false, "Логин не может быть пустым");
        if(user.getControlAnswer() == null || user.getControlAnswer() == "") return new Response(false, "Ответ на контрольный вопрос не может быть пустым");
        String login = user.getLogin();
        MongoDatabase database = new MongoDbUtill().getDataBase();
        MongoCollection<Document> collection =  database.getCollection("Accounts");
        FindIterable<Document> userList = collection.find(eq("login", login));
        for(Document doc: userList){
            if(doc.get("secretanswer") == user.getControlAnswer()){
                //to do token of restore pass
                return new Response(true, (Object) "OK");
            }
        }
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
