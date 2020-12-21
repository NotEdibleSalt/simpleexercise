package com.example.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Author: xush
 * @Date: 2020-12-21
 * @Version: v1.0
 */
@Data
@Document(indexName = "userentity")
public class UserEntity {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String sex;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Boolean)
    private boolean isSuccess;

    @Field(type = FieldType.Date ,format = DateFormat.date)
    private Date birthday;

}
