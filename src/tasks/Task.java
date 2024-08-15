package tasks;

public class Task {
    protected final String name;
    protected final String description;
    protected int id_number = 0;
    protected Status status;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId_number() {
        return id_number;
    }

    public Status getStatus() {
        return status;
    }

    public void setId_number(int id_number) {
        this.id_number = id_number;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Task(String name, String description, Status status){
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Task(String name, String description, Status status, int id_number){
        this.name = name;
        this.description = description;
        this.status = status;
        this.id_number = id_number;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id_number=" + id_number +
                ", status=" + status +
                '}';
    }
}
