package com.training.security.resourceserver.configuration;

import com.training.security.authorizationserver.entity.PrivilegeType;
import com.training.security.authorizationserver.entity.RoleType;
import com.training.security.authorizationserver.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private CustomUserDetailsService customUserDetailsService;

    public SpringSecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/admin").hasRole(RoleType.ADMIN)
                        .requestMatchers("/editor").hasRole(RoleType.EDITOR)
                        .requestMatchers("/user").hasRole(RoleType.USER)
                        .requestMatchers("/read").hasAuthority(PrivilegeType.READ)
                        .requestMatchers("/write").hasAuthority(PrivilegeType.WRITE)
                        .requestMatchers("/delete").hasAuthority(PrivilegeType.DELETE)
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/default"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/home"))
                .rememberMe(remember -> remember
                        .alwaysRemember(true))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("sha256", new StandardPasswordEncoder());

        PasswordEncoder passwordEncoder =
                new DelegatingPasswordEncoder(idForEncode, encoders);

        return passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role(RoleType.ADMIN).implies(RoleType.EDITOR)
                .role(RoleType.EDITOR).implies(RoleType.USER)
                .build();
    }

}