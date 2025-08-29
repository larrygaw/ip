import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Events extends Task{
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    public Events(String description, String fromStr, String toStr) throws ChatterException {
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
            return "E | 1 | " + description + " | " + from.format(INPUT_FORMAT)
                    + " | " + to.format(INPUT_FORMAT);
        } else {
            return "E | 0 | " + description + " | " + from.format(INPUT_FORMAT)
                    + " | " + to.format(INPUT_FORMAT);
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
