# springboot-security-basic-in-memory #

The `spring-boot-starter-security` dependency enables the authentication and authorization in all end-points.

The configurations override the default user creation; see `springboot-security-basic-properties`; and the default `SecurityFilterChain`.

```java
@Configuration
public class WebSecurityConfiguration {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(requests -> requests
        .requestMatchers("/api/duey").hasRole("USER")
        .requestMatchers("/api/huey").authenticated()
        .requestMatchers("/api/luey").permitAll());

    httpSecurity.formLogin(Customizer.withDefaults());
    httpSecurity.httpBasic(Customizer.withDefaults());

    return httpSecurity.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails donald = User.withUsername("donald")
        .password("{bcrypt}$2a$12$gnGgQDZOcqZ/nCCPSSGXOOcgHSkI3pvyAGYfpFqWx5c74RrumWV7e")
        .authorities("ROLE_USER")
        .build();

    UserDetails fethry = User.withUsername("fethry")
        .password("{bcrypt}$2a$12$gnGgQDZOcqZ/nCCPSSGXOOcgHSkI3pvyAGYfpFqWx5c74RrumWV7e")
        .build();

    return new InMemoryUserDetailsManager(donald, fethry);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
```

## Security Filter Chain ##

The `SecurityFilterChain` is a bean that configures security settings for each end-point:
- `/api/duey` is accessible only for users with `ROLE_USER` role
- `/api/huey` is accessible for authenticated users
- `/api/luey` is accessible for all users

The `httpSecurity.formLogin()` and `httpSecurity.httpBasic()` methods enable form-based and basic authentication.

## User Details Service ##

The `UserDetailsService` is a bean that creates two users:
- `donald` with `ROLE_USER` role
- `fethry` without any role

## Password Encoder ##

The `PasswordEncoder` is a bean that creates a password encoder that uses the bcrypt algorithm.

The bcrypt is basically the default password encoder in Spring Security.

The password can be encoded in https://bcrypt-generator.com/ for testing purposes.
