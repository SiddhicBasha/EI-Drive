import java.util.ArrayList;
import java.util.List;

public class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks;
    private List<Observer> observers;

    private ScheduleManager() {
        tasks = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void addTask(Task task) {
        if (isConflict(task)) {
            notifyObservers("Task conflicts with existing task.");
        } else {
            tasks.add(task);
            tasks.sort((t1, t2) -> t1.getStartTime().compareTo(t2.getStartTime()));
            notifyObservers("Task added successfully. No conflicts.");
        }
    }

    public void removeTask(String description) {
        Task taskToRemove = null;
        for (Task task : tasks) {
            if (task.getDescription().equals(description)) {
                taskToRemove = task;
                break;
            }
        }
        if (taskToRemove != null) {
            tasks.remove(taskToRemove);
            notifyObservers("Task removed successfully.");
        } else {
            notifyObservers("Error: Task not found.");
        }
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    public void editTask(String oldDescription, String newDescription, String newStartTime, String newEndTime, String newPriority) {
        Task taskToEdit = null;
        for (Task task : tasks) {
            if (task.getDescription().equals(oldDescription)) {
                taskToEdit = task;
                break;
            }
        }
        if (taskToEdit != null) {
            try {
                Task newTask = TaskFactory.createTask(newDescription, newStartTime, newEndTime, newPriority);
                if (!isConflict(newTask)) {
                    taskToEdit = newTask;
                    notifyObservers("Task edited successfully.");
                } else {
                    notifyObservers("Error: New task details conflict with existing tasks.");
                }
            } catch (IllegalArgumentException e) {
                notifyObservers("Error: " + e.getMessage());
            }
        } else {
            notifyObservers("Error: Task not found.");
        }
    }

    public void viewTasksByPriority(String priority) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority().equals(priority)) {
                filteredTasks.add(task);
            }
        }
        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks with priority '" + priority + "' scheduled for the day.");
        } else {
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }

    private boolean isConflict(Task newTask) {
        for (Task task : tasks) {
            if (!(newTask.getEndTime().isBefore(task.getStartTime()) || newTask.getStartTime().isAfter(task.getEndTime()))) {
                return true;
            }
        }
        return false;
    }
}
