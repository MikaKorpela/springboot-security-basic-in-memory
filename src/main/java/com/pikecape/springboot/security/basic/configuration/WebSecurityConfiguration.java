package com.pikecape.springboot.security.basic.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
