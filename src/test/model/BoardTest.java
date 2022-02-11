package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private ArrayList<Panel> testPanelList;
    private Board testBoard;


    @BeforeEach
    void runbefore() {
        testPanelList = new ArrayList<Panel>(Arrays.asList(new Panel("A","1"),new Panel("A","2"),
                new Panel("B","3"),new Panel("B","4"),new Panel("C","5"),new Panel("C","6"),new Panel("D","7"),
             new Panel("D","8"),new Panel("E","9"),new Panel("E","10"),new Panel("F","11"),new Panel("F","12"),new Panel("G","13"),new Panel("G","14"),new Panel("H","15"),
             new Panel("H","16")));

        testBoard = new Board();

    }

    @Test
    void testConstructor() {
        assertEquals(16, testPanelList.size());
    }


    @Test
    void testGetPanelList() {
        assertEquals(0, testBoard.getPanelList().size());
        testBoard.getPanelList().add(new Panel("A","1"));
        assertEquals(1, testBoard.getPanelList().size());

    }

    @Test
    void testIsComplete() {
        Panel panel = new Panel("A", "1");
        panel.setIsFlipped(true);
        for (int i = 0; i <= 16; i++) {
            testBoard.getPanelList().add(panel);
        }
        assertTrue(testBoard.isComplete());

    }

    @Test
    void testInNotComplete() {
        Panel panel = new Panel("A", "1");
        panel.setIsFlipped(true);
        for (int i = 0; i <= 15; i++) {
            testBoard.getPanelList().add(panel);
        }
        Panel panel2 = new Panel("B", "2");
        testBoard.getPanelList().add(panel2);

        assertFalse(testBoard.isComplete());
    }
    @Test
    void testRevealPanel() {
        testBoard.getPanelList().add(new Panel("A","1"));
        testBoard.revealPanel(0);
        assertTrue(testBoard.getPanelList().get(0).getIsFlipped());


    }

//    @Test
//    void testIsMatching() {
//        Panel panel = new Panel("A", "1");
//        panel.setIsFlipped(true);
//        testBoard.getPanelList().add(panel);
//
//        Panel panel2 = new Panel("B","3");
//        panel.setIsFlipped(true);
//        testBoard.getPanelList().add(panel2);
//
//        testBoard.isMatching(0, 2);
//
//        assertFalse(panel.getIsFlipped());
//
//
//
//
//    }

}
