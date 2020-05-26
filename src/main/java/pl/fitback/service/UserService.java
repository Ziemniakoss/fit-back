package pl.fitback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.fitback.mapper.UserMapper;
import pl.fitback.model.Sport;
import pl.fitback.model.User;
import pl.fitback.model.UserSport;
import pl.fitback.repository.UserRepository;
import pl.fitback.repository.UserSportRepository;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SportService sportService;
    private final UserSportRepository userSportRepository;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SportService sportService, UserSportRepository userSportRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sportService = sportService;
        this.userSportRepository = userSportRepository;
    }

    public User createNewUser(User user) {
        //TODO validate password rules
//        if(checkIfLoginIsTaken(user.getLogin())) {
//            throw new IllegalArgumentException("Login is already taken!");
//        };
        User user1 = new User();
        user1.setLogin(user.getLogin());
        user1.setPassword(user.getPassword());
        return userRepository.save(user1);
    }

    private boolean checkIfLoginIsTaken(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    public void signUpForSport(Long sportId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = (String) authentication.getPrincipal();
        User user = userRepository.findByLogin(login).orElseThrow(IllegalArgumentException::new);
        Long userId = user.getId();

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

    public Float getUserWeightById(Long id){
        Optional<User> userOptional;
        userOptional = userRepository.findById(id);
        User user = new User();
        if(userOptional.isPresent()) {
            user = userOptional.get();
        }
        return user.getWeight();
    }

    public Integer getUserPulseById(Long id){
        Optional<User> userOptional;
        userOptional = userRepository.findById(id);
        User user = new User();
        if(userOptional.isPresent()) {
            user = userOptional.get();
        }
        return user.getPulse();
    }

    public boolean addPulse(User user){
        Optional<User> userOptional;
        userOptional = userRepository.findById(user.getId());
        User user1 = new User();
        if(userOptional.isPresent()) {
            user1 = userOptional.get();
        }
        else {
            return false;
        }
        user1.setPulse(user.getPulse());
        userRepository.save(user1);
        return true;
    }

    public boolean addWeight(User user){
        Optional<User> userOptional;
        userOptional = userRepository.findById(user.getId());
        User user1 = new User();
        if(userOptional.isPresent()) {
            user1 = userOptional.get();
        }
        else {
            return false;
        }
        user1.setWeight(user.getWeight());
        userRepository.save(user1);
        return true;
    }

}
