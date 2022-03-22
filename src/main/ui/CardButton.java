package ui;

import model.Panel;

import javax.swing.*;

// represents a button (4 in total)  per panel
public class CardButton extends JButton {

    private Panel panel;

    // EFFECTS: sets button to "?" and assign a panel to it
    public CardButton(Panel panel) {
        super("?");
        this.panel = panel;
    }

    // EFFECTS: returns assigned panel
    public Panel getPanel() {
        return panel;
    }


}
