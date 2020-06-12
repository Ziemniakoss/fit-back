package pl.fitback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fitback.dto.QuestUpdateRequestDTO;
import pl.fitback.model.Quest;
import pl.fitback.repository.QuestDOesNotExists;
import pl.fitback.service.QuestService;

@RestController
public class QuestsController {
	private final QuestService questService;

	public QuestsController(QuestService questService) {
		this.questService = questService;
	}


	@GetMapping("/quests")
	public ResponseEntity getAll() {
		return ResponseEntity.ok(questService.getAllQuests());
	}

	@GetMapping("/quests/{id}")
	public ResponseEntity get(@PathVariable int id, @RequestBody QuestUpdateRequestDTO req) {
		try {
			Quest q = questService.get(id);
			q.setCompleted(req.isDone());
			questService.updateStatus(q);
			return ResponseEntity.ok(q);
		} catch (QuestDOesNotExists questDOesNotExists) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quest not found");
		}
	}

	@PostMapping("/quests/{id}")
	public ResponseEntity updateStatus(@PathVariable int id) {
		try {
			questService.updateStatus(questService.get(id));
			return ResponseEntity.ok("Updated");
		} catch (QuestDOesNotExists questDOesNotExists) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quest not found");
		}
	}
}
