package pl.fitback.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import pl.fitback.model.PulseMeasurement;
import pl.fitback.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PulseRepository {
	private final JdbcTemplate jdbcTemplate;
	private final UserRepository userRepository;
	private final String BASE_QUERY ="SELECT * FROM pulses";

	public PulseRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.userRepository = userRepository;
	}

	public Optional<PulseMeasurement> get(int id){
		try{
			return Optional.ofNullable(jdbcTemplate.queryForObject(BASE_QUERY+" WHERE id = ?",
					(rs,rn)->map(rs,true),id));
		}catch (EmptyResultDataAccessException e){
			return Optional.empty();
		}
	}

	private PulseMeasurement map(ResultSet rs, boolean mapUser) throws SQLException {
		PulseMeasurement p = new PulseMeasurement();
		p.setId(rs.getInt("id"));
		p.setValue(rs.getInt("value"));
		p.setDate(rs.getTimestamp("date"));
		if(mapUser){
			p.setUser(userRepository.getById(rs.getInt("user_id")));
		}
		return p;
	}

	public List<PulseMeasurement> getAll(User u){
		List<PulseMeasurement> pulses = jdbcTemplate.query(BASE_QUERY + " WHERE user_id = ?",
				(rs, rn) -> map(rs, false), u.getId());
		pulses.forEach(e->e.setUser(u));
		return pulses;
	}

	public void add(PulseMeasurement p , User user){
		int result = jdbcTemplate.queryForObject("SELECT * FROM add_pulse(?,?)",
				Integer.class, user.getId(), p.getValue());
		switch (result){
			case -1:
				throw new UsernameNotFoundException("");
			case -2:
				throw new IllegalArgumentException();
		}

	}
}
