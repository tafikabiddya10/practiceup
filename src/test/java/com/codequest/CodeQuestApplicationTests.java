package com.codequest;

import com.codequest.domain.Role;
import com.codequest.domain.User;
import com.codequest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CodeQuestApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Context loads and seeded demo accounts exist")
    void contextLoadsAndSeedDataCreated() {
        // Verify default demo accounts created by DemoDataSeeder
        assertTrue(userRepository.existsByEmail("teacher@codequest.com"), "Default teacher should exist");
        assertTrue(userRepository.existsByEmail("student@codequest.com"), "Default student should exist");

        User teacher = userRepository.findByEmail("teacher@codequest.com").orElseThrow();
        assertEquals(Role.ROLE_TEACHER, teacher.getRole());
        assertTrue(passwordEncoder.matches("password123", teacher.getPasswordHash()), "Password should match BCrypt hash");

        User student = userRepository.findByEmail("student@codequest.com").orElseThrow();
        assertEquals(Role.ROLE_STUDENT, student.getRole());
    }
}
