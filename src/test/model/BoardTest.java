package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

// class represents the testing for the Board class
public class BoardTest {

    private ArrayList<Panel> testPanelList;
    private Board testBoard;


    @BeforeEach
    void runbefore() {
        testPanelList = new ArrayList<Panel>(Arrays.asList(new Panel("A",0, false),new Panel("A",1,false),
                new Panel("B",2,false),new Panel("B",3,false),new Panel("C",4,false),new Panel("C",5,false),new Panel("D",6,false),
             new Panel("D",7,false),new Panel("E",8,false),new Panel("E",9,false),new Panel("F",10,false),new Panel("F",11,false),new Panel("G",12,false),new Panel("G",13,false),new Panel("H",14,false),
             new Panel("H",15,false)));

        // size of board is based on user input, for this purpose it is 16
        testBoard = new Board("Test Board", 16, 0);

    }

    @Test
    void testConstructor() {
        assertEquals(16, testPanelList.size());
    }


    @Test
    void testGetPanelList() {
        assertEquals(0, testBoard.getPanelList().size());
        testBoard.getPanelList().add(new Panel("A",1,false));
        assertEquals(1, testBoard.getPanelList().size());

    }

    @Test
    void testIsComplete() {
        Panel panel = new Panel("A", 1,false);
        panel.setIsFlipped(true);
        for (int i = 0; i <= 16; i++) {
            testBoard.getPanelList().add(panel);
        }
        assertTrue(testBoard.isComplete());

    }

    @Test
    void testInNotComplete() {
        Panel panel = new Panel("A", 1,false);
        panel.setIsFlipped(true);
        for (int i = 0; i <= 15; i++) {
            testBoard.getPanelList().add(panel);
        }
        Panel panel2 = new Panel("B", 2,false);
        testBoard.getPanelList().add(panel2);

        assertFalse(testBoard.isComplete());
    }
    @Test
    void testRevealPanel() {
        testBoard.getPanelList().add(new Panel("A",1,false));
        testBoard.revealPanel(1);
        assertTrue(testBoard.getPanelList().get(0).getIsFlipped());


    }

    @Test
    void testIsNotMatching() {
        Panel panel = new Panel("A", 0,false);
        panel.setIsFlipped(true);
        testBoard.getPanelList().add(panel);

        Panel panel2 = new Panel("B",1,false);
        panel2.setIsFlipped(true);
        testBoard.getPanelList().add(panel2);

        boolean test = testBoard.isMatching(1,2);

        assertFalse(test);

        assertFalse(panel.getIsFlipped());
        assertFalse(panel2.getIsFlipped());

    }

    @Test
    void testIsMatching() {
        Panel panel = new Panel("A", 0,false);
        panel.setIsFlipped(true);
        testBoard.getPanelList().add(panel);

        Panel panel2 = new Panel("A",1,false);
        panel2.setIsFlipped(true);
        testBoard.getPanelList().add(panel2);


        assertTrue(panel.getIsFlipped());
        assertTrue(panel2.getIsFlipped());

        assertTrue(testBoard.isMatching(1,2));
    }

    // Note: this can fail sometimes but it is 2/16 chance
    @Test
    void testShufflePannels() {
        for (Panel panel : testPanelList) {
            testBoard.getPanelList().add(panel);
        }
        Panel testPanel = testBoard.getPanelList().get(5);
        testBoard.shufflePanels(); // count == 1
        assertEquals(16, testBoard.getPanelList().size());

        Panel testPanel2 = testBoard.getPanelList().get(5);

        assertNotEquals(testPanel, testPanel2);

        int testCount = testBoard.shufflePanels();

        assertEquals(2, testCount);

    }

    @Test
    void testAddPanel() {
        assertEquals(0, testBoard.getPanelList().size());
        testBoard.addPanel(new Panel("A", 1, false));

        assertEquals(1, testBoard.getPanelList().size());

        testBoard.addPanel(new Panel("B", 2, false));

        assertEquals(2, testBoard.getPanelList().size());
    }

    @Test
    void testRevealBoard() {
        testBoard.addPanel(new Panel("A", 1, false));
        assertEquals(1, testBoard.getPanelList().size());

        Panel test = testBoard.getPanelList().get(0);
        assertFalse(test.getIsFlipped());

        testBoard.revealBoard();

        assertTrue(test.getIsFlipped());


    }

    @Test
    void testSetBoardSize() {
        testBoard.setBoardSize(16);
        assertEquals(16, testBoard.getBoardSize());
    }

    @Test
    void testStartTime() {
        assertEquals(testBoard.startTime(), System.currentTimeMillis());
    }

    @Test
    void testEndTime() {
        assertEquals(testBoard.endTime(), System.currentTimeMillis());
    }

    @Test
    void testGetElapsed() {
        assertEquals(0,testBoard.getElapsed() );
    }

    @Test
    void testGetSavedTime() {
        assertEquals(0, testBoard.getSavedTime());
    }




}
