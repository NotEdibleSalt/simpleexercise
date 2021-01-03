package com.example.jpa.repository;

import com.example.jpa.entity.PersonEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest{

    @Resource
    private PersonRepository personRepository;

    @Test
    public void save(){

        PersonEntity personEntity = new PersonEntity();
        personEntity.setName("张三");
        personEntity.setAge(23);
        personEntity.setSex("男");
        personEntity.setSuccess(true);
        personEntity.setBirthday(new Date());

        PersonEntity save = personRepository.save(personEntity);
        System.out.println("*******************************");
        System.out.println(save);
    }

    @Test
    public void del(){

        personRepository.deleteById(2);
        Optional<PersonEntity> personEntityOptional = personRepository.findById(2);
        System.out.println("删除后：" + personEntityOptional);
    }

    @Test
    public void update(){

        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(5);
        personEntity.setName("张翠花");
        personEntity.setAge(18);
        personEntity.setSex("女");
        personEntity.setSuccess(false);

        PersonEntity save = personRepository.save(personEntity);
        System.out.println("*******************************");
        System.out.println(save);
    }

    @Test
    public void namedQuery(){

        List<PersonEntity> personEntityList = personRepository.findByAgeGreaterThan(10);
        System.out.println(personEntityList);
    }

    @Test
    public void stream(){

        Streamable<PersonEntity> personEntities = personRepository.findByAgeBetween(12, 60);
        List<PersonEntity> personEntityList = personEntities.stream().collect(Collectors.toList());

        System.out.println(personEntityList);
    }

    @Test
    public void completableFuture() throws ExecutionException, InterruptedException {

        CompletableFuture<List<PersonEntity>> future = personRepository.findByNameContaining("张");

        System.out.println("isCancelled: " + future.isCancelled());
        System.out.println("isDone: " + future.isDone());

        future.thenAccept(System.out::println);
    }

    @Test
    public void jpql(){

        List<PersonEntity> entityList = personRepository.queryJpql(true);
        System.out.println(entityList);

    }
}