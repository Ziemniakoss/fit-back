package pl.fitback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String mail;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private Integer pulse;

    @Column
    private Float weight;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserSport> userSports;

    @Column
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private UserRole role = UserRole.ROLE_USER;

}
