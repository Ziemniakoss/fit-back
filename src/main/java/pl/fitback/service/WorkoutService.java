package pl.fitback.service;

import org.springframework.stereotype.Service;
import pl.fitback.model.User;
import pl.fitback.model.Workout;
import pl.fitback.repository.ExerciseDoesNotExists;
import pl.fitback.repository.PerrmisionDenied;
import pl.fitback.repository.WorkoutDoesNotExists;
import pl.fitback.repository.WorkoutRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {
	private final UserService userService;
	private final WorkoutRepository workoutRepository;

	public WorkoutService(UserService userService, WorkoutRepository workoutRepository) {
		this.userService = userService;
		this.workoutRepository = workoutRepository;
	}

	public void add(Workout w) throws ExerciseDoesNotExists {
		User u = userService.getCurrentUser();
		w.setUser(u);
		workoutRepository.add(w);
	}


	public List<Workout> getAll() {
		User u = userService.getCurrentUser();
		return workoutRepository.geAll(u);
	}

	public Workout get(int id) throws PerrmisionDenied, WorkoutDoesNotExists {
		User u = userService.getCurrentUser();
		Optional<Workout> w = workoutRepository.get(id);
		if (!w.isPresent()) {
			throw new WorkoutDoesNotExists();
		}
		if (w.get().getUser().getId() != u.getId()) {
			throw new PerrmisionDenied();
		}
		return w.get();
	}
}
