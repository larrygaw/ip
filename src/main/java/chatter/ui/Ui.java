package chatter.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import chatter.exception.ChatterException;
import chatter.task.Deadline;
import chatter.task.Event;
import chatter.task.Task;
import chatter.task.TaskList;

/**
 * Ui class handles all user interactions, including input reading
 * and output display to the console.
 */
public class Ui {
    /** {@link Scanner} for reading user input from the console */
    private final Scanner sc;

    /** Divider line used for formatting messages */
    private final String line = "   ____________________________________________________________";

    /** Constructs a new {@code Ui} instance with a {@code Scanner} for console input */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Reads a line of user input from the console.
     *
     * @return the user's input as a String
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /** Displays the welcome message to the user */
    public void showWelcome() {
        System.out.println(line);
        System.out.println("   Hello! I'm Chatter\n   What can I do for you?");
        System.out.println(line);
    }

    /** Displays the exit message to the user */
    public void showExit() {
        System.out.println(line);
        System.out.println("   Bye. Hope to see you again soon!");
        System.out.println(line);
    }

    /**
     * Displays all tasks currently in the TaskList.
     *
     * @param tasks the {@link TaskList} to display
     * @throws ChatterException if accessing any task fails
     */
    public void showList(TaskList tasks) throws ChatterException {
        System.out.println(line);
        System.out.println("   Here are the tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println("   " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(line);
    }

    /**
     * Displays a message when a task is added.
     *
     * @param t the task that was added
     * @param size the new number of tasks in the list
     */
    public void showAdded(Task t, int size) {
        System.out.println(line);
        System.out.println("   Got it. I've added this task:");
        System.out.println("     " + t);
        System.out.println("   Now you have " + size + " tasks in the list.");
        System.out.println(line);
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param t the task that was deleted
     * @param size the new number of tasks in the list
     */
    public void showDeleted(Task t, int size) {
        System.out.println(line);
        System.out.println("   Noted. I've removed this task:");
        System.out.println("     " + t);
        System.out.println("   Now you have " + size + " tasks in the list.");
        System.out.println(line);
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param t the task that was marked
     */
    public void showMarked(Task t) {
        System.out.println(line);
        System.out.println("   Nice! I've marked this task as done:");
        System.out.println("     " + t);
        System.out.println(line);
    }

    /**
     * Displays a message when a task is unmarked.
     *
     * @param t the task that was unmarked
     */
    public void showUnmarked(Task t) {
        System.out.println(line);
        System.out.println("   OK, I've unmarked this task:");
        System.out.println("     " + t);
        System.out.println(line);
    }

    /**
     * Displays an error message to the user.
     *
     * @param msg the error message to display
     */
    public void showError(String msg) {
        System.out.println(line);
        System.out.println("   " + msg);
        System.out.println(line);
    }

    /**
     * Displays all tasks that occur on a specified date.
     *
     * @param date the date to filter tasks in yyyy-MM-dd HHmm format
     * @param tasks the {@code TaskList} to search through
     */
    public void showTasksOnDate(LocalDate date, TaskList tasks) {
        System.out.println(line);
        System.out.println("   Tasks occurring on "
                + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
        boolean found = false;
        for (Task task : tasks.getAllTasks()) {
            if (task instanceof Deadline) {
                Deadline d = (Deadline) task;
                if (d.getDateTime().toLocalDate().equals(date)) {
                    System.out.println("   " + d);
                    found = true;
                }
            } else if (task instanceof Event) {
                Event e = (Event) task;
                if (!date.isBefore(e.getFrom().toLocalDate())
                        && !date.isAfter(e.getTo().toLocalDate())) {
                    System.out.println("   " + e);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("   No tasks on this date.");
        }
        System.out.println(line);
    }

    /**
     * Displays the list of tasks that match a given find search query.
     *
     * @param matchingTasks the list of tasks that matched the search keyword
     */
    public void showFound(TaskList matchingTasks) throws ChatterException {
        System.out.println(line);
        System.out.println("   Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.getSize(); i++) {
            System.out.println("   " + (i + 1) + "." + matchingTasks.get(i));
        }
        System.out.println(line);
    }
}
