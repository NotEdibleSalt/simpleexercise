package com.example.elasticsearch.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.elasticsearch.entity.UserEntity;
import com.example.elasticsearch.repository.UserRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/**
 * @Author: xush
 * @Date: 2020-12-21
 * @Version: v1.0
 */
@RestController
public class UserController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private UserRepository userRepository;

    /**
     * 通过UserRepository#findById方法查询数据
     *
     * @param id:
     * @return UserEntity
     * @Date 2020-12-21
     **/
    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable("id") String id){

        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        return userEntityOptional.orElseGet(null);
    }

    /**
     * 通过UserRepository#save方法保存数据
     *
     * @param userEntity:
     * @return UserEntity
     * @Date 2020-12-21
     **/
    @PostMapping("/save")
    public UserEntity saveUser(@RequestBody UserEntity userEntity){

        userEntity.setAge(RandomUtil.randomInt(10, 90));
        userEntity.setSuccess(RandomUtil.randomBoolean());
        userEntity.setBirthday(new Date());
        return userRepository.save(userEntity);
    }

    /**
     *  通过UserRepository#deleteById方法删除数据
     *
     * @param id:
     * @return boolean
     * @Date 2020-12-21
     **/
    @DeleteMapping("/{id}")
    public boolean delUser(@PathVariable("id") String id){

        userRepository.deleteById(id);
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        return userEntityOptional.isPresent();
    }

    /**
     * 通过ElasticsearchRestTemplate#get方法查询数据
     *
     * @param id:
     * @return UserEntity
     * @Date 2020-12-21
     **/
    @GetMapping("template/{id}")
    public UserEntity get(@PathVariable("id") String id){

        return elasticsearchRestTemplate.get(id, UserEntity.class);
    }


    /**
     * 通过ElasticsearchRestTemplate#search方法查询数据
     * 查询参数为string类型
     *
     * @param param:
     * @return SearchHits<UserEntity>
     * @Date 2020-12-21
     **/
    @GetMapping("template/stringQuery")
    public SearchHits<UserEntity> stringQuery(@RequestParam("param") String param){

        String queryCondition = "{\n" +
                "    \"match\": {\n" +
                "      \"name\": {\n" +
                "        \"query\": \"" + param + "\"\n" +
                "      }\n" +
                "    }\n" +
                "  }";
        Query query = new StringQuery(queryCondition);

        SearchHits<UserEntity> searchHits = elasticsearchRestTemplate.search(query, UserEntity.class);
        return searchHits;
    }

    /**
     * 通过ElasticsearchRestTemplate#search方法查询数据
     * 查询参数为Native类型
     *
     * @return SearchHits<UserEntity>
     * @Date 2020-12-21
     **/
    @GetMapping("template/nativeSearch")
    public SearchHits<UserEntity> nativeSearch(){


        QueryBuilder queryBuilder = new MatchQueryBuilder("name", "小");
        Query query = new NativeSearchQuery(queryBuilder);

        SearchHits<UserEntity> searchHits = elasticsearchRestTemplate.search(query, UserEntity.class);
        return searchHits;
    }

    /**
     * 通过ElasticsearchRestTemplate#search方法查询数据
     * 查询参数为Criteria类型
     *
     * @return SearchHits<UserEntity>
     * @Date 2020-12-21
     **/
    @GetMapping("template/criteriaQuery")
    public SearchHits<UserEntity> criteriaQuery(){

        Criteria criteria = new Criteria("sex").is("女");
        Query query = new CriteriaQuery(criteria);

        SearchHits<UserEntity> searchHits = elasticsearchRestTemplate.search(query, UserEntity.class);
        return searchHits;
    }

    /**
     * 通过ElasticsearchRestTemplate#save方法保存数据
     * index(索引) 为实体类上注解标识的索引
     *
     * @return SearchHits<UserEntity>
     * @Date 2020-12-21
     **/
    @PostMapping("template/save")
    public UserEntity saveUserEntity(@RequestParam("name") String name){

        UserEntity userEntity = new UserEntity();
        userEntity.setId(IdUtil.simpleUUID());
        userEntity.setName(name);
        userEntity.setAge(RandomUtil.randomInt(10, 90));
        userEntity.setSuccess(RandomUtil.randomBoolean());
        userEntity.setBirthday(new Date());

        return elasticsearchRestTemplate.save(userEntity);
    }

    /**
     * 通过ElasticsearchRestTemplate#delete方法删除数据
     *
     *
     * @return SearchHits<UserEntity>
     * @Date 2020-12-21
     **/
    @DeleteMapping("template/{id}")
    public String delUserEntity(@PathVariable("id") String id){

        String delete = elasticsearchRestTemplate.delete(id, UserEntity.class);
        return delete;
    }

    /**
     * 通过ElasticsearchRestTemplate#exists方法查询数据是否存在
     *
     *
     * @return SearchHits<UserEntity>
     * @Date 2020-12-21
     **/
    @GetMapping("template/exists/{id}")
    public boolean exists(@PathVariable("id") String id){

        return elasticsearchRestTemplate.exists(id, UserEntity.class);
    }


}
