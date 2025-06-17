package org.example.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public enum SquareInfo {
    // TOP ROW
    START("START", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    ALEJA_MICKIEWICZA("Aleja Mickiewicza", SquareType.PROPERTY, 100, 6, "BROWN"),
    KASA1("KASA 1", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    BRATYSLAWSKA("Bratysławska", SquareType.PROPERTY, 100, 6, "BROWN"),
    PODATEK1("PODATEK 1", SquareType.UNBUYABLE, 120, 8, "FUNC"),
    KRAKOW_GLOWNY("KRAKÓW\nGŁÓWNY", SquareType.PROPERTY, 100, 6, "STATION"),
    LUZYCKA("Łużycka", SquareType.PROPERTY, 100, 6, "LIGHTBLUE"),
    SZANSA1("SZANSA 1", SquareType.UNBUYABLE, 100, 6, "FUNC"),
    LAGIEWNICKA("Łagiewnicka", SquareType.PROPERTY, 100, 6, "LIGHTBLUE"),
    PODGORSKA("Podgórska", SquareType.PROPERTY, 100, 6, "LIGHTBLUE"),

    // RIGHT COLUMN
    MIASTECZKO_STUDENCKIE("MIASTECZKO\nSTUDENCKIE", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    LIPSKA("Lipska", SquareType.PROPERTY, 140, 10, "PINK"),
    COMPANY1("COMPANY 1", SquareType.PROPERTY, 0, 0, "COMPANY"),
    SASKA("Saska", SquareType.PROPERTY, 140, 10, "PINK"),
    LEA("Lea", SquareType.PROPERTY, 160, 12, "PINK"),
    KRAKOW_PLASZOW("KRAKÓW\nPŁASZÓW", SquareType.PROPERTY, 100, 6, "STATION"),
    Barska("Barska", SquareType.PROPERTY, 100, 6, "ORANGE"),
    KASA2("KASA 2", SquareType.UNBUYABLE, 100, 6, "FUNC"),
    test9("test9", SquareType.PROPERTY, 100, 6, "ORANGE"),
    ANTYCZNA("Antyczna", SquareType.PROPERTY, 100, 6, "ORANGE"),

    // BOTTOM ROW
    AKADEMIK("AKADEMIK", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    FLORIANSKA("Floriańska", SquareType.PROPERTY, 180, 14, "RED"),
    SZANSA2("SZANSA 2", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    PLAC_BISKUPI("Plac Biskupi", SquareType.PROPERTY, 180, 14, "RED"),
    PARK_KRAKOWSKI("Park Krakowski", SquareType.PROPERTY, 200, 16, "RED"),
    KRAKOW_LOTNISKO("KRAKÓW\nLOTNISKO", SquareType.PROPERTY, 100, 6, "STATION"),
    test12("Podchorążych", SquareType.PROPERTY, 100, 6, "YELLOW"),
    test13("Warszawska", SquareType.PROPERTY, 100, 6, "YELLOW"),
    COMPANY2("COMPANY 2", SquareType.PROPERTY, 100, 6, "COMPANY"),
    test15("Sukiennicka", SquareType.PROPERTY, 100, 6, "YELLOW"),

    // LEFT COLUMN
    IDZ_NA_MIASTECZKO("IDZIESZ NA\nMIASTECZKO\nSTUDENCKIE", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    MIODOWA("Miodowa", SquareType.PROPERTY, 300, 26, "GREEN"),
    BRONOWICKA("Bronowicka", SquareType.PROPERTY, 300, 26, "GREEN"),
    KASA3("KASA 3", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    PAWIA("Pawia", SquareType.PROPERTY, 320, 28, "GREEN"),
    KRAKOW_BRONOWICE("KRAKÓW\nBRONOWICE", SquareType.PROPERTY, 100, 6, "STATION"),
    SZANSA3("SZANSA 3", SquareType.UNBUYABLE, 100, 6, "FUNC"),
    test18("Grodzka", SquareType.PROPERTY, 100, 6, "DARKBLUE"),
    PODATEK2("PODATEK 2", SquareType.UNBUYABLE, 100, 6, "FUNC"),
    test20("Stare\nMiasto", SquareType.PROPERTY, 100, 6, "DARKBLUE");

    private static final HashMap<String, List<SquareInfo>> map = new HashMap<>();
    private final String displayName;
    private final SquareType type;
    private final int price;
    private final int rent;
    private final String propertyGroup;

    private static final HashMap<String, List<SquareInfo>> GROUPS = new HashMap<>();

    static {
        for (SquareInfo sq : values()) {
            // pomijamy czynności i stacje
            if (sq.type == SquareType.PROPERTY && !"COMPANY".equals(sq.propertyGroup) && !"STATION".equals(sq.propertyGroup)) {
                GROUPS.computeIfAbsent(sq.propertyGroup, k -> new ArrayList<>()).add(sq);
            }
        }
    }

    SquareInfo(String displayName, SquareType type, int price, int rent, String propertyGroup) {
        this.displayName = displayName;
        this.type = type;
        this.price = price;
        this.rent = rent;
        this.propertyGroup = propertyGroup;
    }

    public static SquareInfo[] getBoardOrder() {
        return new SquareInfo[]{
                START, ALEJA_MICKIEWICZA, KASA1, BRATYSLAWSKA, PODATEK1, KRAKOW_GLOWNY, LUZYCKA, SZANSA1, LAGIEWNICKA, PODGORSKA,
                MIASTECZKO_STUDENCKIE, LIPSKA, COMPANY1, SASKA, LEA, KRAKOW_PLASZOW, Barska, KASA2, test9, ANTYCZNA,
                AKADEMIK, FLORIANSKA, SZANSA2, PLAC_BISKUPI, PARK_KRAKOWSKI, KRAKOW_LOTNISKO, test12, test13, COMPANY2, test15,
                IDZ_NA_MIASTECZKO, MIODOWA, BRONOWICKA, KASA3, PAWIA, KRAKOW_BRONOWICE, SZANSA3, test18, PODATEK2, test20
        };
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isUnbuyable() {
        return type.isUnbuyable();
    }

    public int getPrice() {
        return price;
    }

    public static List<SquareInfo> getPropertiesByGroup(String group) {
        return GROUPS.getOrDefault(group, Collections.emptyList());
    }

    public int getRent() {
        return rent;
    }

    public String getPropertyGroup() {
        return propertyGroup;
    }
}
