package pl.fitback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fitback.model.Sport;
import pl.fitback.service.SportService;

@RestController
@RequestMapping("/sports")
public class SportController {

    private final SportService sportService;

    @Autowired
    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @PostMapping
    public ResponseEntity createSport(@RequestBody Sport sport) {
        sportService.createSport(sport);

        return ResponseEntity.ok().build();
    }
}
