package model;

// represents a panel for each board position
public class Panel {

    private String letter;
    private boolean isFlipped;
    private String position;

    private Panel matchingPanel;

    //EFFECTS: creates a panel with a letter and a position
    public Panel(String letter, String position) {
        this.letter = letter;
        isFlipped = false;
        matchingPanel = null;
        this.position = position;
    }

    public String getLetter() {
        return letter;
    }

    public boolean getIsFlipped() {
        return isFlipped;
    }

    public void setIsFlipped(Boolean isFlipped) {
        this.isFlipped = isFlipped;
    }


//    //EFFECTS: sets panel to be matching
//    public void setMatchingPanel(Panel panel) {
//        this.matchingPanel = panel;
//        panel.matchingPanel = this;
//    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }




}
