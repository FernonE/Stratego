package nl.koffiepot.Stratego.service;

import nl.koffiepot.Stratego.model.data.SpelData;
import nl.koffiepot.Stratego.persistance.SpelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpelService {

    @Autowired
    private SpelRepository spelRepository;

    private SpelData spelData;

    public SpelData save(SpelData entity) {
        return spelRepository.save(entity);
    }
}
