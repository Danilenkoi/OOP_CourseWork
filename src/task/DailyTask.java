package task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;
import type.*;
import info.Information;

public class DailyTask extends Task implements Information {
    private final Frequency frequency;

    public DailyTask(Type type, String title, LocalDateTime dateTime, String description) {
        super(type, title, dateTime, description);
        this.frequency = Frequency.DAILY;
    }

    @Override
    public boolean appearsIn(LocalDate date) {
        Period periodBetween = Period.between(getOnlyDate(), date);
        return !periodBetween.isNegative() || getOnlyDate().equals(date);
    }

    @Override
    public LocalDate getDateFromTask() {
        return getOnlyDate();
    }

    @Override
    public String getTitleFromTask() {
        return getTitle();
    }

    @Override
    public String getDescriptionFromTask() {
        return getDescription();
    }

    @Override
    public void setTitleFromTask(String title) {
        setTitle(title);
    }

    @Override
    public void setDescriptionFromTask(String description) {
        setDescription(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyTask dailyTask)) return false;
        if (!super.equals(o)) return false;
        return frequency == dailyTask.frequency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), frequency);
    }

    @Override
    public String toString() {
        return super.toString() +
                "периодичность=" + frequency.getFrequencyTranslate() + '}';
    }
}