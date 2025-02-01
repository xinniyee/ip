package buddy;

import java.util.ArrayList;

/**
 * The Ui class handles all user interface outputs, including displaying
 * welcome messages, task lists, and task-related actions such as adding,
 * deleting, and marking tasks as done or undone. It ensures consistent
 * formatting for the console output.
 */
public class Ui {
    /**
     * Prints a line of underscores to separate sections in the output.
     */
    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays the welcome message when the program starts.
     * Prints a greeting and asks the user what they want to do.
     */
    public static void showWelcomeMessage() {
        printLine();
        System.out.println("Hello! I'm Duke.Buddy");
        System.out.println("What can I do for you?");
        printLine();
    }

    /**
     * Displays the list of tasks in the user's task list.
     *
     * @param tasks the list of tasks to display
     */
    public static void printTaskList(ArrayList<Task> tasks) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        printLine();
    }

    /**
     * Displays a message confirming the addition of a new task to the list.
     *
     * @param task the task that was added
     * @param size the current size of the task list
     */
    public static void printAddTask(Task task, int size) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        printLine();
    }

    /**
     * Displays a message confirming the deletion of a task from the list.
     *
     * @param task the task that was removed
     * @param size the current size of the task list
     */
    public static void printDeleteTask(Task task, int size) {
        printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        printLine();
    }

    /**
     * Displays a message confirming the marking of a task as done.
     *
     * @param task the task that was marked as done
     */
    public static void printMarkTask(Task task) {
        printLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        printLine();
    }

    /**
     * Displays a message confirming the unmarking of a task as done.
     *
     * @param task the task that was unmarked
     */
    public static void printUnmarkTask(Task task) {
        printLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        printLine();
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display
     */
    public static void printError(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    /**
     * Prints a list of tasks that match the search keyword.
     *
     * @param matchingTaskList the list of tasks that matched the search
     */
    public static void printFoundTasks(TaskList matchingTaskList) {
        printLine();
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTaskList.size(); i++) {
            System.out.println((i + 1) + "." + matchingTaskList.get(i));
        }
        printLine();
    }

    /**
     * Displays a goodbye message when the program ends.
     */
    public static void printGoodbye() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }
}
