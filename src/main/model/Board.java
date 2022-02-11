package model;

import java.util.ArrayList;
import java.util.Set;


public class Board {


    private  ArrayList<String> boardList = new ArrayList<String>();
    private Letters letters;

    public Board() {
        letters = new Letters();
    }

    //MODIFIES: this
    //EFFECTS: prepares the cards according to the level given
    public void prepareGame() {
        for (int i = 1; i <= 16; i++) {
            boardList.add(String.valueOf(i)); // makes board into strings
        }
        letters.shuffleLetters();
        letters.inputList();
        printBoard();

    }


    //MODIFIES:
    //EFFECTS: prints out board
    public void printBoard() {

        for (int i = 0; i < boardList.size(); i += 4) {
            for (int j = 0; j < 4; j++) {
                System.out.print(boardList.get(i + j));
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


//    //EFFECTS: returns value at given index
//    public String getValue(int index) {
//        return boardList.get(index - 1);
//    }


    public boolean checkBoard() {
        boolean isValid = true;
        while (isValid) {
            for (String str : boardList) {
                if (str.equals("X")) {
                    isValid = true;
                }
                isValid = false;
            }
        }
        if (isValid == true) {
            return true;
        }
        return false;

    }

    public ArrayList<String> getBoardList() {
        return boardList;
    }

    public String revealLetter(Integer pos) {
        String replace = letters.getShuffledList().get(pos); // gets element at chosen pos
        boardList.set(pos, replace); //inserts letter at index
        printBoard();
        return replace;
    }

    public void revertBoard(Integer pos1, Integer pos2) {
        boardList.set(pos1 - 1,String.valueOf(pos1));
        boardList.set(pos2 - 1,String.valueOf(pos2));
        printBoard();

    }

    public void letterToBoard(Integer pos1, Integer pos2, String letter1, String letter2) {
        boardList.set(pos1, letter1);
        boardList.set(pos2, letter2);
        printBoard();

    }

    public Letters getLetters() {
        return letters;
    }
}