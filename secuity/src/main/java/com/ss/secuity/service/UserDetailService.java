package com.ss.secuity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: xush
 * @Date: 2021-5-26
 * @Version: v1.0
 */
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return User.withUsername("aaa")
                .password(passwordEncoder.encode("123"))
                .authorities("admin")
                .roles("admin")
                .build();
    }
}
