package com.training.security.repository;

import com.training.security.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    AppUser findByUsername(String username);

    AppUser save(AppUser user);

}
