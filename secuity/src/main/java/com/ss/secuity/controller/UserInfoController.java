package com.ss.secuity.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @Author: xush
 * @Date: 2021-5-26
 * @Version: v1.0
 */
@Slf4j
@RestController
public class UserInfoController {

    @GetMapping("/user/flux")
    public Flux<Principal> userInfoFlux(Principal principal){

        return Flux.just(principal);
    }

    @Autowired
    private KeyPair keyPair;

    @GetMapping("/rsa/publicKey")
    public Map<String, Object> getKey() {

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        log.info("****************publicKey****************");
        log.info("publicKey: {}",publicKey);
        return new JWKSet(key).toJSONObject();
    }
}
