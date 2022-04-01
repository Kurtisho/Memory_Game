package model;


import org.json.JSONArray;
import org.json.JSONObject;
//import persistence.JsonReader;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Random;

// represents a list of Panels
public class Board implements Writable {

    private ArrayList<Panel> panelList;
    private String name;
    private Integer boardSize;


    private long start;
    private long end;
    private long elapsed;

    private int count;

    private long savedTime;


    //EFFECTS: creates a new board for the game
    public Board(String name, Integer boardSize, long savedTime) {

        panelList = new ArrayList<>();
        this.name = name;
        this.boardSize = boardSize;
        this.savedTime = savedTime;

    }


    //MODIFIES: this and panel
    //EFFECTS: reveals the panel value
    public void revealPanel(Integer pos) {
        Panel panel = panelList.get(pos - 1); // gets element at chosen pos
        panel.setIsFlipped(true);
    }

    //getter-
    public long getElapsed() {
        return elapsed;
    }

    public long getSavedTime() {
        return savedTime;
    }

    public Integer getBoardSize() {
        return boardSize;
    }

    public ArrayList<Panel> getPanelList() {
        return panelList;
    }

    public String getName() {
        return name;
    }

    //setter
    public void setBoardSize(Integer size) {
        boardSize = size;
    }


    //MODIFIES: this, EventLog
    //EFFECTS: shuffles panels and adds to panelList
    public int shufflePanels() {
        ArrayList<Panel> shuffledPanels = new ArrayList<>();
        Random random = new Random();
        int index;
        while (panelList.size() > 0) {
            index = random.nextInt(panelList.size());
            Panel currentPanel = panelList.remove(index);
            currentPanel.setPosition((shuffledPanels.size() + 1));
            shuffledPanels.add(currentPanel);
        }
        panelList = shuffledPanels;
        count++;
        if (count > 1) {
            EventLog.getInstance().logEvent(new Event("The Board has been shuffled!"));
        }

        return count;
    }


    //MODIFIES: this and Panel
    //EFFECTS: checks if pair is matching
    public boolean isMatching(Integer firstPick, Integer secondPick) {
        Panel firstPanel = panelList.get(firstPick - 1);
        Panel secondPanel = panelList.get(secondPick - 1);

        if (!firstPanel.getLetter().equals(secondPanel.getLetter())) {
            firstPanel.setIsFlipped(false);
            secondPanel.setIsFlipped(false);
            return false;
        } else {
            return true;
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
        EventLog.getInstance().logEvent(new Event("Game won!"));
        return true;
    }

    // MODIFIES: this, EventLog
    // EFFECTS: adds panel to board
    public void addPanel(Panel panel) {
        panelList.add(panel);
        EventLog.getInstance()
                .logEvent(new Event("Added panel: " + panel.getLetter() + " || board pos: "
                        + (panel.getPosition())));
    }

    // MODIFIES: this, EventLog
    // EFFECTS: sets panel flipvalue to true
    public void revealBoard() {
        for (Panel p : panelList) {
            p.setIsFlipped(true);
        }
        EventLog.getInstance().logEvent(new Event("The Board has been revealed, Game Over!"));
    }



    //MODIFIES: this
    //EFFECTS: starts clock
    public long startTime() {
        start = System.currentTimeMillis();
        return start;
    }

    //MODIFIES: this
    //EFFECTS: ends clock, calculates time elapsed
    public long endTime() {
        end = System.currentTimeMillis();
        elapsed = end - start;
        return end;
    }


    //EFFECTS: stores parameters of board to JSON array
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("panels", panelstoJson());
        json.put("boardsize", boardSize);
        json.put("time",elapsed + savedTime);
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