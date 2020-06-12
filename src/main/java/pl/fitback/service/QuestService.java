package pl.fitback.service;

import org.springframework.stereotype.Service;
import pl.fitback.model.Quest;
import pl.fitback.repository.QuestDOesNotExists;
import pl.fitback.repository.QuestRepository;

import java.util.List;

@Service
public class QuestService {
	private final UserService userService;
	private final QuestRepository questRepository;

	public QuestService(UserService userService, QuestRepository questRepository) {
		this.userService = userService;
		this.questRepository = questRepository;
	}

	public List<Quest> getAllQuests() {
		return questRepository.getAll();
	}

	public Quest get(int id) throws QuestDOesNotExists {
		Quest q = questRepository.get(id);
		if(q == null){
			throw new QuestDOesNotExists();
		}
		return q;

	}

	public void updateStatus(Quest quest) throws QuestDOesNotExists {
		questRepository.update(quest);
	}

}
