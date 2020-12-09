/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService());
    }

    @Bean
    public UserDetailsService myUserDetailsService() {

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        String[][] usersGroupsAndRoles = {
                {"salaboy", "1", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"ryandawsonuk", "1", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"erdemedeiros", "1", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"other", "1", "ROLE_ACTIVITI_USER", "GROUP_otherTeam"},
                {"jingli", "1", "ROLE_ACTIVITI_JL", "GROUP_JL"},
                {"renshi", "1", "ROLE_ACTIVITI_RS", "GROUP_RS"},
                {"admin", "1", "ROLE_ACTIVITI_ADMIN"},
        };

        for (String[] user : usersGroupsAndRoles) {
            List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
            log.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings + "]");

            inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
        }

        return inMemoryUserDetailsManager;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/login", "/login/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
