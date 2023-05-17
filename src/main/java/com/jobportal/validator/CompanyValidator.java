package com.jobportal.validator;

import com.jobportal.entity.Company;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CompanyValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Company.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Company company = (Company)target;

        validateCompanyName(errors, company);
        validateCompanyDesc(errors, company);
    }

    private void validateCompanyName(Errors errors, Company company) {
        if (company.getName().length() > 50) {
            errors.rejectValue( "company.name", "error.invalid.companyname", "Company Name must not be more than 50 characters");
        }
    }

    private void validateCompanyDesc(Errors errors, Company company) {
        if (company.getDescription().length() > 1000) {
            errors.rejectValue( "company.description", "error.invalid.companydesc", "Company Description must not be more than 1000 characters");
        }
    }
}
