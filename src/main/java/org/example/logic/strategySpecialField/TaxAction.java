package org.example.logic.strategySpecialField;

import org.example.gui.Player;

import java.util.Random;

public class TaxAction implements SquareAction {
    private static final Random RANDOM = new Random();

    @Override
    public void execute(Player player, int position) {
        int taxAmount = RANDOM.nextInt(701) + 100;
        player.withdrawMoneyFromWallet(taxAmount);
        System.out.println("Gracz " + player.getPlayerNumber() + " płaci podatek: " + taxAmount + " zł");
    }
}
