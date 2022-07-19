package br.com.bmont.task.controller;

import br.com.bmont.task.request.UserRequest;
import br.com.bmont.task.response.TokenResponse;
import br.com.bmont.task.service.AuthenticationService;
import br.com.bmont.task.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    public AuthController(AuthenticationService authenticationService, TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> authorized(@RequestBody UserRequest userRequest){
        Authentication authentication = authenticationService.authenticate(userRequest);
        return new ResponseEntity<>(tokenService.createToken(authentication), HttpStatus.OK );
    }
}
