package buddy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {
    public static void parseCommand(String input, TaskList taskList) {
        if (input.equals("bye")) {
            Ui.printGoodbye();
        } else if (input.equals("list")) {
            taskList.listTasks();
        } else if (input.startsWith("mark")) {
            int index = parseTaskIndex(input, "mark");
            taskList.markTaskAsDone(index);
        } else if (input.startsWith("unmark")) {
            int index = parseTaskIndex(input, "unmark");
            taskList.unmarkTaskAsDone(index);
        } else if (input.startsWith("delete")) {
            int index = parseTaskIndex(input, "delete");
            taskList.deleteTask(index);
        } else if (input.isEmpty()) {
            Ui.printError("Please provide an input.");
        } else if (input.startsWith("todo") || input.startsWith("deadline")
                || input.startsWith("event") || input.startsWith("find")){
            Task newTask = parseTask(taskList, input);
            if (newTask != null) {
                taskList.addTask(newTask);
            }
        } else {
            Ui.printError("Sorry, I'm not sure what you mean.");
        }
    }

    private static Task parseTask(TaskList taskList, String input) {
        if (input.startsWith("todo")) {
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                Ui.printError("The description of a todo cannot be empty.");
            } else {
                return new ToDo(description);
            }
        } else if (input.startsWith("deadline")) {
            String[] parts = input.substring(8).split(" /by ", 2);
            if (parts[0].trim().isEmpty() || parts.length < 2 || parts[1].trim().isEmpty()) {
                Ui.printError("The description or deadline must be provided.");
            } else {
                try {
                    String dateString = parts[1].trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime deadline = LocalDateTime.parse(dateString, formatter);
                    return new Deadline(parts[0].trim(), deadline.format(formatter));
                } catch (Exception e) {
                    Ui.printError("Invalid date format. Please use yyyy-MM-dd HHmm.");
                }
            }
        } else if (input.startsWith("event")) {
            String[] parts = input.substring(5).split(" /from | /to ", 3);
            if (parts[0].trim().isEmpty() || parts.length < 3 || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
                Ui.printError("The description, start time, or end time of an event must be provided.");
            } else {
                try {
                    String startDateString = parts[1].trim();
                    String endDateString = parts[2].trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime start = LocalDateTime.parse(startDateString, formatter);
                    LocalDateTime end = LocalDateTime.parse(endDateString, formatter);
                    return new Event(parts[0].trim(), start.format(formatter), end.format(formatter));
                } catch (Exception e) {
                    Ui.printError("Invalid date format. Please use yyyy-MM-dd HHmm.");
                }
            }
        } else if (input.startsWith("find")) {
            String command = input.trim().split(" ")[0].toLowerCase();
            String argument = input.substring(command.length()).trim();
            if (argument.isEmpty()) {
                Ui.printError("Please specify a keyword to search for.");
            } else {
                taskList.findTasks(argument);
            }
        }
        return null;
    }

    private static int parseTaskIndex(String input, String command) {
        try {
            return Integer.parseInt(input.substring(command.length()).trim());
        } catch (NumberFormatException e) {
            Ui.printError("Invalid task number.");
            return -1;
        }
    }
}
