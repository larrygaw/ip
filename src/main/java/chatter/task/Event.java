package chatter.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import chatter.exception.ChatterException;

/**
 * Represents an event task with a start and end time.
 * A {@code Event} has a description and a start and end time.
 * Inherits from {@link Task}.
 */
public class Event extends Task {
    /** Formatter for parsing input date/time strings */
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /** Formatter for displaying date/time to the user */
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs an Event task with a description, start time, and end time.
     *
     * @param description Description of the event.
     * @param fromStr Start time of the event in yyyy-MM-dd HHmm format.
     * @param toStr End time of the event in yyyy-MM-dd HHmm format.
     * @throws ChatterException If the date format is invalid or end time is before/equal to start time.
     */
    public Event(String description, String fromStr, String toStr) throws ChatterException {
        super(description);
        try {
            this.from = LocalDateTime.parse(fromStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ChatterException("/from must be followed by event start time in yyyy-MM-dd HHmm format!");
        }
        try {
            this.to = LocalDateTime.parse(toStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ChatterException("/to must be followed by event end time in yyyy-MM-dd HHmm format!");
        }
        if (to.isBefore(from) || to.equals(from)) {
            throw new ChatterException("Event end time must be after event start time!");
        }
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toSaveFormat() {
        if (isDone) {
            return "E | 1 | " + description + " | " + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
        } else {
            return "E | 0 | " + description + " | " + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
