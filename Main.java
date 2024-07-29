import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ScheduleManager scheduleManager = ScheduleManager.getInstance();
        UserNotifier userNotifier = new UserNotifier();
        scheduleManager.addObserver(userNotifier);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Task\n2. Remove Task\n3. View Tasks\n4. Edit Task\n5. Mark Task as Completed\n6. View Tasks by Priority\n7. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter start time (HH:MM): ");
                    String startTime = scanner.nextLine();
                    System.out.print("Enter end time (HH:MM): ");
                    String endTime = scanner.nextLine();
                    System.out.print("Enter priority level: ");
                    String priority = scanner.nextLine();
                    try {
                        Task task = TaskFactory.createTask(description, startTime, endTime, priority);
                        scheduleManager.addTask(task);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.print("Enter task description to remove: ");
                    String removeDescription = scanner.nextLine();
                    scheduleManager.removeTask(removeDescription);
                    break;
                case "3":
                    scheduleManager.viewTasks();
                    break;
                case "4":
                    System.out.print("Enter existing task description: ");
                    String oldDescription = scanner.nextLine();
                    System.out.print("Enter new task description: ");
                    String newDescription = scanner.nextLine();
                    System.out.print("Enter new start time (HH:MM): ");
                    String newStartTime = scanner.nextLine();
                    System.out.print("Enter new end time (HH:MM): ");
                    String newEndTime = scanner.nextLine();
                    System.out.print("Enter new priority level: ");
                    String newPriority = scanner.nextLine();
                    scheduleManager.editTask(oldDescription, newDescription, newStartTime, newEndTime, newPriority);
                    break;
                case "5":
                    System.out.print("Enter task description to mark as completed: ");
                    String completeDescription = scanner.nextLine();
                    scheduleManager.removeTask(completeDescription);
                    break;
                case "6":
                    System.out.print("Enter priority level to view: ");
                    String viewPriority = scanner.nextLine();
                    scheduleManager.viewTasksByPriority(viewPriority);
                    break;
                case "7":
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }
}
