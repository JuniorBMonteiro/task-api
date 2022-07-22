package br.com.bmont.task.controller;

import br.com.bmont.task.request.UserRequest;
import br.com.bmont.task.response.UserResponse;
import br.com.bmont.task.service.UserService;
import br.com.bmont.task.util.UserCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void registerUser_ReturnsUserResponse_WhenSuccessful(){
        UserRequest userRequest = UserCreator.createUserRequest();
        UserResponse expectedUser = UserCreator.createUserResponse();

        BDDMockito.when(userService.registerUser(ArgumentMatchers.any(UserRequest.class)))
                .thenReturn(expectedUser);

        ResponseEntity<UserResponse> userResponse = userController.registerUser(userRequest);
        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(expectedUser, userResponse.getBody());
    }
}