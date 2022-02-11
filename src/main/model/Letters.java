package model;

import java.util.*;

public class Letters {


    private ArrayList<String> letterList;
    private ArrayList<String> shuffledList;
    private Set<Integer> availableInputList;

    public Letters() {
        letterList = new ArrayList<>();
        shuffledList = new ArrayList<>();
        availableInputList = new HashSet<>();

    }

    public void shuffleLetters() {
        Random random = new Random();
        int index;

        while (letterList.size() > 0) {
            index = random.nextInt(letterList.size());
            shuffledList.add(letterList.remove(index));
        }


    }

    public void inputList() {
        for (int i = 1; i <= 16; i++) {
            availableInputList.add(i);
        }
    }


    public boolean isMatchLetter(String letter1, String letter2) {
        if (letter1.equals(letter2)) {
            System.out.println("Yay! you got a pair!");

            return true;
        }
        System.out.println("Sorry! this was not a pair!");
        return false;
    }

    public ArrayList<String> getShuffledList() {
        return shuffledList;
    }

    public Set<Integer> getavailableInputList() {
        return availableInputList;

    }

    public ArrayList<String> getLetterList() {
        return letterList;
    }


}
