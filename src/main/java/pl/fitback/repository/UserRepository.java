package pl.fitback.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.fitback.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public User getByLogin(String login) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM users WHERE login = ?",
					(rs, rn) -> map(rs), login);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public User getById(int id) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
					(rs, rn) -> map(rs), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	private User map(ResultSet rs) throws SQLException {
		User u = new User();
		u.setId(rs.getInt("id"));
		u.setLogin(rs.getString("login"));
		u.setPassword(rs.getString("password"));
		u.setWeight(rs.getDouble("weight"));
		return u;
	}

	public boolean exists(String login) {
		return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT id FROM users WHERE login = ?)", Boolean.class, login);
	}

	public void add(User user) {
		if (exists(user.getLogin())) {
			throw new IllegalArgumentException();
		}
		jdbcTemplate.update("INSERT INTO  users (login, password, weight) VALUES (?,?,?)"
				, user.getLogin(), user.getPassword(), user.getWeight());

	}
}
