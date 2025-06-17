package org.example.logic.strategySpecialField;

import org.example.gui.dialog.CardDialog;
import org.example.gui.Player;

public class CommunityChestCardAction implements SquareAction {
    @Override
    public void execute(Player player, int position) {
        new CardDialog(player, "KASA SPOŁECZNA", player.drawCard()).setVisible(true);
    }
}
