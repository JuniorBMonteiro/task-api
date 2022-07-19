package br.com.bmont.task.service;

import br.com.bmont.task.mapper.UserMapper;
import br.com.bmont.task.model.User;
import br.com.bmont.task.repository.UserRepository;
import br.com.bmont.task.request.UserRequest;
import br.com.bmont.task.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public UserResponse registerUser(UserRequest userRequest){
        validateCredentials(userRequest);
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user.setAuthorities("USER");
        User userSaved = userRepository.save(user);
        return UserMapper.toUserResponse(userSaved);
    }

    private void validateCredentials(UserRequest user){
        Optional<User> userSaved= userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        userSaved.ifPresent( e -> {
            throw new RuntimeException("User is already registered");
        });
    }
}
