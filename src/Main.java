import tasks.*;


public class Main {
    public static void main(String[] args) {

//        Измените статусы созданных объектов, распечатайте их. Проверьте, что статус задачи и подзадачи сохранился,
//        а статус эпика рассчитался по статусам подзадач.


        TaskManager taskManager = new TaskManager();
        //Создадим две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.
        Task washClothes = new Task("Постирать", "Постирать все вещи", Status.NEW);
        taskManager.createTask(washClothes);
        Task ironClothes = new Task("Погладить", "Погладить все вещи", Status.NEW);
        taskManager.createTask(ironClothes);

        Task washDishes = new Epic("Помыть посуду", "Вымыть всю посуду");
        taskManager.createTask(washDishes);
        Task washCups = new Subtask("Помыть чашки", "Вымыть все чашки", Status.NEW, (Epic) washDishes);
        taskManager.createTask(washCups);
        Task washPlates = new Subtask("Помыть тарелки", "Вымыть все тарелки", Status.NEW, (Epic) washDishes);
        taskManager.createTask(washPlates);

        Task cleanTheRoom = new Epic("Убраться", "Убраться в комнате");
        taskManager.createTask(cleanTheRoom);
        Task vacuum = new Subtask("Пропылесосить", "Пропылесосить в комнате", Status.NEW, (Epic) cleanTheRoom);
        taskManager.createTask(vacuum);

        //Распечатаем списки эпиков, задач и подзадач.
        System.out.println(taskManager.getListOfTasks());

        System.out.println(taskManager.getTask(2)); //получим задачу с id_number = 2
        System.out.println(taskManager.getListOfTasks(3)); //Получим список всех подзадач заданного эпика
        //обновим данные в задаче с id_number = 1
        taskManager.renewTask(new Task("Точно постирать", "Постирать все вещи", Status.NEW, 1));
        System.out.println(taskManager.getListOfTasks());

//      Поменяем статусы объектов и распечатаем.
        taskManager.renewTask(new Task("Постирать", "Постирать все вещи", Status.IN_PROGRESS, 1));
        taskManager.renewTask(new Task("Погладить", "Погладить все вещи", Status.DONE, 2));
        taskManager.renewTask(new Subtask("Помыть чашки", "Вымыть все чашки", Status.IN_PROGRESS, (Epic) washDishes, 4));
        taskManager.renewTask(new Subtask("Помыть тарелки", "Вымыть все тарелки", Status.DONE, (Epic) washDishes, 5));
        taskManager.renewTask(new Subtask("Пропылесосить", "Пропылесосить в комнате", Status.DONE, (Epic) cleanTheRoom, 7));
        System.out.println(taskManager.getListOfTasks());

//      Проверим удаление объектов
        taskManager.deleteTask(2); //удаляем задачу
        taskManager.deleteTask(4); //удаляем подзадачу
        System.out.println(taskManager.getListOfTasks());
        taskManager.deleteTask(3); //удаляем эпик
        System.out.println(taskManager.getListOfTasks());
        taskManager.deleteAllTasks(); //удаляем все задачи
        System.out.println(taskManager.getListOfTasks());
    }
}