package com.pikecape.springboot.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
