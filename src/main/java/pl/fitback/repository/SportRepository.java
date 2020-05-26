package pl.fitback.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.fitback.model.Sport;

@Repository
public interface SportRepository extends PagingAndSortingRepository<Sport, Long> {

    @Override
    <S extends Sport> S save(S s);
}
