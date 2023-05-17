/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jobportal.security;

import com.jobportal.entity.User;

import java.util.Collection;
import java.util.Optional;

import com.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 *
 * @author aravind
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String user_email) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername called! " + user_email);
        Optional<User> user = userService.getUserByEmail(user_email);

        if (user.isEmpty()) {
            System.out.println("Throwing User not found! " + user_email);
            throw new UsernameNotFoundException("User with" + user_email + " is not found!");
        }

        return new org.springframework.security.core.userdetails.User(
            user.get().getEmail(),
            user.get().getPassword(),
            getAuthorities(user.get())
        );
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        String userType = String.valueOf(user.getUserType());

        return AuthorityUtils.createAuthorityList(userType);
    }

}
