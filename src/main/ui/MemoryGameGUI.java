package ui;

import model.Board;
import model.Panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


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

    private Timer playAgainDelay;

    private int rows;


//    private static final LayoutManager playingBoard = new GridLayout(4,4,10,10); // change later


    private CardLayout cl;

    private JPanel mainPanel; // panel everything is worked on

    private JPanel mainMenuPanel; //first action panel
    private JPanel menuLayout; // panel where menu buttons are added to

    private JPanel gamePanel; // second action panel
    private JPanel gameBoardPanel;  // panel where panel(card) buttons are added to

    private JPanel endGamePanel; // third action panel -> show winning image

    private JPanel playTimePanel; // fourth action panel -> display playtimes



    private JFrame frame;

    private Board board;
    private ArrayList<CardPanel> cardPanels;

    private ArrayList<Long> myProgress;


    // main menu
    public MemoryGameGUI() {
        cl = new CardLayout();
        frame = new JFrame(labelSmt);
        myProgress = new ArrayList<>();

        frame.setSize(HUD_WIDTH,HUD_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // change later
        frame.setResizable(false);
        frame.setVisible(true);

        mainMenuHUD();



        // add json later
    }

    private void mainMenuHUD() {
        mainPanel = new JPanel();
        mainPanel.setLayout(cl);
        frame.add(mainPanel);
        initMenu(); // makes the menu

        cl.show(mainPanel, "menu");
    }


    private void initMenu() {
        mainMenuPanel = new JPanel();
        mainMenuPanel.setBackground(BACKGROUND_COLOUR);
        mainMenuPanel.setLayout(null);

        initTitle();
        initMenuOptions();


        mainPanel.add(mainMenuPanel, "menu");

    }

    private void initTitle() {
        JLabel title = new JLabel("Memory Game");
        title.setFont(TITLE_FONT);
        title.setForeground(TITLE_COLOUR);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds(100,50,550,100);
        mainMenuPanel.add(title);

    }

    private void initMenuOptions() {
        menuLayout = new JPanel();
        menuLayout.setLayout(MENU_LAYOUT);
        menuLayout.setBackground(BACKGROUND_COLOUR);
        menuLayout.setBounds(140,200,500,400);

        newBoardButton();
        loadBoardButton();
        quitGameButton();

        mainMenuPanel.add(menuLayout);

    }

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

    private void setGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setBackground(BACKGROUND_COLOUR);
//        gamePanel.setLayout(null);

        initGameBoardPanel();
        initializeBoard();

        mainPanel.add(gamePanel, "game");

    }

    //EFFECTS: initializes the pairs and board
    private void initializeBoard() {
        howManyRows(); // gets users desired board

        board = new Board("User's Board", (rows * 4), 0);
        designPanel(); // user designs letters in panel
        board.shufflePanels();

        setUpPanelBoard();
        doSmt();


    }

    //EFFECTS: user specifies how many rows
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

    private void invalidNumberPopUp(String message) {
        JOptionPane.showConfirmDialog(this, message, "Invalid Number!!!", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

    }

    //EFFECTS: users determine the string on panels
    private void designPanel() {

        for (int i = 1; i < (board.getBoardSize() / 2) + 1; i++) {
            String input = JOptionPane.showInputDialog("Enter a letter you'd like!");
            input = input.toUpperCase();

            Panel firstPanel = new Panel(input, board.getPanelList().size(), false);
            Panel secondPanel  = new Panel(input, board.getPanelList().size(), false);

            board.getPanelList().add(firstPanel);
            board.getPanelList().add(secondPanel);
        }

    }

    // creates the individual panels (determined by user's row)
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


    private void initGameBoardPanel() {
        gameBoardPanel = new JPanel();
        gamePanel.setLayout(null);
        gameBoardPanel.setLayout(new GridLayout(5,1, 30, 30));
        gameBoardPanel.setBackground(BACKGROUND_COLOUR);
        gameBoardPanel.setBounds(35,100,700,400);


        gamePanel.add(gameBoardPanel);

    }


    private void doSmt() {
        JLabel smt = new JLabel("Solve!");

        smt.setBounds(300,30,200,50);
        smt.setFont(new Font("Spectre", Font.PLAIN,50));
        smt.setForeground(TITLE_COLOUR);

        gamePanel.add(smt);
    }




    public boolean isGameOver() {
        if (board.isComplete()) {
            gameWonPanel();
            board.endTime();
            addTime(board.getElapsed());
            mainPanel.add(endGamePanel, "game won");
            cl.show(mainPanel, "game won");
            playAgain();

            return true;

        } else {
            // CONTINUE GAME
            return false;
        }
    }

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

    private void playAgain() {
        // TODO change delay time later
        playAgainDelay = new Timer(1000, e -> {
            int result = JOptionPane.showConfirmDialog(endGamePanel,"Want to play again?", "Play Again",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                restartGame();
            } else {
                playTime();
                cl.show(mainPanel, "play time");
            }
        });
        playAgainDelay.setRepeats(false);
        playAgainDelay.start();

    }

    private void restartGame() {
        frame.getContentPane().removeAll();
        frame.repaint();
        mainMenuHUD();
    }

    private void addTime(long currentTime) {
        currentTime /= 1000;
        myProgress.add(currentTime);
    }


    private void playTime() {
        playTimePanel = new JPanel();
        playTimePanel.setBackground(BACKGROUND_COLOUR);

        addPlayTime();

        mainPanel.add(playTimePanel, "play time");
    }

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




    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Create Board" :
                setGamePanel();
                cl.show(mainPanel, "game");
                break;
            case "Load Board":
                System.out.println("Loading");
                break;
            case "Quit Game":
                System.out.println("Quitting Game");
        }
    }


    // EFFECTS: returns the Board associated with this game
    public Board getGameboard() {
        return board;
    }
}
