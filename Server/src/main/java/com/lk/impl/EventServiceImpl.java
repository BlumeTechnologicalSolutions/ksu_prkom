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
import java.sql.Timestamp;
import java.util.List;

@Named("eventService")
public class EventServiceImpl implements EventService {

    public Response getEventEducation(Integer education) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction transaction = null;

            transaction = session.beginTransaction();
            List<Event> events = session.createSQLQuery("SELECT * FROM event_on_calendar " +
                    "WHERE education_id = :education_id ")
                    .addEntity(Event.class)
                    .setParameter("education_id", education)
                    .list();
            transaction.commit();

            return new Response(true, events);
        }
        catch (Exception e) {
            return new Response(false, "Ошибка при получение событий: " + e.getLocalizedMessage());
        }
        finally {
            session.close();
        }
    }


    public Response getEvents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction transaction = null;

            transaction = session.beginTransaction();
            List<Event> events = session.createSQLQuery(
                    "SELECT * FROM event_on_calendar")
                    .addEntity(Event.class)
                    .list();
            transaction.commit();

            return new Response(true, events);
        }
        catch (Exception e) {
            return new Response(false, "Ошибка при получение событий: " + e.getLocalizedMessage());
        }
        finally {
            session.close();
        }
    }


    public Response addEvent(Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction transaction = null;

            transaction = session.beginTransaction();
            List<Event> events = session.createSQLQuery("INSERT INTO event_on_calendar " +
                    "VALUES (:id, :event, TIMESTAMP :time, :education_id, :form_education_id)")
                    .addEntity(Event.class)
                    .setParameter("id", event.getId())
                    .setParameter("event", event.getEvent())
                    .setParameter("time", event.getDate())
                    .setParameter("education_id", event.getEducation_id())
                    .setParameter("form_education_id", event.getForm_education_id())
                    .list();
            transaction.commit();

            return new Response(true, events);
        }
        catch (Exception e) {
            return new Response(false, "Ошибка при получение событий: " + e.getLocalizedMessage());
        }
        finally {
            session.close();
        }
    }


    public Response removeEvent(Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction transaction = null;

            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM event_on_calendar " +
                    "WHERE id = :id")
                    .addEntity(Event.class)
                    .setParameter("id", event.getId());
            transaction.commit();

            return new Response(true);
        }
        catch (Exception e) {
            return new Response(false, "Ошибка при получение событий: " + e.getLocalizedMessage());
        }
        finally {
            session.close();
        }
    }
}
