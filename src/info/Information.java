package info;

import java.time.LocalDate;

public interface Information {
    boolean appearsIn(LocalDate date);

    LocalDate getDateFromTask();

    String getTitleFromTask();

    String getDescriptionFromTask();

    void setTitleFromTask(String title);

    void setDescriptionFromTask(String description);
}
