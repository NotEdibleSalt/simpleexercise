# spring boot整合activiti7

## 引入依赖
关键依赖
~~~xml
    <!--activiti依赖-->
    <dependency>
        <groupId>org.activiti</groupId>
        <artifactId>activiti-spring-boot-starter</artifactId>
        <version>7.1.0.M6</version>
    </dependency>
    <!--activiti7强依赖spring-security-->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-config</artifactId>
        <version>5.3.2.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.security.oauth</groupId>
        <artifactId>spring-security-oauth2</artifactId>
        <version>2.3.4.RELEASE</version>
    </dependency>
~~~
## 错误
1. 启动时报错



