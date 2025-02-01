package buddy;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        Ui.printAddTask(task, tasks.size());
    }

    public void deleteTask(int index) {
        if (index < 1 || index > tasks.size()) {
            Ui.printError("Invalid task number for deletion");
            return;
        }

        Task removedTask = tasks.remove(index - 1);
        Ui.printDeleteTask(removedTask, tasks.size());
    }

    public void markTaskAsDone(int index) {
        if (index < 1 || index > tasks.size()) {
            Ui.printError("Invalid task number for marking as done");
            return;
        }
        tasks.get(index - 1).markAsDone();
        Ui.printMarkTask(tasks.get(index - 1));
    }

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

    public void listTasks() {
        Ui.printTaskList(tasks);
    }

    public int size() {
        return tasks.size();
    }

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

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
