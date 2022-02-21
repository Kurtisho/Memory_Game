package persistence;

import model.Board;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/smtFunnyFile.json");
        try {
            Board board = reader.read();
            fail("Was not expection to read file");
        } catch (IOException e) {
            // pass test
        }
    }

    @Test
    void testReaderEmptyBoard() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBoard.json");

        try {
            Board board = reader.read();
            assertEquals("Empty Board", board.getName());
            assertEquals(0, board.getBoardSize());
        } catch (IOException e) {
            fail("Empty Reading file does not exist");
        }
    }

    @Test
    void testReaderGeneralBoard() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBoard.json");

        try {
            Board board = reader.read();
            assertEquals("User's Board", board.getName());
            assertEquals(8, board.getBoardSize());
            checkPanel("B", "1", true, board.getPanelList().get(0));
            checkPanel("A", "6", false, board.getPanelList().get(5));
        } catch(IOException e){
            fail("General Reading file does not exist");
        }
    }


}