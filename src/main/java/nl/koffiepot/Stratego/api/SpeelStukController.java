package nl.koffiepot.Stratego.api;

import nl.koffiepot.Stratego.model.Speelstuk;
import nl.koffiepot.Stratego.model.data.SpeelStukData;
import nl.koffiepot.Stratego.service.SpeelStukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SpeelStukController {

    @Autowired
    private SpeelStukService speelStukService;


    public void saveSpeelStukken(Object[][] speelbord) {
        List<SpeelStukData> speelStukken = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (speelbord[y][x] instanceof Speelstuk) {
                    Speelstuk speelstukOpBord = (Speelstuk) speelbord[y][x];
                    SpeelStukData opTeSlaanStuk = new SpeelStukData();
                    opTeSlaanStuk.setSpelNaam("een spelnaam");
                    opTeSlaanStuk.setTeam(speelstukOpBord.getTeam());
                    opTeSlaanStuk.setValue(speelstukOpBord.getValue());
                    opTeSlaanStuk.setXcoordinate(x);
                    opTeSlaanStuk.setYcoordinate(y);
                    speelStukken.add(opTeSlaanStuk);
                }
            }
        }
        speelStukService.saveAll(speelStukken);
    }
}
