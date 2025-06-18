package org.example.logic.strategySpecialField;

import org.example.gui.Player;
import org.example.gui.dialog.TaxDialog;

import java.util.Random;

public class TaxAction implements SquareAction {

    @Override
    public void execute(Player player, int position) {
        Random rand = new Random();
        int taxAmount = rand.nextInt(800);
        player.withdrawMoneyFromWallet(taxAmount);
        System.out.println("Gracz " + player.getPlayerNumber() + " płaci podatek: " + taxAmount + " zł");
        new TaxDialog(player, player.getPlayerNumber(), taxAmount).setVisible(true);
    }
}
