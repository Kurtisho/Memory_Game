package model;

public class Panel {

    private String letter;
    private boolean isFlipped;
    private String position;

    private Panel matchingPanel;

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


    public void setMatchingPanel(Panel panel) {
        this.matchingPanel = panel;
        panel.matchingPanel = this;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }




}
