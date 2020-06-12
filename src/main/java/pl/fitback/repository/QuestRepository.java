package pl.fitback.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import pl.fitback.model.Quest;
import pl.fitback.service.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuestRepository {
	private final JdbcTemplate jdbcTemplate;
	private final UserService userService;
	private final String BASE_QUERY =
			"SELECT q.id as id, q.name as name, q.description as description, q.xp as xp, (user_id IS NOT NULL) as done" +
					" FROM quests q " +
					" LEFT JOIN users_quests uq ON q.id = uq.quest_id " +
					" WHERE (user_id = ? OR user_id is NULL) ";

	public QuestRepository(JdbcTemplate jdbcTemplate, UserService userService) {
		this.jdbcTemplate = jdbcTemplate;
		this.userService = userService;
	}

	public Quest get(int id) {
		try {
			return jdbcTemplate.queryForObject(BASE_QUERY + " AND q.id = ?",
					(rs, rn) -> map(rs), userService.getCurrentUser().getId(), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Quest> getAll() {
		return jdbcTemplate.query(BASE_QUERY, (rs, rn) -> map(rs), userService.getCurrentUser().getId());
	}

	public void update(Quest quest) throws QuestDOesNotExists {
		int result = jdbcTemplate.queryForObject("SELECT * FROM update_quest_status(?,?,?)",
				Integer.class, quest.getId(), userService.getCurrentUser().getId(), quest.isCompleted());
		switch (result){
			case -1:
				throw new QuestDOesNotExists();
			case -2:
				throw new UsernameNotFoundException("aa");
		}
	}

	private Quest map(ResultSet rs) throws SQLException {
		Quest q = new Quest();
		q.setId(rs.getInt("id"));
		q.setName(rs.getString("name"));
		q.setDescription(rs.getString("description"));
		q.setXp(rs.getInt("xp"));
		q.setCompleted(rs.getBoolean("done"));
		return q;
	}
}
