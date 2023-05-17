/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.service;

import java.util.List;
import java.util.Optional;

import com.jobportal.entity.Application;
import com.jobportal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.entity.Job;
import com.jobportal.dao.JobDAO;

/**
 *
 * @author aravind
 */

@Service
public class JobService {
    private final JobDAO jobDao;

    @Autowired
    public JobService(JobDAO jobDao) {
        this.jobDao = jobDao;
    }
    
    public List<Job> getJobAfterFilter(String filter) {
        return jobDao.fetchAfterFilter(filter);
    }
    
    public Optional<Job> getJobById(int id) {
        return jobDao.fetchById(id);
    }

    public void saveApplication(Application jobApp) {
        jobDao.saveApplication(jobApp);
    }

    public List<Job> getJobsByRecruiter(User recruiter) {
        return jobDao.fetchByRecruiter(recruiter);
    }

    public void addJob(Job job) {
        jobDao.save(job);
    }
}
