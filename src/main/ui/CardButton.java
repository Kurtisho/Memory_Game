package ui;

import model.Panel;

import javax.swing.*;

public class CardButton extends JButton {

    private Panel panel;

    public CardButton(Panel panel) {
        super("?");
        this.panel = panel;
    }

    public Panel getPanel() {
        return panel;
    }


}
