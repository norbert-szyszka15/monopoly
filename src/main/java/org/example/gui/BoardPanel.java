package org.example.gui;

import org.example.logic.Board;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoardPanel extends JLayeredPane {
    private final Board gameBoard;
    private final Dice dice1;
    private final Dice dice2;
    private final Player player1View;
    private final Player player2View;

    private final List<RelativeComponent> relativeComponents = new ArrayList<>();

    public BoardPanel() {
        setBorder(new LineBorder(Color.BLACK));
        setLayout(null); // absolutne pozycjonowanie

        // 1. Board (plansza) — pełny obszar
        gameBoard = new Board(0, 0, 1112, 1112);
        gameBoard.setBackground(new Color(51, 255, 153));
        add(gameBoard, Integer.valueOf(0));
        gameBoard.setBounds(0, 0, 1112, 1112); // początkowo, ale będzie skalowane razem z panelem

        // 2. Gracze
        player1View = new Player(1, Color.RED);
        player2View = new Player(2, Color.BLUE);
        add(player1View, Integer.valueOf(1));
        add(player2View, Integer.valueOf(1));
        addRelativeComponent(player1View, 0.01f, 0.01f, 0.03f, 0.03f); // startowy narożnik
        addRelativeComponent(player2View, 0.06f, 0.01f, 0.03f, 0.03f);

        // 3. Kostki
        dice1 = new Dice();
        dice2 = new Dice();
        add(dice1, Integer.valueOf(1));
        add(dice2, Integer.valueOf(1));
        addRelativeComponent(dice1, 497f / 1124f, 900f / 1124f, 60f / 1124f, 60f / 1124f);
        addRelativeComponent(dice2, 567f / 1124f, 900f / 1124f, 60f / 1124f, 60f / 1124f);


        // 4. Etykiety tekstowe
        createResponsiveLabel(
                "MONOPOLY",
                Color.WHITE, new Color(235, 28, 34),
                160f / 1124f, 200f / 1124f, 800f / 1124f, 140f / 1124f,
                0.4f // wielkość fontu względem wysokości
        );

        createResponsiveLabel(
                "SZANSA",
                Color.WHITE, new Color(106, 208, 249),
                200f / 1124f, 550f / 1124f, 300f / 1124f, 180f / 1124f,
                0.3f
        );

        createResponsiveLabel(
                "KASA",
                Color.WHITE, new Color(240, 113, 30),
                610f / 1124f, 550f / 1124f, 300f / 1124f, 180f / 1124f,
                0.3f
        );

        // 4. Obsługa skalowania – przelicz bounds proporcjonalnie
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeRelativeComponents();
                gameBoard.setBounds(0, 0, getWidth(), getHeight());
            }
        });
    }

    private void addRelativeComponent(JComponent comp, float x, float y, float w, float h) {
        relativeComponents.add(new RelativeComponent(comp, x, y, w, h));
    }

    private void resizeRelativeComponents() {
        int w = getWidth();
        int h = getHeight();
        for (RelativeComponent rc : relativeComponents) {
            rc.updateBounds(w, h);
        }
    }

    private JLabel createResponsiveLabel(String text, Color fg, Color bg, float x, float y, float w, float h, float fontScale) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(fg);
        label.setBackground(bg);
        label.setOpaque(true);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        add(label, Integer.valueOf(2)); // warstwa powyżej pól

        relativeComponents.add(new RelativeComponent(label, x, y, w, h) {
            @Override
            public void updateBounds(int parentWidth, int parentHeight) {
                super.updateBounds(parentWidth, parentHeight);
                int newFontSize = Math.round(Math.min(widthPercent * parentWidth, heightPercent * parentHeight) * fontScale);
                label.setFont(label.getFont().deriveFont((float) newFontSize));
            }
        });

        return label;
    }

    private void drawHouses(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        // zakładamy planszę 11×11 w całym obszarze
        int cell = Math.min(width, height) / 11;

        // iterujemy obie wizualizacje graczy
        for (Player p : new Player[]{ player1View, player2View }) {
            HashMap<Integer,Integer> houses = p.getHousesOnPropertyMap();
            for (HashMap.Entry<Integer,Integer> e : houses.entrySet()) {
                int pos = e.getKey();
                int count = e.getValue();
                if (count <= 0) continue;

                // przeliczanie pozycji na wiersz/kolumnę
                int row, col;
                if (pos < 11) {            // dolna krawędź
                    row = 10; col = 10 - pos;
                } else if (pos < 20) {     // lewa krawędź
                    row = 10 - (pos - 11) - 1; col = 0;
                } else if (pos < 31) {     // górna krawędź
                    row = 0; col = pos - 20;
                } else {                   // prawa krawędź
                    row = pos - 31 + 1; col = 10;
                }

                int x0 = col * cell;
                int y0 = row * cell;
                int w = cell / 6;
                int h = cell / 6;

                // rysuj obok siebie max 4 domki u dołu kafelka
                for (int i = 0; i < count; i++) {
                    int x = x0 + 2 + i * (w + 2);
                    int y = y0 + cell - h - 2;
                    g.setColor(Color.GREEN);
                    g.fillRect(x, y, w, h);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, w, h);
                }
            }
        }
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public Dice getDice1() {
        return dice1;
    }

    public Dice getDice2() {
        return dice2;
    }

    public Player getPlayer1View() {
        return player1View;
    }

    public Player getPlayer2View() {
        return player2View;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHouses(g);
    }
}
