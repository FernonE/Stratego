package nl.koffiepot.Stratego.service;

import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.persistance.SpelerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpelerDataService {

    @Autowired
    private SpelerDataRepository spelerDataRepository;

    public SpelerData save(SpelerData speler) {
        return spelerDataRepository.save(speler);
    }

    public Iterable<SpelerData> findAll() {
        return spelerDataRepository.findAll();
    }

    public Optional<SpelerData> findBySpelerNaam(String name) {
        return spelerDataRepository.findBySpelerNaam(name);
    }

    public void deleteBySpelerNaam(String naam){
        spelerDataRepository.deleteBySpelerNaam(naam);
    }

    public void deleteById(Long aLong) {
        spelerDataRepository.deleteById(aLong);
    }


}
