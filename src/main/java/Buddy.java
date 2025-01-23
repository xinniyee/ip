import java.util.ArrayList;
import java.util.Scanner;

public class Buddy {
    private static final ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
                break;
            } else if (userInput.equals("list")) {
                printLine();
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
                printLine();
            } else if (userInput.startsWith("mark ")) {
                int index = parseTaskIndex(userInput, "mark");
                if (index >= 0 && index < tasks.size()) {
                    tasks.get(index).markAsDone();
                    printLine();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(index));
                    printLine();
                }
            } else if (userInput.startsWith("unmark ")) {
                int index = parseTaskIndex(userInput, "unmark");
                if (index >= 0 && index < tasks.size()) {
                    tasks.get(index).unmarkAsDone();
                    printLine();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks.get(index));
                    printLine();
                }
            } else if (userInput.startsWith("todo ")) {
                String description = userInput.substring(5).trim();
                Task newTask = new ToDo(description);
                tasks.add(newTask);
                printAddTask(newTask);
            } else if (userInput.startsWith("deadline ")) {
                String[] parts = userInput.substring(9).split(" /by ", 2);
                String description = parts[0].trim();
                String by = parts.length > 1 ? parts[1].trim() : "";
                Task newTask = new Deadline(description, by);
                tasks.add(newTask);
                printAddTask(newTask);
            } else if (userInput.startsWith("event ")) {
                String[] parts = userInput.substring(6).split(" /from | /to ", 3);
                String description = parts[0].trim();
                String from = parts.length > 1 ? parts[1].trim() : "";
                String to = parts.length > 2 ? parts[2].trim() : "";
                Task newTask = new Event(description, from, to);
                tasks.add(newTask);
                printAddTask(newTask);
            } else {
                Task newTask = new Task(userInput);
                tasks.add(newTask);
                printAddTask(newTask);
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

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
