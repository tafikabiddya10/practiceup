package com.codequest.common.seed;

import com.codequest.domain.Role;
import com.codequest.domain.User;
import com.codequest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Idempotent Seed Runner:
 * Inserts default demo accounts ONLY when the users table is completely empty.
 * Creates:
 * 1. Default Teacher: teacher@codequest.com / password123
 * 2. Default Student: student@codequest.com / password123
 *
 * Feature content (classrooms, castles, floors, obstacles, assessments, hints)
 * is NOT seeded here — those feature modules are pending implementation in
 * upcoming sprints (see README.md).
 */
@Component
public class DemoDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DemoDataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            // Idempotent guard: Database already initialized
            return;
        }

        // 1. Create Default Teacher
        userRepository.save(new User(
                "Professor Ada Lovelace",
                "teacher@codequest.com",
                passwordEncoder.encode("password123"),
                Role.ROLE_TEACHER
        ));

        // 2. Create Default Student
        userRepository.save(new User(
                "Student Alex",
                "student@codequest.com",
                passwordEncoder.encode("password123"),
                Role.ROLE_STUDENT
        ));
    }
}
