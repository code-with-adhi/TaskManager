import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * The main class for a command-line task scheduler application.
 * It provides a user interface to interact with the TaskManager.
 */
public class Main {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        System.out.println("Welcome to the Command-Line Task Scheduler!");
        
        while (isRunning) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add a new task");
            System.out.println("2. View all tasks");
            System.out.println("3. Delete a task");
            System.out.println("4. Exit and save");
            System.out.print("Please enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addTask(scanner, taskManager);
                    break;
                case "2":
                    viewAllTasks(taskManager);
                    break;
                case "3":
                    deleteTask(scanner, taskManager);
                    break;
                case "4":
                    taskManager.saveTasks();
                    isRunning = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    /**
     * Handles adding a new task from user input.
     */
    private static void addTask(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter due date (yyyy-MM-dd HH:mm): ");
        String dateString = scanner.nextLine();

        try {
            LocalDateTime dueDate = LocalDateTime.parse(dateString, FORMATTER);
            taskManager.addTask(title, dueDate);
        } catch (DateTimeParseException e) {
            System.err.println("Error: Invalid date format. Please use yyyy-MM-dd HH:mm.");
        }
    }

    /**
     * Handles viewing all tasks.
     */
    private static void viewAllTasks(TaskManager taskManager) {
        List<Task> allTasks = taskManager.getAllTasks();
        if (allTasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("\n--- All Tasks ---");
            allTasks.forEach(System.out::println);
        }
    }

    /**
     * Handles deleting a task based on user input for the task ID.
     */
    private static void deleteTask(Scanner scanner, TaskManager taskManager) {
        System.out.print("Enter the ID of the task to delete: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            if (taskManager.deleteTask(id)) {
                System.out.println("Task with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Error: Task with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid input. Please enter a valid number for the ID.");
        }
    }
}
