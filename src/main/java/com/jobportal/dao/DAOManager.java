/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aravind
 */

public class DAOManager {

    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Object> sessionThread = new ThreadLocal<>();
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    
    protected DAOManager() {
    }

    public static EntityManager getEntityManager() {
        EntityManager entityManager = (EntityManager) DAOManager.sessionThread.get();

        if (entityManager == null) {
            entityManager = sessionFactory.createEntityManager();
            DAOManager.sessionThread.set(entityManager);
        }

        return entityManager;
    }

      public static Session getSession() {
          Session session = (Session) DAOManager.sessionThread.get();

          if (session == null) {
              session = sessionFactory.openSession();
              DAOManager.sessionThread.set(session);
          }
          return session;
      }

    protected void begin() {
        getSession().getTransaction().begin();
    }

    protected void commit() {
        getSession().getTransaction().commit();
    }

    protected void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot rollback", e);
        }
        try {
            getSession().close();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot close", e);
        }
        DAOManager.sessionThread.set(null);
    }

    public static void close() {
        getSession().close();
        DAOManager.sessionThread.set(null);
    }
}


