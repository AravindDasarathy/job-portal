package com.jobportal.validator;

import com.jobportal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

@Component
public class UserValidator implements Validator {
    private final CompanyValidator companyValidator;

    @Autowired
    public UserValidator(CompanyValidator companyValidator) {
        this.companyValidator = companyValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        validateFirstName(errors, user);
        validateLastName(errors, user);
        validatePassword(errors, user);
        validateEmail(errors, user);
        validateDesignation(errors, user);
        validateMobileNumber(errors, user);
        validateUserType(errors, user);
        validateCompany(errors, user);
    }

    private void validateFirstName(Errors errors, User user) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.firstname", "First Name is required");

        if (StringUtils.containsWhitespace(user.getFirstName())) {
            errors.rejectValue("firstName", "error.invalid.firstname", "First Name must not contain spaces");
        }

        if (user.getFirstName().length() > 50) {
            errors.rejectValue( "firstName", "error.invalid.firstname", "First Name must not be more than 50 characters");
        }
    }

    private void validateLastName(Errors errors, User user) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.lastname", "Last Name is required");
        if (StringUtils.containsWhitespace(user.getLastName())) {
            errors.rejectValue("lastName", "error.invalid.lastname", "Last Name must not contain spaces");
        }
        if (user.getLastName().length() > 50) {
            errors.rejectValue( "lastName", "error.invalid.lastname", "Last Name must not be more than 50 characters");
        }
    }

    private void validatePassword(Errors errors, User user) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password is required");
        if (StringUtils.containsWhitespace(user.getPassword())) {
            errors.rejectValue("password", "error.invalid.password", "Password must not contain spaces");
        }
        if (user.getPassword().length() < 5) {
            errors.rejectValue("password", "error.invalid.password", "Password must be at least 5 characters");
        }
    }

    private void validateEmail(Errors errors, User user) {
        final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        if (!user.getEmail().matches(EMAIL_REGEX)) {
            errors.rejectValue( "email", "error.invalid.email", "Email must be valid");
        }
    }

    private void validateDesignation(Errors errors, User user) {
        if (user.getDesignation() != null && !user.getDesignation().matches("^[A-Za-z ]*$")) {
            errors.rejectValue("designation", "error.invalid.designation", "Designation must contain only letters");
        }
    }

    private void validateMobileNumber(Errors errors, User user) {
        if (user.getMobileNumber() != null && !user.getMobileNumber().matches("\\d{10}")) {
            errors.rejectValue("mobileNumber", "error.invalid.mobileNumber", "Mobile Number must be 10 digits");
        }
    }

    private void validateUserType(Errors errors, User user) {
        if (user.getUserType() == null) {
            errors.rejectValue("userType", "error.invalid.usertype", "User Type is required");
        }
    }

    private void validateCompany(Errors errors, User user) {
        if (user.getUserType() == User.UserType.JOB_RECRUITER) {
            if (user.getCompany() == null) {
                errors.rejectValue("company", "error.invalid.company", "Company details are required");
            }

            companyValidator.validate(user.getCompany(), errors);
        }
    }
}
