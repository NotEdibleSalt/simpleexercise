package com.example.kafka.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: xush
 * @Date: 2020-12-26
 * @Version: v1.0
 */
@Data
public class Person {

    private String id;

    private String name;

    private String sex;

    private Integer age;

    private boolean isSuccess;

    private Date birthday;

}
