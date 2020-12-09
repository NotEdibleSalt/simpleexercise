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
## 配置Security，设置用户信息
~~~java
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService());
    }

    @Bean
    public UserDetailsService myUserDetailsService() {

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        // 配置用户名,密码, 角色
        String[][] usersGroupsAndRoles = {
                {"salaboy", "1", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"ryandawsonuk", "1", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"erdemedeiros", "1", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"other", "1", "ROLE_ACTIVITI_USER", "GROUP_otherTeam"},
                {"jingli", "1", "ROLE_ACTIVITI_JL", "GROUP_JL"},
                {"renshi", "1", "ROLE_ACTIVITI_RS", "GROUP_RS"},
                {"admin", "1", "ROLE_ACTIVITI_ADMIN"},
        };

        for (String[] user : usersGroupsAndRoles) {
            List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
            log.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings + "]");

            inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
        }

        return inMemoryUserDetailsManager;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/login").permitAll() // 允许login请求
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
~~~

## 测试方式
 1. 先请求/login接口，将用户信息放入到springSecurity管理中，否则请求会出现未授权异常

## 报错
1. 启动流程时出现：org.activiti.api.runtime.shared.UnprocessableEntityException: Process definition with the given id:'zx:1:70693c00-39e9-11eb-83a7-00ff1ec9ac8e' belongs to a different application version.
解决方式：
1. 在resources目录下创建default-project.json文件
~~~json
{
  "createdBy": "superadminuser",
  "creationDate": "2019-08-16T15:58:46.056+0000",
  "lastModifiedBy": "qa-modeler-1",
  "lastModifiedDate": "2019-08-16T16:03:41.941+0000",
  "id": "c519a458-539f-4385-a937-2edfb4045eb9",
  "name": "projectA",
  "description": "",
  "version": "1"
}
~~~
2. 在application.yml文件中添加如下内容
~~~yml
project:
  manifest:
    file:
      path: classpath:/default-project.json
~~~




