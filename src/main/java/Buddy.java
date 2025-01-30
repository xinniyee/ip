import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Buddy {
    private static ArrayList<Task> tasks;
    private static final String FILE_PATH = Paths.get("data", "Buddy.txt").toString();
    private static final Storage storage = new Storage(FILE_PATH);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            tasks = (ArrayList<Task>) storage.load();
        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
            tasks = new ArrayList<>();
        }

        printLine();
        System.out.println("Hello! I'm Buddy");
        System.out.println("What can I do for you?");
        printLine();

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                printLine();
                System.out.println("Bye. Hope to see you again soon!");
                printLine();
                try {
                    storage.save(tasks);
                } catch (IOException e) {
                    System.out.println("Error saving tasks to file: " + e.getMessage());
                }
                break;
            } else if (userInput.equals("list")) {
                printLine();
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
                printLine();
            } else if (userInput.startsWith("mark")) {
                try {
                    int index = parseTaskIndex(userInput, "mark");
                    tasks.get(index).markAsDone();
                    printLine();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(index));
                    printLine();
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    printError("Invalid task number for marking as done.");
                }
            } else if (userInput.startsWith("unmark")) {
                try {
                    int index = parseTaskIndex(userInput, "unmark");
                    tasks.get(index).unmarkAsDone();
                    printLine();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks.get(index));
                    printLine();
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    printError("Invalid task number for unmarking.");
                }
            } else if (userInput.startsWith("todo")) {
                String description = userInput.substring(4).trim();
                if (description.isEmpty()) {
                    printError("The description of a todo cannot be empty.");
                } else {
                    Task newTask = new ToDo(description);
                    tasks.add(newTask);
                    printAddTask(newTask);
                }
            } else if (userInput.startsWith("deadline")) {
                String[] parts = userInput.substring(8).split(" /by ", 2);
                if (parts[0].trim().isEmpty() || parts.length < 2 || parts[1].trim().isEmpty()) {
                    printError("The description or deadline must be provided.");
                } else {
                    Task newTask = new Deadline(parts[0].trim(), parts[1].trim());
                    tasks.add(newTask);
                    printAddTask(newTask);
                }
            } else if (userInput.startsWith("event")) {
                String[] parts = userInput.substring(5).split(" /from | /to ", 3);
                if (parts[0].trim().isEmpty() || parts.length < 3 || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
                    printError("The description, start time, or end time of an event must be provided.");
                } else {
                    Task newTask = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    tasks.add(newTask);
                    printAddTask(newTask);
                }
            } else if (userInput.startsWith("delete")) {
                try {
                    int index = parseTaskIndex(userInput, "delete");
                    Task removedTask = tasks.remove(index);
                    printLine();
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(" " + removedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    printLine();
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    printError("Invalid task number for deletion.");
                }
            }else {
                printError("I'm sorry, but I don't know what you mean");
            }
        }

        scanner.close();
    }

    private static void printAddTask(Task task) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    private static int parseTaskIndex(String input, String command) {
        return Integer.parseInt(input.substring(command.length()).trim()) - 1; // Convert to zero-based index
    }

    private static void printError(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
