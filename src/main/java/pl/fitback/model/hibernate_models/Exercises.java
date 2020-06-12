package pl.fitback.model.hibernate_models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "exercises")
public class Exercises {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    private String name;

    @Column(nullable = false)
    private double met;

    private String description;

    @OneToMany(mappedBy = "exercises", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserExercise> users = new ArrayList<>();

    @ManyToOne
    private ExerciseCategory exerciseCategory;

    public Exercises() {
    }

    public Exercises(String name, double met, String description) {
        this.name = name;
        this.met = met;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMet() {
        return met;
    }

    public void setMet(double met) {
        this.met = met;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserExercise> getUsers() {
        return users;
    }

    public void setUsers(List<UserExercise> users) {
        this.users = users;
    }

    public ExerciseCategory getExerciseCategory() {
        return exerciseCategory;
    }

    public void setExerciseCategory(ExerciseCategory exerciseCategory) {
        this.exerciseCategory = exerciseCategory;
    }
}
