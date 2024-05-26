package com.training.security.repository;

import com.training.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

    Role save(Role role);

}
