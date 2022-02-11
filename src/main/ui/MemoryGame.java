package ui;

import model.Board;
import model.Panel;

import java.util.ArrayList;
import java.util.Scanner;

// modelled after ca.ubc.cpsc210.bank.ui.TellerApp

// Memory game
public class MemoryGame {

    private Board board;
    private Scanner scanner;
    private ArrayList<Long>  myProgress;

    private Integer firstPick;
    private Integer secondPick;

    //MODIFIES: this
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

    //MODIFIES: board
    //EFFECTS: prepares the board for user
    private void prepGame() {
        board = new Board();
        System.out.println("Create your own pairs! Enter a letter: ");
        for (int i = 1; i < 9; i++) {
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine().toUpperCase();

            Panel firstPanel = new Panel(input, String.valueOf(board.getPanelList().size()));
            Panel secondPanel = new Panel(input, String.valueOf(board.getPanelList().size()));

            //firstPanel.setMatchingPanel(secondPanel);

            board.getPanelList().add(firstPanel);
            board.getPanelList().add(secondPanel);

        }
        System.out.println("Board is prepped and ready to play!");
    }

    //add guard later !!!
    //EFFECTS: Allows the user to rate the game
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

    //EFFECTS: prints out each attempt of the user's play
    private void showProgress() {
        for (int i = 0; i < myProgress.size(); i++) {
            int run = i + 1;
            System.out.println("Play-through " + run + " : " + myProgress.get(i) + " seconds");
        }

    }

    //MODIFIES: this
    //EFFECTS: tracks the play though time of each run done by the user
    private void addTime(long elapsed) {
        elapsed /= 1000;
        myProgress.add(elapsed);
    }


    //EFFECTS: displays the menu of the game
    public void displayMenu() {
        System.out.println("Enter \"p\" to play or \"q\" to quit: ");
    }

    //MODIFIES: this
    //EFFECTS: runs the game
    public void playGame() {
        boolean keepGame = true; // game over?
        board.shufflePanels();
        System.out.println("\n");
        printBoard();

        while (keepGame) {
            doTurn(); // player turn
            if (board.isComplete()) {
                System.out.println("Congrats, You finished the game!");
                keepGame = false;
            }
        }

    }

    //MODIFIES: board
    //EFFECTS: Alters the board each time a pair is found
    private void doTurn() {
        selectNumbers(); // player selects 2 numbers
        board.isMatching(firstPick, secondPick); // checks if numbers are matching
        System.out.println("\n");
        printBoard();
    }

    //MODIFIES: board
    //EFFECTS: user inserts 2 numbers
    private void selectNumbers() {
        String firstInput;
        String secondInput;
        // user selects first number, show board and letter
        System.out.println("Select a Number: ");
        Scanner scan1 = new Scanner(System.in);

        // break point to check if valid entry
        firstInput = scan1.nextLine();
        firstPick = checkValidNumber(Integer.parseInt(firstInput)); // will get a valid number
        board.revealPanel(firstPick);
        System.out.println("\n");
        printBoard();

        System.out.println("Select another Number: ");
        Scanner scan2 = new Scanner(System.in);
        //break point to check if valid entry

        secondInput = scan2.nextLine();
        secondPick =  checkValidNumber(Integer.parseInt(secondInput)); // will get a valid number
        board.revealPanel(secondPick);
        System.out.println("\n");
        printBoard();

    }

    //MODIFIES: this
    //EFFECTS: checks if input number is in range
    private Integer checkValidNumber(Integer pick) {
        boolean numberInvalid = true;

        while (numberInvalid) {
            if (1 <= pick && pick <= 16) {
                pick = notAlreadyEntered(pick - 1);
                numberInvalid = false;
            } else {
                System.out.println("Invalid input given, Please enter a Number: ");
                Scanner scan = new Scanner(System.in);
                pick = scan.nextInt();
            }
        }
        return pick;
    }

    //MODIFIES: this
    //EFFECTS: checks if panel at position is flipped
    private Integer notAlreadyEntered(Integer pick) {
        Integer placeHolder;
        boolean alreadyEntered = true;
        while (alreadyEntered) {
            if (!board.getPanelList().get(pick).getIsFlipped()) {
                alreadyEntered = false;

            } else {
                System.out.println("Already found this pair, enter another number: ");
                Scanner scan = new Scanner(System.in);
                placeHolder = scan.nextInt();
                pick = placeHolder - 1;
            }
        }
        return pick;
    }


    //MODIFIES: this
    //EFFECTS: asks user if they would like to play again
    private Boolean playAgain(boolean run) {
        System.out.println("Want to go another round?");
        System.out.println("y -> yes or n -> no");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        // add user guard later
        return (input.equals("y"));
    }

    //EFFECTS: prints out board
    public void printBoard() {
        for (int i = 0; i < board.getPanelList().size(); i += 4) {
            for (int j = 0; j < 4; j++) {
                Panel currentPanel = board.getPanelList().get(i + j);
                if (currentPanel.getIsFlipped()) {
                    System.out.print(board.getPanelList().get(i + j).getLetter());
                } else {
                    System.out.print(board.getPanelList().get(i + j).getPosition());
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
}





