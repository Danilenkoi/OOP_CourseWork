package type;

public enum Type {
    PERSONAL("личная задача"),

    WORK("рабочая задача");

    private final String typeTranslate;

    Type(String typeTranslate) {
        this.typeTranslate = typeTranslate;
    }

    public String getTypeTranslate() {
        return typeTranslate;
    }
}