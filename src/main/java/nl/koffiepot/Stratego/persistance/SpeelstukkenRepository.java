package nl.koffiepot.Stratego.persistance;

import nl.koffiepot.Stratego.model.data.SpeelstukkenData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpeelstukkenRepository extends CrudRepository <SpeelstukkenData,Long> {
    //Optional <List<SpeelstukkenData>> findByNaamEnValue(String speelstukNaam,  int value);
    void deleteBySpeelstukNaam (String naam);
    Iterable<SpeelstukkenData> findBySpeelstukNaam (String naam);
}
