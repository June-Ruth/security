package com.training.security.configuration;

import com.training.security.entity.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.core.userdetails.User.builder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin").hasRole(RoleType.ADMIN)
                        .requestMatchers("/editor").hasRole(RoleType.EDITOR)
                        .requestMatchers("/user").hasRole(RoleType.USER)
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = builder()
                .username("admin")
                .password(passwordEncoder().encode("test"))
                .roles(RoleType.ADMIN, RoleType.EDITOR, RoleType.USER).build();
        UserDetails editor = builder()
                .username("editor")
                .password(passwordEncoder().encode("test"))
                .roles(RoleType.EDITOR, RoleType.USER).build();
        UserDetails user = builder()
                .username("user")
                .password(passwordEncoder().encode("test"))
                .roles(RoleType.USER).build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
