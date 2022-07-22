package br.com.bmont.task.service;

import br.com.bmont.task.exception.EntityAlreadyExistsException;
import br.com.bmont.task.model.User;
import br.com.bmont.task.repository.UserRepository;
import br.com.bmont.task.request.UserRequest;
import br.com.bmont.task.response.UserResponse;
import br.com.bmont.task.util.UserCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init(){
        User user = UserCreator.createUserWithId();

        BDDMockito.when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(user));

       BDDMockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
               .thenReturn(user);

        BDDMockito.when(userRepository.findByUsernameOrEmail(ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
    }

    @Test
    void loadUserByUsername_ReturnsUserDetails_WhenSuccessful(){
        User expectedUser = UserCreator.createUserWithId();
        User user = (User) userService.loadUserByUsername("teste");
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(expectedUser, user);
    }

    @Test
    void registerUser_ReturnsUser_WhenSuccessful(){
        User expectedUser = UserCreator.createUserWithId();
        UserResponse userSaved = userService.registerUser(UserCreator.createUserRequest());
        Assertions.assertNotNull(userSaved);
        Assertions.assertEquals(expectedUser.getId(), userSaved.getId());
        Assertions.assertEquals(expectedUser.getUsername(), userSaved.getUsername());
    }

    @Test
    void validateCredentials_ThrowsEntityAlreadyExistsException_WhenCredentialsIsAlreadyRegistered(){
        User user = UserCreator.createUserWithId();
        BDDMockito.when(userRepository.findByUsernameOrEmail(ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(user));
        UserRequest userRequest = UserCreator.createUserRequest();
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> userService.registerUser(userRequest));
    }
}