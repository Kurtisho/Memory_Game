package model;

// represents a panel for each board position
public class Panel {

    private String letter;
    private boolean isFlipped;
    private String position;

    //EFFECTS: creates a panel with a letter, position and flip value
    public Panel(String letter, String position) {
        this.letter = letter;
        isFlipped = false;
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


}
