package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class MemoryGameGUI extends JFrame implements ActionListener {

    private static final int PLAYING_WIDTH = 1200;
    private static final int PLAYING_HEIGHT = 900;

    private static final int HUD_WIDTH = 600;
    private static final int HUD_HEIGHT = 600;

    private static final String title = "Memory Game";

    private static final Color BACKGROUND_COLOUR = new Color(36, 30, 30);
    private static final Font TITLE_FONT = new Font("Spectre", Font.PLAIN, 50);
    private static final Color TITLE_COLOUR = new Color(208, 200, 200);
    private static final LayoutManager MENU_LAYOUT = new GridLayout(3,1,20,20);

    private static final Color MAIN_MENU_BUT_COLOUR = new Color(86, 78, 78);
    private static final Font MENU_BUTTON_FONT = new Font("Spectre", Font.PLAIN, 20);
    private static final Dimension MAIN_MENU_BUT_DIM = new Dimension(140, 36);


    private JPanel panel;
    private JPanel menuLayout;



    // main menu
    public MemoryGameGUI() {
        super(title);
        mainMenuHUD();
        // add json
    }

    private void mainMenuHUD() {
        this.setSize(HUD_WIDTH,HUD_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // change later
        this.setResizable(false);
        initMenu();

        this.setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: initializes main menu panel, buttons and labels
    private void initMenu() {
        panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOUR);
        this.setContentPane(panel);

        this.setLayout(null);

        initTitle();
        initMenuButtons();

//        this.setVisible(true);


    }

    private void initTitle() {
        JLabel title = new JLabel("Memory Game");
        title.setFont(TITLE_FONT);
        title.setForeground(TITLE_COLOUR);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds(0,50,550,50);

        panel.add(title);
    }


    private void initMenuButtons() {

        menuLayout = new JPanel();
        menuLayout.setLayout(MENU_LAYOUT);
        menuLayout.setBackground(BACKGROUND_COLOUR);
        menuLayout.setBounds(160,200,250,200);

        newBoardButton();
        loadBoardButton();
        quitGameButton();

        panel.add(menuLayout);



    }



    private void newBoardButton() {
        JButton gameBut = new JButton("Create New Board");
        gameBut.setBackground(MAIN_MENU_BUT_COLOUR);
        gameBut.setForeground(TITLE_COLOUR);
        gameBut.setFont(MENU_BUTTON_FONT);

        // add listener for response

        gameBut.setPreferredSize(MAIN_MENU_BUT_DIM);
        gameBut.setToolTipText("Start the game!");
        menuLayout.add(gameBut);


    }

    private void loadBoardButton() {
        JButton loadBut = new JButton("Load Previous Board");
        loadBut.setBackground(MAIN_MENU_BUT_COLOUR);
        loadBut.setForeground(TITLE_COLOUR);
        loadBut.setFont(MENU_BUTTON_FONT);

        // add listener for response

        loadBut.setPreferredSize(MAIN_MENU_BUT_DIM);
        loadBut.setToolTipText("Load previously saved board");
        menuLayout.add(loadBut);
    }

    private void quitGameButton() {
        JButton quitBut = new JButton("Quit Game");
        quitBut.setBackground(MAIN_MENU_BUT_COLOUR);
        quitBut.setForeground(TITLE_COLOUR);
        quitBut.setFont(MENU_BUTTON_FONT);

        // add listener for response

        quitBut.setPreferredSize(MAIN_MENU_BUT_DIM);
        quitBut.setToolTipText("Close the game");
        menuLayout.add(quitBut);
    }




    // listens to buttons to do action
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
