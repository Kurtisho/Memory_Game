package ui;

import model.Board;
import model.Panel;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// represents the Memory Game (Graphical User Interface)
public class MemoryGameGUI extends JFrame implements ActionListener {

    private static final int HUD_WIDTH = 800;
    private static final int HUD_HEIGHT = 800;

    private static final String labelSmt = "Memory Game";

    private static final Color BACKGROUND_COLOUR = new Color(36, 30, 30);
    private static final Font TITLE_FONT = new Font("Spectre", Font.PLAIN, 80);
    private static final Color TITLE_COLOUR = new Color(208, 200, 200);
    private static final LayoutManager MENU_LAYOUT = new GridLayout(3,1,50,50);

    private static final Color MAIN_MENU_BUT_COLOUR = new Color(86, 78, 78);
    private static final Font MENU_BUTTON_FONT = new Font("Spectre", Font.PLAIN, 50);
    private static final Dimension MAIN_MENU_BUT_DIM = new Dimension(500, 250);

    // timer delay
    private Timer playAgainDelay;

    // data persistence
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static String JSON_STORE = "./data/board.json";

    private int rows;

    private CardLayout cl;

    // Swing components
    private JPanel mainPanel; // panel everything is worked on

    private JPanel mainMenuPanel; //first action panel
    private JPanel menuLayout; // panel where menu buttons are added to

    private JPanel gamePanel; // second action panel
    private JPanel gameBoardPanel;  // panel where panel(card) buttons are added to

    private JPanel endGamePanel; // third action panel -> show winning image

    private JPanel playTimePanel; // fourth action panel -> display playtimes

    private JPanel ratePanel; // fifth action panel -> rate the game

    private JPanel playOptionPanel;

    private JFrame frame;

    // Board components
    private Board board;
    private ArrayList<CardPanel> cardPanels;

    private ArrayList<Long> myProgress;


    // EFFECTS: Sets up the frame for the rest of GUI
    public MemoryGameGUI() {
        cl = new CardLayout();
        frame = new JFrame(labelSmt);
        myProgress = new ArrayList<>();

        frame.setSize(HUD_WIDTH,HUD_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // change later
        frame.setResizable(false);
        frame.setVisible(true);

        initDataPersistence();
        mainMenuHUD();

    }

    // MODIFIES: this
    // EFFECTS: makes the main panel and main menu
    private void mainMenuHUD() {
        mainPanel = new JPanel();
        mainPanel.setLayout(cl);
        frame.add(mainPanel);
        initMenu(); // makes the menu

        cl.show(mainPanel, "menu");
    }

    // MODIFIES: this
    // EFFECTS: creates title and main menu buttons
    private void initMenu() {
        mainMenuPanel = new JPanel();
        mainMenuPanel.setBackground(BACKGROUND_COLOUR);
        mainMenuPanel.setLayout(null);

        initTitle();
        initMenuOptions();

        mainPanel.add(mainMenuPanel, "menu");
    }

    // MODIFIES: this
    // EFFECTS: creates title
    private void initTitle() {
        JLabel title = new JLabel("Memory Game");
        title.setFont(TITLE_FONT);
        title.setForeground(TITLE_COLOUR);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds(100,50,550,100);
        mainMenuPanel.add(title);

    }

    // MODIFIES: this
    // EFFECTS: creates menu layout and menu buttons
    private void initMenuOptions() {
        menuLayout = new JPanel();
        menuLayout.setLayout(MENU_LAYOUT);
        menuLayout.setBackground(BACKGROUND_COLOUR);
        menuLayout.setBounds(140,250,500,400);

        newBoardButton();
        loadBoardButton();
        quitGameButton();

        mainMenuPanel.add(menuLayout);
    }

    // MODIFIES: this
    // EFFECTS: creates new board button for play
    private void newBoardButton() {
        JButton gameBut = new JButton("Create Board");
        gameBut.setBackground(MAIN_MENU_BUT_COLOUR);
        gameBut.setForeground(TITLE_COLOUR);
        gameBut.setFont(MENU_BUTTON_FONT);

        gameBut.setActionCommand("Create Board");
        gameBut.addActionListener(this);

        gameBut.setPreferredSize(MAIN_MENU_BUT_DIM);
        gameBut.setToolTipText("Start the game!");
        menuLayout.add(gameBut);

    }

    // MODIFIES: this
    // EFFECTS: creates load button for loading previous play-through
    private void loadBoardButton() {
        JButton loadBut = new JButton("Load Board");
        loadBut.setBackground(MAIN_MENU_BUT_COLOUR);
        loadBut.setForeground(TITLE_COLOUR);
        loadBut.setFont(MENU_BUTTON_FONT);

        loadBut.setActionCommand("Load Board");
        loadBut.addActionListener(this);

        loadBut.setPreferredSize(MAIN_MENU_BUT_DIM);
        loadBut.setToolTipText("Load previously saved board");
        menuLayout.add(loadBut);
    }

    // MODIFIES: this
    // EFFECTS: creates quit button to exit application
    private void quitGameButton() {
        JButton quitBut = new JButton("Quit Game");
        quitBut.setBackground(MAIN_MENU_BUT_COLOUR);
        quitBut.setForeground(TITLE_COLOUR);
        quitBut.setFont(MENU_BUTTON_FONT);

        quitBut.setActionCommand("Quit Game");
        quitBut.addActionListener(this);

        quitBut.setPreferredSize(MAIN_MENU_BUT_DIM);
        quitBut.setToolTipText("Close the game");
        menuLayout.add(quitBut);
    }

    // MODIFIES: this
    // EFFECTS: initializes game board panel and board panels
    private void setGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setBackground(BACKGROUND_COLOUR);

        initGameBoardPanel();
        initializeBoard();

        mainPanel.add(gamePanel, "game");
    }

    // MODIFIES: this
    // EFFECTS: initializes the pairs and board panel
    private void initializeBoard() {
        if (board.getBoardSize() == 0) {
            howManyRows(); // gets users desired board
            board.setBoardSize(rows * 4);
            designPanel(); // user designs letters in panel
            board.shufflePanels();
        }

        setUpPanelBoard(); // setup panels for game
        boardMessage(); // setup title of game panel
        setUpPlayOptions(); // setup play options
    }

    // MODIFIES: this
    //EFFECTS: specifies row size
    private void howManyRows() {
        boolean keepGoing = true;
        while (keepGoing) {
            try {
                String input = JOptionPane.showInputDialog("Enter the number of rows you'd like (MAX: 5)");
                rows = Integer.parseInt(input);
                if (rows > 5 || rows <= 0) {
                    throw new NumberFormatException();
                }
                keepGoing = false;
            } catch (NumberFormatException e) {
                invalidNumberPopUp("Please enter a valid number!"); // invalid number given or > 4
            }
        }

    }

    // EFFECTS: Gives error message
    private void invalidNumberPopUp(String message) {
        JOptionPane.showConfirmDialog(this, message, "Invalid Number!!!", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: creates panels for board
    private void designPanel() {

        for (int i = 1; i < (board.getBoardSize() / 2) + 1; i++) {
            String input = JOptionPane.showInputDialog("Enter a letter you'd like!");
            input = input.toUpperCase();

            Panel firstPanel = new Panel(input, board.getPanelList().size(), false);
            board.getPanelList().add(firstPanel);
            Panel secondPanel  = new Panel(input, board.getPanelList().size(), false);
            board.getPanelList().add(secondPanel);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the individual panels for buttons
    private void setUpPanelBoard() {
        cardPanels = new ArrayList<>();
        // Create the rows of panels and add cards to each panel
        for (int i = 0; i < rows; i++) {
            CardPanel cardPanel = new CardPanel(this);
            cardPanels.add(cardPanel);
            gameBoardPanel.add(cardPanels.get(i));
        }
        board.startTime();
    }

    // MODIFIES: this
    // EFFECTS: creates game board panel for individual panels with buttons
    private void initGameBoardPanel() {
        gameBoardPanel = new JPanel();
        gamePanel.setLayout(null);
        gameBoardPanel.setLayout(new GridLayout(5,1, 30, 30));
        gameBoardPanel.setBackground(BACKGROUND_COLOUR);
        gameBoardPanel.setBounds(35,100,700,400);

        gamePanel.add(gameBoardPanel);
    }

    // MODIFIES: this
    // EFFECTS: display message on board panel
    private void boardMessage() {
        JLabel smt = new JLabel("Solve!");

        smt.setBounds(300,30,200,50);
        smt.setFont(new Font("Spectre", Font.PLAIN,50));
        smt.setForeground(TITLE_COLOUR);

        gamePanel.add(smt);
    }

    // MODIFIES: this
    // EFFECTS: create play button options at bottom
    private void setUpPlayOptions() {
        playOptionPanel = new JPanel();
        playOptionPanel.setLayout(null);
        playOptionPanel.setLayout(new GridLayout(1, 3, 50,80));
        playOptionPanel.setBounds(0, 650, 800, 50); // reset later
        playOptionPanel.setBackground(BACKGROUND_COLOUR);

        initGiveUpBut();
        initReshuffleBut();
        initSaveBut();

        gamePanel.add(playOptionPanel);

    }

    // MODIFIES: this
    // EFFECTS: Creates reveal button
    private void initGiveUpBut() {
        JButton revealBut = new JButton("Reveal");
        revealBut.setBackground(MAIN_MENU_BUT_COLOUR);
        revealBut.setForeground(TITLE_COLOUR);
        revealBut.setFont(new Font("Spectre", Font.PLAIN, 30));

        revealBut.setActionCommand("Reveal");
        revealBut.addActionListener(this);

        revealBut.setToolTipText("Forfeit Board");
        playOptionPanel.add(revealBut);

    }

    // MODIFIES: this
    // EFFECTS: Creates Shuffle button
    private void initReshuffleBut() {
        JButton reshuffleBut = new JButton("Reshuffle");
        reshuffleBut.setBackground(MAIN_MENU_BUT_COLOUR);
        reshuffleBut.setForeground(TITLE_COLOUR);
        reshuffleBut.setFont(new Font("Spectre", Font.PLAIN, 30));

        reshuffleBut.setActionCommand("Reshuffle");
        reshuffleBut.addActionListener(this);

        reshuffleBut.setToolTipText("Reshuffle the board");
        playOptionPanel.add(reshuffleBut);
    }

    // MODIFIES: this
    // EFFECTS: Creates save button
    private void initSaveBut() {
        JButton saveBut = new JButton("Save & Quit");
        saveBut.setBackground(MAIN_MENU_BUT_COLOUR);
        saveBut.setForeground(TITLE_COLOUR);
        saveBut.setFont(new Font("Spectre", Font.PLAIN, 30));

        saveBut.setActionCommand("Save");
        saveBut.addActionListener(this);

        saveBut.setToolTipText("Save the board");
        playOptionPanel.add(saveBut);
    }

    // EFFECTS: Checks if game is over
    public boolean isGameOver() {
        if (board.isComplete()) {
            gameWonPanel();
            board.endTime();
            addTime(board.getElapsed(), board.getSavedTime());
            mainPanel.add(endGamePanel, "game won");
            cl.show(mainPanel, "game won");
            playAgain();

            return true;

        } else {
            // CONTINUE GAME
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: displays winning image
    private void gameWonPanel() {
        endGamePanel = new JPanel();

        BufferedImage image;
        ImageIcon icon;

        try {
            image = ImageIO.read(new File(".\\data\\winning-image.jpg"));

            icon = new ImageIcon(image);

            JLabel jl = new JLabel();
            jl.setPreferredSize(new Dimension(800,800));
            jl.setIcon(icon);
            jl.setHorizontalAlignment(JLabel.CENTER);
            endGamePanel.add(jl);
        } catch (IOException e) {
            System.out.println("Image not found!"); // only display in console
        }
    }

    // MODIFIES: this
    // EFFECTS: Gives player opportunity to play again
    private void playAgain() {
        playAgainDelay = new Timer(1000, e -> {
            int result = JOptionPane.showConfirmDialog(endGamePanel,"Want to play again?", "Play Again",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                restartGame();
            } else {
                playTime();
                cl.show(mainPanel, "play time");
                rateGame();
            }
        });
        playAgainDelay.setRepeats(false);
        playAgainDelay.start();

    }


    // MODIFIES: this
    // EFFECTS: resets frame
    private void restartGame() {
        frame.getContentPane().removeAll();
        frame.repaint();
        mainMenuHUD();
    }

    // MODIFIES: this
    // EFFECTS: adds play time to progress list
    private void addTime(long currentTime, long savedTime) {
        currentTime /= 1000;
        savedTime /= 1000;
        myProgress.add(currentTime + savedTime);
    }


    // MODIFIES: this
    // EFFECTS: Creates play time panel to display
    private void playTime() {
        playTimePanel = new JPanel();
        playTimePanel.setBackground(BACKGROUND_COLOUR);

        addPlayTime();

        mainPanel.add(playTimePanel, "play time");
    }

    // MODIFIES: this
    // EFFECTS: adds play through to play time panel
    private void addPlayTime() {
        for (int i = 0; i < myProgress.size(); i++) {
            int run = i + 1;
            String text = "\nPlay-through " + run + " : " + myProgress.get(i) + " seconds";
            JLabel label = new JLabel(text);
            label.setFont(new Font("Spectre", Font.PLAIN, 50));
            label.setForeground(Color.white);
            playTimePanel.add(label);
            System.out.println("\nPlay-through " + run + " : " + myProgress.get(i) + " seconds");
        }
    }

    // MODIFIES: this
    // EFFECTS: reveals all buttons letters
    private void revealButtons() {
        CardPanel cardPanel = cardPanels.get(0);
        cardPanel.revealButtons();
    }

    // MODIFIES: this
    // EFFECTS: shuffles buttons
    private void shuffleBoard() {
        CardPanel cardPanel = cardPanels.get(0);
        cardPanel.setCardIndex(0);

        gameBoardPanel.removeAll();
        gameBoardPanel.repaint();
        board.shufflePanels();
        setUpPanelBoard();

        gameBoardPanel.validate();
    }

    // MODIFIES: this
    // EFFECTS: creates rating panel
    private void rateGame() {
        ratePanel = new JPanel();
        ratePanel.setBackground(BACKGROUND_COLOUR);
        ratePanel.setLayout(null);
        ratePanel.setLayout(new GridLayout(5,1,80,25));

        rateOptions();

        mainPanel.add(ratePanel, "rate");

        Timer delay = new Timer(3000, e -> {
            cl.show(mainPanel, "rate");
            rate();
            thankYou();
        });
        delay.setRepeats(false);
        delay.start();

    }

    // MODIFIES: this
    // EFFECTS: creates label for rating panel
    private void rateOptions() {
        for (int i = 1; i <= 5; i++) {
            String text = getLabelOption(i);
            JLabel label = new JLabel(text);

            label.setFont(new Font("Spectre", Font.PLAIN, 35));
            label.setForeground(Color.white);
            ratePanel.add(label);

        }
    }

    // EFFECTS : returns string for label
    private String getLabelOption(int i) {
        switch (i) {
            case 1 : return "5 - Best game ever!";
            case 2: return "4 - It was really fun";
            case 3 : return "3 - It was alright";
            case 4: return "2 - Challenged me a little";
            default: return "1 - Not challenging, too easy for me";
        }
    }


    // EFFECTS: rating method and checks user input
    private void rate() {
        boolean keepGoing = true;

        while (keepGoing) {
            try {
                int result = Integer.parseInt(JOptionPane.showInputDialog(ratePanel, "Enter a selection"));

                if (result > 5 || result <= 0) {
                    throw new NumberFormatException();
                }
                keepGoing = false;
            } catch (NumberFormatException d) {
                invalidNumberPopUp("Please enter a valid number!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays thank you image at the end
    private void thankYou() {
        JPanel thankYou = new JPanel();

        BufferedImage image;
        ImageIcon icon;

        try {
            image = ImageIO.read(new File(".\\data\\thank-you.jpg"));

            icon = new ImageIcon(image);

            JLabel jl = new JLabel();
            jl.setPreferredSize(new Dimension(800,800));
            jl.setIcon(icon);
            jl.setHorizontalAlignment(JLabel.CENTER);
            thankYou.add(jl);
            mainPanel.add(thankYou, "final");
            showFinal();

        } catch (IOException e) {
            System.out.println("Image not found!"); // only display in console
        }

    }

    // MODIFIES: this
    // EFFECTS: Switches card layout to final
    private void showFinal() {
        cl.show(mainPanel, "final");
        Timer delay = new Timer(3000, e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        delay.setRepeats(false);
        delay.start();
    }

    // MODIFIES: this
    // EFFECTS: initializes data persistence
    private void initDataPersistence() {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: Ends time and closes frame
    private void saveGame() {
        board.endTime();
        saveGameBoard();
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    // MODIFIES: this
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
            rows = board.getBoardSize() / 4;
            setGamePanel();
            System.out.println("Loaded previous board from " + JSON_STORE);
            cl.show(mainPanel, "game");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: performs action depending on given command
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Create Board" :
                board = new Board("User's Board", 0, 0);
                setGamePanel();
                cl.show(mainPanel, "game");
                break;
            case "Load Board":
                loadBoard();
                break;
            case "Quit Game":
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                break;
            case "Reveal" :
                revealButtons();
                break;
            case "Reshuffle":
                shuffleBoard();
                break;
            default: saveGame();
        }
    }


    // EFFECTS: returns the Board associated with this game
    public Board getGameboard() {
        return board;
    }

    // EFFECTS: returns list of CardPanel
    public ArrayList<CardPanel> getCardPanels() {
        return cardPanels;
    }

}