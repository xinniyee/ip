package Buddy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a description and completion status.
 * This is an abstract class that serves as a base for different task types.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the given description and completion status.
     *
     * @param description The description of the task.
     * @param isDone Whether the task is completed.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Checks if the task is done.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a status icon for the task.
     *
     * @return A string representing the completion status of the task.
     */
    public String getStatusIcon() {
        return "[" + (isDone ? "X" : " ") + "]"; // mark done task with X
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts the task into a file-friendly format for storage.
     *
     * @return A formatted string representing the task.
     */
    public abstract String toFileFormat();
}

/**
 * Represents a to-do task that does not have a specific date or time.
 */
class ToDo extends Task {
    /**
     * Constructs a ToDo task with the given description, initially not done.
     *
     * @param description The description of the to-do task.
     */
    public ToDo(String description) {
        super(description, false);
    }

    /**
     * Constructs a ToDo task with a specified completion status.
     *
     * @param description The description of the to-do task.
     * @param isDone Whether the task is completed.
     */
    public ToDo(String description, boolean isDone) {
       super(description, isDone);
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + getStatusIcon() + " " + description;
    }
}

/**
 * Represents a task with a deadline.
 */
class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Constructs a Deadline task with a description and due date.
     *
     * @param description The description of the deadline task.
     * @param by The due date and time in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String description, String by) {
        super(description, false);
        this.by = LocalDateTime.parse(by, DateTimeFormatter.ofPattern(("yyyy-MM-dd HHmm")));
    }

    /**
     * Constructs a Deadline task with a specified completion status.
     *
     * @param description The description of the deadline task.
     * @param by The due date and time in "yyyy-MM-dd HHmm" format.
     * @param isDone Whether the task is completed.
     */
    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = LocalDateTime.parse(by, DateTimeFormatter.ofPattern(("yyyy-MM-dd HHmm")));

    }

    /**
     * Returns the formatted due date of the deadline.
     *
     * @return A string representing the due date in "MMM dd yyyy HH:mm" format.
     */
    public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return by.format(formatter);
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    @Override
    public String toString() {
        return "[D]" + getStatusIcon() + " " + description + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a")) + ")";
    }
}

/**
 * Represents an event with a start and end time.
 */
class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs an Event task with a description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from The start date and time in "yyyy-MM-dd HHmm" format.
     * @param to The end date and time in "yyyy-MM-dd HHmm" format.
     */
    public Event(String description, String from, String to) {
        super(description, false);
        this.from = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        this.to = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

    }

    /**
     * Constructs an Event task with a specified completion status.
     *
     * @param description The description of the event.
     * @param from The start date and time in "yyyy-MM-dd HHmm" format.
     * @param to The end date and time in "yyyy-MM-dd HHmm" format.
     * @param isDone Whether the task is completed.
     */
    public Event(String description, String from, String to, boolean isDone){
            super(description, isDone);
            this.from = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            this.to = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Returns the formatted start time of the event.
     *
     * @return A string representing the start time in "MMM dd yyyy HH:mm" format.
     */
    public String getFormattedStartTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return from.format(formatter);
    }

    /**
     * Returns the formatted end time of the event.
     *
     * @return A string representing the end time in "MMM dd yyyy HH:mm" format.
     */
    public String getFormattedEndTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        return to.format(formatter);
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")) + " | "
                + to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    @Override
    public String toString() {
        return "[E]" + getStatusIcon() + " " + description + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a")) + ")";
    }
}

