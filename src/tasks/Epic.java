package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Epic extends Task {
    public List<Integer> listOfSubtasksID = new ArrayList<>();
    public Map<Integer, Status> listOfSubtaskStatuses = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "listOfSubtasksID=" + listOfSubtasksID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id_number=" + id_number +
                ", status=" + status +
                '}';
    }
}
