# springboot-security-basic-in-memory
Spring Boot basic authentication with custom configuration; using in-memory storage.

## Add Dependencies

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## Enable Web Security

Web security is enabled in custom configuration.

## Create Custom Configuration

This custom configuration:
* contains two user accounts with different roles.
* Allows access to ```/api/hello``` end-point only to users having ```ADMIN``` role.

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder()
      .username("user2")
      .password("password2")
      .roles("USER")
      .build();

    UserDetails admin = User.withDefaultPasswordEncoder()
      .username("user3")
      .password("password3")
      .roles("ADMIN", "USER")
      .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.authorizeRequests(authorizeRequest -> {
        authorizeRequest.antMatchers("/").permitAll();
        authorizeRequest.antMatchers("/api/hello").hasRole("ADMIN");
      })
      .formLogin(withDefaults());

    return  http.build();
  }
}
```
