package pl.fitback.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fitback.model.UserRole;
import pl.fitback.model.UserSport;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String mail;

    private Integer pulse;

    private Float weight;

    private String login;

    private String password;

    private Set<UserSport> userSport;

    private UserRole userRole;

}
