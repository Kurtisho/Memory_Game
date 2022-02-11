package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PanelTest {

    private Panel testPanel;
    private Panel matchingPanel;

    @BeforeEach
    void runBefore() {
        testPanel = new Panel("A", "1");
        matchingPanel = new Panel("B", "1");
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

//    @Test
//    void testMakeMatchingPanel() {
//
//        testPanel.setMatchingPanel(testPanel);
//
//        assertEquals(testPanel, matchingPanel);
//    }

    @Test
    void testGetPosition() {
        assertEquals("1", testPanel.getPosition());
    }

    @Test
    void testSetPosition() {
        testPanel.setPosition("B");

        assertEquals("B", testPanel.getPosition());
    }

}
