package com.jobportal.validator;

import com.jobportal.entity.Job;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class JobValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Job.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Job job = (Job)target;

        validateJobTitle(job, errors);
        validateJobDescription(job, errors);
        validateJobSalary(job, errors);
    }

    public void validateJobTitle(Object target, Errors errors) {
        Job job = (Job)target;

        ValidationUtils.rejectIfEmpty(errors, "title", "error.invalid.title", "Title is required");

        if (job.getTitle().length() > 50) {
            errors.rejectValue("title", "error.invalid.jobtitle", "Job title must not be more than 50 characters");
        }
    }

    public void validateJobDescription(Object target, Errors errors) {
        Job job = (Job)target;

        ValidationUtils.rejectIfEmpty(errors, "description", "error.invalid.description", "Description is required");

        if (job.getDescription().length() > 1000) {
            errors.rejectValue("description", "error.invalid.description", "Description must not be more than 1000 characters");
        }
    }

    public void validateJobSalary(Object target, Errors errors) {
        Job job = (Job)target;

        if (job.getSalary().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("salary", "error.invalid.salary", "Salary must not be negative");
        }
    }
}
