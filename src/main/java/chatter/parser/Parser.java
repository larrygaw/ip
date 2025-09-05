package chatter.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import chatter.exception.ChatterException;
import chatter.storage.Storage;
import chatter.task.Deadline;
import chatter.task.Event;
import chatter.task.Task;
import chatter.task.TaskList;
import chatter.task.ToDo;
import chatter.ui.Ui;

/**
 * Interprets user input commands and executes
 * the corresponding operations on the task list}.
 */
public class Parser {
    /**
     * Parses the given user input, performs the corresponding action
     * on the {@link TaskList}, updates the {@link Storage}, and interacts with the {@link Ui}.
     *
     * @param input The input string entered by the user.
     * @param tasks The {@code TaskList} that stores all current tasks.
     * @param ui The {@code Ui} object used to display messages to the user.
     * @param storage The {@code Storage} object used to persist changes.
     * @return {@code true} if the input is "bye" (exit command), {@code false} otherwise.
     * @throws ChatterException If the command is invalid or has incorrect format.
     */
    public static boolean parse(String input, TaskList tasks, Ui ui, Storage storage)
            throws ChatterException {

        String[] parts = input.split(" ", 2);
        String command = parts[0];

        switch (command) {
        case "bye":
            ui.showExit();
            return true;
        case "list":
            ui.showList(tasks);
            return false;
        case "todo":
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new ChatterException("todoTask must have a description!");
            }
            ToDo t = new ToDo(parts[1].trim());
            tasks.add(t);
            storage.save(tasks);
            ui.showAdded(t, tasks.getSize());
            return false;
        case "deadline":
            if (parts.length < 2 || !parts[1].contains("/by")) {
                throw new ChatterException("deadlineTask must have description and /by!");
            }
            String[] details = parts[1].split(" /by ", 2);
            if (details.length == 1) {
                throw new ChatterException("/by must be followed by deadline in yyyy-MM-dd HHmm format!");
            }
            Deadline d = new Deadline(details[0].trim(), details[1].trim());
            tasks.add(d);
            storage.save(tasks);
            ui.showAdded(d, tasks.getSize());
            return false;
        case "event":
            if (parts.length < 2 || !parts[1].contains("/from") || !parts[1].contains("/to")) {
                throw new ChatterException("eventTask must have description, /from and /to!");
            }
            String[] fromSplit = parts[1].split(" /from ", 2);
            if (fromSplit.length == 1) {
                throw new ChatterException("eventTask must have description and "
                        + "/from must be followed by event start time in yyyy-MM-dd HHmm format!");
            }
            String[] toSplit = fromSplit[1].split(" /to ");
            if (toSplit.length == 1) {
                throw new ChatterException("/from and /to must be followed by "
                        + "event start and end time in yyyy-MM-dd HHmm format respectively!");
            }
            Event e = new Event(fromSplit[0].trim(), toSplit[0].trim(), toSplit[1].trim());
            tasks.add(e);
            storage.save(tasks);
            ui.showAdded(e, tasks.getSize());
            return false;
        case "delete":
            if (parts.length < 2) {
                throw new ChatterException("Provide index!");
            }
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                Task deleteTask = tasks.get(index);
                tasks.remove(index);
                storage.save(tasks);
                ui.showDeleted(deleteTask, tasks.getSize());
            } catch (NumberFormatException nfe) {
                throw new ChatterException("Task number must be an integer!");
            }
            return false;
        case "mark":
            if (parts.length < 2) {
                throw new ChatterException("Provide index!");
            }
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                Task markTask = tasks.get(index);
                markTask.markAsDone();
                ui.showMarked(markTask);
            } catch (NumberFormatException nfe) {
                throw new ChatterException("Task number must be an integer!");
            }
            storage.save(tasks);
            return false;
        case "unmark":
            if (parts.length < 2) {
                throw new ChatterException("Provide index!");
            }
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                Task unmarkTask = tasks.get(index);
                unmarkTask.unmark();
                ui.showUnmarked(unmarkTask);
            } catch (NumberFormatException nfe) {
                throw new ChatterException("Task number must be an integer!");
            }
            storage.save(tasks);
            return false;
        case "on":
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new ChatterException("Please provide a date in yyyy-MM-dd format!");
            }
            try {
                LocalDate date = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                ui.showTasksOnDate(date, tasks);
            } catch (DateTimeParseException dtpe) {
                throw new ChatterException("Invalid date format! Please use yyyy-MM-dd!");
            }
            return false;
        case "find":
            if (parts.length < 2) {
                throw new ChatterException("Please enter what you are looking for!");
            }
            String keyword = parts[1];
            TaskList matchingTasks = tasks.findMatching(keyword);
            ui.showFound(matchingTasks);
            return false;
        default:
            throw new ChatterException("SORRY! I am not qualified to do this!");
        }
    }
}
