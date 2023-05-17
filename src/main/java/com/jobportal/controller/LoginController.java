/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.controller;

import com.jobportal.entity.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author aravind
 */

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password");
        }

        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        if (authUser.isAuthenticated() && !(authUser instanceof AnonymousAuthenticationToken)) {
            System.out.println("User already logged in" + authUser.getName());

            for (GrantedAuthority auth : authUser.getAuthorities()) {
                return auth.getAuthority().equals(User.UserType.JOB_SEEKER.toString())
                    ? "redirect:/job_seeker"
                    : "redirect:/job_recruiter";
            }
        }

        return "login";
    }
}
