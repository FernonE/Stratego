package nl.koffiepot.Stratego.persistance;

import nl.koffiepot.Stratego.model.Speelstuk;
import org.springframework.data.repository.CrudRepository;

public interface SpeelstukRepository extends CrudRepository<Speelstuk, Long> {
}
