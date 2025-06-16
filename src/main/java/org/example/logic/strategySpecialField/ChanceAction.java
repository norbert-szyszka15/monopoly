package org.example.logic.strategySpecialField;

import org.example.gui.Player;

public class ChanceAction implements SquareAction {
    @Override
    public void execute(Player player, int position) {
        player.drawChanceCard();
    }
}
