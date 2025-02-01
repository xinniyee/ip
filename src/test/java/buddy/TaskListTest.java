package buddy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class TaskListTest {
    @Test
    public void addTaskSuccess() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Task task =  new ToDo("Read book");
        taskList.addTask(task);
        List<Task> tasks = taskList.getTasks();

        assertEquals(1, tasks.size());
        assertEquals("Read book", tasks.get(0).getDescription());
    }
}
