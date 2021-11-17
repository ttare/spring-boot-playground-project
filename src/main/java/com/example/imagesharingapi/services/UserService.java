package com.example.imagesharingapi.services;

import com.example.imagesharingapi.exceptions.EmailExistsException;
import com.example.imagesharingapi.models.MyUserDetails;
import com.example.imagesharingapi.models.dao.*;
import com.example.imagesharingapi.models.dto.CreateUser;
import com.example.imagesharingapi.models.dto.Credentials;
import com.example.imagesharingapi.models.dto.Suggestion;
import com.example.imagesharingapi.repositories.UserRepository;
import com.example.imagesharingapi.config.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final RoleService roleService;

    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, VerificationTokenService verificationTokenService, JwtTokenUtil jwtTokenUtil, RoleService roleService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.verificationTokenService = verificationTokenService;
        this.roleService = roleService;
    }

    public User login(Credentials credentials) throws BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

        if (!myUserDetails.getUser().isActive()) {
            throw new BadCredentialsException("Inactive account");
        }

        return myUserDetails.getUser();
    }


    public boolean userAlreadyExist(String email, String username) {
        return userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(email, username).isPresent();
    }

    public void sendUserActivationEmail(User user) {
        emailService.send(user.getEmail(),
                "Activation email",
                "Click to activate: http://localhost:8080/api/users/verify?token=" + user.getVerificationToken().getToken()
        );
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(CreateUser createUser) {
        if (userAlreadyExist(createUser.getEmail(), createUser.getUsername())) {
            throw new EmailExistsException();
        }
        User user = new User();
        user.setName(createUser.getName());
        user.setUsername(createUser.getUsername());
        user.setEmail(createUser.getEmail());
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpiryDate();
        user.setVerificationToken(verificationToken);

        Role role = roleService.findByName(Role.USER).orElse(new Role(Role.USER));
        user.getRoles().add(role);


        Album album = new Album("Default Album");
        album.setUser(user);
        user.getAlbums().add(album);

        User created = save(user);

//        sendUserActivationEmail(created);

        return created;
    }

    public boolean verify(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenService.findByToken(token);

        if (verificationToken.isPresent()) {
            User user = verificationToken.get().getUser();

            if (user.isActive()) {
                return false;
            }

            user.setActive(true);

            userRepository.save(user);

            return true;
        }

        return false;
    }

    public User me() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = myUserDetails.getUser();
        user.setAlbums(null);
        return myUserDetails.getUser();
    }

    public User update(Long id, CreateUser updateUser) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = myUserDetails.getUser();
        if (user.getId().equals(id)) {
            if(updateUser.getEmail() != null) {
                user.setEmail(updateUser.getEmail());
            }

            if(updateUser.getUsername() != null) {
                user.setUsername(updateUser.getUsername());
            }

            if(updateUser.getName() != null) {
                user.setName(updateUser.getName());
            }

            if(updateUser.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            }
            return userRepository.save(user);
        }
        return null;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public Set<User> findAllByIdIsIn(Long[] ids) {
        return userRepository.findAllByIdIsIn(ids);
    }

    public Set<Suggestion> findAllByQuery(String query) {
        return userRepository.findAllByNameIgnoreCaseIsContaining(query)
                .stream()
                .map(Suggestion::new)
                .collect(Collectors.toSet());
    }

    public Optional<User> details(Long id) {
        return userRepository.findById(id);
    }
}