package org.example.logic;

public enum SquareInfo {
    // TOP ROW
    START("START", -45, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    ALEJA_MICKIEWICZA("Aleja Mickiewicza", 0, SquareType.PROPERTY, 100, 6, "BROWN"),
    KASA1("KASA 1", 0, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    BRATYSLAWSKA("Bratysławska", 0, SquareType.PROPERTY, 100, 6, "BROWN"),
    PODATEK1("PODATEK 1", 0, SquareType.UNBUYABLE, 120, 8, "FUNC"),
    test1("DWORZEC 1", 0, SquareType.PROPERTY, 100, 6, "STATION"),
    test2("test2", 0, SquareType.PROPERTY, 100, 6, "LIGHTBLUE"),
    SZANSA1("SZANSA 1", 0, SquareType.UNBUYABLE, 100, 6, "FUNC"),
    test4("test4", 0, SquareType.PROPERTY, 100, 6, "LIGHTBLUE"),
    test5("test5", 0, SquareType.PROPERTY, 100, 6, "LIGHTBLUE"),

    // RIGHT COLUMN
    MIASTECZKO_STUDENCKIE("MIASTECZKO STUDENCKIE", 45, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    OLSZEWSKIEGO("Olszewskiego", -0, SquareType.PROPERTY, 140, 10, "PINK"),
    COMPANY1("COMPANY 1", -0, SquareType.PROPERTY, 0, 0, "COMPANY"),
    CZARNOWIEJSKA("Czarnowiejska", -0, SquareType.PROPERTY, 140, 10, "PINK"),
    LEA("Lea", -0, SquareType.PROPERTY, 160, 12, "PINK"),
    DWORZEC2("DWORZEC 2", 0, SquareType.PROPERTY, 100, 6, "STATION"),
    test7("test7", 0, SquareType.PROPERTY, 100, 6, "ORANGE"),
    KASA2("KASA 2", 0, SquareType.UNBUYABLE, 100, 6, "FUNC"),
    test9("test9", 0, SquareType.PROPERTY, 100, 6, "ORANGE"),
    test10("test10", 0, SquareType.PROPERTY, 100, 6, "ORANGE"),

    // BOTTOM ROW
    AKADEMIK("AKADEMIK", -45, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    FLORIANSKA("Floriańska", 0, SquareType.PROPERTY, 180, 14, "RED"),
    SZANSA2("SZANSA 2", 0, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    PLAC_BISKUPI("Plac Biskupi", 0, SquareType.PROPERTY, 180, 14, "RED"),
    PARK_KRAKOWSKI("Park Krakowski", 0, SquareType.PROPERTY, 200, 16, "RED"),
    DWORZEC3("DWORZEC 3", 0, SquareType.PROPERTY, 100, 6, "STATION"),
    test12("test12", 0, SquareType.PROPERTY, 100, 6, "YELLOW"),
    test13("test13", 0, SquareType.PROPERTY, 100, 6, "YELLOW"),
    COMPANY2("COMPANY 2", 0, SquareType.PROPERTY, 100, 6, "COMPANY"),
    test15("test15", 0, SquareType.PROPERTY, 100, 6, "YELLOW"),

    // LEFT COLUMN
    IDZ_NA_MIASTECZKO("IDZIESZ NA MIASTECZKO STUDENCKIE", 45, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    MIODOWA("Miodowa", 0, SquareType.PROPERTY, 300, 26, "GREEN"),
    DUPA("DUPA", 0, SquareType.PROPERTY, 300, 26, "GREEN"),
    KASA3("KASA 3", 0, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    PAVIA("Pawia", 0, SquareType.PROPERTY, 320, 28, "GREEN"),
    DWORZEC4("DWORZEC 4", 0, SquareType.PROPERTY, 100, 6, "STATION"),
    SZANSA3("SZANSA 3", 0, SquareType.UNBUYABLE, 100, 6, "FUNC"),
    test18("test18", 0, SquareType.PROPERTY, 100, 6, "DARKBLUE"),
    PODATEK2("PODATEK 2", 0, SquareType.UNBUYABLE, 100, 6, "FUNC"),
    test20("test20", 0, SquareType.PROPERTY, 100, 6, "DARKBLUE");

    private final String displayName;
    private final int rotation;
    private final SquareType type;
    private final int price;
    private final int rent;
    private final String propertyGroup;

    SquareInfo(String displayName, int rotation, SquareType type, int price, int rent, String propertyGroup) {
        this.displayName = displayName;
        this.rotation = rotation;
        this.type = type;
        this.price = price;
        this.rent = rent;
        this.propertyGroup = propertyGroup;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getRotation() {
        return rotation;
    }

    public boolean isUnbuyable() {
        return type.isUnbuyable();
    }

    public int getPrice() {
        return price;
    }

    public int getRent() {
        return rent;
    }

    public String getPropertyGroup() {
        return propertyGroup;
    }

    public static SquareInfo[] getBoardOrder() {
        return new SquareInfo[]{
                START, ALEJA_MICKIEWICZA, KASA1, BRATYSLAWSKA, PODATEK1, test1, test2, SZANSA1, test4, test5,
                MIASTECZKO_STUDENCKIE, OLSZEWSKIEGO, COMPANY1, CZARNOWIEJSKA, LEA, DWORZEC2, test7, KASA2, test9, test10,
                AKADEMIK, FLORIANSKA, SZANSA2, PLAC_BISKUPI, PARK_KRAKOWSKI, DWORZEC3, test12, test13, COMPANY2, test15,
                IDZ_NA_MIASTECZKO, MIODOWA, DUPA, KASA3, PAVIA, DWORZEC4, SZANSA3, test18, PODATEK2, test20
        };
    }
}
