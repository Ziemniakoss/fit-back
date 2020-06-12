package pl.fitback.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import pl.fitback.model.User;
import pl.fitback.model.WeightMeasurement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class WeightRepository {
	private final JdbcTemplate jdbcTemplate;
	private final String BASE_QUERY =
			"SELECT w.id as id, w.value as value, w.measurement_date as date, u.id as u_id " +
					" FROM weight_measurements w " +
					" JOIN users u ON w.user_id = u.id ";

	public WeightRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Optional<WeightMeasurement> get(int id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(BASE_QUERY, (rs, rn) -> map(rs, true), id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<WeightMeasurement> getAll(User user) {
		List<WeightMeasurement> weights = jdbcTemplate.query(BASE_QUERY + " WHERE u.id = ?",
				(rs, rn) -> map(rs, false), user.getId());
		weights.forEach(e->e.setUser(user));
		return weights;
	}

	public void add(WeightMeasurement measurement) {
		int result = jdbcTemplate.queryForObject("SELECT * FROM add_measurement(?,?);",
				Integer.class, measurement.getUser().getId(), measurement.getValue());
		if(result ==-1){
			throw new UsernameNotFoundException(measurement.getUser().getLogin());
		}
	}

	private WeightMeasurement map(ResultSet rs, boolean mapUser) throws SQLException {
		WeightMeasurement result =new WeightMeasurement();
		result.setId(rs.getInt("id"));
		result.setMeasurementDate(rs.getDate("date"));
		result.setValue(rs.getFloat("value"));
		if(mapUser){
			User u = new User();
			u.setId(rs.getInt("u_id"));
			result.setUser(u);
		}
		return result;
	}
}
