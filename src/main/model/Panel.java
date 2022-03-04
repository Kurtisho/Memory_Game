package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a panel for each board position
public class Panel implements Writable {

    private String letter;
    private boolean isFlipped;
    private String position;

    //EFFECTS: creates a panel with a letter, position and flip value
    public Panel(String letter, String position, Boolean flipValue) {
        this.letter = letter;
        isFlipped = flipValue;
        this.position = position;
    }

    //getters
    public String getLetter() {
        return letter;
    }

    public boolean getIsFlipped() {
        return isFlipped;
    }

    public String getPosition() {
        return position;
    }

    //setters
    public void setIsFlipped(Boolean isFlipped) {
        this.isFlipped = isFlipped;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    //EFFECTS: stores panel parameters into JSON array
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Panel Letter", letter);
        json.put("Flipped Value", isFlipped);
        json.put("Position", position);
        return json;
    }



}
