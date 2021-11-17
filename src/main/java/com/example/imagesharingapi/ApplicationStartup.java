package com.example.imagesharingapi;

import com.example.imagesharingapi.models.dao.Role;
import com.example.imagesharingapi.models.dao.User;
import com.example.imagesharingapi.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    public ApplicationStartup(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        seedData();
    }

    private void seedData() {
        String username = mailUsername.split("@")[0];
        if (!userService.userAlreadyExist(mailUsername, username)) {
            User user = new User();
            user.setUsername(username);
            user.setEmail(mailUsername);
            user.setName("Image sharing admin");
            user.setPassword(passwordEncoder.encode(mailPassword));
            user.setActive(true);
            user.setEnabled(true);

            Role adminRole = new Role(Role.ADMIN);
            Role userRole = new Role(Role.USER);
            user.getRoles().addAll(Arrays.asList(adminRole, userRole));

            userService.save(user);
        }
    }
}


