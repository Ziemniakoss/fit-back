package pl.fitback.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.fitback.model.ExerciseCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ExercisesCategoriesRepository {
	private final JdbcTemplate jdbcTemplate;

	public ExercisesCategoriesRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ExerciseCategory> getAll() {
		return jdbcTemplate.query("SELECT * FROM exercises_categories", (rs,rn)->map(rs));
	}

	public Optional<ExerciseCategory> get(int id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM exercises_categories WHERE id = ?",
					(rs, rn) -> map(rs), id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	private ExerciseCategory map(ResultSet rs) throws SQLException {
		ExerciseCategory ec = new ExerciseCategory();
		ec.setId(rs.getInt("id"));
		ec.setName(rs.getString("name"));
		return ec;
	}
}
