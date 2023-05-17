package com.jobportal.dao;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ApplicationDAO extends DAOManager {
    public List<Application> fetchJobApplicants(Job job) {
        try {
            Session session = getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Application> cq = cb.createQuery(Application.class);
            Predicate predicate = cb.equal(cq.from(Application.class).get("job"), job);
            cq.where(predicate);

            return session.createQuery(cq).getResultList();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while fetching job applicants: " + e.getMessage());
        } finally {
            close();
        }
    }

    public Optional<Application> fetchApplicationById(int id) {
        try {
            return Optional.ofNullable(getSession().get(Application.class, id));
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while fetching application by id: " + e.getMessage());
        } finally {
            close();
        }
    }

    public void updateApplication(Application application) {
        try {
            begin();
            getSession().merge(application);
            commit();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while updating application: " + e.getMessage());
        } finally {
            close();
        }
    }

    public List<Application> fetchApplicationsByUserId(int userId) {
        try {
            Session session = getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Application> cq = cb.createQuery(Application.class);
            Predicate predicate = cb.equal(cq.from(Application.class).get("applicant").get("id"), userId);
            cq.where(predicate);

            return session.createQuery(cq).getResultList();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while fetching applications by user id: " + e.getMessage());
        } finally {
            close();
        }
    }
}
