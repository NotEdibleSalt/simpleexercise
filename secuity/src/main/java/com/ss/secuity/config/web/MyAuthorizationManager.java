package com.ss.secuity.config.web;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @Author: xush
 * @Date: 2021-5-27
 * @Version: v1.0
 */
@Component
public class MyAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {

        return Mono.just(new AuthorizationDecision(false));
    }
}
