import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    protected final Map<Integer, Task> mapTask = new HashMap<>();
    protected final Map<Integer, Task> mapSubtask = new HashMap<>();
    protected final Map<Integer, Task> mapEpic = new HashMap<>();
    protected List<Task> listOfTasks;
    private int numberOfTask = 1;


    public List<Task> getListOfTasks(){
        listOfTasks = new ArrayList<>();
        //Получение списка всех задач.
        for (int key : mapTask.keySet()) {
            listOfTasks.add(mapTask.get(key));
        }
        for (int key : mapSubtask.keySet()) {
            listOfTasks.add(mapSubtask.get(key));
        }
        for (int key : mapEpic.keySet()) {
            listOfTasks.add(mapEpic.get(key));
        }
        return listOfTasks;
    }
    public void deleteAllTasks(){
        //Удаление всех задач.
        mapTask.clear();
        mapSubtask.clear();
        mapEpic.clear();
    }
    public Task getTask(int id_number){
        //Получение по идентификатору.
        if (mapSubtask.containsKey(id_number)){
            return mapSubtask.get(id_number);
        } else if (mapEpic.containsKey(id_number)) {
            return mapEpic.get(id_number);
        } else if (mapTask.containsKey(id_number)) {
            return mapTask.get(id_number);
        }
        return null;
    }

    public void createTask(Task task){
        //Создание. Сам объект должен передаваться в качестве параметра.
        int currentID = numberOfTask;
        if (task.getId_number() == 0) {
            task.setId_number(numberOfTask);
            numberOfTask ++;
        } else {
            currentID = task.getId_number();
        }

        if (task instanceof Subtask){
            mapSubtask.put(currentID, task);
            int id_number_Epic = ((Subtask) task).getId_number_Epic(); //получаем id Эпик-задачи
            Epic epic = (Epic)mapEpic.get(id_number_Epic);
            epic.listOfSubtasksID.add(currentID); //запись подзадачи в список у соответствующей Эпик-задачи
            epic.listOfSubtaskStatuses.put(currentID, task.getStatus()); //запись пары id-статус в мапу
            setStatusForEpic(epic); //высчитываем статус Эпика с учётом новой подзадачи
        } else if (task instanceof Epic) {
            mapEpic.put(currentID, task);
            setStatusForEpic((Epic)task);
        } else {
            mapTask.put(currentID, task);
        }
    }

    public void renewTask(Task newTask){
        //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
        int id_number = newTask.getId_number();
        if (mapSubtask.containsKey(id_number)){
            mapSubtask.put(id_number, newTask);
            int id_number_Epic = ((Subtask) newTask).getId_number_Epic(); //получаем id Эпик-задачи
            Epic epic = (Epic)mapEpic.get(id_number_Epic);
            epic.listOfSubtaskStatuses.put(id_number, newTask.getStatus()); //запись пары id-статус в мапу
            setStatusForEpic(epic);
        } else if (mapEpic.containsKey(id_number)){
            mapEpic.put(id_number, newTask);
        } else if (mapTask.containsKey(id_number)){
            mapTask.put(id_number, newTask);
        } else {
            System.out.println("Нет такой задачи");
        }
    }

    public void deleteTask(int id_number){
        //Удаление по идентификатору.
        if (mapSubtask.containsKey(id_number)){
            Subtask subtask = (Subtask) mapSubtask.get(id_number); //получаем нужную подзадачу
            Epic epic = (Epic) mapEpic.get(subtask.getId_number_Epic()); //получаем нужную Эпик-задачу
            for (int i = 0; i < epic.listOfSubtasksID.size(); i++) {
                if (epic.listOfSubtasksID.get(i) == id_number) {
                    epic.listOfSubtasksID.remove(i);//удаляем подзадачу из списка подзадач в Эпик по её номеру
                    epic.listOfSubtaskStatuses.remove(id_number); //удаление пары id-статус из мапы
                }
            }
            mapSubtask.remove(id_number); //удаляем саму подзадачу
            setStatusForEpic(epic); //высчитываем статус Эпика с учётом удаления подзадачи
        } else if (mapEpic.containsKey(id_number)) {
            Epic epic = (Epic) mapEpic.get(id_number);
            for (int i = 0; i < epic.listOfSubtasksID.size(); i++) {
                mapSubtask.remove(epic.listOfSubtasksID.get(i)); //удаляем все подзадачи эпика по списку
            }
            mapEpic.remove(id_number);
        } else if (mapTask.containsKey(id_number)) {
            mapTask.remove(id_number);
        } else {
            System.out.println("Нет такой задачи");
        }
    }

    public List<Task> getListOfTasks(int id_number_Epic){
        //Получение списка всех подзадач определённого эпика.
        listOfTasks = new ArrayList<>();
        Epic epic = (Epic)mapEpic.get(id_number_Epic);
        for (int id_number : epic.listOfSubtasksID) {
            listOfTasks.add(mapSubtask.get(id_number));
        }
        return listOfTasks;
    }

    public void setStatusForEpic(Epic epic){
        boolean allNew = true;
        boolean allDone = true;

        for (Status status : epic.listOfSubtaskStatuses.values()) {
            if (status != Status.NEW) {
                allNew = false;
            }
            if (status != Status.DONE) {
                allDone = false;
            }
        }

        if (epic.listOfSubtasksID == null){
            epic.setStatus(Status.NEW);
        } else if (allNew){
            epic.setStatus(Status.NEW);
        } else if (allDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
