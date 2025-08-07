// TaskManager.java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File; // New import
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator; // New import
import java.util.List;
import java.util.Optional;

/**
 * Manages all the business logic for the task scheduler.
 * This includes managing the list of tasks, handling file I/O for
 * persistence, and providing methods to add, view, and delete tasks.
 */
public class TaskManager {

    private static final String FILE_NAME = "tasks.csv";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private final List<Task> tasks;
    private long nextId;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.nextId = 1;
        loadTasks(); // Load tasks from file when the manager is created
    }

    /**
     * Adds a new task to the list with a unique ID.
     * @param title The title of the new task.
     * @param dueDate The due date and time of the task.
     */
    public void addTask(String title, LocalDateTime dueDate) {
        Task newTask = new Task(nextId, title, dueDate);
        tasks.add(newTask);
        nextId++;
        System.out.println("Task added successfully! ID: " + newTask.getId());
    }

    /**
     * Finds and returns a task by its unique ID.
     * @param id The ID of the task to find.
     * @return An Optional containing the Task, or an empty Optional if not found.
     */
    public Optional<Task> getTaskById(long id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
    }

    /**
     * Deletes a task by its unique ID.
     * @param id The ID of the task to delete.
     * @return true if the task was found and deleted, false otherwise.
     */
    public boolean deleteTask(long id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    /**
     * Returns a sorted list of all tasks. The list is sorted by due date,
     * with the soonest tasks appearing first.
     * @return a List of all tasks.
     */
    public List<Task> getAllTasks() {
        // Sort the tasks by their due date before returning
        Collections.sort(tasks, Comparator.comparing(Task::getDueDate));
        return tasks;
    }

    /**
     * Saves the current list of tasks to a CSV file.
     * Each line in the file will contain a task's details.
     */
    public void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                // Format the task data into a single line
                String line = String.format("%d,%s,%s",
                        task.getId(),
                        task.getTitle().replace(",", ";"), // Escape commas in the title
                        task.getDueDate().format(FORMATTER));
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the CSV file into the application.
     * This method is called automatically when the TaskManager is initialized.
     */
    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // No file to load, start with an empty list
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            long maxId = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    try {
                        long id = Long.parseLong(parts[0]);
                        String title = parts[1].replace(";", ","); // Restore commas
                        LocalDateTime dueDate = LocalDateTime.parse(parts[2], FORMATTER);
                        tasks.add(new Task(id, title, dueDate));
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (Exception e) {
                        System.err.println("Skipping invalid task entry: " + line);
                    }
                }
            }
            this.nextId = maxId + 1;
            System.out.println("Tasks loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
    }
}
