package pl.fitback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fitback.dto.WeightMeasurementCreationDTO;
import pl.fitback.model.WeightMeasurement;
import pl.fitback.repository.PerrmisionDenied;
import pl.fitback.repository.WeightMeasurementDoesNotExists;
import pl.fitback.service.WeightService;

@RestController
public class WeightController {
	private final WeightService weightService;

	public WeightController(WeightService weightService) {
		this.weightService = weightService;
	}

	@GetMapping("/weights")
	public ResponseEntity getAllMeasurements() {
		return ResponseEntity.ok(weightService.getAllWeightMeasurements());
	}

	@PostMapping("/weights")
	public ResponseEntity addMeasurement(@RequestBody WeightMeasurementCreationDTO req) {
		WeightMeasurement weightMeasurement = new WeightMeasurement();
		weightMeasurement.setValue(req.getValue());
		weightService.add(weightMeasurement);
		return ResponseEntity.ok("added");
	}

	@GetMapping("/weights/{id}")
	public ResponseEntity getMeasurement(@PathVariable int id) {
		try {
			return ResponseEntity.ok(weightService.get(id));
		} catch (WeightMeasurementDoesNotExists weightMeasurementDoesNotExists) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measuremnt does not exists");
		} catch (PerrmisionDenied perrmisionDenied) {
			return ResponseEntity.status(HttpStatus.valueOf(401)).body("Nu allowed");
		}
	}
}
