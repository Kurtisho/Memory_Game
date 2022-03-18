package ui;

import model.Board;
import model.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


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

    private int rows;

//    private static final LayoutManager playingBoard = new GridLayout(4,4,10,10); // change later


    private CardLayout cl;

    private JPanel mainPanel; // panel everything is worked on

    private JPanel mainMenuPanel; //first action panel
    private JPanel menuLayout;

    private JPanel gamePanel; // second action panel


    private JFrame frame;

    private Board board;
    private ArrayList<CardPanel> cardPanels;




    // main menu
    public MemoryGameGUI() {
        cl = new CardLayout();
        frame = new JFrame(labelSmt);
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
        menuLayout.setBounds(160,200,500,400);

        newBoardButton();
        loadBoardButton();
        quitGameButton();

        mainMenuPanel.add(menuLayout);

    }

    private void newBoardButton() {
        JButton gameBut = new JButton("Create New Board");
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
        JButton loadBut = new JButton("Load Previous Board");
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

//        doSmt();


    }

    //EFFECTS: user specifies how many rows
    private void howManyRows() {
        boolean keepGoing = true;
        while (keepGoing) {
            try {
                String input = JOptionPane.showInputDialog("Enter the number of rows you'd like (MAX: 4)");
                rows = Integer.parseInt(input);
                if (rows > 4 || rows <= 0) {
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
            gamePanel.add(cardPanels.get(i));

        }


    }

//    private void doSmt() {
//        JLabel smt = new JLabel("Hello");
//
//        smt.setBounds(50,200,200,50);
//        smt.setFont(new Font("Spectre", Font.PLAIN,50));
//        smt.setForeground(TITLE_COLOUR);
//
//        gamePanel.add(smt);
//    }






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
