package buddy;

import java.util.ArrayList;

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
     * @param task the task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
        Ui.printAddTask(task, tasks.size());
    }

    /**
     * Deletes a task from the TaskList by index.
     *
     * @param index the index (1-based) of the task to be deleted
     */
    public void deleteTask(int index) {
        if (index < 1 || index > tasks.size()) {
            Ui.printError("Invalid task number for deletion");
            return;
        }

        Task removedTask = tasks.remove(index - 1);
        Ui.printDeleteTask(removedTask, tasks.size());
    }

    /**
     * Marks a task as done by index.
     *
     * @param index the index (1-based) of the task to be marked as done
     */
    public void markTaskAsDone(int index) {
        if (index < 1 || index > tasks.size()) {
            Ui.printError("Invalid task number for marking as done");
            return;
        }
        tasks.get(index - 1).markAsDone();
        Ui.printMarkTask(tasks.get(index - 1));
    }

    /**
     * Unmarks a task as done by index.
     *
     * @param index the index (1-based) of the task to be unmarked
     */
    public void unmarkTaskAsDone(int index) {
        if (index < 1 || index > tasks.size()) {
            Ui.printError("Invalid task number for unmarking");
            return;
        }
        tasks.get(index - 1).unmarkAsDone();
        Ui.printUnmarkTask(tasks.get(index - 1));
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param keyword the keyword to search for
     */
    public void findTasks(String keyword) {
        TaskList matchingTaskList = new TaskList(new ArrayList<>());

        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTaskList.addTask(task);
            }
        }

        if (matchingTaskList.isEmpty()) {
            Ui.printError("No matching tasks found.");
        } else {
            Ui.printFoundTasks(matchingTaskList);
        }
    }

    /**
     * Lists all tasks in the TaskList.
     */
    public void listTasks() {
        Ui.printTaskList(tasks);
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
