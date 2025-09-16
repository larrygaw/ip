package chatter.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import chatter.exception.ChatterException;

/**
 * Represents a task with a deadline.
 * A {@code Deadline} has a description and a due date/time.
 * Inherits from {@link Task}.
 */
public class Deadline extends Task {
    /** Formatter for parsing input date/time strings */
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /** Formatter for displaying date/time to the user */
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    /** Deadline date and time of the task */
    protected LocalDateTime by;

    /**
     * Constructs a {@code Deadline} task with the given description and due date/time string.
     *
     * @param description the description of the task
     * @param byStr the deadline of the task in {@code yyyy-MM-dd HHmm} format
     * @throws ChatterException if {@code byStr} is not in the correct format
     */
    public Deadline(String description, String byStr) throws ChatterException {
        super(description);
        try {
            this.by = LocalDateTime.parse(byStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ChatterException("/by must be followed by deadline in yyyy-MM-dd HHmm format!");
        }
    }

    public LocalDateTime getDateTime() {
        return by;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Deadline)) {
            return false;
        }
        Deadline newDeadlineTask = (Deadline) obj;
        return by.equals(newDeadlineTask.by);
    }

    @Override
    public String toSaveFormat() {
        if (isDone) {
            return "D | 1 | " + description + " | " + by.format(INPUT_FORMAT);
        } else {
            return "D | 0 | " + description + " | " + by.format(INPUT_FORMAT);
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
