package nl.koffiepot.Stratego.service;

import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.persistance.SpelerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpelerService {

    @Autowired
    private SpelerRepository spelerRepository;

    public SpelerData save(SpelerData speler) {
        return spelerRepository.save(speler);
    }

    public Iterable<SpelerData> findAll() {
        return spelerRepository.findAll();
    }

    public Optional<SpelerData> findBySpelerNaam(String name) {
        return spelerRepository.findBySpelerNaam(name);
    }

    public void deleteBySpelerNaam(String naam){
        spelerRepository.deleteBySpelerNaam(naam);
    }
    public void deleteById(Long aLong) {
        spelerRepository.deleteById(aLong);
    }


}
