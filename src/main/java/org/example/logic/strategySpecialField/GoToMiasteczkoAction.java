package org.example.logic.strategySpecialField;

import org.example.gui.Player;
import org.example.logic.Board;

public class GoToMiasteczkoAction implements SquareAction  {
    @Override
    public void execute(Player player, int position) {
        int miasteczkoPosition = Board.getInstance().getMiasteczkoPosition();
        if (miasteczkoPosition != -1) {
            player.setPosition(miasteczkoPosition);
            player.setSkipNextTurn(true);
            System.out.println("Gracz " + player.getPlayerNumber() + " idzie na Miasteczko Studenckie!");
        }
    }
}
