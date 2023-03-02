import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import info.Information;
import task.TaskService;

public class Main {
    public static void main(String[] args) {

        Map<Integer, Information> mapOfTasks = new HashMap<>();
        List<Information> removedTasks = new ArrayList<>();

        TaskService scheduler = new TaskService();
        scheduler.mainWindow(mapOfTasks, removedTasks);

    }
}