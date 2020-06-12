package pl.fitback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.fitback.model.Exercise;
import pl.fitback.model.ExerciseCategory;
import pl.fitback.repository.ExercisesCategoriesRepository;
import pl.fitback.repository.ExercisesRepository;

import java.util.Optional;

@Controller
public class ExercisesController {
	private final ExercisesRepository exercisesRepository;
	private final ExercisesCategoriesRepository exercisesCategoriesRepository;


	public ExercisesController(ExercisesRepository exercisesRepository, ExercisesCategoriesRepository exercisesCategoriesRepository) {
		this.exercisesRepository = exercisesRepository;
		this.exercisesCategoriesRepository = exercisesCategoriesRepository;
	}

	@GetMapping("/exercises/categories")
	private ResponseEntity getCategories() {
		return ResponseEntity.ok(exercisesCategoriesRepository.getAll());
	}

	@GetMapping("/exercises/categories/{id}")
	public ResponseEntity getCategoryInfo(@PathVariable int id) {
		Optional<ExerciseCategory> exerciseCategory = exercisesCategoriesRepository.get(id);
		if (exerciseCategory.isPresent()) {
			return ResponseEntity.ok(exerciseCategory.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
	}

	@GetMapping("/exercises/{id}")
	private ResponseEntity getExerciseInfo(@PathVariable int id) {
		Optional<Exercise> exercise = exercisesRepository.get(id);
		if (exercise.isPresent()) {
			return ResponseEntity.ok(exercise.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id+" not found");
	}

	@GetMapping("/exercises/categories/{categoryId}/exercises")
	private ResponseEntity getExercisesFromCategory(@PathVariable int categoryId){
		Optional<ExerciseCategory> category = exercisesCategoriesRepository.get(categoryId);
		if(!category.isPresent()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category does not exists");
		}
		return ResponseEntity.ok(exercisesRepository.getAllFromCategory(category.get()));
	}
}
