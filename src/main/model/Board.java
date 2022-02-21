package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Random;

// represents a list of Panels
public class Board implements Writable {

    private ArrayList<Panel> panelList;
    private String name;
    private Integer boardSize;


    //EFFECTS: creates a new board for the game
    public Board(String name, Integer boardSize) {

        panelList = new ArrayList<>();
        this.name = name;
        this.boardSize = boardSize;

    }


    //MODIFIES: this and panel
    //EFFECTS: reveals the panel value
    public void revealPanel(Integer pos) {
        Panel panel = panelList.get(pos); // gets element at chosen pos
        panel.setIsFlipped(true);
    }


    //MODIFIES: this
    //EFFECTS: shuffles panels and adds to panelList
    public void shufflePanels() {
        ArrayList<Panel> shuffledPanels = new ArrayList<>();
        Random random = new Random();
        int index;
        while (panelList.size() > 0) {
            index = random.nextInt(panelList.size());
            Panel currentPanel = panelList.remove(index);
            currentPanel.setPosition(String.valueOf(shuffledPanels.size() + 1));
            shuffledPanels.add(currentPanel);
        }
        panelList = shuffledPanels;
    }


    //getter
    public ArrayList<Panel> getPanelList() {
        return panelList;
    }

    public String getName() {
        return name;
    }

    //MODIFIES: this and Panel
    //EFFECTS: checks if pair is matching
    public void isMatching(Integer firstPick, Integer secondPick) {
        Panel firstPanel = panelList.get(firstPick);
        Panel secondPanel = panelList.get(secondPick);

        if (!firstPanel.getLetter().equals(secondPanel.getLetter())) {
            firstPanel.setIsFlipped(false);
            secondPanel.setIsFlipped(false);
        }

    }

    //MODIFIES: this
    //EFFECTS: checks if panels are flipped for game to be over
    public boolean isComplete() {
        for (Panel panel : panelList) {
            if (!panel.getIsFlipped()) {
                return false;
            }
        }
        return true;
    }

    //getter
    public Integer getBoardSize() {
        return boardSize;
    }

    //setter
    public void setBoardSize(Integer size) {
        boardSize = size;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("panels", panelstoJson());
        json.put("boardsize", boardSize);
        return json;
    }

    // EFFECTS: returns things in this Board as a JSON array
    private JSONArray panelstoJson() {
        JSONArray jsonArray = new JSONArray();

        for (Panel p : panelList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }


}