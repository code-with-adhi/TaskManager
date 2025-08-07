// Task.java
import java.time.LocalDateTime;

/**
 * A data class representing a single task in the scheduler.
 * It holds the task's ID, title, and due date/time.
 */
public class Task {
    private final long id;
    private String title;
    private LocalDateTime dueDate;

    public Task(long id, String title, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
    }

    // --- Getters ---
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    // --- Setters ---
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Title: %s | Due: %s", id, title, dueDate);
    }
}