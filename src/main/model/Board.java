package model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;


public class Board {

    private ArrayList<Panel> panelList;

    public Board() {
        panelList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: prepares the cards according to the level given
    public void prepareGame() {
        shufflePanels();
        printBoard();

    }


    //MODIFIES:
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


    public void revealPanel(Integer pos) {
        Panel panel = panelList.get(pos); // gets element at chosen pos
        panel.setIsFlipped(true);
    }



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


    public void isMatching(Integer firstPick, Integer secondPick) {
        Panel firstPanel = panelList.get(firstPick);
        Panel secondPanel = panelList.get(secondPick);

        if (!firstPanel.getLetter().equals(secondPanel.getLetter())) {
            System.out.println("Sorry! this was not a pair!");
            firstPanel.setIsFlipped(false);
            secondPanel.setIsFlipped(false);
        } else {
            System.out.println("\nYay! you got a pair!");
        }

    }

    public boolean isComplete() {
        for (Panel panel : panelList) {
            if (!panel.getIsFlipped()) {
                return false;
            }
        }
        return true;
    }





}