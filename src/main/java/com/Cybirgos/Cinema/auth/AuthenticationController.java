package com.Cybirgos.Cinema.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @GetMapping("/testUsername") //to be tested
    public ResponseEntity<String > testUsername (@RequestBody String username){
        return authenticationService.testUsername(username);
    }
    @PostMapping("/register") //done
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request){
        return authenticationService.register(request);
    }
    @PostMapping("/login") //done
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/refresh-token") //done
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request,response);
    }
    @GetMapping("/test")
    public String test(){
        return "test successful";
    }
}
