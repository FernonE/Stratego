package nl.koffiepot.Stratego.persistance;

import nl.koffiepot.Stratego.model.data.SpeelStukData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeelStukRepository extends CrudRepository<SpeelStukData, Long> {
}
