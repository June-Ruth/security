package com.training.security.authorizationserver.repository;

import com.training.security.authorizationserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

    Role save(Role role);

}
