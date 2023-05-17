/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.entity.Company;
import com.jobportal.dao.CompanyDAO;

/**
 *
 * @author aravind
 */

@Service
public class CompanyService {
    
    private final CompanyDAO companyDao;
    
    @Autowired
    public CompanyService(CompanyDAO companyDao) {
        this.companyDao = companyDao;
    }

    public void save(Company company) {
        companyDao.save(company);
    }
    
    public List<Company> getAllCompanies() {
        return companyDao.fetchAll();
    }
    
    public Optional<Company> getCompanyById(int id) {
        return companyDao.fetchById(id);
    }

    public Optional<Company> getCompanyByName(String name) {
        return companyDao.fetchByName(name);
    }
}
