package com.controller;

import com.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping("login")
    public void login(@RequestParam("username") String username){

        securityUtil.logInAs(username);
    }
}
