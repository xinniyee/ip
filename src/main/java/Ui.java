import java.util.ArrayList;

public class Ui {
    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static void showWelcomeMessage() {
        printLine();
        System.out.println("Hello! I'm Buddy");
        System.out.println("What can I do for you?");
        printLine();
    }

    public static void printTaskList(ArrayList<Task> tasks) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        printLine();
    }

    public static void printAddTask(Task task, int size) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        printLine();
    }

    public static void printDeleteTask(Task task, int size) {
        printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        printLine();
    }

    public static void printMarkTask(Task task) {
        printLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        printLine();
    }

    public static void printUnmarkTask(Task task) {
        printLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        printLine();
    }

    public static void printError(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    public static void printGoodbye() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }
}
