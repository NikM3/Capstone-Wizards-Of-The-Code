package wotc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wotc.domain.Result;
import wotc.domain.ResultType;
import wotc.domain.UserService;
import wotc.models.User;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> findAllUsers() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody User user) {
        Result<User> result = service.add(user);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

}
