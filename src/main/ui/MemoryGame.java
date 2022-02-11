package ui;

import model.Board;

import java.util.ArrayList;
import java.util.Scanner;


public class MemoryGame {

    private Board board;
    private Scanner scanner;
    private ArrayList<Long>  myProgress;

    private Integer firstPick;
    private Integer secondPick;

    private String firstLetter;
    private String secondLetter;



    //EFFECTS: runs the memory game
    public MemoryGame() {
        myProgress = new ArrayList<Long>();
        runGame();

    }

    //MODIFIES: this
    //EFFECTS: reads the users inputs
    public void runGame() {

        boolean keepRunning = true;

        long start = 0;
        long finish = 0;
        long elapsed;

        while (keepRunning) {
            displayMenu(); // display main menu
            scanner = new Scanner(System.in);

            String input = scanner.nextLine();

            if (input.equals("q")) {
                break;

            } else if (input.equals("p")) {
                prepGame();
                start = System.currentTimeMillis();
                playGame();
                finish = System.currentTimeMillis();
            }

            elapsed = finish - start;
            addTime(elapsed);
            keepRunning = playAgain(keepRunning);
        }

        showProgress();
        rateGame();

    }

    // user selects characters
    private void prepGame() {
        board = new Board();
        System.out.println("Create your own pairs! Enter a letter: ");
        for (int i = 1; i < 9; i++) {
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            input = input.toUpperCase();

            board.getLetters().getLetterList().add(input);
            board.getLetters().getLetterList().add(input);
        }
        System.out.println("Board is prepped and ready to play!");
    }

    // later implement for each play through
    private void rateGame() {
        System.out.println("Rate this game from 1-5!");
        System.out.println("5 - Best game ever!");
        System.out.println("4 - It was really fun");
        System.out.println("3 - It was alright");
        System.out.println("2 - Challenged me a little");
        System.out.println("1 - Not challenging, too easy for me");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        System.out.println("Thank you for rating and playing the game! ");
    }

    private void showProgress() {
        for (int i = 0; i < myProgress.size(); i++) {
            int run = i + 1;
            System.out.println("Play-through " + run + " : " + myProgress.get(i) + " seconds");
        }

    }

    private void addTime(long elapsed) {
        elapsed /= 1000;
        myProgress.add(elapsed);
    }


    //EFFECTS: displays the menu of the game
    public void displayMenu() {
        System.out.println("Enter \"p\" to play or \"q\" to quit: ");
    }

    // main method to play
    public void playGame() {
        boolean keepGame = true; // game over?
        board.prepareGame();

        while (keepGame) {
            doTurn(); // player turn
            if (isGameOver()) {
                System.out.println("Congrats, You finished the game!");
                keepGame = false;
            }
        }

    }

    private void doTurn() {
        selectNumbers(); // player selects 2 numbers
        isMatching(); // checks if numbers are matching
    }

    private void selectNumbers() {
        String firstInput;
        String secondInput;
        // user selects first number, show board and letter
        System.out.println("Select a Number: ");
        Scanner scan1 = new Scanner(System.in);


        // break point to check if valid entry
        firstInput = scan1.nextLine();
        firstPick = checkValidNumber(Integer.parseInt(firstInput)); // will get a valid number
        firstLetter = board.revealLetter(firstPick - 1);


        System.out.println("Select another Number: ");
        Scanner scan2 = new Scanner(System.in);
        //break point to check if valid entry

        secondInput = scan2.nextLine();
        secondPick =  checkValidNumber(Integer.parseInt(secondInput)); // will get a valid number
        secondLetter = board.revealLetter(secondPick - 1);

    }

//    private String checkValidInput() {
//        boolean inputInvalid = true;
//        Scanner scan = new Scanner(System.in);
//        String input = scan.nextLine();
//
//        while(inputInvalid) {
//            if (Integer.parseInt(input)) {
//                input = scan;
//                inputInvalid = false;
//            } else {
//                System.out.println("Please enter an Integer: ");
//            }
//        }
//        return input;
//    }


    private Integer checkValidNumber(Integer pick) {
        boolean numberInvalid = true;

        while (numberInvalid) {
            if (1 <= pick && pick <= 16) {
                pick = notAlreadyEntered(pick);
                numberInvalid = false;
            } else {
                System.out.println("Invalid input given, Please enter a Number: ");
                Scanner scan = new Scanner(System.in);
                pick = scan.nextInt();
            }
        }
        return pick;
    }

    //EFFECTS: checks if number given is already removed
    private Integer notAlreadyEntered(Integer pick) {
        boolean alreadyEntered = true;
        while (alreadyEntered) {
            if (board.getLetters().getavailableInputList().contains(pick)) {
                alreadyEntered = false;

            } else {
                System.out.println("Already found this pair, enter another number: ");
                Scanner scan = new Scanner(System.in);
                pick = scan.nextInt();
            }
        }
        return pick;
    }


    private void isMatching() {
        boolean match;
        match = board.getLetters().isMatchLetter(firstLetter, secondLetter);
        if (match == true) {
            board.letterToBoard(firstPick - 1, secondPick - 1, firstLetter, secondLetter);
            board.getLetters().getavailableInputList().remove(firstPick);
            board.getLetters().getavailableInputList().remove(secondPick);
        } else {
            board.revertBoard(firstPick, secondPick);
        }
    }



    private boolean isGameOver() {
        return board.getLetters().getavailableInputList().size() == 0;
    }

    private Boolean playAgain(boolean run) {
        System.out.println("Want to go another round?");
        System.out.println("y -> yes or n -> no");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        // add user guard later
        return (input.equals("y"));
    }

}





