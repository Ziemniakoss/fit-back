package pl.fitback.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.fitback.model.Sport;

import java.util.UUID;

@Repository
public interface SportRepository extends PagingAndSortingRepository<Sport, UUID> {

    @Override
    <S extends Sport> S save(S s);
}
