package ui;

import model.Board;
import model.Panel;



import javax.swing.*;
import java.awt.*;

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

    public CardPanel(MemoryGameGUI ui) {
        this.ui = ui;

        setLayout(cardLayout);
        createPanelButtons(ui.getGameboard());
        setBackground(Color.orange);


    }

    // SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
    private void createPanelButtons(Board gameBoard) {
        for (int i = 0; i < CARD_COLS; i++) {
            Panel panel = gameBoard.getPanelList().get(cardIndex);
            cardIndex++;

            // JButton

            CardButton btn = new CardButton(panel);

            btn.setFont(new Font("Spectre", Font.PLAIN, 40));
            btn.setEnabled(true);
            btn.setBackground(Color.LIGHT_GRAY);
//            btn.setPreferredSize(new Dimension(50, 80));


            cardButtonActionListener(btn); // add button listeners for each card

            add(btn); // adds button to the panel

        }
    }

    //EFFECTS:
    private void cardButtonActionListener(CardButton btn) {
        btn.addActionListener(e -> {
//            Panel panel = btn.getPanel();
//            btn.setEnabled(false);            // if set to disabled, will grey out text.
            count++;
            System.out.println("Count: " + count);
            guess(btn);
        });
    }


    private void guess(CardButton btn) {
        // if clicks count is 2, disable all buttons
        if (count == 1) {
            firstPanel = btn.getPanel();
            btn1 = btn;
            pos1 = firstPanel.getPosition();
            btn.setText(firstPanel.getLetter());
            ui.getGameboard().revealPanel(pos1);
        } else if (count == 2) {
            secondPanel = btn.getPanel();
            btn2 = btn;
            pos2 = secondPanel.getPosition();
            btn.setText(secondPanel.getLetter());
            ui.getGameboard().revealPanel(pos2);

            checkmatch();
            if (ui.isGameOver()) {
                cardIndex = 0;
            }
            count = 0;
        }

    }

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

    public void setCardIndex(int index) {
        cardIndex = index;
    }



    public void revealButtons() {
        //SOURCE : https://stackoverflow.com/questions/18704904/swing-using-getcomponent-to-update-all-jbuttons/18705604
        for (CardPanel cp : ui.getCardPanels()) {
            cp.reveal();
        }
    }

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


}
