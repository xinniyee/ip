package buddy;

import java.util.ArrayList;

/**
 * The Ui class handles all user interface outputs, now returning strings
 * instead of printing to the console. It ensures consistent formatting
 * for use in different output mediums (e.g., GUI, logs).
 */
public class Ui {
    /**
     * Returns the welcome message when the program starts.
     */
    public static String showWelcomeMessage() {
        return String.join("\n",
                "Hello! I'm Duke.Buddy",
                "What can I do for you?"
        );
    }

    /**
     * Returns the list of tasks in the user's task list.
     *
     * @param tasks the list of tasks to display
     */
    public static String getTaskList(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Here are the tasks in your list:").append("\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a message confirming the addition of a new task to the list.
     *
     * @param task the task that was added
     * @param size the current size of the task list
     */
    public static String getAddTaskMessage(Task task, int size) {
        return String.join("\n",
                "Got it. I've added this task:",
                "  " + task,
                "Now you have " + size + " tasks in the list."
        );
    }

    /**
     * Returns a message confirming the deletion of a task from the list.
     *
     * @param task the task that was removed
     * @param size the current size of the task list
     */
    public static String getDeleteTaskMessage(Task task, int size) {
        return String.join("\n",
                "Noted. I've removed this task:",
                "  " + task,
                "Now you have " + size + " tasks in the list."
        );
    }

    /**
     * Returns a message confirming the marking of a task as done.
     *
     * @param task the task that was marked as done
     */
    public static String getMarkTaskMessage(Task task) {
        return String.join("\n",
                "Nice! I've marked this task as done:",
                "  " + task
        );
    }

    /**
     * Returns a message confirming the unmarking of a task as done.
     *
     * @param task the task that was unmarked
     */
    public static String getUnmarkTaskMessage(Task task) {
        return String.join("\n",
                "OK, I've marked this task as not done yet:",
                "  " + task
        );
    }

    /**
     * Returns an error message.
     *
     * @param message the error message to display
     */
    public static String getErrorMessage(String message) {
        return String.join("\n",
                message
        );
    }

    /**
     * Returns a list of tasks that match the search keyword.
     *
     * @param matchingTaskList the list of tasks that matched the search
     */
    public static String getFoundTasksMessage(TaskList matchingTaskList) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Here are the matching tasks in your list:").append("\n");
        for (int i = 0; i < matchingTaskList.size(); i++) {
            sb.append((i + 1)).append(". ").append(matchingTaskList.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the goodbye message when the program ends.
     */
    public static String getGoodbyeMessage() {
        return String.join("\n",
                "Bye. Hope to see you again soon!"
        );
    }
}
