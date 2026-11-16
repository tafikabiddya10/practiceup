package com.codequest.common.web;

import com.codequest.common.web.form.RegisterForm;
import com.codequest.domain.Role;
import com.codequest.domain.User;
import com.codequest.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for public routes (Landing, Login, Register) and the post-login
 * placeholder dashboard.
 */
@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.teacher-code:TEACH-2026}")
    private String configuredTeacherCode;

    public HomeController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String index(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "home/index";
    }

    /**
     * Placeholder dashboard shown after login. All feature modules are pending
     * implementation in upcoming sprints (see README.md section 2).
     */
      @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        userRepository.findByEmail(authentication.getName())
                .ifPresent(user -> model.addAttribute("user", user));

        boolean isTeacher = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));

        return isTeacher ? "teacher/dashboard" : "student/dashboard";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("registerForm") RegisterForm form,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match");
            return "auth/register";
        }

        if (userRepository.existsByEmail(form.getEmail().trim().toLowerCase())) {
            bindingResult.rejectValue("email", "error.email", "An account with this email already exists");
            return "auth/register";
        }

        Role selectedRole;
        try {
            selectedRole = Role.valueOf("ROLE_" + form.getRole().toUpperCase());
        } catch (Exception ex) {
            bindingResult.rejectValue("role", "error.role", "Invalid role selected");
            return "auth/register";
        }

        // Validate Teacher Registration Code if role is TEACHER
        if (selectedRole == Role.ROLE_TEACHER) {
            if (form.getTeacherCode() == null || !form.getTeacherCode().trim().equals(configuredTeacherCode)) {
                bindingResult.rejectValue("teacherCode", "error.teacherCode", "Invalid teacher registration code");
                return "auth/register";
            }
        }

        User newUser = new User();
        newUser.setName(form.getName().trim());
        newUser.setEmail(form.getEmail().trim().toLowerCase());
        newUser.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        newUser.setRole(selectedRole);

        userRepository.save(newUser);

        redirectAttributes.addFlashAttribute("successMessage", "Account created successfully! Please log in.");
        return "redirect:/login";
    }
}
