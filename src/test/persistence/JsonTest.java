package persistence;

import model.Panel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPanel(String panelLetter, String position, Boolean flippedValue, Panel p) {
        assertEquals(panelLetter, p.getLetter());
        assertEquals(position, p.getPosition());
        assertEquals(flippedValue, p.getIsFlipped());
    }
}

