package pl.fitback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fitback.model.User;
import pl.fitback.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity registerNewUser(@RequestBody User user) {

        try {
            userService.createNewUser(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/me")
    public ResponseEntity getMe() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PostMapping(path = "/{sportId}/signup")
    public ResponseEntity signUpForSport(@PathVariable Long sportId, Principal principal) {
        userService.signUpForSport(sportId);

        return ResponseEntity.ok().build();
    }

  /*  @GetMapping(path = "/logout")
    public boolean signOut(@RequestParam Long id){
        return userService.logout(id);
    }*/

    @PostMapping(path = "/addPulse")
    public boolean addPulse(@RequestBody  User user){
        return userService.addPulse(user);
    }

    @PostMapping(path = "/addWeight")
    public boolean addWeight(@RequestBody User user){
        return userService.addWeight(user);
    }

    @GetMapping(path = "/showPulse/{id}")
    public Integer getPulse(@PathVariable Long id){
        return userService.getUserPulseById(id);
    }

    @GetMapping(path = "/showWeight/{id}")
    public Float getWeight(@PathVariable Long id){
        return userService.getUserWeightById(id);
    }








}
