package com.learning.platform;

import com.learning.platform.domain.AppUser;
import com.learning.platform.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Fix the dummy passwords from V3 migration to valid BCrypt hashes for "password"
        
        Optional<AppUser> teacher = appUserRepository.findById(1L);
        if (teacher.isPresent()) {
            AppUser t = teacher.get();
            t.setPassword(passwordEncoder.encode("password"));
            appUserRepository.save(t);
        }

        Optional<AppUser> parent = appUserRepository.findById(2L);
        if (parent.isPresent()) {
            AppUser p = parent.get();
            p.setPassword(passwordEncoder.encode("password"));
            appUserRepository.save(p);
        }
    }
}
