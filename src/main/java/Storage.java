import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<Task> load() throws IOException {
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent()); // Ensure the directory exists
            Files.createFile(filePath); // Create an empty file if it doesn't exist
            return new ArrayList<>();
        }

        List<String> lines = Files.readAllLines(filePath);
        List<Task> tasks = new ArrayList<>();
        for (String line : lines) {
            Task task = fromFileFormat(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void save(TaskList taskList) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString()))) {
            for (Task task : taskList.getTasks()) {  // Assuming TaskList has getTasks() method
                writer.write(task.toFileFormat());   // Assuming Task has a method to convert it to a file-friendly string
                writer.newLine();
            }
        }
    }

    // Converts a line from file format to the corresponding Task object
    private static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null; // Handle corrupted lines gracefully
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                return new ToDo(description, isDone);
            case "D":
                if (parts.length < 4) return null; // Incomplete data for Deadline
                String by = parts[3];
                return new Deadline(description, by, isDone);
            case "E":
                if (parts.length < 5) return null; // Incomplete data for Event
                String from = parts[3];
                String to = parts[4];
                return new Event(description, from, to, isDone);
            default:
                return null; // If the task type is not recognized
        }
    }
}

