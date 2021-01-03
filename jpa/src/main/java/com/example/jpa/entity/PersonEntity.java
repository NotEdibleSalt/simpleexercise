package com.example.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: xush
 * @Date: 2021-1-2
 * @Version: v1.0
 */
@Data
@Entity
@Table(name = "persion")
public class PersonEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String sex;

    @Column
    private Integer age;

    @Column
    private boolean isSuccess;

    @Column
    private Date birthday;

}
