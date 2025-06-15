package org.example.logic;

import org.example.gui.Square;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Board extends JPanel {
    private ArrayList<Square> allSquares = new ArrayList<>();
    private ArrayList<Square> unbuyableSquares = new ArrayList<>();


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

        JLabel lblMonopoly = getLblMonopoly();
        this.add(lblMonopoly);
    }
    //metoda do oblugi pol szansa
    public boolean isChanceSquare(int position) {
        Square square = allSquares.get(position);
        return square.getName().contains("SZANSA");
    }
    //metoda do oblugi pol szansa
    public boolean isTaxSquare(int position) {
        Square square = allSquares.get(position);
        return square.getName().contains("PODATEK");
    }


    private JLabel getLblMonopoly() {
        JLabel lblMonopoly = new JLabel("MONOPOLY"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                AffineTransform aT = g2.getTransform();
                Shape oldshape = g2.getClip();
                double x = getWidth()/2.0;
                double y = getHeight()/2.0;
                aT.rotate(Math.toRadians(-45), x, y);
                g2.setTransform(aT);
                g2.setClip(oldshape);
                super.paintComponent(g);
            }
        };
        lblMonopoly.setForeground(Color.WHITE);
        lblMonopoly.setBackground(Color.RED);
        lblMonopoly.setOpaque(true);
        lblMonopoly.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonopoly.setFont(new Font("Lucida Grande", Font.PLAIN, 120));
        lblMonopoly.setBounds(160, 460, 800, 140);
        return lblMonopoly;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.getBackground());
        g.fillOval(0, 0, getWidth(), getHeight());
    }
}
