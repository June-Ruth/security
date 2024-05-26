package com.training.security.authorizationserver.service;

import com.training.security.authorizationserver.entity.AppUser;
import com.training.security.authorizationserver.entity.Privilege;
import com.training.security.authorizationserver.entity.Role;
import com.training.security.authorizationserver.repository.AppUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    AppUserRepository userRepository;

    public CustomUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(getPermissions(user.getRole())));
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        return  permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private List<String> getPermissions(Collection<Role> roles) {
        List<String> permissions = new ArrayList<>();
        for (Role role : roles) {
            permissions.add("ROLE_" + role.getName());
            permissions.addAll(role.getPrivileges().stream().map(Privilege::getName).toList());
        }
        return permissions;
    }
}