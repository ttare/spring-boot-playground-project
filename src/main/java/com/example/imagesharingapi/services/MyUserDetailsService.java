package com.example.imagesharingapi.services;

import com.example.imagesharingapi.models.MyUserDetails;
import com.example.imagesharingapi.models.dao.User;
import com.example.imagesharingapi.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(username, username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        MyUserDetails myUserDetails = new MyUserDetails(user.get());
        myUserDetails.setAuthorities(user.get().getRoles());

        return myUserDetails;
    }
}
