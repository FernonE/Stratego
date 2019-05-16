package nl.koffiepot.Stratego.persistance;

import nl.koffiepot.Stratego.model.data.SpelerData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpelerDataRepository extends CrudRepository <SpelerData,Long> {
    Optional<SpelerData> findBySpelerNaam(String name);
    void deleteBySpelerNaam(String naam);
}
