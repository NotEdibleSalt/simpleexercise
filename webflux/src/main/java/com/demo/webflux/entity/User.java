package com.demo.webflux.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String id;

    private String name;

    private int age;
}
