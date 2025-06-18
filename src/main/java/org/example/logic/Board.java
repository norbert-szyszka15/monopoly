package org.example.logic;

import org.example.gui.RelativeComponent;
import org.example.gui.Square;
import org.example.logic.strategySpecialField.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board extends JPanel {
    private static Board instance;
    private final ArrayList<Square> allSquares = new ArrayList<>();
    private final ArrayList<Square> unbuyableSquares = new ArrayList<>();
    private final Map<Integer, SquareAction> squareActions = new HashMap<>();
    private final List<RelativeComponent> relativeComponents = new ArrayList<>();

    public Board() {
        instance = this;
        setBorder(new LineBorder(Color.BLACK));
        setLayout(null);
        setBackground(new Color(51, 255, 153));

        initializeSquares();
        initializeSquareActions();

        // listener do skalowania elementów
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resizeRelativeComponents();
            }
        });
    }

    public static Board getInstance() {
        return instance;
    }

    public ArrayList<Square> getAllSquares() {
        return allSquares;
    }

    public ArrayList<Square> getUnbuyableSquares() {
        return unbuyableSquares;
    }

    public Square getSquareAtPosition(int position) {
        return allSquares.get(position);
    }

    public SquareAction getSquareAction(int position) {
        return squareActions.get(position);
    }

    public int getMiasteczkoPosition() {
        for (int i = 0; i < allSquares.size(); i++) {
            if (allSquares.get(i).getName().contains("MIASTECZKO\nSTUDENCKIE")) {
                return i;
            }
        }
        return -1;
    }

    public void initializeSquares() {
        SquareInfo[] squaresOrder = SquareInfo.getBoardOrder();

        int[] xCoords = {
                6, 106, 206, 306, 406, 506, 606, 706, 806, 906,
                1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006,
                1006, 906, 806, 706, 606, 506, 406, 306, 206, 106,
                6, 6, 6, 6, 6, 6, 6, 6, 6, 6
        };
        int[] yCoords = {
                6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
                6, 106, 206, 306, 406, 506, 606, 706, 806, 906,
                1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006,
                1006, 906, 806, 706, 606, 506, 406, 306, 206, 106
        };

        for (int i = 0; i < squaresOrder.length; i++) {
            SquareInfo squareInfo = squaresOrder[i];
            Square square = new Square(squareInfo.getDisplayName());

            this.add(square);
            allSquares.add(square);

            // Ustaw proporcje pozycji
            float x = xCoords[i] / 1112f;
            float y = yCoords[i] / 1112f;
            float size = 100f / 1112f;

            relativeComponents.add(new RelativeComponent(square, x, y, size, size));

            // Konfiguracja ceny i typu
            if (squareInfo.isUnbuyable()) {
                unbuyableSquares.add(square);
            } else {
                square.setPrice(squareInfo.getPrice());
                square.setRentPrice();
                square.refreshLabel();
            }
            System.out.println("[" + squareInfo.getDisplayName() + "] cena: " + squareInfo.getPrice());

        }
    }

    public void initializeSquareActions() {
        for (int i = 0; i < allSquares.size(); i++) {
            Square square = allSquares.get(i);
            String name = square.getName();

            if (name.contains("SZANSA")) {
                squareActions.put(i, new ChanceCardAction());
            } else if (name.contains("KASA")) {
                squareActions.put(i, new CommunityChestCardAction());
            } else if (name.contains("PODATEK")) {
                squareActions.put(i, new TaxAction());
            } else if (name.contains("IDZIESZ")) {
                squareActions.put(i, new GoToMiasteczkoAction());
            }
        }
    }

    private void resizeRelativeComponents() {
        int w = getWidth();
        int h = getHeight();
        for (RelativeComponent rc : relativeComponents) {
            rc.updateBounds(w, h);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
