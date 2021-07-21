package com.demo.webflux.handle;

import com.demo.webflux.entity.User;
import com.demo.webflux.validator.UserValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

/**
 * @Author: xush
 * @Date: 2021-6-1
 * @Version: v1.0
 */
public class UserHandle {

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");
        User user = User.builder()
                .id(id)
                .name("张三")
                .age(18)
                .build();

        return ServerResponse.ok().bodyValue(user);
    }


    public Mono<ServerResponse> addUser(ServerRequest serverRequest) {

        Mono<User> userMono = serverRequest.bodyToMono(User.class)
                .doOnNext(this::validate).map(user -> {
                    user.setAge(user.getAge() + 12);
                    return user;
                });

        return ServerResponse.ok().body(userMono, User.class);
    }

    private void validate(User user) {

        Validator validator = new UserValidator();
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
