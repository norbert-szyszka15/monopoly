package org.example.logic.strategySpecialField;

import org.example.gui.Player;
import org.example.gui.dialog.CardDialog;

public class ChanceCardAction implements SquareAction {
    @Override
    public void execute(Player player, int position) {
        new CardDialog(player, "SZANSA", player.drawCard()).setVisible(true);
    }
}
