package pl.fitback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.fitback.model.Sport;
import pl.fitback.model.User;
import pl.fitback.model.UserRole;
import pl.fitback.model.UserSport;
import pl.fitback.repository.UserRepository;
import pl.fitback.repository.UserSportRepository;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SportService sportService;
    private final UserSportRepository userSportRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SportService sportService, UserSportRepository userSportRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sportService = sportService;
        this.userSportRepository = userSportRepository;
    }

    public User createNewUser(User user) {
        //TODO validate password rules
        if(checkIfLoginIsTaken(user.getLogin())) {
            throw new IllegalArgumentException("Login is already taken!");
        }
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private boolean checkIfLoginIsTaken(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    public void signUpForSport(UUID sportId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = (String) authentication.getPrincipal();
        User user = userRepository.findByLogin(login).orElseThrow(IllegalArgumentException::new);
        UUID userId = user.getId();

        Sport sport = sportService.getSport(sportId);
        UserSport userSport = new UserSport(user, sport);

        user.getUserSports().add(userSport);
        userSportRepository.save(userSport);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = (String) authentication.getPrincipal();
        return userRepository.findByLogin(login).orElseThrow(IllegalArgumentException::new);
    }
}
