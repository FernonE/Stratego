package nl.koffiepot.Stratego.persistance;

import nl.koffiepot.Stratego.model.data.BordData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BordDataRepository extends CrudRepository<BordData,Long> {
}
