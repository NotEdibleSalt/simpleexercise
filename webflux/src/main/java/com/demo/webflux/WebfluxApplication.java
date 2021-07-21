package com.demo.webflux;

import com.demo.webflux.route.UserRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootApplication
public class WebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

	@Bean
	public RouterFunction routerFunctionA() {

		return new UserRoute().routerFunction();
	}
}
