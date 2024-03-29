package br.com.bmont.task.controller;

import br.com.bmont.task.exception.ExceptionDetails;
import br.com.bmont.task.request.LoginRequest;
import br.com.bmont.task.response.TokenResponse;
import br.com.bmont.task.service.AuthenticationService;
import br.com.bmont.task.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    public AuthController(AuthenticationService authenticationService, TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Authorize the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "403", description = "The credentials are not valid",
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping
    public ResponseEntity<TokenResponse> authorize(@RequestBody @Valid LoginRequest loginRequest){
        Authentication authentication = authenticationService.authenticate(loginRequest);
        return new ResponseEntity<>(tokenService.createToken(authentication), HttpStatus.OK );
    }
}
