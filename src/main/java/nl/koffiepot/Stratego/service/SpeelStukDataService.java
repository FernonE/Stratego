package nl.koffiepot.Stratego.service;

import nl.koffiepot.Stratego.model.Bord;
import nl.koffiepot.Stratego.model.Speelstuk;
import nl.koffiepot.Stratego.model.data.SpeelStukData;
import nl.koffiepot.Stratego.persistance.SpeelStukDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpeelStukDataService {

    @Autowired
    private SpeelStukDataRepository speelStukDataRepository;

    public SpeelStukData save(SpeelStukData speelStukData) {
        return speelStukDataRepository.save(speelStukData);
    }

    //public SpelerData save(SpelerData speler) {
    //    return spelerDataRepository.save(speler);
    //}

    public Iterable<SpeelStukData> saveAll(Iterable<SpeelStukData> speelStukDataList) {
        return speelStukDataRepository.saveAll(speelStukDataList);
    }

    public Iterable<SpeelStukData> findAll() {
        return speelStukDataRepository.findAll();
    }


    public List<SpeelStukData> generateList(Bord spelerBord, String spelNaam) {
        Object[][] bord = spelerBord.getSpeelBord();
        List<SpeelStukData> speelStukDataList = new ArrayList<>();
        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 10; x++){
                if (bord[y][x] instanceof Speelstuk){
                    SpeelStukData speelStukData = new SpeelStukData();
                    speelStukData.setAllData((Speelstuk)bord[y][x], x, y, spelNaam);
                    speelStukDataList.add(speelStukData);
                }
            }
        }
        return speelStukDataList;
    }
}
