package org.example.logic;

import org.example.gui.Player;

import java.util.Random;
import java.util.function.Consumer;

public enum Card {

    CHANCE_WINDA_BABILON("Włożyłeś podpalone krzesło do windy płacisz 1000zl na pokrycie szkód",
            player -> player.withdrawMoneyFromWallet(1000)),

    WIN_LOTTERY("Wygrana w loterii! +150 zł",
            player -> player.depositMoneyToWallet(150)),

    TAX_AUDIT("Kontrola skarbowa! Płać 100 zł",
            player -> player.withdrawMoneyFromWallet(100)),

    STUDENT_SCHOLARSHIP("Stypendium! +200 zł",
            player -> player.depositMoneyToWallet(200)),

    DRUNK_FINE("Mandat za pijaństwo! -80 zł",
            player -> player.withdrawMoneyFromWallet(80)),
    DZIEKANAT_ZAMKNIETY("Dziekanat zamknięty... tracisz czas (i kolejkę)",
            player -> player.setSkipNextTurn(true)),

    FREE_PARKING("Darmowy parking! Otrzymujesz 50 zł",
            player -> player.depositMoneyToWallet(50));

    private final String description;
    private final Consumer<Player> action;

    Card(String description, Consumer<Player> action) {
        this.description = description;
        this.action = action;
    }
    private static final Random RANDOM = new Random();

    public static Card getRandomCard() {
        Card[] cards = values();
        return cards[RANDOM.nextInt(cards.length)];
    }

    public String getDescription() {
        return description;
    }

    public void execute(Player player) {
        action.accept(player);
    }
}