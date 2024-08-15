package tasks;

public class Subtask extends Task {
    protected int id_number_Epic;


    public int getId_number_Epic() {
        return id_number_Epic;
    }

    public void setId_number_Epic(int id_number_Epic) {
        this.id_number_Epic = id_number_Epic;
    }

    public Subtask(String name, String description, Status status, Epic epic) {
        super(name, description, status);
        this.id_number_Epic = epic.getId_number();
    }

    public Subtask(String name, String description, Status status, Epic epic, int id_number){
        super(name, description, status);
        this.id_number_Epic = epic.getId_number();
        this.id_number = id_number;
    }


    @Override
    public String toString() {
        return "Subtask{" +
                "id_number_Epic=" + id_number_Epic +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id_number=" + id_number +
                ", status=" + status +
                '}';
    }
}
