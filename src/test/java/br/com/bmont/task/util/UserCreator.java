package br.com.bmont.task.util;

import br.com.bmont.task.model.User;
import br.com.bmont.task.request.UserRequest;
import br.com.bmont.task.response.UserResponse;

public class UserCreator {
    public static User createUserWithoutId(){
        User user = new User();
        user.setUsername("userTeste");
        user.setEmail("user@teste.com");
        user.setPassword("teste");
        user.setAuthorities("USER");
        return user;
    }

    public static User createUserWithId(){
        return new User(1L, "userTeste", "user@teste.com",
                "teste", "USER");
    }

    public static UserRequest createUserRequest() {
        return new UserRequest("userTeste", "user@teste.com", "teste");
    }

    public static UserResponse createUserResponse() {
        return new UserResponse(1L, "userTeste", "user@teste.com");
    }
}
