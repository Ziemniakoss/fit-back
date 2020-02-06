package pl.fitback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fitback.model.User;
import pl.fitback.service.UserService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity registerNewUser(@RequestBody User newUser) {
        System.out.println(newUser.getLogin());
        System.out.println(newUser.getPassword());

        try {
            userService.createNewUser(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/me")
    public ResponseEntity getMe() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PostMapping(path = "/{sportId}/singup")
    public ResponseEntity singUpForSport(@PathVariable UUID sportId, Principal principal) {
        userService.signUpForSport(sportId);

        return ResponseEntity.ok().build();
    }
}
