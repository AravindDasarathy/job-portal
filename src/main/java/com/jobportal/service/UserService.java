/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.service;

import java.util.List;
import java.util.Optional;

import com.jobportal.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jobportal.entity.User;
import com.jobportal.dao.UserDAO;

/**
 *
 * @author aravind
 */

@Service
public class UserService {

    private final UserDAO userDao;

    @Autowired
    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void save(User user) {
        userDao.save(user);
    }
    
    public List<User> getAllUsers() {
        return userDao.fetchAll();
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userDao.fetchByEmail(email);
    }

    public Optional<User> getLoggedInUser() {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        String email = authUser.getName();
        return userDao.fetchByEmail(email);
    }

    public List<User> getJobApplicants(Job job) {
        return userDao.fetchApplicantsForJob(job);
    }
}
