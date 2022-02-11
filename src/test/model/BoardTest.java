package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {

    private ArrayList<Panel> panelList;
    private Board board;


    @BeforeEach
    void runbefore() {
        panelList = new ArrayList<Panel>(Arrays.asList(new Panel("A","1"),new Panel("A","2"),
                new Panel("B","3"),new Panel("B","4"),new Panel("C","5"),new Panel("C","6"),new Panel("D","7"),
             new Panel("D","8"),new Panel("E","9"),new Panel("E","10"),new Panel("F","11"),new Panel("F","12"),new Panel("G","13"),new Panel("G","14"),new Panel("H","15"),
             new Panel("H","16")));

    }

    @Test
    void testConstructor() {
        assertEquals(16, panelList.size());
    }

    @Test
    void testPrepareGame() {
         board.prepareGame();
         assertEquals(16, board.getPanelList().size());

    }


    @Test
    void testGetPanelList() {
        assertEquals(panelList, board.getPanelList());
    }

    @Test
    void testIsComplete() {
        Panel panel = new Panel("A", "1");
        panel.setIsFlipped(true);
        for (int i = 0; i <= 16; i++) {
            panelList.add(panel);
        }

        assertTrue(board.isComplete());

    }

//    @Test
//    void testIsMatching() {
//
//        panelList.isMatching(0, 1);
//
//        assertEquals("A",board.getPanelList().get(0).getLetter());
//
//
//
//
//    }

}
