package ui;

import model.Board;
import model.Panel;

import javax.swing.*;
import java.awt.*;

// represents CardPanel and buttons contained on the panel
public class CardPanel extends JPanel {

    private static int cardIndex = 0;           // Counts from 0 - X amount of cards
    private static final int CARD_COLS = 4;     // Number of cards per panel
    private final GridLayout cardLayout = new GridLayout(1, CARD_COLS, 60, 20);

    private static Panel firstPanel;
    private static Panel secondPanel;

    private static JButton btn1;
    private static JButton btn2;

    private static int pos1;
    private static int pos2;
    private static int count;

    private Timer timeDelay;

    private MemoryGameGUI ui;                   // to access board

    // EFFECTS: creates panel layout
    public CardPanel(MemoryGameGUI ui) {
        this.ui = ui;

        setLayout(cardLayout);
        createPanelButtons(ui.getGameboard());
        setBackground(new Color(36, 30, 30));

    }

    // MODIFIES: this, CardButton
    // EFFECTS: creates buttons for each panel
    // SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
    private void createPanelButtons(Board gameBoard) {
        for (int i = 0; i < CARD_COLS; i++) {
            Panel panel = gameBoard.getPanelList().get(cardIndex);
            cardIndex++;

            // JButton

            CardButton btn = new CardButton(panel);

            btn.setFont(new Font("Spectre", Font.PLAIN, 40));
            btn.setBackground(Color.LIGHT_GRAY);

            // for loading purposes
            if (panel.getIsFlipped()) {
                btn.setText(panel.getLetter());
                btn.setEnabled(false);
            } else {
                btn.setEnabled(true);
            }

            cardButtonActionListener(btn); // add button listeners for each card

            add(btn); // adds button to the panel
        }
    }

    // MODIFIES: this, CardButton
    //EFFECTS: Creates button listener
    private void cardButtonActionListener(CardButton btn) {
        btn.addActionListener(e -> {
            count++;
            System.out.println("Count: " + count);
            guess(btn);
        });
    }

    // MODIFIES: this, Board
    // EFFECTS: registers the button clicks and assigns panels to them
    private void guess(CardButton btn) {
        if (count == 1) {
            firstPanel = btn.getPanel();
            btn1 = btn;
            pos1 = firstPanel.getPosition();
            btn.setText(firstPanel.getLetter());
            ui.getGameboard().revealPanel(pos1);
            ui.getSaveBut().setEnabled(false);
        } else if (count == 2) {
            secondPanel = btn.getPanel();
            if (checkFirst(btn)) {
                // do nothing
            } else {
                btn2 = btn;
                pos2 = secondPanel.getPosition();
                btn.setText(secondPanel.getLetter());
                ui.getGameboard().revealPanel(pos2);
                checkmatch();
                if (ui.isGameOver()) {
                    cardIndex = 0;
                }
                count = 0;
                ui.getSaveBut().setEnabled(true);
            }
        }

    }

    // MODIFIES: this, board, CardButton
    //EFFECTS: checks if panel letters match, if not reset button and panel (card)
    private void checkmatch() {
        if (ui.getGameboard().isMatching(pos1, pos2)) {
            btn1.setEnabled(false);
            btn2.setEnabled(false);
        } else {
            timeDelay = new Timer(500, e -> {
                btn1.setText("?");
                btn2.setText("?");
            });
            timeDelay.setRepeats(false);
            timeDelay.start();
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets cardIndex to given index
    public void setCardIndex(int index) {
        cardIndex = index;
    }

    // MODIFIES: this, CardButton
    // EFFECTS: reveals buttons
    public void revealButtons() {
        //SOURCE : https://stackoverflow.com/questions/18704904/swing-using-getcomponent-to-update-all-jbuttons/18705604
        for (CardPanel cp : ui.getCardPanels()) {
            cp.reveal();
        }
    }

    // MODIFIES: this, CardButton
    // EFFECTS: sets buttons to false and reveals letters
    private void reveal() {
        for (Component component : this.getComponents()) {
            if (component instanceof JButton) {
                CardButton btn = (CardButton) component;
                Panel panel = btn.getPanel();
                btn.setEnabled(false);
                btn.setText(panel.getLetter());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: checks if secondPanel is the same as the first selection
    private boolean checkFirst(JButton btn) {
        if (secondPanel == firstPanel) {
            JOptionPane.showMessageDialog(this, "Select a different panel!");
            count = 1;
            secondPanel = null;
            return true;
        }
        return false;
    }


}
