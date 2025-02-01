package buddy;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Buddy {
    private static final String FILE_PATH = Paths.get("data", "Buddy.txt").toString();
    private final Storage storage;
    private final TaskList taskList;

    public Buddy() {
        this.storage = new Storage(FILE_PATH);
        ArrayList<Task> loadedTasks;
        try {
            loadedTasks = (ArrayList<Task>) storage.load();
        } catch (IOException e) {
            Ui.printError("Error loading tasks from file: " + e.getMessage());
            loadedTasks = new ArrayList<>();
        }
        this.taskList = new TaskList(loadedTasks);
    }

    public void run() {
        Ui.showWelcomeMessage();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                exit();
                break;
            }
            Parser.parseCommand(userInput,taskList);
        }
        scanner.close();
    }

    private void exit() {
        Ui.printGoodbye();
        try {
            storage.save(taskList);
        } catch (IOException e) {
            Ui.printError("Error saving tasks to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Buddy().run();
    }
}
