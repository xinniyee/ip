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
        return """
            🌟 Welcome to Buddy! 🌟
            👋 Hello! I'm Buddy, your personal task manager.
            I'm here to help you stay organized and on top of your tasks! ✅

            📌 Here's what I can do for you:
            📝 todo <task> – Add a to-do task
            ⏳ deadline <task> /by YYYY-MM-DD HHMM – Add a deadline task
            🎉 event <task> /from YYYY-MM-DD HHMM /to YYYY-MM-DD HHMM – Add an event
            🔍 find <keyword> – Search for tasks
            📋 list – View all tasks
            ❌ delete <task number> – Remove a task
            ✅ mark <task number> – Mark a task as done
            🚪 bye – Exit Buddy

            💡 Tip: You can type commands in uppercase or lowercase—I'll understand both!

            Let's get started! 🚀
            What would you like to do today? 😊
            """;
    }

    /**
     * Returns a formatted string displaying the list of tasks.
     *
     * @param tasks the list of tasks to be displayed
     * @return the formatted task list as a string
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
     * Returns a message confirming the addition of a new task.
     *
     * @param task the task that was added
     * @param size the current number of tasks in the list
     * @return the formatted message confirming the addition
     */
    public static String getAddTaskMessage(Task task, int size) {
        return String.join("\n",
                "Got it. I've added this task:",
                "  " + task,
                "Now you have " + size + " tasks in the list."
        );
    }

    /**
     * Returns a message confirming the deletion of a task.
     *
     * @param task the task that was removed
     * @param size the current number of tasks in the list
     * @return the formatted message confirming the deletion
     */
    public static String getDeleteTaskMessage(Task task, int size) {
        return String.join("\n",
                "Noted. I've removed this task:",
                "  " + task,
                "Now you have " + size + " tasks in the list."
        );
    }

    /**
     * Returns a message confirming that a task was marked as done.
     *
             * @param task the task that was marked as done
     * @return the formatted message confirming the task completion
     */
    public static String getMarkTaskMessage(Task task) {
        return String.join("\n",
                "Nice! I've marked this task as done:",
                "  " + task
        );
    }

    /**
     * Returns a message confirming that a task was unmarked as done.
     *
     * @param task the task that was unmarked
     * @return the formatted message confirming the unmarking
     */
    public static String getUnmarkTaskMessage(Task task) {
        return String.join("\n",
                "OK, I've marked this task as not done yet:",
                "  " + task
        );
    }

    /**
     * Returns an error message with the provided details.
     *
     * @param message the error message to be displayed
     * @return the formatted error message
     */
    public static String getErrorMessage(String message) {
        return String.join("\n",
                message
        );
    }
}
