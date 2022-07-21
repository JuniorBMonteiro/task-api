package br.com.bmont.task.util;

import br.com.bmont.task.model.User;

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
}
