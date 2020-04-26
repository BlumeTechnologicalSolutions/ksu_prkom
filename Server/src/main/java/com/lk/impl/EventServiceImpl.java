package com.lk.impl;

import com.lk.entity.Event;
import com.lk.entity.Response;
import com.lk.persistence.HibernateUtil;
import com.lk.service.EventService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.transaction.spi.LocalStatus;

import javax.inject.Named;
import javax.transaction.Synchronization;
import java.util.List;

@Named("eventService")
public class EventServiceImpl implements EventService {

    public Response getEventEducation(Integer education) {
        return null;
    }


    public Response getEvents() {
        String check = "";
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction transaction = null;
            //check += "1";

            transaction = session.beginTransaction();
            //check += "2";
            List<Event> events = session.createSQLQuery(
                    "SELECT * FROM event_on_calendar")
                    .addEntity(Event.class)
                    .list();
            //check += "3";
            transaction.commit();
            //check += "4";
            Event event = events.get(0);
            //check += "5";
            return new Response(true, events.get(0));
        }
        catch (Exception e) {
            return new Response(false, "Ошибка при получение событий: " + e.getLocalizedMessage());
        }
        finally {
            session.close();
        }
    }


    public Response addEvent(Event event) {
        return null;
    }


    public Response removeEvent(Event event) {
        return null;
    }
}
