package com.example.jpa.config;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.MySQL8Dialect;

/**
 * @Author: xush
 * @Date: 2021-1-2
 * @Version: v1.0
 */
public class MysqlDialectUtf8mb4Config extends MySQL5Dialect{

    /**
     * 设置建表时的存储引擎和字符集
     *
     * @return java.lang.String
     * @Date 2021-1-2
     **/
    @Override
    public String getTableTypeString() {

        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
    }
}
