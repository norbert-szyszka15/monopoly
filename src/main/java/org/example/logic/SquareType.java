package org.example.logic;

public enum SquareType {
    PROPERTY(false),
    UNBUYABLE(true),
    SPECIAL(true);

    private final boolean unbuyable;

    SquareType(boolean unbuyable) {
        this.unbuyable = unbuyable;
    }

    public boolean isUnbuyable() {
        return unbuyable;
    }
}