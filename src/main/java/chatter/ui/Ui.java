package chatter.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import chatter.exception.ChatterException;
import chatter.task.Deadline;
import chatter.task.Event;
import chatter.task.Task;
import chatter.task.TaskList;

/**
 * Ui class handles all user interactions,
 * including returning output messages as {@link String}.
 */
public class Ui {
    /** Divider line used for formatting messages */
    private static final String LINE = "   _______________________________________________________";

    /** Constructs a new {@code Ui} instance */
    public Ui() {
    }

    /**
     * Returns the welcome message.
     *
     * @return welcome message string
     */
    public String showWelcome() {
        return LINE + "\n   Hello! I'm Chatter\n   What can I do for you?\n" + LINE;

    }

    /**
     * Returns the exit message.
     *
     * @return exit message string
     */
    public String showExit() {
        return LINE + "\n   Bye. Hope to see you again soon!\n" + LINE;
    }

    /**
     * Returns a formatted string of all tasks currently in the TaskList.
     *
     * @param tasks the {@link TaskList} to display.
     * @return formatted string of list of tasks
     * @throws ChatterException if accessing any task fails
     */
    public String showList(TaskList tasks) throws ChatterException {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append("\n");
        sb.append("   Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append("   ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        sb.append(LINE);
        return sb.toString();
    }

    /**
     * Returns a message when a task is added.
     *
     * @param t the task that was added.
     * @param size the new number of tasks in the list.
     * @return formatted add confirmation string
     */
    public String showAdded(Task t, int size) {
        return LINE + "\n   Got it. I've added this task:\n     "
                + t + "\n   Now you have " + size + " tasks in the list.\n" + LINE;
    }

    /**
     * Returns a message when a task is deleted.
     *
     * @param t the task that was deleted.
     * @param size the new number of tasks in the list.
     * @return formatted delete confirmation string
     */
    public String showDeleted(Task t, int size) {
        return LINE + "\n   Noted. I've removed this task:\n     "
                + t + "\n   Now you have " + size + " tasks in the list.\n" + LINE;
    }

    /**
     * Returns a message when a task is marked as done.
     *
     * @param t the task that was marked.
     * @return formatted marked message string
     */
    public String showMarked(Task t) {
        return LINE + "\n   Nice! I've marked this task as done:\n     "
                + t + "\n" + LINE;
    }

    /**
     * Returns a message when a task is unmarked.
     *
     * @param t the task that was unmarked.
     * @return formatted unmarked message string
     */
    public String showUnmarked(Task t) {
        return LINE + "\n   OK, I've unmarked this task:\n     "
                + t + "\n" + LINE;
    }

    /**
     * Returns an error message.
     *
     * @param msg the error message to display.
     * @return formatted error message string
     */
    public String showError(String msg) {
        return LINE + "\n   " + msg + "\n" + LINE;
    }

    /**
     * Returns all tasks that occur on a specified date.
     *
     * @param date the date to filter tasks.
     * @param tasks the {@code TaskList} to search through.
     * @return formatted string of list of tasks on that date or a message if none found
     */
    public String showTasksOnDate(LocalDate date, TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append("\n");
        sb.append("   Tasks occurring on ")
                .append(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")))
                .append(":\n");
        boolean isFound = false;
        for (Task task : tasks.getAllTasks()) {
            if (task instanceof Deadline) {
                Deadline deadlineTask = (Deadline) task;
                if (deadlineTask.getDateTime().toLocalDate().equals(date)) {
                    sb.append("   ").append(deadlineTask).append("\n");
                    isFound = true;
                }
            } else if (task instanceof Event) {
                Event eventTask = (Event) task;
                if (!date.isBefore(eventTask.getFrom().toLocalDate())
                        && !date.isAfter(eventTask.getTo().toLocalDate())) {
                    sb.append("   ").append(eventTask).append("\n");
                    isFound = true;
                }
            }
        }
        if (!isFound) {
            sb.append("   No tasks on this date.\n");
        }
        sb.append(LINE);
        return sb.toString();
    }

    /**
     * Returns the list of tasks that match a given find search query.
     *
     * @param matchingTasks the list of tasks that matched the search keyword.
     * @return formatted matching tasks string
     * @throws ChatterException if accessing tasks fails
     */
    public String showFound(TaskList matchingTasks) throws ChatterException {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append("\n");
        sb.append("   Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.getSize(); i++) {
            sb.append("   ").append(i + 1).append(".").append(matchingTasks.get(i)).append("\n");
        }
        sb.append(LINE);
        return sb.toString();
    }
}
