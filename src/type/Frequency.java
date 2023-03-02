package type;

public enum Frequency {
    ONETIME("однократная задача"),
    DAILY("ежедневная задача"),
    WEEKLY("еженедельная задача"),
    MONTHLY("ежемесячная задача"),
    YEARLY("ежегодная задача");

    private final String frequencyTranslate;

    Frequency(String frequencyTranslate) {
        this.frequencyTranslate = frequencyTranslate;
    }

    public String getFrequencyTranslate() {
        return "'" + frequencyTranslate + "'";
    }
}
