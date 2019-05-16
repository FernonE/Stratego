package nl.koffiepot.Stratego.service;

import nl.koffiepot.Stratego.model.data.SpelData;
import nl.koffiepot.Stratego.persistance.SpelDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpelDataService {

    @Autowired
    private SpelDataRepository spelDataRepository;

    private SpelData spelData;

    public SpelData save(SpelData entity) {
        return spelDataRepository.save(entity);
    }
}
