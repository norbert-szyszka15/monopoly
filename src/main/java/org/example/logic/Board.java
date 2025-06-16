package org.example.logic;

import org.example.gui.Square;
import org.example.logic.strategySpecialField.CardAction;
import org.example.logic.strategySpecialField.GoToMiasteczkoAction;
import org.example.logic.strategySpecialField.SquareAction;
import org.example.logic.strategySpecialField.TaxAction;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Board extends JPanel {
    private final ArrayList<Square> allSquares = new ArrayList<>();
    private final ArrayList<Square> unbuyableSquares = new ArrayList<>();


    private static Board instance;

    public static Board getInstance() {
        return instance;
    }

    public Board(int xValue, int yValue, int width, int height) {
        instance = this;
        setBorder(new LineBorder(Color.BLACK));
        setBounds(xValue, yValue, 1112, 1112);
        this.setLayout(null);
        initializeSquares();
        initializeSquareActions();
    }

    public ArrayList<Square> getAllSquares() {
        return allSquares;
    }

    public ArrayList<Square> getUnbuyableSquares() {
        return unbuyableSquares;
    }

    public Square getSquareAtPosition(int positon) {
        return allSquares.get(positon);
    }

    public void initializeSquares() {
        SquareInfo[] squaresOrder = SquareInfo.getBoardOrder();
        int[] xCoords = {6, 106, 206, 306, 406, 506, 606, 706, 806, 906,
                         1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006,
                         1006, 906, 806, 706, 606, 506, 406, 306, 206, 106,
                         6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
        int[] yCoords = {6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
                         6, 106, 206, 306, 406, 506, 606, 706, 806, 906,
                         1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006, 1006,
                         1006, 906, 806, 706, 606, 506, 406, 306, 206, 106};
        for (int i = 0; i < squaresOrder.length; i++) {
            SquareInfo squareInfo = squaresOrder[i];
            Square square = new Square(
                    xCoords[i], yCoords[i], 100, 100,
                    squareInfo.getDisplayName(), squareInfo.getRotation()
            );

            this.add(square);
            allSquares.add(square);

            if (squareInfo.isUnbuyable()) {
                unbuyableSquares.add(square);
            } else {
                square.setPrice(squareInfo.getPrice());
                square.setRentPrice(squareInfo.getRent());
            }
        }

        JLabel labelMonopoly = getLabel(
                Color.WHITE, new Color(235, 28, 34),
                "MONOPOLY", 120,
                160, 200, 800, 140
        );
        this.add(labelMonopoly);

        JLabel labelChanceCards = getLabel(
                Color.WHITE, new Color(106, 208, 249),
                "SZANSA", 60,
                200, 550, 300, 180
        );
        this.add(labelChanceCards);

        JLabel labelCommunityChestsCards = getLabel(
                Color.WHITE, new Color(240, 113, 30),
                "KASA", 60,
                610, 550, 300, 180
        );
        this.add(labelCommunityChestsCards);
    }




    private JLabel getLabel(
            Color foregroundColor,
            Color backgroundColor,
            String labelText,
            int textSize,
            int xBound,
            int yBound,
            int width,
            int height
    ) {
        JLabel label = new JLabel(labelText);
        label.setForeground(foregroundColor);
        label.setBackground(backgroundColor);
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Lucida Grande", Font.PLAIN, textSize));
        label.setBounds(xBound, yBound, width, height);
        return label;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.getBackground());
        g.fillOval(0, 0, getWidth(), getHeight());
    }


    //zmiany wzorzec strategii
    private final Map<Integer, SquareAction> squareActions = new HashMap<>();

    public void initializeSquareActions() {
        for (int i = 0; i < allSquares.size(); i++) {
            Square square = allSquares.get(i);
            String name = square.getName();

            if (name.contains("SZANSA")) {
                squareActions.put(i, new CardAction());
            }
            else if (name.contains("PODATEK")) {
                squareActions.put(i, new TaxAction());
            }
            else if (name.equals("IDZIESZ NA MIASTECZKO STUDENCKIE")) {
                squareActions.put(i, new GoToMiasteczkoAction());
            }
        }
    }

    public SquareAction getSquareAction(int position) {
        return squareActions.get(position);
    }

    public int getMiasteczkoPosition() {
        for (int i = 0; i < allSquares.size(); i++) {
            if (allSquares.get(i).getName().equals("MIASTECZKO STUDENCKIE")) {
                return i;
            }
        }
        return -1;
    }
}


