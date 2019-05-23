package nl.koffiepot.Stratego.model.data;

import java.util.List;

public class FrontEndGameData {
    private List<SpeelStukData> speelstukken;
    private int turn;
    private boolean gamerunning;

    public FrontEndGameData(){}

    public void setAll(List<SpeelStukData> speelstukken, int turn, boolean gamerunning){
        this.speelstukken = speelstukken;
        this.gamerunning = gamerunning;
        this.turn = turn;
    }
}
