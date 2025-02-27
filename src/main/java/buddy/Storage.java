package buddy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;


/**
 * Handles loading and saving tasks to a file.
 * This class provides persistent storage for tasks by reading from and writing to a specified class.
 */
public class Storage {
    private final Path filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath  The path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.isEmpty() : "File path must not be null or empty.";
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from the file.
     * If the file does not exist, it creates a new file and returns an empty list.
     *
     * @return  A list of tasks loaded from the file.
     * @throws IOException  If an error occurs while reading the file.
     */
    public List<Task> load() throws IOException {
        assert filePath != null : "File path must not be null.";
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent()); // Ensure the directory exists
            Files.createFile(filePath); // Create an empty file if it doesn't exist
            return new ArrayList<>();
        }

        List<String> lines = Files.readAllLines(filePath);
        assert lines != null : "Lines read from file should not be null.";
        List<Task> tasks = new ArrayList<>();
        for (String line : lines) {
            Task task = fromFileFormat(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Saves the current list of tasks to the file.
     *
     * @param taskList  The TaskList containing tasks to be saved.
     * @throws IOException  If an error occurs while writing to the file.
     */
    public void save(TaskList taskList) throws IOException {
        assert taskList != null : "TaskList must not be null.";
        assert taskList.getTasks() != null : "TaskList.getTasks() must not return null.";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString()))) {
            for (Task task : taskList.getTasks()) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        }
    }

    /**
     * Converts a line from file format to the corresponding Task object.
     *
     * @param line  A string representing a task in file storage format.
     * @return A Task object corresponding to the given line, or null if the format is invalid.
     */
    @SuppressWarnings("checkstyle:Regexp")
    private static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
        case "T":
            return new ToDo(description, isDone);
        case "D":
            if (parts.length < 4) {
                return null;
            }
            String by = parts[3];
            return new Deadline(description, by, isDone);
        case "E":
            if (parts.length < 5) {
                return null;
            }
            String from = parts[3];
            String to = parts[4];
            return new Event(description, from, to, isDone);
        default:
            return null;
        }
    }
}

