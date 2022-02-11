package model;



import java.util.ArrayList;
import java.util.Random;

// represents a list of Panels
public class Board {

    private ArrayList<Panel> panelList;

    //EFFECTS: creates a new board for the game
    public Board() {
        panelList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: shuffles and adds panels to the game board, then prints
    public void prepareGame() {
        shufflePanels();
        printBoard();

    }



    //EFFECTS: prints out board
    public void printBoard() {
        for (int i = 0; i < panelList.size(); i += 4) {
            for (int j = 0; j < 4; j++) {
                Panel currentPanel = panelList.get(i + j);
                if (currentPanel.getIsFlipped()) {
                    System.out.print(panelList.get(i + j).getLetter());
                } else {
                    System.out.print(panelList.get(i + j).getPosition());
                }
                if (j == 3) {
                    System.out.print("\n");
                } else {
                    System.out.print(" | ");
                }
            }
            if (i == 12) {
                break;

            }
            System.out.println("---------------");

        }

    }

    //MODIFIES: this and panel
    //EFFECTS: reveals the panel value
    public void revealPanel(Integer pos) {
        Panel panel = panelList.get(pos); // gets element at chosen pos
        panel.setIsFlipped(true);
    }


    //MODIFIES: this
    //EFFECTS: shuffles panels and adds to panelList
    public void shufflePanels() {
        ArrayList<Panel> shuffledPanels = new ArrayList<>();
        Random random = new Random();
        int index;
        while (panelList.size() > 0) {
            index = random.nextInt(panelList.size());
            Panel currentPanel = panelList.remove(index);
            currentPanel.setPosition(String.valueOf(shuffledPanels.size() + 1));
            shuffledPanels.add(currentPanel);
        }
        panelList = shuffledPanels;
    }



    public ArrayList<Panel> getPanelList() {
        return panelList;
    }

    //MODIFIES: this
    //EFFECTS: checks if pair is matching
    public void isMatching(Integer firstPick, Integer secondPick) {
        Panel firstPanel = panelList.get(firstPick);
        Panel secondPanel = panelList.get(secondPick);

        if (!firstPanel.getLetter().equals(secondPanel.getLetter())) {

            firstPanel.setIsFlipped(false);
            secondPanel.setIsFlipped(false);
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
        return true;
    }





}