import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TaskFactory {
    public static Task createTask(String description, String startTime, String endTime, String priority) throws IllegalArgumentException {
        try {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);
            if (start.isAfter(end)) {
                throw new IllegalArgumentException("End time must be after start time");
            }
            return new Task(description, start, end, priority);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format");
        }
    }
}
