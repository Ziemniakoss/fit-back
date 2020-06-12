package pl.fitback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fitback.dto.UserCreationDTO;
import pl.fitback.model.User;
import pl.fitback.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(path = "/register")
	public ResponseEntity registerNewUser(@RequestBody UserCreationDTO newUser) {
		System.out.println(newUser.getLogin());
		System.out.println(newUser.getPassword());
		System.out.println(newUser.getWeight());
		try {
			userService.createNewUser(new User(0, newUser.getLogin(), newUser.getPassword(), newUser.getWeight()));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/me")
	public ResponseEntity getMe() {
		return ResponseEntity.ok(userService.getCurrentUser());
	}

   
}
