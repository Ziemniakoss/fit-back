package pl.fitback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fitback.dto.PulseCreationDTO;
import pl.fitback.model.PulseMeasurement;
import pl.fitback.repository.PerrmisionDenied;
import pl.fitback.repository.PulseMeasurementDOesNotExists;
import pl.fitback.service.PulsesService;

@RestController
public class PulseController {
	private final PulsesService pulsesService;

	public PulseController(PulsesService pulsesService) {
		this.pulsesService = pulsesService;
	}

	@GetMapping("/pulses/{id}")
	public ResponseEntity<?> get(@PathVariable int id){
		try{
			return ResponseEntity.ok(pulsesService.get(id));
		} catch (PerrmisionDenied perrmisionDenied) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permmison dneied");
		} catch (PulseMeasurementDOesNotExists pulseMeasurementDOesNotExists) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
		}
	}

	@GetMapping("/pulses")
	public ResponseEntity getAll(){
		return ResponseEntity.ok(pulsesService.getAll());
	}

	@PostMapping("/pulses")
	public ResponseEntity<?> addNew(@RequestBody PulseCreationDTO req){
		PulseMeasurement p = new PulseMeasurement();
		p.setValue(req.getValue());
		pulsesService.add(p);
		return ResponseEntity.ok("created");
	}
}
