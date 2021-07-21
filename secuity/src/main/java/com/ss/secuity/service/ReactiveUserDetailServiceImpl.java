package com.ss.secuity.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @Author: xush
 * @Date: 2021-5-27
 * @Version: v1.0
 */
@Service
public class ReactiveUserDetailServiceImpl implements ReactiveUserDetailsService {

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return null;
    }

}
