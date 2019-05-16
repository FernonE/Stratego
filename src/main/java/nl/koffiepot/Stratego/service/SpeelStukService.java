package nl.koffiepot.Stratego.service;

import nl.koffiepot.Stratego.model.data.SpeelStukData;
import nl.koffiepot.Stratego.persistance.SpeelStukRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpeelStukService {

    @Autowired
    private SpeelStukRepository speelStukRepository;

    public <S extends SpeelStukData> S save(S entity) {
        return speelStukRepository.save(entity);
    }

    public <S extends SpeelStukData> Iterable<S> saveAll(Iterable<S> entities) {
        return speelStukRepository.saveAll(entities);
    }

    public Iterable<SpeelStukData> findAll() {
        return speelStukRepository.findAll();
    }
}
