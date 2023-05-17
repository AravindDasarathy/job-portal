/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.controller;

import com.jobportal.entity.Company;
import com.jobportal.entity.User;
import com.jobportal.service.UserService;
import com.jobportal.service.CompanyService;
import com.jobportal.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author aravind
 */
@Controller
public class SignupController {
    private final UserService userService;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    private final UserValidator userValidator;

    @Autowired
    public SignupController(
        UserService userService,
        CompanyService companyService,
        PasswordEncoder passwordEncoder,
        UserValidator userValidator) {
        this.userService = userService;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @GetMapping("/signup")
    public String showUserRegistrationPage(@ModelAttribute("user") User user, Model model) {
        List<Company> companies = companyService.getAllCompanies();
        System.out.println("Companies: " + companies);

        model.addAttribute("companies", companies);

        return "signup";
    }
    
    @PostMapping("/signup")
    public String handleUserRegistration(@ModelAttribute("user") User user, BindingResult result, Model model) {
        userValidator.validate(user, result);

        // check for validation errors, redirect to signup if any.
        if (result.hasErrors()) {
            List<Company> companies = companyService.getAllCompanies();
            model.addAttribute("companies", companies);
            model.addAttribute("errors", result.getAllErrors());

            return "signup";
        }

        String userPassword = user.getPassword();

        user.setPassword(passwordEncoder.encode(userPassword));

        if (user.getUserType() == User.UserType.JOB_SEEKER) {
            user.setCompany(null);
            userService.save(user);
            return "redirect:/job_seeker";
        }

        // when recruiter has selected company from the dropdown list.
        if (user.getCompany().getName().isBlank()
            && user.getCompany().getDescription().isBlank() && user.getCompany().getId() != -1) {
            Optional<Company> company = companyService.getCompanyById(user.getCompany().getId());

            if (company.isPresent()) {
                user.setCompany(company.get());
                userService.save(user);
            } else {
                List<Company> companies = companyService.getAllCompanies();
                model.addAttribute("companies", companies);
                model.addAttribute("errors", new ArrayList<String>() {{
                    add("Something went wrong! Please try again later.");
                }});

                return "signup";
            }

            return "redirect:/login";
        }

        // when recruiter has entered a new company.
        if (!user.getCompany().getName().isBlank() &&
            !user.getCompany().getDescription().isBlank() && user.getCompany().getId() == -1) {
            Company newCompany = new Company();
            newCompany.setName(user.getCompany().getName());
            newCompany.setDescription(user.getCompany().getDescription());

            System.out.println("before saving new company: " + newCompany);
            companyService.save(newCompany);

            Optional<Company> newCompanyFromDb = companyService.getCompanyByName(newCompany.getName());

            System.out.println("after saving new company: " + newCompanyFromDb);

            newCompanyFromDb.ifPresent(user::setCompany);
            userService.save(user);

            return "redirect:/login";

        }

        user.setPassword("");

        return "signup";
    }
}
