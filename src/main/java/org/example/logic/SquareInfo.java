package org.example.logic;

public enum SquareInfo {
    // TOP ROW
    START("START", 0, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    ALEJA_MICKIEWICZA("Aleja Mickiewicza", 0, SquareType.PROPERTY, 100, 6, "BROWN"),
    KASA1("KASA 1", 0, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    BRATYSLAWSKA("Bratysławska", 0, SquareType.PROPERTY, 100, 6, "BROWN"),
    PODATEK1("PODATEK 1", 0, SquareType.UNBUYABLE, 120, 8, "FUNC"),
    KRAKOW_GLOWNY("<html>KRAKÓW<br> GŁÓWNY</html>", 0, SquareType.PROPERTY, 100, 6, "STATION"),
    LUZYCKA("Łużycka", 0, SquareType.PROPERTY, 100, 6, "LIGHTBLUE"),
    SZANSA1("SZANSA 1", 0, SquareType.UNBUYABLE, 100, 6, "FUNC"),
    LAGIEWNICKA("Łagiewnicka", 0, SquareType.PROPERTY, 100, 6, "LIGHTBLUE"),
    PODGORSKA("Podgórska", 0, SquareType.PROPERTY, 100, 6, "LIGHTBLUE"),

    // RIGHT COLUMN
    MIASTECZKO_STUDENCKIE("<html>MIASTECZKO<br> STUDENCKIE</html>", 0, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    LIPSKA("Lipska", -0, SquareType.PROPERTY, 140, 10, "PINK"),
    COMPANY1("COMPANY 1", -0, SquareType.PROPERTY, 0, 0, "COMPANY"),
    SASKA("Saska", -0, SquareType.PROPERTY, 140, 10, "PINK"),
    LEA("Lea", -0, SquareType.PROPERTY, 160, 12, "PINK"),
    KRAKOW_PLASZOW("<html>KRAKÓW<br>PŁASZÓW</html>", 0, SquareType.PROPERTY, 100, 6, "STATION"),
    Barska("Barska", 0, SquareType.PROPERTY, 100, 6, "ORANGE"),
    KASA2("KASA 2", 0, SquareType.UNBUYABLE, 100, 6, "FUNC"),
    test9("test9", 0, SquareType.PROPERTY, 100, 6, "ORANGE"),
    ANTYCZNA("Antyczna", 0, SquareType.PROPERTY, 100, 6, "ORANGE"),

    // BOTTOM ROW
    AKADEMIK("AKADEMIK", 0, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    FLORIANSKA("Floriańska", 0, SquareType.PROPERTY, 180, 14, "RED"),
    SZANSA2("SZANSA 2", 0, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    PLAC_BISKUPI("Plac Biskupi", 0, SquareType.PROPERTY, 180, 14, "RED"),
    PARK_KRAKOWSKI("Park Krakowski", 0, SquareType.PROPERTY, 200, 16, "RED"),
    KRAKOW_LOTNISKO("<html>KRAKÓW<br>LOTNISKO</html>", 0, SquareType.PROPERTY, 100, 6, "STATION"),
    test12("Podchorążych", 0, SquareType.PROPERTY, 100, 6, "YELLOW"),
    test13("Warszawska", 0, SquareType.PROPERTY, 100, 6, "YELLOW"),
    COMPANY2("COMPANY 2", 0, SquareType.PROPERTY, 100, 6, "COMPANY"),
    test15("Sukiennicka", 0, SquareType.PROPERTY, 100, 6, "YELLOW"),

    // LEFT COLUMN
    IDZ_NA_MIASTECZKO("<html>IDZIESZ NA<br> MIASTECZKO<br> STUDENCKIE<html>", 0, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    MIODOWA("Miodowa", 0, SquareType.PROPERTY, 300, 26, "GREEN"),
    BRONOWICKA("Bronowicka", 0, SquareType.PROPERTY, 300, 26, "GREEN"),
    KASA3("KASA 3", 0, SquareType.UNBUYABLE, 0, 0, "FUNC"),
    PAWIA("Pawia", 0, SquareType.PROPERTY, 320, 28, "GREEN"),
    KRAKOW_BRONOWICE("<html>KRAKÓW<br>BRONOWICE</html>", 0, SquareType.PROPERTY, 100, 6, "STATION"),
    SZANSA3("SZANSA 3", 0, SquareType.UNBUYABLE, 100, 6, "FUNC"),
    test18("Grodzka", 0, SquareType.PROPERTY, 100, 6, "DARKBLUE"),
    PODATEK2("PODATEK 2", 0, SquareType.UNBUYABLE, 100, 6, "FUNC"),
    test20("<html>Stare<br>Miasto</html>", 0, SquareType.PROPERTY, 100, 6, "DARKBLUE");

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
                START, ALEJA_MICKIEWICZA, KASA1, BRATYSLAWSKA, PODATEK1, KRAKOW_GLOWNY, LUZYCKA, SZANSA1, LAGIEWNICKA, PODGORSKA,
                MIASTECZKO_STUDENCKIE, LIPSKA, COMPANY1, SASKA, LEA, KRAKOW_PLASZOW, Barska, KASA2, test9, ANTYCZNA,
                AKADEMIK, FLORIANSKA, SZANSA2, PLAC_BISKUPI, PARK_KRAKOWSKI, KRAKOW_LOTNISKO, test12, test13, COMPANY2, test15,
                IDZ_NA_MIASTECZKO, MIODOWA, BRONOWICKA, KASA3, PAWIA, KRAKOW_BRONOWICE, SZANSA3, test18, PODATEK2, test20
        };
    }
}
