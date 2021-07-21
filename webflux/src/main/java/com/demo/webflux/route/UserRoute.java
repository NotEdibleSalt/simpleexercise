package com.demo.webflux.route;

import com.demo.webflux.handle.UserHandle;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * @Author: xush
 * @Date: 2021-6-1
 * @Version: v1.0
 */
public class UserRoute {

    private UserHandle userHandle = new UserHandle();

    public RouterFunction routerFunction() {

        return RouterFunctions.route()
                .GET("/getById/{id}", userHandle::getById)
                .POST("/addUser", userHandle::addUser)
                .build();
    }
}
