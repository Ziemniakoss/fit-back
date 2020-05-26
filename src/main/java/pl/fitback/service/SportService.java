package pl.fitback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fitback.model.Sport;
import pl.fitback.repository.SportRepository;

@Service
public class SportService {

    private final SportRepository sportRepository;

    @Autowired
    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public Sport createSport(Sport sport) {
        return sportRepository.save(sport);
    }

    public Sport getSport(Long sportId) {
        return sportRepository.findById(sportId).orElseThrow(IllegalArgumentException::new);
    }
}
