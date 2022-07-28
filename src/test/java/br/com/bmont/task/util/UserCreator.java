package br.com.bmont.task.util;

import br.com.bmont.task.model.User;
import br.com.bmont.task.request.LoginRequest;
import br.com.bmont.task.request.UserRequest;
import br.com.bmont.task.response.UserResponse;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserCreator {
    public static User createUserWithoutId(){
        User user = new User();
        user.setUsername("userTeste");
        user.setEmail("user@teste.com");
        user.setPassword("teste123");
        user.setAuthorities("USER");
        return user;
    }

    public static User createUserWithId(){
        return new User(1L, "userTeste", "user@teste.com",
                "teste123", "USER");
    }

    public static User createUserEncode(){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User user = new User();
        user.setUsername("userTeste");
        user.setEmail("user@teste.com");
        user.setPassword(passwordEncoder.encode("teste123"));
        user.setAuthorities("USER");
        return user;
    }

    public static UserRequest createUserRequest() {
        return new UserRequest("userTeste", "user@teste.com", "teste123");
    }

    public static UserResponse createUserResponse() {
        return new UserResponse(1L, "userTeste", "user@teste.com");
    }

    public static LoginRequest createLoginRequest(){
        return new LoginRequest("userTeste", "teste123");
    }
}
