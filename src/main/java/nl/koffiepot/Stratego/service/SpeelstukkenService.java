package nl.koffiepot.Stratego.service;


import nl.koffiepot.Stratego.model.data.SpeelstukkenData;
import nl.koffiepot.Stratego.persistance.SpeelstukkenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpeelstukkenService {

    @Autowired
    private SpeelstukkenRepository speelstukkenRepository;

    public Optional<SpeelstukkenData> findById(Long aLong) {
        return speelstukkenRepository.findById(aLong);
    }

    public Iterable<SpeelstukkenData> findAll() {
        return speelstukkenRepository.findAll();
    }

    public Iterable<SpeelstukkenData> findAllById(Iterable<Long> longs) {
        return speelstukkenRepository.findAllById(longs);
    }

    public Iterable<SpeelstukkenData> findBySpeelstukNaam(String naam) {
        return speelstukkenRepository.findBySpeelstukNaam(naam);
    }


    public SpeelstukkenData save(SpeelstukkenData speelstuk) {
        return speelstukkenRepository.save(speelstuk);
    }

    public void deleteById(Long aLong) {
        speelstukkenRepository.deleteById(aLong);
    }

    public void deleteAll() {
        speelstukkenRepository.deleteAll();
    }

    public void deleteBySpeelstukNaam(String naam){
        speelstukkenRepository.deleteBySpeelstukNaam(naam);
    }

    //public Optional <List<SpeelstukkenData>> findByNaamEnValue(String speelstukNaam, int value) {
      //  return this.speelstukkenRepository.findByNaamEnValue(speelstukNaam, value);}
}

