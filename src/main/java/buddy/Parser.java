package buddy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles parsing of user input and executing the corresponding commands.
 * This class is responsible for interpreting user commands and delegating the
 * appropriate operations to the TaskList and Storage classes. It supports commands
 * for listing tasks, adding tasks (ToDo, Deadline, Event), marking tasks as done,
 * unmarking tasks, deleting tasks, and finding tasks based on keywords.
 */
public class Parser {

    /**
     * Parses and executes the given command.
     *
     * Depending on the user's input, this method performs actions such as
     * listing tasks, marking tasks as done, unmarking tasks, deleting tasks,
     * or adding new tasks. For unsupported commands, an error message is returned.
     *
     * @param input    The user input command as a string.
     * @param taskList The task list to modify based on the command.
     * @param storage  The storage system to save changes to the task list.
     * @return A string response to be displayed to the user, indicating the result of the command.
     * @throws IOException If an error occurs while saving to the storage.
     */
    public static String parseCommand(String input, TaskList taskList, Storage storage) throws IOException {
        assert input != null : "Input should not be null.";
        assert taskList != null : "TaskList should not be null.";
        assert storage != null : "Storage should not be null.";

        if (input.equals("list")) {
            return taskList.listTasks();
        } else if (input.startsWith("mark")) {
            int index = parseTaskIndex(input, "mark");
            assert index > 0 : "Task index should be a positive integer.";
            String response = taskList.markTaskAsDone(index);
            storage.save(taskList);
            return response;
        } else if (input.startsWith("unmark")) {
            int index = parseTaskIndex(input, "unmark");
            assert index > 0 : "Task index should be a positive integer.";
            String response = taskList.unmarkTaskAsDone(index);
            storage.save(taskList);
            return response;
        } else if (input.startsWith("delete")) {
            int index = parseTaskIndex(input, "delete");
            assert index > 0 : "Task index should be a positive integer.";
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
            assert keyword != null : "Keyword should not be null.";
            if (keyword.isEmpty()) {
                return Ui.getErrorMessage("Please specify a keyword to search for.");
            } else {
                return taskList.findTasks(keyword);
            }
        }
        return Ui.getErrorMessage("Sorry, I'm not sure what you mean.");
    }

    /**
     * Parses a task addition command and adds the corresponding task to the task list.
     *
     * Supports three types of tasks: ToDo, Deadline, and Event. Each type of task
     * requires specific formatting in the user input.
     *
     * @param taskList The task list to which the new task will be added.
     * @param input    The user input command specifying the task to be added.
     * @param storage  The storage system to save changes to the task list.
     * @return A string response confirming the addition of the task or an error message.
     * @throws IOException If an error occurs while saving to the storage.
     */
    private static String parseTask(TaskList taskList, String input, Storage storage) throws IOException {
        assert taskList != null : "TaskList should not be null.";
        assert input != null : "Input should not be null.";
        assert storage != null : "Storage should not be null.";

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
                    if (start.isBefore(end)) {
                        Event newTask = new Event(parts[0].trim(), start.format(formatter), end.format(formatter));
                        taskList.addTask(newTask);
                        storage.save(taskList);
                        return "Event task added: " + parts[0].trim() + " from "
                                + start.format(formatter) + " to " + end.format(formatter);
                    } else {
                        return "Error: Start time must be before end time.";
                    }
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
     * This method is used to extract the task index for commands like "mark",
     * "unmark", or "delete". If the input is invalid, an error message is displayed.
     *
     * @param input   The full command string entered by the user.
     * @param command The specific command (e.g., "mark", "delete").
     * @return The parsed task index, or -1 if the input is invalid.
     */
    private static int parseTaskIndex(String input, String command) {
        assert input != null : "Input should not be null.";
        assert command != null : "Command should not be null.";
        try {
            return Integer.parseInt(input.substring(command.length()).trim());
        } catch (NumberFormatException e) {
            Ui.getErrorMessage("Invalid task number.");
            return -1;
        }
    }
}
