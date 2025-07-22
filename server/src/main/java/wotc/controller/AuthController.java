package wotc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wotc.models.User;
import wotc.security.AuthenticationResponse;
import wotc.security.AuthenticationService;

@RestController
public class AuthController {
    @Autowired
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) {
        try {
            AuthenticationResponse res = authenticationService.register(user);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User user) {
        try {
            AuthenticationResponse res = authenticationService.authenticate(user);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
    /*
    * Endpoints for Testing purposes
    * */

    @GetMapping("/user")
    public ResponseEntity<String> userHome() {
        return ResponseEntity.ok("Welcome to the User Home!");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminHome() {
        return ResponseEntity.ok("Welcome to the Admin Dashboard!");
    }


}
