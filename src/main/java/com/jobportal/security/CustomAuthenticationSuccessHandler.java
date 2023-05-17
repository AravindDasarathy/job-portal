package com.jobportal.security;

import com.jobportal.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (auth.getAuthority().equals(User.UserType.JOB_SEEKER.toString())) {
                response.sendRedirect("/job_seeker");
            } else {
                response.sendRedirect("/job_recruiter");
            }
        }
    }
}
