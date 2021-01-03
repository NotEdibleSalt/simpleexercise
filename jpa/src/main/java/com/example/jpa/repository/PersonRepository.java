package com.example.jpa.repository;

import com.example.jpa.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: xush
 * @Date: 2021-1-2
 * @Version: v1.0
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

    List<PersonEntity> findByAgeGreaterThan(Integer age);

    Streamable<PersonEntity> findByAgeBetween(Integer start, Integer end);

    @Async
    CompletableFuture<List<PersonEntity>> findByNameContaining(String con);

    @Query("from PersonEntity where isSuccess = :b")
    List<PersonEntity> queryJpql(@Param("b") boolean b);

}
