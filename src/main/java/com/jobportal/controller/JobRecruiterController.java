package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import com.jobportal.validator.JobValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/job_recruiter")
public class JobRecruiterController {
    private final JobService jobService;
    private final UserService userService;

    private final JobValidator jobValidator;

    private final ApplicationService applicationService;
    @Autowired
    public JobRecruiterController(
        JobService jobService,
        UserService userService,
        ApplicationService applicationService,
        JobValidator jobValidator
    ) {
        this.jobService = jobService;
        this.userService = userService;
        this.applicationService = applicationService;
        this.jobValidator = jobValidator;
    }
    @GetMapping
    public String showJobRecruiterPage(Model model) {
        Optional<User> recruiter = userService.getLoggedInUser();

        if (recruiter.isEmpty()) {
            return "redirect:/login";
        }

        List<Job> jobs = jobService.getJobsByRecruiter(recruiter.get());

        model.addAttribute("jobs", jobs);

        return "job_recruiter";
    }

    @GetMapping("/job")
    public String showAddJobPage(@ModelAttribute("job") Job job) {
        Optional<User> recruiter = userService.getLoggedInUser();

        if (recruiter.isEmpty()) {
            return "redirect:/login";
        }

        job.setRecruiter(recruiter.get());

        return "job_form";
    }

    @PostMapping("/job")
    public String addJob(@ModelAttribute("job") Job job, BindingResult result, Model model) {
        jobValidator.validate(job, result);

        if (result.hasErrors()) {
            model.addAttribute("job", job);
            model.addAttribute("errors", result.getAllErrors());

            return "job_form";
        }

        Optional<User> recruiter = userService.getLoggedInUser();

        if (recruiter.isEmpty()) {
            return "redirect:/login";
        }

        job.setRecruiter(recruiter.get());
        job.setCompany(recruiter.get().getCompany());
        job.setPostedDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        jobService.addJob(job);

        return "redirect:/job_recruiter";
    }

    @GetMapping("/job/{job_id}/applicants")
    public String showJobApplicantsPage(@PathVariable int job_id, Model model) {
        Optional<Job> job = jobService.getJobById(job_id);

        if (job.isEmpty()) {
            return "redirect:/job_recruiter";
        }

        List<Application> applicantDetails = applicationService.getJobApplicants(job.get());

        model.addAttribute("job_applicants", applicantDetails);

        return "job_applicants";
    }

    @PostMapping("/applicants/{applicant_id}")
    public String reviewApplicant(
        @RequestParam("status") Application.ApplicationStatus status,
        @PathVariable int applicant_id
    ) {
        Optional<Application> application = applicationService.getApplicationById(applicant_id);

        if (application.isEmpty()) {
            return "redirect:/job_recruiter";
        }

        application.get().setStatus(status);

        applicationService.updateApplication(application.get());

        return "redirect:/job_recruiter/job/" + application.get().getJob().getId() + "/applicants";
    }
}
