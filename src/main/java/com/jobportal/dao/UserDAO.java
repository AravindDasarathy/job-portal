/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jobportal.entity.Application;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.jobportal.entity.User;
import com.jobportal.entity.Job;

/**
 *
 * @author aravind
 */

@Repository
public class UserDAO extends DAOManager {
    public void save(User user) {
        try {
            begin();
            getSession().merge(user);
            commit();
        } catch (Exception e) {
            rollback();
            e.printStackTrace();

            throw new RuntimeException("Exception while saving company: " + e.getMessage());
        } finally {
            close();
        }
    }
    public List<User> fetchAll() {
        return new ArrayList<>();
    }
    
    public Optional<User> fetchById() {
        return Optional.empty();
    }
    
    public Optional<User> fetchByEmail(String email) {
        try {
            begin();
            Query query = getSession().createQuery("from User where email = :email");
            query.setParameter("email", email);
            User user = (User) query.getSingleResult();
            commit();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            rollback();
            e.printStackTrace();
            throw new RuntimeException("Exception while fetching user by email: " + e.getMessage());
        } finally {
            close();
        }
    }

    public List<User> fetchApplicantsForJob(Job job) {
        try {
            Session session = getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<Application> applicationRoot = cq.from(Application.class);
            Join<Application, User> userJoin = applicationRoot.join("applicant");

            cq.where(cb.equal(applicationRoot.get("job"), job));
            cq.select(userJoin);

            return session.createQuery(cq).getResultList();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while fetching job applicants: " + e.getMessage());
        } finally {
            close();
        }
    }
}
