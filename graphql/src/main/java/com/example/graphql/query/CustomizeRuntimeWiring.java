package com.example.graphql.query;

import com.example.graphql.entity.Author;
import com.example.graphql.entity.Book;
import com.example.graphql.entity.SexEnum;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author xsh
 * @version 1.0
 * @date 2021-7-12
 */
@Component
public class CustomizeRuntimeWiring implements RuntimeWiringBuilderCustomizer {

    @Override
    public void customize(RuntimeWiring.Builder builder) {

        builder.type("Query", typeWiring -> {

            // 查询greeting,返回hello
            typeWiring.dataFetcher("greeting", env -> "hello");

            // 查询作者
            typeWiring.dataFetcher("author", env -> {

                Integer id = Integer.valueOf(env.getArgument("id"));

                Author author = new Author();
                author.setId(id);
                author.setName("小明");
                author.setSex(SexEnum.man);

                Book book1 = new Book();
                book1.setBookName("无敌拳法十三式");
                book1.setPublish(false);
                book1.setPrice(new SecureRandom().nextFloat());

                Book book2 = new Book();
                book2.setBookName("独孤十三剑");
                book2.setPublish(true);
                book2.setPrice(new SecureRandom().nextFloat());

                author.setBooks(Arrays.asList(book1, book2));

                return author;
            });

            return typeWiring;
        });
    }
}
