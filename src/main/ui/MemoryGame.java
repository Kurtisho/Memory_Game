package ui;

import model.Board;
import model.Panel;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



// modelled after ca.ubc.cpsc210.bank.ui.TellerApp from https://github.students.cs.ubc.ca/CPSC210/TellerApp
// modelled template and menu

// represents the Memory Game (user interface)
public class MemoryGame {

    private static final String JSON_STORE = "./data/board.json";
    private Board board;
    private Scanner scanner;
    private ArrayList<Long>  myProgress;

    private Integer firstPick;
    private Integer secondPick;

    private Integer size;

    // time from old board
    private long savedTime;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //EFFECTS: runs the memory game
    public MemoryGame() throws FileNotFoundException {
        myProgress = new ArrayList<Long>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runGame();

    }

    //MODIFIES: this
    //EFFECTS: reads the users inputs
    public void runGame() {
        while (true) {
            displayMenu();
            scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            if (command.equals("p")) {
                prepGame();
                playGame();
                break;
            } else if (command.equals("l")) {
                loadBoard();
                printBoard();
                playGame();
            } else if (command.equals("q")) {
                System.out.println("Goodbye! ...");
                break;
            } else {
                System.out.println("Invalid input, please select one of the options");
            }

        }
    }

    //MODIFIES: board
    //EFFECTS: prepares the board for user
    private void prepGame() {
        validBoardSize();
        System.out.println("Enter your letters: ");
        for (int i = 1; i < ((size / 2) + 1); i++) {
            Scanner scan2 = new Scanner(System.in);
            String input = scan2.nextLine().toUpperCase();

            Panel firstPanel = new Panel(input, String.valueOf(board.getPanelList().size()), false);
            Panel secondPanel = new Panel(input, String.valueOf(board.getPanelList().size()), false);

            board.getPanelList().add(firstPanel);
            board.getPanelList().add(secondPanel);

        }
        board.shufflePanels();
        System.out.println("Board is prepped and ready to play!");
        System.out.println("\n");
        printBoard();
    }

    //MODIFIES: this
    //EFFECTS: sets up parameter for board inputs
    private void validBoardSize() {
        boolean isInvalid = true;
        System.out.println("Create your own pairs! Enter a number divisible by 4! ");
        Scanner scan = new Scanner(System.in);
        size = scan.nextInt();
        while (isInvalid) {
            if ((size % 4 == 0) && (size != 0)) {
                isInvalid = false;
            } else {
                System.out.println("Invalid number! Please enter a number divisible by 4!");
                Scanner scan2 = new Scanner(System.in);
                size = scan.nextInt();
            }
        }
        board = new Board("User's Board", size, 0);
        board.setBoardSize(size);

    }

    //add guard later !!!
    //EFFECTS: Allows the user to rate the game
    private void rateGame() {
        System.out.println("\nRate this game from 1-5!");
        System.out.println("5 - Best game ever!");
        System.out.println("4 - It was really fun");
        System.out.println("3 - It was alright");
        System.out.println("2 - Challenged me a little");
        System.out.println("1 - Not challenging, too easy for me");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        System.out.println("Thank you for rating and playing the game! ");
        System.exit(0);

    }

    //EFFECTS: prints out each attempt of the user's play
    private void showProgress() {
        for (int i = 0; i < myProgress.size(); i++) {
            int run = i + 1;
            System.out.println("\nPlay-through " + run + " : " + myProgress.get(i) + " seconds");
        }

    }

    //MODIFIES: this
    //EFFECTS: tracks the play though time of each run done by the user
    private void addTime(long currentTime, long savedTime) {
        savedTime /= 1000;
        currentTime /= 1000;
        myProgress.add(savedTime + currentTime);
    }


    //EFFECTS: displays the menu of the game
    public void displayMenu() {
        System.out.println("\nWelcome to the Memory Game!");
        System.out.println("Please choose one of the following options");
        System.out.println("\tp -> play");
        System.out.println("\tl -> load previous board");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: runs the game
    public void playGame() {
        board.startTime();

        boolean keepGame = true;
        while (keepGame) {
            doTurn(); // player turn
            if (board.isComplete()) {
                System.out.println("Congrats, You finished the game!");
                board.endTime();
                keepGame = false;
            }
        }
        addTime(board.getElapsed(), board.getSavedTime());
        playAgain();
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
        System.out.println("\nIf you would like to save this game and continue later press \"s\" ");
        String firstInput;
        String secondInput;
        // user selects first number or save,
        System.out.println("Select a Number: ");
        Scanner scan1 = new Scanner(System.in);

        // break point to check if valid entry
        firstInput = scan1.nextLine();
        checkSaveOption(firstInput);
        firstPick = checkValidNumber(Integer.parseInt(firstInput)); // will get a valid number, check if "s"
        board.revealPanel(firstPick);
        System.out.println("\n");
        printBoard();

        System.out.println("Select another Number: ");
        Scanner scan2 = new Scanner(System.in);
        //break point to check if valid entry

        secondInput = scan2.nextLine();
        checkSaveOption(secondInput);
        secondPick =  checkValidNumber(Integer.parseInt(secondInput)); // will get a valid number
        board.revealPanel(secondPick);
        System.out.println("\n");
        printBoard();
    }

    //EFFECTS: checks if save option was selected
    private void checkSaveOption(String input) {

        if (input.equals("s")) {
            board.endTime();
            saveGameBoard();
            System.exit(0);
        }
    }


    //MODIFIES: this
    //EFFECTS: checks if input number is in range
    private Integer checkValidNumber(Integer pick) {
        boolean numberInvalid = true;

        while (numberInvalid) {
            if (1 <= pick && pick <= board.getBoardSize()) {
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
    private void playAgain() {
        while (true) {
            System.out.println("\nWant to go another round?");
            System.out.println("y -> yes or n -> no");
            scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            if (command.equals("y")) {
                prepGame();
                playGame();
                break;
            } else if (command.equals("n")) {
                showProgress();
                rateGame();
                break;
            } else {
                System.out.println("Invalid input, please select one of the options");
            }
        }
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
            if (i == (board.getBoardSize() - 4)) {
                break;
            }
            System.out.println("---------------");
        }

    }

    // EFFECTS: saves the Board to file
    private void saveGameBoard() {
        try {
            jsonWriter.open();
            jsonWriter.write(board);
            jsonWriter.close();
            System.out.println("Saved Board to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads board from file
    private void loadBoard() {
        try {
            board = jsonReader.read();
            System.out.println("Loaded previous board from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}





