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
 * the corresponding operations on the task list.
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
     * @return Response message string for user's query.
     * @throws ChatterException If the command is invalid or has incorrect format.
     */
    public static String parse(String input, TaskList tasks, Ui ui, Storage storage) throws ChatterException {

        String[] parts = input.split(" ", 2);
        String command = parts[0];

        switch (command) {
        case "bye":
            return ByeCommand.execute(parts, ui);
        case "list":
            return ListCommand.execute(parts, tasks, ui);
        case "todo":
            return TodoCommand.execute(parts, tasks, storage, ui);
        case "deadline":
            return DeadlineCommand.execute(parts, tasks, storage, ui);
        case "event":
            return EventCommand.execute(parts, tasks, storage, ui);
        case "delete":
            return DeleteCommand.execute(parts, tasks, storage, ui);
        case "mark":
            return MarkCommand.execute(parts, tasks, storage, ui);
        case "unmark":
            return UnmarkCommand.execute(parts, tasks, storage, ui);
        case "on":
            return OnCommand.execute(parts, tasks, ui);
        case "find":
            return FindCommand.execute(parts, tasks, ui);
        default:
            throw new ChatterException("SORRY! I am not qualified to do this!");
        }
    }

    /**
     * Handles the 'bye' command.
     */
    private static class ByeCommand {
        /**
         * Executes the 'bye' command.
         *
         * @param parts the input split into command and content
         * @param ui the Ui object used to generate the exit message
         * @return the exit message string
         * @throws ChatterException if the command has unexpected additional content
         */
        public static String execute(String[] parts, Ui ui) throws ChatterException {
            if (parts.length == 1) {
                return ui.showExit();
            } else {
                throw new ChatterException("bye command should not be followed by anything!");
            }
        }
    }

    /**
     * Handles the 'list' command.
     */
    private static class ListCommand {
        /**
         * Executes the 'list' command.
         *
         * @param parts the input split into command and content
         * @param tasks the TaskList containing all tasks
         * @param ui the Ui object used to generate the task list display
         * @return the string representing all tasks in the task list
         * @throws ChatterException if the command has unexpected additional content
         */
        public static String execute(String[] parts, TaskList tasks, Ui ui) throws ChatterException {
            if (parts.length == 1) {
                return ui.showList(tasks);
            } else {
                throw new ChatterException("list command should not be followed by anything else!");
            }
        }
    }

    /**
     * Handles the 'todo' command.
     */
    private static class TodoCommand {
        /**
         * Executes the 'todo' command.
         *
         * @param parts the input split into command and content
         * @param tasks the TaskList to add the new ToDo task
         * @param storage the Storage object to save tasks
         * @param ui the Ui object used to generate confirmation message
         * @return the string confirming the task has been added
         * @throws ChatterException if the content (description) is missing or invalid
         */
        public static String execute(String[] parts, TaskList tasks, Storage storage, Ui ui) throws ChatterException {
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new ChatterException("todoTask must have a description!");
            }
            ToDo todoTask = new ToDo(parts[1].trim());
            tasks.add(todoTask);
            storage.save(tasks);
            return ui.showAdded(todoTask, tasks.getSize());
        }
    }

    /**
     * Handles the 'deadline' command.
     */
    private static class DeadlineCommand {
        /**
         * Executes the 'deadline' command.
         *
         * @param parts the input split into command and content
         * @param tasks the TaskList to add the new Deadline task
         * @param storage the Storage object to save tasks
         * @param ui the Ui object used to generate confirmation message
         * @return the string confirming the task has been added
         * @throws ChatterException if the content (description or /by date) is missing or invalid
         */
        public static String execute(String[] parts, TaskList tasks, Storage storage, Ui ui) throws ChatterException {
            if (parts.length < 2 || !parts[1].contains("/by")) {
                throw new ChatterException("deadlineTask must have description and /by!");
            }
            String[] details = parts[1].split(" /by ", 2);
            if (details.length == 1) {
                throw new ChatterException("/by must be followed by deadline in yyyy-MM-dd HHmm format!");
            }
            Deadline deadlineTask = new Deadline(details[0].trim(), details[1].trim());
            tasks.add(deadlineTask);
            storage.save(tasks);
            return ui.showAdded(deadlineTask, tasks.getSize());
        }
    }

    /**
     * Handles the 'event' command.
     */
    private static class EventCommand {
        /**
         * Executes the 'event' command.
         *
         * @param parts the input split into command and content
         * @param tasks the TaskList to add the new Event task
         * @param storage the Storage object to save tasks
         * @param ui the Ui object used to generate confirmation message
         * @return the string confirming the task has been added
         * @throws ChatterException if the content (description, /from, or /to dates) is missing or invalid
         */
        public static String execute(String[] parts, TaskList tasks, Storage storage, Ui ui) throws ChatterException {
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
            Event eventTask = new Event(fromSplit[0].trim(), toSplit[0].trim(), toSplit[1].trim());
            tasks.add(eventTask);
            storage.save(tasks);
            return ui.showAdded(eventTask, tasks.getSize());
        }
    }

    /**
     * Handles the 'delete' command.
     */
    private static class DeleteCommand {
        /**
         * Executes the 'delete' command.
         *
         * @param parts the input split into command and content
         * @param tasks the TaskList from which the task will be removed
         * @param storage the Storage object to save changes
         * @param ui the Ui object used to generate confirmation message
         * @return the string confirming the task has been deleted
         * @throws ChatterException if the index is missing, not an integer, or invalid
         */
        public static String execute(String[] parts, TaskList tasks, Storage storage, Ui ui) throws ChatterException {
            if (parts.length < 2) {
                throw new ChatterException("Provide index!");
            }
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                Task deleteTask = tasks.get(index);
                tasks.remove(index);
                storage.save(tasks);
                return ui.showDeleted(deleteTask, tasks.getSize());
            } catch (NumberFormatException nfe) {
                throw new ChatterException("Task number must be an integer!");
            }
        }
    }

    /**
     * Handles the 'mark' command.
     */
    private static class MarkCommand {
        /**
         * Executes the 'mark' command.
         *
         * @param parts the input split into command and content
         * @param tasks the TaskList containing the task to mark
         * @param storage the Storage object to save changes
         * @param ui the Ui object used to generate confirmation message
         * @return the string confirming the task has been marked
         * @throws ChatterException if the index is missing, not an integer, or invalid
         */
        public static String execute(String[] parts, TaskList tasks, Storage storage, Ui ui) throws ChatterException {
            if (parts.length < 2) {
                throw new ChatterException("Provide index!");
            }
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                Task markTask = tasks.get(index);
                markTask.markAsDone();
                storage.save(tasks);
                return ui.showMarked(markTask);
            } catch (NumberFormatException nfe) {
                throw new ChatterException("Task number must be an integer!");
            }
        }
    }

    /**
     * Handles the 'unmark' command.
     */
    private static class UnmarkCommand {
        /**
         * Executes the 'unmark' command.
         *
         * @param parts the input split into command and content
         * @param tasks the TaskList containing the task to unmark
         * @param storage the Storage object to save changes
         * @param ui the Ui object used to generate confirmation message
         * @return the string confirming the task has been unmarked
         * @throws ChatterException if the index is missing, not an integer, or invalid
         */
        public static String execute(String[] parts, TaskList tasks, Storage storage, Ui ui) throws ChatterException {
            if (parts.length < 2) {
                throw new ChatterException("Provide index!");
            }
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                Task unmarkTask = tasks.get(index);
                unmarkTask.unmark();
                storage.save(tasks);
                return ui.showUnmarked(unmarkTask);
            } catch (NumberFormatException nfe) {
                throw new ChatterException("Task number must be an integer!");
            }
        }
    }

    /**
     * Handles the 'on' command.
     */
    private static class OnCommand {
        /**
         * Executes the 'on' command.
         *
         * @param parts the input split into command and content
         * @param tasks the TaskList to search for tasks on a given date
         * @param ui the Ui object used to generate the task list message
         * @return the string listing tasks occurring on the specified date
         * @throws ChatterException if the date is missing or in an invalid format
         */
        public static String execute(String[] parts, TaskList tasks, Ui ui) throws ChatterException {
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new ChatterException("Please provide a date in yyyy-MM-dd format!");
            }
            try {
                LocalDate date = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return ui.showTasksOnDate(date, tasks);
            } catch (DateTimeParseException dtpe) {
                throw new ChatterException("Invalid date format! Please use yyyy-MM-dd!");
            }
        }
    }

    /**
     * Handles the 'find' command.
     */
    private static class FindCommand {
        /**
         * Executes the 'find' command.
         *
         * @param parts the input split into command and content
         * @param tasks the TaskList to search for matching tasks
         * @param ui the Ui object used to generate the matching tasks message
         * @return the string listing tasks that match the search keyword
         * @throws ChatterException if the keyword is missing
         */
        public static String execute(String[] parts, TaskList tasks, Ui ui) throws ChatterException {
            if (parts.length < 2) {
                throw new ChatterException("Please enter what you are looking for!");
            }
            String keyword = parts[1];
            TaskList matchingTasks = tasks.findMatching(keyword);
            return ui.showFound(matchingTasks);
        }
    }
}
