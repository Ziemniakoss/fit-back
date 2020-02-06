package pl.fitback.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.fitback.model.UserSport;

import java.util.UUID;

public interface UserSportRepository extends PagingAndSortingRepository<UserSport, UUID> {
}
