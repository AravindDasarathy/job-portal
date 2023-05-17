package com.jobportal.validator;

import com.jobportal.entity.Application;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ApplicationValiator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Application.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Application application = (Application)target;

        validateFullName(errors, application);
        validateEmail(errors, application);
        validatePhone(errors, application);
        validateLinkedinUrl(errors, application);
        validateGithubUrl(errors, application);
        validatePronouns(errors, application);
        validateAdditionalInfo(errors, application);
    }

    private void validateFullName(Errors errors, Application application) {
        ValidationUtils.rejectIfEmpty(errors, "fullName", "error.invalid.fullname", "Full Name is required");

        if (application.getFullName().length() > 50) {
            errors.rejectValue("fullName", "error.invalid.fullname", "Full Name must not be more than 50 characters");
        }
    }

    private void validateEmail(Errors errors, Application application) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.invalid.email", "Email is required");

        final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";

        if (!application.getEmail().matches(EMAIL_REGEX)) {
            errors.rejectValue("email", "error.invalid.email", "Email must be valid");
        }

        if (application.getEmail().length() > 50) {
            errors.rejectValue("email", "error.invalid.email", "Email must not be more than 50 characters");
        }
    }

    private void validatePhone(Errors errors, Application application) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "error.invalid.phone", "Phone number is required");

        if (!application.getPhone().matches("\\d{10}")) {
            errors.rejectValue("phone", "error.invalid.phone", "Phone number must be 10 digits");
        }
    }

    private void validateLinkedinUrl(Errors errors, Application application) {
        final String LINKEDIN_URL_REGEX = "^((http|https):\\/\\/)?+(www.linkedin.com\\/)+[a-z]+(\\/)+[a-zA-Z0-9-]{5,30}+$";

        System.out.println("Linkedin url: " + application.getLinkedin());

        if (!application.getGithub().isBlank() && !application.getLinkedin().matches(LINKEDIN_URL_REGEX)) {
            errors.rejectValue("linkedin", "error.invalid.linkedinUrl", "LinkedIn URL must be valid");
        }
    }

    private void validateGithubUrl(Errors errors, Application application) {
        final String GITHUB_URL_REGEX = "/^(https?://)?(www\\.)?github\\.com/[a-zA-Z0-9_]{1,25}$/igm\n";

        if (!application.getGithub().isBlank() && !application.getGithub().matches(GITHUB_URL_REGEX)) {
            errors.rejectValue("github", "error.invalid.githubUrl", "GitHub URL must be valid");
        }
    }

    private void validatePronouns(Errors errors, Application application) {
        if (!application.getPronouns().isBlank() && application.getPronouns().length() > 10) {
            errors.rejectValue("pronouns", "error.invalid.pronouns", "Pronouns must not be more than 10 characters");
        }
    }

    private void validateAdditionalInfo(Errors errors, Application application) {
        if (!application.getAdditionalInfo().isBlank() && application.getAdditionalInfo().length() > 1000) {
            errors.rejectValue("additionalInfo", "error.invalid.additionalInfo", "Additional Info must not be more than 1000 characters");
        }
    }
}
