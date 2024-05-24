package com.training.security.configuration;

import com.training.security.entity.AppUser;
import com.training.security.entity.RoleType;
import com.training.security.repository.AppUserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        AppUser admin = new AppUser("admin", passwordEncoder.encode("test"), RoleType.ADMIN);
        AppUser editor = new AppUser("editor", passwordEncoder.encode("test"), RoleType.EDITOR);
        AppUser user = new AppUser("user", passwordEncoder.encode("test"), RoleType.USER);
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
