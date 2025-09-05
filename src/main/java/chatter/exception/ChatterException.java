package chatter.exception;

/**
 * Represents an exception specific to the Chatter application.
 * Thrown when an invalid operation is attempted in the program,
 * such as accessing a task that does not exist.
 */
public class ChatterException extends Exception {
    /**
     * Constructs a new {@code ChatterException} with the specified detail message.
     *
     * @param message The detail message describing the cause of the exception.
     */
    public ChatterException(String message) {
        super(message);
    }
}
