package wotc.domain;

import jakarta.validation.ValidationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wotc.data.UserJdbcTemplateRepository;
import wotc.models.Role;
import wotc.models.User;
import wotc.security.AuthenticationResponse;

import java.util.HashMap;

@Service
public class AuthenticationService {
    private final UserJdbcTemplateRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserJdbcTemplateRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Result<AuthenticationResponse> register(User user) {
        Result<AuthenticationResponse> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        User newUser = userRepository.add(user);
        String jwtToken = jwtService.generateToken(newUser);
        String refreshToken = jwtService.generateRefresh(new HashMap<>(), newUser);
        result.setPayload(new AuthenticationResponse(jwtToken, refreshToken));
        return result;
    }

    public Result<AuthenticationResponse> authenticate(User userLogin) {
        Result<AuthenticationResponse> result = new Result<>();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword())
        );
        User user = userRepository.findByEmail(userLogin.getEmail());
        if(user == null) {
            result.addMessage("User not found with email provided.", ResultType.NOT_FOUND);
            return result;
        }
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        result.setPayload(new AuthenticationResponse(jwtToken, refreshToken));
        return result;
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

    private Result<AuthenticationResponse> validate(User user) {
        Result<AuthenticationResponse> result = new Result<>();
        if (user == null) {
            result.addMessage("User cannot be null", ResultType.INVALID);
            return result;
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.addMessage("Username cannot be null", ResultType.INVALID);
            return result;
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            result.addMessage("Email cannot be null", ResultType.INVALID);
            return result;
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            result.addMessage("Password cannot be null", ResultType.INVALID);
        }
        return result;
    }
}
