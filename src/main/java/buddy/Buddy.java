package buddy;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The main class for the Buddy application.
 * Handles initialization, user interaction and execution flow.
 */
public class Buddy {
    private static final String FILE_PATH = Paths.get("data", "Buddy.txt").toString();
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Constructs a new instance of Buddy.
     * Initializes storage, loads tasks from file, and sets up the task list.
     */
    public Buddy() {
        this.storage = new Storage(FILE_PATH);
        ArrayList<Task> loadedTasks;
        try {
            loadedTasks = (ArrayList<Task>) storage.load();
        } catch (IOException e) {
            Ui.getErrorMessage("Error loading tasks from file: " + e.getMessage());
            loadedTasks = new ArrayList<>();
        }
        this.taskList = new TaskList(loadedTasks);
    }


    /**
     * Processes the user's input and returns the appropriate response.
     *
     * This method interprets the input provided by the user and generates a
     * response accordingly. If the input is "bye", it handles the termination
     * message. For all other inputs, it delegates the parsing and task
     * management logic to the {@code Parser} class.
     *
     * @param input The user's input as a string.
     * @return A string containing the appropriate response based on the user's input.
     *         If an exception occurs during processing, an error message is returned.
     */
    public String getResponse(String input) {
        try {
            // Use a StringBuilder to capture the response
            StringBuilder response = new StringBuilder();

            if (input.equals("bye")) {
                response.append("Goodbye! Hope to see you again soon.");
            } else {
                // Redirect input to the Parser
                response.append(Parser.parseCommand(input, taskList, storage));
            }

            return response.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
