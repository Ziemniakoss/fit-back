package pl.fitback.mapper;

import pl.fitback.dto.UserDto;
import pl.fitback.model.User;

public class UserMapper {

    public UserDto toDto(User user){

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setMail(user.getMail());
        userDto.setLogin(user.getLogin());
        userDto.setPulse(user.getPulse());
        userDto.setWeight(user.getWeight());
        userDto.setPassword(user.getPassword());
        userDto.setUserRole(user.getRole());
        userDto.setUserSport(user.getUserSports());

        return userDto;

    }


}
