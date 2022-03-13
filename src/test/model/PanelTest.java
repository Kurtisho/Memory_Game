package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// class represents the testing for the Panel class
public class PanelTest {

    private Panel testPanel;


    @BeforeEach
    void runBefore() {
        Board board = new Board("Test Board", 16, 0);
        testPanel = new Panel("A", "1",false);


    }

    @Test
    void testConstructor() {
        assertEquals("A", testPanel.getLetter());
        assertFalse(testPanel.getIsFlipped());
        assertEquals("1", testPanel.getPosition());
    }

    @Test
    void testGetLetter() {
        assertEquals("A", testPanel.getLetter());
        testPanel = new Panel("B", "3",false);
        assertEquals("B", testPanel.getLetter());
    }



    @Test
    void testIsFlipped() {
        assertFalse(testPanel.getIsFlipped());
    }

    @Test
    void testMakeFlipped() {
        assertFalse(testPanel.getIsFlipped());
        testPanel.setIsFlipped(true);
        assertTrue(testPanel.getIsFlipped());
    }

    @Test
    void testGetPosition() {
        assertEquals("1", testPanel.getPosition());
        Panel testPanel2 = new Panel("B", "3",false);
        assertEquals("3", testPanel2.getPosition());
    }

    @Test
    void testSetPosition() {
        testPanel.setPosition("B");

        assertEquals("B", testPanel.getPosition());
    }

}
