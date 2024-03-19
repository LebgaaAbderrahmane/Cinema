package com.Cybirgos.Cinema.auth;

import com.Cybirgos.Cinema.config.JwtService;
import com.Cybirgos.Cinema.profile.Profile;
import com.Cybirgos.Cinema.profile.ProfileRepo;
import com.Cybirgos.Cinema.token.Token;
import com.Cybirgos.Cinema.token.TokenRepo;
import com.Cybirgos.Cinema.token.TokenType;
import com.Cybirgos.Cinema.user.Role;
import com.Cybirgos.Cinema.user.User;
import com.Cybirgos.Cinema.user.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final ProfileRepo profileRepo;
    private final TokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public boolean isEmailValid (String email){
        return userRepo.findByEmail(email).isEmpty();
    }
    public boolean isUsernameValid (String username){
        return userRepo.findByUsername(username).isEmpty();
    }

    public ResponseEntity<AuthenticationResponse> register(RegistrationRequest request) {
        if (!isEmailValid(request.getEmail())) {
            //throw new IllegalArgumentException("Email already in use !");
            return new ResponseEntity<>(null,HttpStatus.valueOf(409));

        }
        if (!isUsernameValid(request.getUsername())) {
            //throw new IllegalArgumentException("Invalid username ");
            return new ResponseEntity<>(null,HttpStatus.valueOf(409));
        }

        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepo.save(user);
        var profile = Profile.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .build();
        var savedProfile = profileRepo.save(profile);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        var authResponse =  AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepo.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.Bearer)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepo.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var userDetails = this.userRepo.findByUsername(username).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
                revokeAllUserTokens(userDetails);
                saveUserToken(userDetails, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public ResponseEntity<String> testUsername(String username) {
       if( isUsernameValid(username)){
           return new ResponseEntity<>("username is valid",HttpStatus.OK);
       }
       return new ResponseEntity<>("username is not valid ",HttpStatus.BAD_REQUEST);
    }
}
