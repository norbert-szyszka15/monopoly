package org.example.gui;

import org.example.logic.Board;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Panel odpowiedzialny za całą część „planszy” w GuiMain:
 * – JLayeredPane o rozmiarze 1124×1124px
 * – wewnątrz gameBoard (1112×1112px)
 * – dwa obiekty Dice
 * – widoki graczy (Player)
 */
public class BoardPanel extends JLayeredPane {
    private final Board   gameBoard;
    private final Dice    dice1;
    private final Dice    dice2;
    private final Player  player1View;
    private final Player  player2View;

    public BoardPanel() {
        // rozmiar i granica „planszy”
        setBorder(new LineBorder(Color.BLACK));
        setBounds(6, 6, 1124, 1124);
        setLayout(null);

        // 1) podłoże: Board z logiki gry (własne JPanel)
        gameBoard = new Board(6, 6, 1112, 1112);
        gameBoard.setBackground(new Color(51, 255, 153));
        // (Board sam ustawia swój internal layout – nie musimy dawać setBounds,
        // bo robi to wewnątrz siebie na podstawie przekazanych argumentów)
        add(gameBoard, Integer.valueOf(0));

        // 2) pionki graczy
        player1View = new Player(1, Color.RED);
        player2View = new Player(2, Color.BLUE);
        add(player1View, Integer.valueOf(1));
        add(player2View, Integer.valueOf(1));

        // 3) kostki – dokładnie w tych samych miejscach co wcześniej
        dice1 = new Dice(497, 900, 60, 60);
        dice2 = new Dice(567, 900, 60, 60);
        add(dice1, Integer.valueOf(1));
        add(dice2, Integer.valueOf(1));
    }

    /** Zwraca dostęp do Board, żeby logika mogła odczytać listę pól itd. */
    public Board getGameBoard() {
        return gameBoard;
    }

    /** Kostka nr 1 */
    public Dice getDice1() {
        return dice1;
    }

    /** Kostka nr 2 */
    public Dice getDice2() {
        return dice2;
    }

    /** Widok pionka gracza 1 */
    public Player getPlayer1View() {
        return player1View;
    }

    /** Widok pionka gracza 2 */
    public Player getPlayer2View() {
        return player2View;
    }
}
