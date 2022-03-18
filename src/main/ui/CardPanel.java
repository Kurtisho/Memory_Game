package ui;

import model.Board;
import model.Panel;



import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    private static int cardIndex = 0;           // Counts from 0 - X amount of cards
    private static final int CARD_COLS = 4;     // Number of cards per panel
    private final GridLayout cardLayout = new GridLayout(1,CARD_COLS);

    private static int turns;

    private MemoryGameGUI ui;                   // to access board

    public CardPanel(MemoryGameGUI ui) {
        this.ui = ui;

        setLayout(cardLayout);
        createPanelButtons(ui.getGameboard());

    }

    private void createPanelButtons(Board gameBoard) {
        for (int i = 0; i < CARD_COLS; i++) {
            Panel panel = gameBoard.getPanelList().get(cardIndex);
            cardIndex++;

            // JButton

            CardButton btn = new CardButton(panel);

            btn.setFont(new Font("Spectre", Font.PLAIN, 30));
            btn.setEnabled(true);
            btn.setBackground(Color.LIGHT_GRAY);

            cardButtonActionListener(btn); // add button listeners for each card

            add(btn); // adds button to the panel

        }
    }

    //EFFECTS:
    private void cardButtonActionListener(CardButton btn) {
        btn.addActionListener(e -> {
//            Panel panel = btn.getPanel();
            btn.setEnabled(false);

            guess(btn);
        });
    }


    private void guess(CardButton btn) {
        while (turns < 2) {
            Panel panel = btn.getPanel();
            ui.getGameboard().revealPanel(panel.getPosition() - 1); // sets panel to flipped
//            int pos = panel.getPosition();
            btn.setText(panel.getLetter());
            turns++;

        }
        turns = 0;

        System.out.println("out of turns");
    }
}
