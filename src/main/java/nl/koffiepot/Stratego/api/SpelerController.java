package nl.koffiepot.Stratego.api;

import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.service.SpelerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("speler")
@CrossOrigin(origins = "http://localhost:4200")
public class SpelerController {

    @Autowired
    private SpelerDataService spelerDataService;

    @GetMapping
    public Iterable<SpelerData> getAllSpeler() {return spelerDataService.findAll();}

    @PostMapping
    public SpelerData createSpeler(@RequestBody SpelerData spelerData) {
        if (!spelerDataService.findBySpelerNaam(spelerData.getSpelerNaam()).isPresent()) {
            if (spelerData.getSpelerNaam() != null)
                return spelerDataService.save(spelerData);
            System.out.println("SpelerNaam mag niet null");
            return null;
        } else {
            System.out.println("Speler bestaat al");
            return null;
        } //Dit mag eigenlijk niet
    }

    @GetMapping("/{naam}")
    public Optional<SpelerData> getSpelerByNaam(@PathVariable String naam) {
        return spelerDataService.findBySpelerNaam(naam);
    }

    @Transactional
    @DeleteMapping("/{naam}")
    public void deleteBySpelerNaam(@PathVariable String naam) {
        spelerDataService.deleteBySpelerNaam(naam);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {spelerDataService.deleteById(id);}
}
