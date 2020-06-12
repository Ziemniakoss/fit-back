package pl.fitback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.fitback.model.User;
import pl.fitback.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void createNewUser(User user) {
		if (checkIfLoginIsTaken(user.getLogin())) {
			throw new IllegalArgumentException("Login is already taken!");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.add(user);
	}

	private boolean checkIfLoginIsTaken(String login) {
		return userRepository.exists(login);
	}


	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String login = (String) authentication.getPrincipal();
		return userRepository.getByLogin(login);
	}
}
