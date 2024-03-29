package br.com.bmont.task.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRequest {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 50, message = "Username length cannot be less than 4 or greater than 50")
    private String username;
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password length cannot be less than 6")
    private String password;

    public UserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
