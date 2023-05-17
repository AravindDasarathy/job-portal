package com.jobportal.service;

import com.jobportal.dao.ApplicationDAO;
import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationDAO applicationDao;

    @Autowired
    public ApplicationService(ApplicationDAO applicationDao) {
        this.applicationDao = applicationDao;
    }
    public List<Application> getJobApplicants(Job job) {
        return applicationDao.fetchJobApplicants(job);
    }

    public Optional<Application> getApplicationById(int id) {
        return applicationDao.fetchApplicationById(id);
    }

    public void updateApplication(Application app) {
        applicationDao.updateApplication(app);
    }

    public List<Application> getApplicationsByUserId(int id) {
        return applicationDao.fetchApplicationsByUserId(id);
    }
}
