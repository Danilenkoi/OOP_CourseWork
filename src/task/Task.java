package task;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import type.*;

public class Task {
    private final int id;
    static int counter;
    private final Type type;
    private String title;
    private final LocalDateTime dateTime;
    private String description;

    public Task(Type type, String title, LocalDateTime dateTime, String description) {
        this.type = type;
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.id = ++counter;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getOnlyDate() {
        return LocalDate.from(dateTime);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Задача{" +
                "id=" + id +
                ", тип='" + type.getTypeTranslate() + '\'' +
                ", название='" + title + '\'' +
                ", дата и время=" + DateTimeFormatter.ofPattern("dd.MM.yyyy в HH:mm").format(dateTime) +
                ", описание='" + description + '\'' +
                ' ';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return getId() == task.getId() && type == task.type && Objects.equals(getTitle(), task.getTitle()) && getDateTime().equals(task.getDateTime()) && Objects.equals(getDescription(), task.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), type, getTitle(), getDateTime(), getDescription());
    }
}
