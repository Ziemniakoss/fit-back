package pl.fitback.service;

import org.springframework.stereotype.Service;

@Service
public class WeightService {
	private final UserService userService;

	public WeightService(UserService userService) {
		this.userService = userService;
	}

	public void getAllWeightMeasurements(){
	}
}
