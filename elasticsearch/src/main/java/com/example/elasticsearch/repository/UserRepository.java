package com.example.elasticsearch.repository;

import com.example.elasticsearch.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: xush
 * @Date: 2020-12-21
 * @Version: v1.0
 */
public interface UserRepository extends ElasticsearchRepository<UserEntity, String> {
}
