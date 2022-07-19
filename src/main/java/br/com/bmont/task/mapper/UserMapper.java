package br.com.bmont.task.mapper;

import br.com.bmont.task.model.User;
import br.com.bmont.task.repository.UserRepository;
import br.com.bmont.task.response.UserResponse;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
