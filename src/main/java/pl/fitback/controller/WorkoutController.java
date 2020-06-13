package pl.fitback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fitback.dto.WorkoutCreationDTO;
import pl.fitback.model.Exercise;
import pl.fitback.model.Workout;
import pl.fitback.repository.ExerciseDoesNotExists;
import pl.fitback.repository.PerrmisionDenied;
import pl.fitback.repository.WorkoutDoesNotExists;
import pl.fitback.service.WorkoutService;

@RestController
public class WorkoutController {
	private final WorkoutService workoutService;

	public WorkoutController(WorkoutService workoutService) {
		this.workoutService = workoutService;
	}

	@GetMapping("/workouts/{id}")
	public ResponseEntity<?> get(@PathVariable int id){
		try{
			return ResponseEntity.ok(workoutService.get(id));
		} catch (WorkoutDoesNotExists workoutDoesNotExists) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workout does not exists");
		} catch (PerrmisionDenied perrmisionDenied) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permmison denied");
		}
	}

	@GetMapping("/workouts")
	public ResponseEntity getAll(){
		return ResponseEntity.ok(workoutService.getAll());
	}

	@PostMapping("/workouts")
	public ResponseEntity addWorkout(@RequestBody WorkoutCreationDTO req){
		Workout w = new Workout();
		w.setExercise(new Exercise());
		w.getExercise().setId(req.getExerciseId());
		w.setDuration(req.getDuration());
		try {
			workoutService.add(w);
			return ResponseEntity.ok("created");
		} catch (ExerciseDoesNotExists exerciseDoesNotExists) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercise does not exists");
		}
	}
}
