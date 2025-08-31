package chatter.task;

import chatter.exception.ChatterException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task{
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

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
