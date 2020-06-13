package pl.fitback.service;

import org.springframework.stereotype.Service;
import pl.fitback.model.PulseMeasurement;
import pl.fitback.model.User;
import pl.fitback.repository.PerrmisionDenied;
import pl.fitback.repository.PulseMeasurementDOesNotExists;
import pl.fitback.repository.PulseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PulsesService {
	private final UserService userService;
	private final PulseRepository pulseRepository;

	public PulsesService(UserService userService, PulseRepository pulseRepository) {
		this.userService = userService;
		this.pulseRepository = pulseRepository;
	}

	public PulseMeasurement get(int id) throws PulseMeasurementDOesNotExists, PerrmisionDenied {
		User u = userService.getCurrentUser();
		Optional<PulseMeasurement> p = pulseRepository.get(id);
		if(!p.isPresent()){
			throw new PulseMeasurementDOesNotExists();
		}
		if(u.getId() != p.get().getUser().getId()){
			throw new PerrmisionDenied();
		}
		return p.get();
	}

	public List<PulseMeasurement> getAll(){
		User u = userService.getCurrentUser();
		return pulseRepository.getAll(u);
	}

	public void add(PulseMeasurement p){
		p.setUser(userService.getCurrentUser());
		pulseRepository.add(p, userService.getCurrentUser());
	}
}
