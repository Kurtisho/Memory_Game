package persistence;

// modelled after persistence.JsonReader from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;


import model.Board;
import model.Panel;


// Represents a reader that reads Board from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Board from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Board read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBoard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Board from JSON object and returns it
    private Board parseBoard(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Integer boardSize = jsonObject.getInt("boardsize");
        Long time = jsonObject.getLong("time");
        Board board = new Board(name, boardSize, time);
        addPanels(board, jsonObject);
        return board;
    }

    // MODIFIES: Board
    // EFFECTS: parses panel from JSON object and adds them to Board
    private void addPanels(Board board, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("panels");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addPanel(board, nextThingy);
        }
    }

    // MODIFIES: Board
    // EFFECTS: parses Panel from JSON object and adds it to Board
    private void addPanel(Board board, JSONObject jsonObject) {
        String panelLetter = jsonObject.getString("Panel Letter");
        Integer position = jsonObject.getInt("Position");
        Boolean flippedValue = jsonObject.getBoolean("Flipped Value");
        Panel p = new Panel(panelLetter, position, flippedValue);
        board.getPanelList().add(p);
    }
}
