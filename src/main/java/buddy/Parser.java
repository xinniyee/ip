package buddy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles parsing of user input and executing the corresponding commands.
 */
public class Parser {

    /**
     * Parses and executes the given command.
     *
     * @param input The user input command.
     * @param taskList  The task list to modify based on the command.
     * @param storage The storage to save changes
     * @return The response message to be displayed to the user.
     */
    public static String parseCommand(String input, TaskList taskList, Storage storage) throws IOException {
        if (input.equals("list")) {
            return taskList.listTasks();
        } else if (input.startsWith("mark")) {
            int index = parseTaskIndex(input, "mark");
            String response = taskList.markTaskAsDone(index);
            storage.save(taskList);
            return response;
        } else if (input.startsWith("unmark")) {
            int index = parseTaskIndex(input, "unmark");
            String response = taskList.unmarkTaskAsDone(index);
            storage.save(taskList);
            return response;
        } else if (input.startsWith("delete")) {
            int index = parseTaskIndex(input, "delete");
            String response = taskList.deleteTask(index);
            storage.save(taskList);
            return response;
        } else if (input.isEmpty()) {
            return Ui.getErrorMessage("Please provide an input.");
        } else if (input.startsWith("todo") || input.startsWith("deadline")
                || input.startsWith("event")) {
            String response = parseTask(taskList, input, storage);
            return response;
        } else if (input.startsWith("find")) {
            String keyword = input.substring(4).trim();
            if (keyword.isEmpty()) {
                return Ui.getErrorMessage("Please specify a keyword to search for.");
            } else {
                return taskList.findTasks(keyword);
            }
        }
        return Ui.getErrorMessage("Sorry, I'm not sure what you mean.");
    }

    private static String parseTask(TaskList taskList, String input, Storage storage) throws IOException {
        if (input.startsWith("todo")) {
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                return "The description of a todo cannot be empty.";
            } else {
                ToDo newTask = new ToDo(description);
                taskList.addTask(newTask);
                storage.save(taskList);
                return "Todo task added: " + description;
            }
        } else if (input.startsWith("deadline")) {
            String[] parts = input.substring(8).split(" /by ", 2);
            if (parts[0].trim().isEmpty() || parts.length < 2 || parts[1].trim().isEmpty()) {
                return "The description or deadline must be provided.";
            } else {
                try {
                    String dateString = parts[1].trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime deadline = LocalDateTime.parse(dateString, formatter);
                    Deadline newTask = new Deadline(parts[0].trim(), deadline.format(formatter));
                    taskList.addTask(newTask);
                    storage.save(taskList);
                    return "Deadline task added: " + parts[0].trim() + " by " + deadline.format(formatter);
                } catch (Exception e) {
                    return "Invalid date format. Please use yyyy-MM-dd HHmm.";
                }
            }
        } else if (input.startsWith("event")) {
            String[] parts = input.substring(5).split(" /from | /to ", 3);
            if (parts[0].trim().isEmpty() || parts.length < 3
                    || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
                return "The description, start time, or end time of an event must be provided.";
            } else {
                try {
                    String startDateString = parts[1].trim();
                    String endDateString = parts[2].trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime start = LocalDateTime.parse(startDateString, formatter);
                    LocalDateTime end = LocalDateTime.parse(endDateString, formatter);
                    Event newTask = new Event(parts[0].trim(), start.format(formatter), end.format(formatter));
                    taskList.addTask(newTask);
                    storage.save(taskList);
                    return "Event task added: " + parts[0].trim() + " from "
                            + start.format(formatter) + " to " + end.format(formatter);
                } catch (Exception e) {
                    return "Invalid date format. Please use yyyy-MM-dd HHmm.";
                }
            }
        }

        return "Unknown command. Please try again.";
    }

    /**
     * Parses the task index from a command input string.
     *
     * @param input The full command string.
     * @param command   The specific command (e.g. "mark", "delete")
     * @return  The parsed task index, or -1 if invalid.
     */
    private static int parseTaskIndex(String input, String command) {
        try {
            return Integer.parseInt(input.substring(command.length()).trim());
        } catch (NumberFormatException e) {
            Ui.getErrorMessage("Invalid task number.");
            return -1;
        }
    }
}
