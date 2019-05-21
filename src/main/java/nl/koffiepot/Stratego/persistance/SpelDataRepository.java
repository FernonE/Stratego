package nl.koffiepot.Stratego.persistance;

import nl.koffiepot.Stratego.model.data.SpelData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpelDataRepository extends CrudRepository<SpelData, Long> {


}
