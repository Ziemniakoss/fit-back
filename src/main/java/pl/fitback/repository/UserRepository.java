package pl.fitback.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.fitback.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {

    @Override
    <S extends User> S save(S s);

    Optional<User> findByLogin(String login);
}
