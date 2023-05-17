/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.dao;

import java.util.List;
import java.util.Optional;

import com.jobportal.entity.Application;
import com.jobportal.entity.User;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.jobportal.entity.Job;
/**
 *
 * @author aravind
 */

@Repository
public class JobDAO extends DAOManager {
    public List<Job> fetchAfterFilter(String filter) {
        try {
            Session session = getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Job> cq = cb.createQuery(Job.class);
            Root<Job> root = cq.from(Job.class);

            if (filter == null || filter.trim().isEmpty()) {
                cq.select(root);
            } else {
                Predicate predicate = cb.like(root.get("title"), "%" + filter + "%");
                cq.where(predicate);
            }

            return session.createQuery(cq).getResultList();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while fetching all jobs: " + e.getMessage());
        } finally {
            close();
        }
    }
    
    public Optional<Job> fetchById(int id) {
        try {
            return Optional.ofNullable(getSession().get(Job.class, id));
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while fetching job by id: " + e.getMessage());
        } finally {
            close();
        }
    }

    public void saveApplication(Application jobApp) {
        try {
            begin();
            getSession().merge(jobApp);
            commit();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while applying for job: " + e.getMessage());
        } finally {
            close();
        }
    }

    public List<Job> fetchByRecruiter(User recruiter) {
        try {
            Session session = getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Job> cq = cb.createQuery(Job.class);
            Predicate predicate = cb.equal(cq.from(Job.class).get("recruiter"), recruiter);
            cq.where(predicate);

            return session.createQuery(cq).getResultList();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while fetching jobs by recruiter: " + e.getMessage());
        } finally {
            close();
        }
    }

    public void save(Job job) {
        try {
            begin();
            getSession().merge(job);
            commit();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while saving job: " + e.getMessage());
        } finally {
            close();
        }
    }
}
