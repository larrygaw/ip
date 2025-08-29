import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Chatter {

    public static void main(String[] args) {

        Storage storage = new Storage("data/chatter.txt");
        TaskList tasks = storage.load();
        Ui ui = new Ui();

        ui.showWelcome();

        while (true) {
            try {
                //read what user types on keyboard
                String input = ui.readCommand();

                if (input.equals("bye")) {
                    ui.showExit();
                    break;
                } else if (input.equals("list")) {
                    ui.showList(tasks);
                } else if (input.startsWith("todo")) {
                    String[] parts = input.split(" ", 2);
                    //throw error if there is not description
                    if (parts.length < 2 || parts[1].isBlank()) {
                        throw new ChatterException("todoTask must have a description!");
                    }
                    ToDos t = new ToDos(parts[1].trim());
                    tasks.add(t);
                    storage.save(tasks);
                    ui.showAdded(t, tasks.size());
                } else if (input.startsWith("deadline")) {
                    String[] parts = input.split(" ", 2);
                    //throw error if there is no description and deadline
                    if (parts.length < 2 || !parts[1].contains("/by")) {
                        throw new ChatterException("deadlineTask must have description and /by!");
                    }
                    String[] details = parts[1].split(" /by ", 2);
                    if (details.length == 1) {
                        throw new ChatterException("/by must be followed by deadline in yyyy-MM-dd HHmm format!");
                    }
                    Deadline t = new Deadline(details[0].trim(), details[1].trim());
                    tasks.add(t);
                    storage.save(tasks);
                    ui.showAdded(t, tasks.size());
                } else if (input.startsWith("event")) {
                    String[] parts = input.split(" ", 2);
                    //throw error is there is no description, start and end time
                    if (parts.length < 2 || !parts[1].contains("/from") || !parts[1].contains("/to")) {
                        throw new ChatterException("eventTask must have description, /from and /to!");
                    }
                    String[] getDetails = parts[1].split(" /from ", 2);
                    if (getDetails.length == 1) {
                        throw new ChatterException("eventTask must have description and "
                                + "/from must be followed by event start time in yyyy-MM-dd HHmm format!");
                    }
                    String[] getTiming = getDetails[1].split(" /to ");
                    if (getTiming.length == 1) {
                        throw new ChatterException("/from and /to must be followed by "
                                + "event start and end time in yyyy-MM-dd HHmm format respectively!");
                    }
                    Events t = new Events(getDetails[0].trim(), getTiming[0].trim(), getTiming[1].trim());
                    tasks.add(t);
                    storage.save(tasks);
                    ui.showAdded(t, tasks.size());
                } else if (input.startsWith("delete")) {
                    String[] parts = input.split(" ");
                    //throw error if there is no index of task to delete
                    if (parts.length < 2) {
                        throw new ChatterException("Provide index!");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task t = tasks.get(index);
                    tasks.remove(index);
                    storage.save(tasks);
                    ui.showDeleted(t, tasks.size());
                } else if (input.startsWith("mark") || input.startsWith("unmark")) {
                    String[] parts = input.split(" ");
                    //throw error if there is no index of task to mark/unmark
                    if (parts.length < 2) {
                        throw new ChatterException("Provide index!");
                    }
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task t = tasks.get(index);
                    if (parts[0].equals("mark")) {
                        t.markAsDone();
                        ui.showMarked(t);
                    } else {
                        t.unmark();
                        ui.showUnmarked(t);
                    }
                    storage.save(tasks);
                } else if (input.startsWith("on ")) {
                    String[] parts = input.split(" ");
                    if (parts.length < 2 || parts[1].isBlank()) {
                        throw new ChatterException("Please provide a date in yyyy-MM-dd format!");
                    }
                    try {
                        LocalDate date = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        ui.showTasksOnDate(date, tasks);
                    } catch (DateTimeParseException e) {
                        throw new ChatterException("Invalid date format! Please use yyyy-MM-dd!");
                    }
                } else {
                    throw new ChatterException("SORRY! I am not qualified to do this!");
                }
            } catch (ChatterException e){
                ui.showError(e.getMessage());
            } catch (NumberFormatException e){
                ui.showError("   Task number must be an integer!");
            } catch (NullPointerException | IndexOutOfBoundsException e){
                ui.showError("   You don't have that many tasks!");
            }
        }
    }
}
