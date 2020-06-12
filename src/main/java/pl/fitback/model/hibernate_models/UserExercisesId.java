package pl.fitback.model.hibernate_models;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserExercisesId implements Serializable {
    private long userId;
    private long exerciseId;

    public UserExercisesId() {
    }

    public UserExercisesId(long userId, long exerciseId) {
        this.userId = userId;
        this.exerciseId = exerciseId;

    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, exerciseId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        UserExercisesId that = (UserExercisesId) obj;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(exerciseId, that.exerciseId);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }
}
