package com.lk.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    private static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    private static SessionFactory buildSessionFactory() {
//        try {
//            // Создает сессию с hibernate.cfg.xml
//            Configuration configuration = new Configuration();
//            configuration.configure("/hibernate.cfg.xml");
//            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
//
//            return configuration.buildSessionFactory(serviceRegistry);
//        }
//        catch (Throwable ex) {
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }


        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("/hibernate.cfg.xml");
                ServiceRegistryBuilder builder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.buildServiceRegistry());
            } catch (Exception ex) {
                logger.error("HibernateUtil error is: " + ex);
                throw new ExceptionInInitializerError(ex);
            } catch (Throwable ex) {
                logger.error("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return buildSessionFactory();
    }



}
