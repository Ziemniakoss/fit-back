package pl.fitback.model.hibernate_models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(nullable = false, length = 200)
    private String login;

    @Column(nullable = false)
    private String passwordHash;

    private double weight;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeightMeasurements> weightMeasurements = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserExercise> userExercises = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_quest",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "quest_id"))
    private List<Quests> quests = new ArrayList<>();


    public User() {
    }

    public User(long id, String login, String passwordHash, double weight) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.weight = weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<WeightMeasurements> getWeightMeasurements() {
        return weightMeasurements;
    }

    public void setWeightMeasurements(List<WeightMeasurements> weightMeasurements) {
        this.weightMeasurements = weightMeasurements;
    }

    public List<UserExercise> getUserExercises() {
        return userExercises;
    }

    public void setUserExercises(List<UserExercise> userExercises) {
        this.userExercises = userExercises;
    }

    public List<Quests> getQuests() {
        return quests;
    }

    public void setQuests(List<Quests> quests) {
        this.quests = quests;
    }


}
