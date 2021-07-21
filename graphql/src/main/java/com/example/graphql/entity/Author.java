package com.example.graphql.entity;

import lombok.Data;

import java.util.List;

@Data
public class Author {

	private Integer id;

	private String name;

	private SexEnum sex;

	private List<Book> books;
}
