package br.com.bmont.task.repository;

import br.com.bmont.task.model.User;
import br.com.bmont.task.util.UserCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void findByUsername_ReturnsUser_WhenSuccessful(){
        User userSaved = userRepository.save(UserCreator.createUserWithoutId());
        User userFound = userRepository.findByUsername(userSaved.getUsername()).get();
        Assertions.assertNotNull(userFound);
        Assertions.assertNotNull(userFound.getId());
        Assertions.assertEquals(userFound, userFound);
    }

    @Test
    void findByUsernameOrEmail_ReturnsUser_WhenSuccessful(){
        User userSaved = userRepository.save(UserCreator.createUserWithoutId());
        User userFoundByUsername = userRepository.findByUsernameOrEmail(userSaved.getUsername(), "").get();
        User userFoundByEmail = userRepository.findByUsernameOrEmail("", userSaved.getEmail()).get();
        Assertions.assertNotNull(userFoundByUsername);
        Assertions.assertNotNull(userFoundByEmail);
        Assertions.assertNotNull(userFoundByUsername.getId());
        Assertions.assertNotNull(userFoundByEmail.getId());
        Assertions.assertEquals(userSaved, userFoundByUsername);
        Assertions.assertEquals(userFoundByUsername, userFoundByEmail);
    }

    @Test
    void saveUser_ReturnsUser_WhenSuccessful(){
        User user = UserCreator.createUserWithoutId();
        User userSaved = userRepository.save(user);
        Assertions.assertNotNull(userSaved);
        Assertions.assertNotNull(userSaved.getId());
        Assertions.assertEquals(user.getUsername(), userSaved.getUsername());
    }
}