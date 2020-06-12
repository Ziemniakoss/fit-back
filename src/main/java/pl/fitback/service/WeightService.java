package pl.fitback.service;

import org.springframework.stereotype.Service;
import pl.fitback.model.User;
import pl.fitback.model.WeightMeasurement;
import pl.fitback.repository.PerrmisionDenied;
import pl.fitback.repository.WeightMeasurementDoesNotExists;
import pl.fitback.repository.WeightRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WeightService {
	private final UserService userService;
	private final WeightRepository weightRepository;

	public WeightService(UserService userService, WeightRepository weightRepository) {
		this.userService = userService;
		this.weightRepository = weightRepository;
	}

	public List<WeightMeasurement> getAllWeightMeasurements() {
		User currentUser = userService.getCurrentUser();
		return weightRepository.getAll(currentUser);
	}

	public void add(WeightMeasurement measurement) {
		User u = userService.getCurrentUser();
		measurement.setUser(u);
		weightRepository.add(measurement);
	}

	public WeightMeasurement get(int id) throws PerrmisionDenied, WeightMeasurementDoesNotExists {
		User u = userService.getCurrentUser();
		Optional<WeightMeasurement> w = weightRepository.get(id);
		if (!w.isPresent()) {
			throw new WeightMeasurementDoesNotExists();
		} else if (w.get().getUser().getId() != u.getId()) {
			throw new PerrmisionDenied();
		}
		return w.get();
	}
}
