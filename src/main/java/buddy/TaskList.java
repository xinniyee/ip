package buddy;

import java.util.ArrayList;
import java.util.List;

/**
 * The TaskList class represents a collection of tasks and provides methods
 * to manage and manipulate the tasks, including adding, deleting, and marking
 * tasks as done or undone.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with the provided list of tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the TaskList.
     *
     * @param task The task to be added.
     * @return A message confirming the task has been added, including the total number of tasks.
     */
    public String addTask(Task task) {
        tasks.add(task);
        return Ui.getAddTaskMessage(task, tasks.size());
    }

    /**
     * Deletes a task from the TaskList by index.
     *
     * @param index the index (1-based) of the task to be deleted
     * @return A message confirming the task has been deleted, or an error message if the index is invalid.
     */
    public String deleteTask(int index) {
        if (index < 1 || index > tasks.size()) {
            return Ui.getErrorMessage("Invalid task number for deletion");
        }

        Task removedTask = tasks.remove(index - 1);
        return Ui.getDeleteTaskMessage(removedTask, tasks.size());
    }

    /**
     * Marks a task as done by index.
     *
     * @param index the index (1-based) of the task to be marked as done
     * @return A message confirming the task has been marked as done, or an error message if the index is invalid.
     */
    public String markTaskAsDone(int index) {
        if (index < 1 || index > tasks.size()) {
            return Ui.getErrorMessage("Invalid task number for marking as done");
        }
        tasks.get(index - 1).markAsDone();
        return Ui.getMarkTaskMessage(tasks.get(index - 1));
    }

    /**
     * Unmarks a task as done by index.
     *
     * @param index the index (1-based) of the task to be unmarked
     * @return A message confirming the task has been unmarked as done, or an error message if the index is invalid.
     */
    public String unmarkTaskAsDone(int index) {
        if (index < 1 || index > tasks.size()) {
            return Ui.getErrorMessage("Invalid task number for unmarking");
        }
        tasks.get(index - 1).unmarkAsDone();
        return Ui.getUnmarkTaskMessage(tasks.get(index - 1));
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param keyword the keyword to search for
     * @return A list of tasks containing the keyword, or a message indicating no matching tasks were found.
     */
    public String findTasks(String keyword) {
        List<Task> foundTasks = tasks.stream()
                .filter(task -> task.getDescription().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .toList();

        if (foundTasks.isEmpty()) {
            return "No tasks found matching that keyword.";
        }

        StringBuilder result = new StringBuilder("Found tasks:\n");
        for (int i = 0; i < foundTasks.size(); i++) {
            result.append(i + 1).append(". ").append(foundTasks.get(i).toString()).append("\n");
        }
        return result.toString();
    }

    /**
     * Lists all tasks in the TaskList.
     *
     * @return A formatted string listing all tasks in the TaskList.
     */
    public String listTasks() {
        return Ui.getTaskList(tasks);
    }

    /**
     * Returns the number of tasks in the TaskList.
     *
     * @return the size of the TaskList
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves a task by its index.
     *
     * @param index the index of the task to retrieve
     * @return the task at the specified index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Checks if the TaskList instance is empty.
     *
     * @return true if the TaskList is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the list of tasks in the TaskList.
     *
     * @return the list of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
