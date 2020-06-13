package pl.fitback.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import pl.fitback.model.User;
import pl.fitback.model.Workout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class WorkoutRepository {
	private final JdbcTemplate jdbcTemplate;
	private final ExercisesRepository exercisesRepository;
	private final UserRepository userRepository;
	private final String BASE_QUERY =
			"SELECT   w.id          AS w_id, " +
					" w.duration    AS w_dur, " +
					" w.date        AS w_date, " +
					" w.calories    AS w_cal, " +
					" w.exercise_id AS e_id, " +
					" w.user_id     AS u_id " +
					" FROM workouts w ";

	public WorkoutRepository(JdbcTemplate jdbcTemplate, ExercisesRepository exercisesRepository,
							 UserRepository userRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.exercisesRepository = exercisesRepository;
		this.userRepository = userRepository;
	}

	public Optional<Workout> get(int id){
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(BASE_QUERY+"WHERE w.id = ?",
					(rs,rn)->map(rs, true),id));
		}catch (EmptyResultDataAccessException e){
			return Optional.empty();
		}
	}

	public List<Workout> geAll(User user){
		Assert.notNull(user,"User can't be null");
		return jdbcTemplate.query(BASE_QUERY +" WHERE w.user_id = ?",
				(rs,rn)->map(rs, false), user.getId());
	}

	public void add(Workout workout) throws ExerciseDoesNotExists {
		Assert.notNull(workout, "Workout can't be null");
		Assert.notNull(workout.getExercise(),"Exercise can't be null");
		Assert.notNull(workout.getUser(),"User can't be null");
		int result = jdbcTemplate.queryForObject("SELECT * FROM add_workout(?,?,?)",
				Integer.class, workout.getUser().getId(), workout.getExercise().getId(), workout.getDuration());
		switch (result){
			case -1:
				throw new UsernameNotFoundException("");
			case -2:
				throw new ExerciseDoesNotExists();
			case -3:
				throw new IllegalArgumentException("Duration must be greater than 0");
		}

	}

	private Workout map(ResultSet rs, boolean mapUser) throws SQLException {
		Workout w = new Workout();
		w.setDate(rs.getTimestamp("w_date"));
		w.setId(rs.getInt("w_id"));
		w.setCalories(rs.getInt("w_cal"));
		w.setDuration(rs.getInt("w_dur"));
		w.setExercise(exercisesRepository.get(rs.getInt("e_id")).orElse(null));
		if(mapUser){
			w.setUser(userRepository.getById(rs.getInt("u_id")));
		}
		return w;
	}
}
