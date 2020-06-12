package pl.fitback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fitback.service.WeightService;

@RestController
public class WeightController {
	private final WeightService weightService;

	public WeightController(WeightService weightService) {
		this.weightService = weightService;
	}

	@GetMapping("/weights")
	public ResponseEntity getAllMeasurements(){
		return ResponseEntity.ok("TODO");//todo
	}

	@PostMapping("/weights")
	public ResponseEntity addMeasurement(){
		return ResponseEntity.ok("aaa");//todo
	}

	@GetMapping("/weights/{id}")
	public ResponseEntity getMeasurement(@PathVariable int id){
		return ResponseEntity.ok("aa");//todo
	}

}
