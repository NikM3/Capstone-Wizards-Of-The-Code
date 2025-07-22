package wotc.security;

import jakarta.validation.ValidationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wotc.data.UserJdbcTemplateRepository;
import wotc.models.Role;
import wotc.models.User;

import java.util.HashMap;

@Service
public class AuthenticationService {
    private final UserJdbcTemplateRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final  JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserJdbcTemplateRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User user) {
        validate(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        User newUser = userRepository.add(user);
        String jwtToken = jwtService.generateToken(newUser);
        String refreshToken = jwtService.generateRefresh(new HashMap<>(), newUser);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public AuthenticationResponse authenticate(User userLogin) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword())
        );
        User user = userRepository.findByEmail(userLogin.getEmail());
        if(user == null) {
            return null;
        }
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public AuthenticationResponse refreshToken(String refreshToken) {

        User user = userRepository.findByEmail(jwtService.getEmailFromToken(refreshToken));
        if (user == null) {
            return null;
        }
        String jwtToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken, newRefreshToken);
        return authenticationResponse;
    }

    public Boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    private void validate(User user) {
        if (user == null) {
            throw new ValidationException("user cannot be null");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new ValidationException("username is required");
        }

        if (user.getUsername().length() > 50) {
            throw new ValidationException("username must be less than 50 characters");
        }
    }
}
