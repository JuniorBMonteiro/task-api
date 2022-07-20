package br.com.bmont.task.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginRequest {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 50, message = "Username length cannot be less than 4 or greater than 50")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, message = "Password length cannot be less than 6")
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {
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
}
