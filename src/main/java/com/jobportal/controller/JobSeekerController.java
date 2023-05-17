/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import com.jobportal.validator.ApplicationValiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author aravind
 */

@Controller
@RequestMapping("/job_seeker")
public class JobSeekerController {

    private final JobService jobService;
    private final UserService userService;

    private final ApplicationService applicationService;

    private final ApplicationValiator applicationValidator;

    @Autowired
    public JobSeekerController(
        JobService jobService,
        UserService userService,
        ApplicationService applicationService,
        ApplicationValiator applicationValidator
    ) {
        this.jobService = jobService;
        this.userService = userService;
        this.applicationService = applicationService;
        this.applicationValidator = applicationValidator;
    }
    
    @GetMapping
    public String showJobSeekerPage(
        @RequestParam(name = "search", required = false) String titleQuery, Model model) {

        List<Job> jobs = jobService.getJobAfterFilter(titleQuery);

        Optional<User> user = userService.getLoggedInUser();

        user.ifPresent(value -> model.addAttribute("user", value));

        model.addAttribute("jobs", jobs);
        model.addAttribute("search", titleQuery);

        return "job_seeker";
    }

    @GetMapping("/apply/{job_id}")
    public String showJobForm(
        @PathVariable int job_id,
        @ModelAttribute("application") Application application
    ) {
        Optional<Job> job = jobService.getJobById(job_id);

        if (job.isEmpty()) {
            return "redirect:/job_seeker";
        }

        application.setJob(job.get());

        return "job_application";
    }

    @PostMapping("/apply/{job_id}")
    public String applyJob(
        @PathVariable int job_id,
        @ModelAttribute("application") Application jobApp,
        BindingResult result,
        Model model
    ) {
        Optional<Job> job = jobService.getJobById(job_id);
        Optional<User> user = userService.getLoggedInUser();

        if (job.isPresent() && user.isPresent()) {
            jobApp.setJob(job.get());
            jobApp.setApplicant(user.get());
            jobApp.setStatus(Application.ApplicationStatus.APPLIED);
            jobApp.setApplyDate(new Date(System.currentTimeMillis()));

            applicationValidator.validate(jobApp, result);

            if (result.hasErrors()) {
                model.addAttribute("application", jobApp);
                model.addAttribute("errors", result.getAllErrors());

                return "job_application";
            }

            jobService.saveApplication(jobApp);
        }

        return "redirect:/job_seeker";
    }

    @GetMapping("{user_id}/applications")
    public String showApplications(@PathVariable("user_id") int id, Model model) {
        List<Application> applications = applicationService.getApplicationsByUserId(id);

        model.addAttribute("applications", applications);

        return "application_list";
    }
}
