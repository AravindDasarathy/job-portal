/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.dao;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.jobportal.entity.Company;

/**
 *
 * @author aravind
 */

@Repository
public class CompanyDAO extends DAOManager {

    public void save(Company company) {
        try {
            begin();
            getSession().merge(company);
            commit();
        } catch (Exception e) {
            rollback();
            e.printStackTrace();
            throw new RuntimeException("Exception while saving company: " + e.getMessage());
        } finally {
            close();
        }
    }
    public List<Company> fetchAll() {
        try {
            begin();
            List<Company> list = getSession().createQuery("from Company").list();
            commit();

            return list;
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while fetching all companies: " + e.getMessage());
        } finally {
            close();
        }
    }
    
    public Optional<Company> fetchById(int id) {
        try {
            begin();
            Company company = getSession().get(Company.class, id);
            commit();

            return Optional.ofNullable(company);
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Exception while fetching company by id: " + e.getMessage());
        } finally {
            close();
        }
    }

    public Optional<Company> fetchByName(String name) {
        try {
            Session session = getSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cq = cb.createQuery(Company.class);
            Predicate predicate = cb.equal(cq.from(Company.class).get("name"), name);

            cq.where(predicate);

            Company company =  session.createQuery(cq).getSingleResult();

            return Optional.ofNullable(company);
        } catch(Exception e) {
            throw new RuntimeException("Exception while fetching company by name: " + e.getMessage());
        } finally {
            close();
        }
    }
}
