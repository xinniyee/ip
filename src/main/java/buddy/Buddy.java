package buddy;

import java.io.IOException;

import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Scanner;

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
     * Starts the Buddy application.
     * Displays a welcome message and continuously processes user input.
     */
    public void run() throws IOException {
        Ui.showWelcomeMessage();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                exit();
                break;
            }
            Parser.parseCommand(userInput, taskList, storage);
        }
        scanner.close();
    }

    /**
     * Handles the exit process by displaying a goodbye message and saving tasks to file.
     */
    private void exit() {
        Ui.getGoodbyeMessage();
        try {
            storage.save(taskList);
        } catch (IOException e) {
            Ui.getErrorMessage("Error saving tasks to file: " + e.getMessage());
        }
    }

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


    /**
    * The main entry point for the Buddy application.
    * Initializes and runs the application.
    */
    public static void main(String[] args) {
        try {
            new Buddy().run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
