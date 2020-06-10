package pl.fitback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    @Column
    private String login;

    @Column
//    @JsonIgnore
    private String password;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserSport> userSports;

    @Column
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private UserRole role = UserRole.ROLE_USER;

}
