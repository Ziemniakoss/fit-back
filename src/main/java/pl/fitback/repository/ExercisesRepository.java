package pl.fitback.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.fitback.model.Exercise;
import pl.fitback.model.ExerciseCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ExercisesRepository {
	private final JdbcTemplate jdbcTemplate;
	private final String BASE_QUERY =
			"SELECT * FROM exercises";

	public ExercisesRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Optional<Exercise> get(int id) {
		try{
			return Optional.ofNullable(jdbcTemplate.queryForObject(BASE_QUERY+" WHERE id = ?", (rs,rn)->map(rs), id));
		}catch (EmptyResultDataAccessException e){
			return Optional.empty();
		}
	}

	public List<Exercise> getAllFromCategory(ExerciseCategory e) {
		return jdbcTemplate.query(BASE_QUERY + " WHERE category =?", (rs, rn) -> map(rs), e.getId());
	}

	public boolean exits(int id) {
		return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT id FROM exercises WHERE id = ?)",
				Boolean.class, id);
	}

	private Exercise map(ResultSet rs) throws SQLException {
		Exercise e = new Exercise();
		e.setDescription(rs.getString("description"));
		e.setId(rs.getInt("id"));
		e.setName(rs.getString("name"));
		e.setMet(rs.getFloat("met"));
		return e;
	}
}
