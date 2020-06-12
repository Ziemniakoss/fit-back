package pl.fitback.model.hibernate_models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity(name = "user_exercise")
public class UserExercise {
    @EmbeddedId
    private UserExercisesId id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Exercises exercises;

    private double duration;

    private Date date;

    public UserExercise() {
    }

    public UserExercise(UserExercisesId id, User user, Exercises exercises, double duration, Date date) {
        this.id = id;
        this.user = user;
        this.exercises = exercises;
        this.duration = duration;
        this.date = date;
    }

    public UserExercisesId getId() {
        return id;
    }

    public void setId(UserExercisesId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exercises getExercises() {
        return exercises;
    }

    public void setExercises(Exercises exercises) {
        this.exercises = exercises;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
