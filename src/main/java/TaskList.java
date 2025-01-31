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

    public void listTasks() {
        Ui.printTaskList(tasks);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
