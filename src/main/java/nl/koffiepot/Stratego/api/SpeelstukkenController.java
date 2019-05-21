package nl.koffiepot.Stratego.api;


import nl.koffiepot.Stratego.model.data.SpeelstukkenData;
import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.service.SpeelstukkenService;
import nl.koffiepot.Stratego.service.SpelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("api/speelstukken")
@CrossOrigin(origins = "http://localhost:4200")
public class SpeelstukkenController{

    private SpelerService spelerService;


    @Autowired
    private SpeelstukkenService speelstukkenService;

    @GetMapping
    public Iterable<SpeelstukkenData> getAllSpeelstukken() {return speelstukkenService.findAll();}

    @PostMapping
    public SpeelstukkenData createSpeelstukken(@RequestBody SpeelstukkenData speelstuk) {
        return speelstukkenService.save(speelstuk);
    }

    @PostMapping("{spelerNaam}")
    public SpeelstukkenData createSpeelstukkenBySpeler(@PathVariable String spelerNaam, @RequestBody SpeelstukkenData speelstuk) {
        Optional<SpelerData> optionalSpeler = this.spelerService.findBySpelerNaam(spelerNaam);
        if (optionalSpeler.isPresent()) {
            this.speelstukkenService.save(speelstuk);
            (optionalSpeler.get()).addSpeelstukkenData(speelstuk);
            this.spelerService.save(optionalSpeler.get());
        }
        return speelstukkenService.save(speelstuk);
    }

    @GetMapping("/{spelerNaam}/{speelstukNaam}")
    public Iterable<SpeelstukkenData> getSpeelstukByNaam(@PathVariable String naam) {
        return speelstukkenService.findBySpeelstukNaam(naam); }

    @Transactional
    @DeleteMapping("/{spelerNaam}/{speelstukNaam}")
    public void deleteSpeelstukByNaam(@PathVariable String naam) {
            speelstukkenService.deleteBySpeelstukNaam(naam);
        }
}

