package persistence;

import model.Panel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import model.Board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//class represents JsonWriter tests
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Board b = new Board("Test Board", 0);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOExpection was expected but not caught");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBoard() {
        try {
            Board b = new Board("Test Board", 0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBoard.json");
            writer.open();
            writer.write(b);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBoard.json");
            assertEquals("Test Board", b.getName());
            assertEquals(0, b.getBoardSize());
        } catch (IOException e) {
            fail("Was not supposed to catch this");
        }
    }

    @Test
    void testWriterGeneralBoard() {
        try {
            Board b = new Board("Test Board", 4);
            b.getPanelList().add(new Panel("A", "1", false));
            b.getPanelList().add(new Panel("B", "2", false));
            b.getPanelList().add(new Panel("B", "3", false));
            b.getPanelList().add(new Panel("A", "4", false));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBoard.json");
            writer.open();
            writer.write(b);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBoard.json");
            b = reader.read();
            assertEquals("Test Board", b.getName());
            assertEquals(4, b.getBoardSize());
            checkPanel("A", "1", false, b.getPanelList().get(0));
            checkPanel("B", "3", false, b.getPanelList().get(2));
        } catch (IOException e) {
            fail("Was not supposed to catch this");
        }
    }

}
