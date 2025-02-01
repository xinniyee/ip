package Buddy;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testParseTodo() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Parser.parseCommand("todo read book", taskList);

        assertEquals(1, taskList.size());
        assertInstanceOf(ToDo.class, taskList.get(0));
        assertEquals("read book", taskList.get(0).getDescription());
    }

    @Test
    public void testParseDeadline() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Parser.parseCommand("deadline return book /by 2024-12-01 1800", taskList);

        assertEquals(1, taskList.size());
        assertInstanceOf(Deadline.class, taskList.get(0));
        assertEquals("return book", taskList.get(0).getDescription());
        assertEquals("Dec 01 2024 18:00", ((Deadline) taskList.get(0)).getFormattedTime());
    }

    @Test
    public void testParseEvent() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Parser.parseCommand("event project meeting /from 2024-12-05 1400 /to 2024-12-05 1600", taskList);

        assertEquals(1, taskList.size());
        assertInstanceOf(Event.class, taskList.get(0));
        assertEquals("project meeting", taskList.get(0).getDescription());
        assertEquals("Dec 05 2024 14:00", ((Event) taskList.get(0)).getFormattedStartTime());
        assertEquals("Dec 05 2024 16:00", ((Event) taskList.get(0)).getFormattedEndTime());
    }

    @Test
    public void testParseDelete() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Parser.parseCommand("todo read book", taskList);
        Parser.parseCommand("delete 1", taskList);

        assertEquals(0, taskList.size());
    }

    @Test
    public void testParseMarkDone() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Parser.parseCommand("todo read book", taskList);
        Parser.parseCommand("mark 1", taskList);

        assertTrue(taskList.get(0).isDone());
    }
}