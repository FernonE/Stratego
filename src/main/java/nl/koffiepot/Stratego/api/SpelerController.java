package nl.koffiepot.Stratego.api;

import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.service.SpelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("speler")
public class SpelerController {

    @Autowired
    private SpelerService spelerService;

    @GetMapping
    public Iterable<SpelerData> getAllSpeler() {return spelerService.findAll();}

    @PostMapping
    public SpelerData createSpeler(@RequestBody SpelerData speler) {
        if (!spelerService.findBySpelerNaam(speler.getSpelerNaam()).isPresent()) {
            return spelerService.save(speler);
        } else {
            System.out.println("Speler bestaat al");
            return null;
        } //Dit mag eigenlijk niet
    }

    @GetMapping("/{naam}")
    public Optional<SpelerData> getSpelerByNaam(@PathVariable String naam) {
        return spelerService.findBySpelerNaam(naam);
    }

    @Transactional
    @DeleteMapping("/{naam}")
    public void deleteBySpelerNaam(@PathVariable String naam) {
        spelerService.deleteBySpelerNaam(naam);
    }

}
