package com.codequest.common.security;

import com.codequest.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Handles post-login routing and streak updates:
 * - Redirects every authenticated user to the placeholder dashboard (/dashboard)
 * - Updates daily login streak
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        userRepository.findByEmail(email).ifPresent(user -> {
            LocalDate today = LocalDate.now();
            LocalDate lastLogin = user.getLastLoginDate();
            if (lastLogin == null) {
                user.setLoginStreak(1);
            } else if (lastLogin.plusDays(1).isEqual(today)) {
                user.setLoginStreak(user.getLoginStreak() + 1);
            } else if (!lastLogin.isEqual(today)) {
                user.setLoginStreak(1);
            }
            user.setLastLoginDate(today);
            userRepository.save(user);
        });

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
