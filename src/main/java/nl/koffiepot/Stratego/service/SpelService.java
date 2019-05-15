package nl.koffiepot.Stratego.service;

import nl.koffiepot.Stratego.persistance.SpelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpelService {

    @Autowired
    private SpelRepository spelRepository;

}
