package org.example.logic;

public enum SquareInfo {
    BASZTOWA("Basztowa", 135, SquareType.UNBUYABLE, 0, 0),
    ALEJA_MICKIEWICZA("Aleja Mickiewicza", 180, SquareType.PROPERTY, 100, 6),
    KARMELICKA("Karmelicka", 180, SquareType.UNBUYABLE, 0, 0),
    BRATYSLAWSKA("Bratysławska", 180, SquareType.PROPERTY, 100, 6),
    WARSZAWSKA("Warszawska", 180, SquareType.PROPERTY, 120, 8),
    KRUPNICZA("Krupnicza", -135, SquareType.UNBUYABLE, 0, 0),
    OLSZEWSKIEGO("Olszewskiego", -90, SquareType.PROPERTY, 140, 10),
    BRACKA("Bracka", -90, SquareType.UNBUYABLE, 0, 0),
    CZARNOWIEJSKA("Czarnowiejska", -90, SquareType.PROPERTY, 140, 10),
    LEA("Lea", -90, SquareType.PROPERTY, 160, 12),
    PRADNICKA("Prądnicka", -45, SquareType.UNBUYABLE, 0, 0),
    FLORIANSKA("Floriańska", 0, SquareType.PROPERTY, 180, 14),
    RYNEK_GLOWNY("Rynek Główny", 0, SquareType.UNBUYABLE, 0, 0),
    PLAC_BISKUPI("Plac Biskupi", 0, SquareType.PROPERTY, 180, 14),
    PARK_KRAKOWSKI("Park Krakowski", 0, SquareType.PROPERTY, 200, 16),
    MUZEUM_LOTNICTWA("Muzeum Lotnictwa Polskiego", 45, SquareType.UNBUYABLE, 0, 0),
    MIODOWA("Miodowa", 90, SquareType.PROPERTY, 300, 26),
    GRODZKA("Grodzka", 90, SquareType.PROPERTY, 300, 26),
    DLUGA("Długa", 90, SquareType.UNBUYABLE, 0, 0),
    PAVIA("Pawia", 90, SquareType.PROPERTY, 320, 28);

    private final String displayName;
    private final int rotation;
    private final SquareType type;
    private final int price;
    private final int rent;

    SquareInfo(String displayName, int rotation, SquareType type, int price, int rent) {
        this.displayName = displayName;
        this.rotation = rotation;
        this.type = type;
        this.price = price;
        this.rent = rent;
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

    public static SquareInfo[] getBoardOrder() {
        return new SquareInfo[]{
                BASZTOWA, ALEJA_MICKIEWICZA, KARMELICKA, BRATYSLAWSKA, WARSZAWSKA,
                KRUPNICZA, OLSZEWSKIEGO, BRACKA, CZARNOWIEJSKA, LEA,
                PRADNICKA, FLORIANSKA, RYNEK_GLOWNY, PLAC_BISKUPI, PARK_KRAKOWSKI,
                MUZEUM_LOTNICTWA, MIODOWA, GRODZKA, DLUGA, PAVIA
        };
    }
}
