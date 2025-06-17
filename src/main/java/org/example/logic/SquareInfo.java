package org.example.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public enum SquareInfo {
    // TOP ROW
    START("START", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    ALEJA_MICKIEWICZA("Aleja Mickiewicza", SquareType.PROPERTY, 100, 10, "BROWN"),
    KASA1("KASA 1", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    BRATYSLAWSKA("Bratysławska", SquareType.PROPERTY, 100, 10, "BROWN"),
    PODATEK1("PODATEK 1", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    KRAKOW_GLOWNY("KRAKÓW\nGŁÓWNY", SquareType.PROPERTY, 400, 50, "STATION"),
    LUZYCKA("Łużycka", SquareType.PROPERTY, 150, 15, "LIGHTBLUE"),
    SZANSA1("SZANSA 1", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    LAGIEWNICKA("Łagiewnicka", SquareType.PROPERTY, 150, 15, "LIGHTBLUE"),
    PODGORSKA("Podgórska", SquareType.PROPERTY, 150, 15, "LIGHTBLUE"),

    // RIGHT COLUMN
    MIASTECZKO_STUDENCKIE("MIASTECZKO\nSTUDENCKIE", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    LIPSKA("Lipska", SquareType.PROPERTY, 200, 20, "PINK"),
    WODOCIAGI("Wodociągi\nKrakowskie", SquareType.PROPERTY, 300, 30, "COMPANY"),
    SASKA("Saska", SquareType.PROPERTY, 200, 20, "PINK"),
    LEA("Lea", SquareType.PROPERTY, 200, 20, "PINK"),
    KRAKOW_PLASZOW("KRAKÓW\nPŁASZÓW", SquareType.PROPERTY, 400, 50, "STATION"),
    Barska("Barska", SquareType.PROPERTY, 250, 25, "ORANGE"),
    DLUGA("Długa", SquareType.PROPERTY, 250, 25, "ORANGE"),
    KASA2("KASA 2", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    ANTYCZNA("Antyczna", SquareType.PROPERTY, 250, 25, "ORANGE"),

    // BOTTOM ROW
    AKADEMIK("AKADEMIK", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    FLORIANSKA("Floriańska", SquareType.PROPERTY, 300, 30, "RED"),
    SZANSA2("SZANSA 2", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    PLAC_BISKUPI("Plac Biskupi", SquareType.PROPERTY, 300, 30, "RED"),
    PARK_KRAKOWSKI("Park Krakowski", SquareType.PROPERTY, 300, 30, "RED"),
    KRAKOW_LOTNISKO("KRAKÓW\nLOTNISKO", SquareType.PROPERTY, 400, 50, "STATION"),
    PODCHORAZYCH("Podchorążych", SquareType.PROPERTY, 350, 35, "YELLOW"),
    WARSZAWSKA("Warszawska", SquareType.PROPERTY, 350, 35, "YELLOW"),
    ELEKTROCIEPLOWNIA("Elektrociepłownia\nPGE\nKraków", SquareType.PROPERTY, 300, 30, "COMPANY"),
    SUKIENNICKA("Sukiennicka", SquareType.PROPERTY, 350, 35, "YELLOW"),

    // LEFT COLUMN
    IDZ_NA_MIASTECZKO("IDZIESZ NA\nMIASTECZKO\nSTUDENCKIE", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    MIODOWA("Miodowa", SquareType.PROPERTY, 400, 40, "GREEN"),
    BRONOWICKA("Bronowicka", SquareType.PROPERTY, 400, 40, "GREEN"),
    KASA3("KASA 3", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    PAWIA("Pawia", SquareType.PROPERTY, 400, 40, "GREEN"),
    KRAKOW_BRONOWICE("KRAKÓW\nBRONOWICE", SquareType.PROPERTY, 400, 50, "STATION"),
    SZANSA3("SZANSA 3", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    GRODZKA("Grodzka", SquareType.PROPERTY, 450, 45, "DARKBLUE"),
    PODATEK2("PODATEK 2", SquareType.UNBUYABLE, 0, 0, "FUNC"),
    STARE_MIASTO("Stare\nMiasto", SquareType.PROPERTY, 500, 50, "DARKBLUE");

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
                MIASTECZKO_STUDENCKIE, LIPSKA, WODOCIAGI, SASKA, LEA, KRAKOW_PLASZOW, Barska, DLUGA, KASA2, ANTYCZNA,
                AKADEMIK, FLORIANSKA, SZANSA2, PLAC_BISKUPI, PARK_KRAKOWSKI, KRAKOW_LOTNISKO, PODCHORAZYCH, WARSZAWSKA, ELEKTROCIEPLOWNIA, SUKIENNICKA,
                IDZ_NA_MIASTECZKO, MIODOWA, BRONOWICKA, KASA3, PAWIA, KRAKOW_BRONOWICE, SZANSA3, GRODZKA, PODATEK2, STARE_MIASTO
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
