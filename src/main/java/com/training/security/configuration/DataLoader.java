package com.training.security.configuration;

import com.training.security.entity.AppUser;
import com.training.security.entity.RoleType;
import com.training.security.repository.AppUserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!alreadySetup) {
            saveDefaultUser();
            alreadySetup = true;
        }
    }

    private void saveDefaultUser() {
        String adminPassword = passwordEncoder.encode("test");
        System.out.println("BCrypt Password : " + adminPassword);
        String editorPassword = "{noop}" + NoOpPasswordEncoder.getInstance().encode("test");
        System.out.println("No0p Password : " + editorPassword);
        String userPassword ="{sha256}" + new StandardPasswordEncoder().encode("test");
        System.out.println("Sha256 Password : " + userPassword);
        AppUser admin = new AppUser("admin", adminPassword, RoleType.ADMIN);
        AppUser editor = new AppUser("editor", editorPassword, RoleType.EDITOR);
        AppUser user = new AppUser("user", userPassword, RoleType.USER);
        saveUserIfNotExists(admin);
        saveUserIfNotExists(editor);
        saveUserIfNotExists(user);
    }

    private void saveUserIfNotExists(AppUser user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            userRepository.save(user);
        }
    }
}
