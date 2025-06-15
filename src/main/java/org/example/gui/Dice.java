package org.example.gui;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Dice extends JPanel {
    private final Random rand = new Random();
    private int faceValue = 1;
    private static final int DOT_SIZE = 8;
    private static final int DOT_OFFSET = 12;


    private enum DotPosition {
        TOP_LEFT    (-1,  1),
        TOP_RIGHT   ( 1,  1),
        CENTER      ( 0,  0),
        BOTTOM_LEFT (-1, -1),
        BOTTOM_RIGHT( 1, -1),
        MID_LEFT    (-1,  0),
        MID_RIGHT   ( 1,  0);

        final int xDir; // -1 = lewo, 0 = środek, 1 = prawo
        final int yDir; // -1 = góra, 0 = środek, 1 = dół

        DotPosition(int xDir, int yDir) {
            this.xDir = xDir;
            this.yDir = yDir;
        }
    }

    public Dice(int x, int y, int width, int height) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setBounds(x, y, width, height);
        setBackground(new Color(240, 240, 240));
    }

    private void drawDot(Graphics g, DotPosition pos) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int x = getWidth()/2  + (pos.xDir * DOT_OFFSET) - DOT_SIZE/2;
        int y = getHeight()/2 + (pos.yDir * DOT_OFFSET) - DOT_SIZE/2;

        g2d.fillOval(x, y, DOT_SIZE, DOT_SIZE);
    }

    private void drawFace(Graphics g, DotPosition... positions) {
        g.setColor(Color.BLACK);
        for (DotPosition pos : positions) {
            drawDot(g, pos);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch(faceValue) {
            case 1 -> drawFace(g, DotPosition.CENTER);
            case 2 -> drawFace(g, DotPosition.TOP_LEFT, DotPosition.BOTTOM_RIGHT);
            case 3 -> drawFace(g, DotPosition.TOP_LEFT, DotPosition.CENTER, DotPosition.BOTTOM_RIGHT);
            case 4 -> drawFace(g, DotPosition.TOP_LEFT, DotPosition.TOP_RIGHT,
                    DotPosition.BOTTOM_LEFT, DotPosition.BOTTOM_RIGHT);
            case 5 -> drawFace(g, DotPosition.TOP_LEFT, DotPosition.TOP_RIGHT, DotPosition.CENTER,
                    DotPosition.BOTTOM_LEFT, DotPosition.BOTTOM_RIGHT);
            case 6 -> drawFace(g, DotPosition.TOP_LEFT, DotPosition.TOP_RIGHT,
                    DotPosition.MID_LEFT, DotPosition.MID_RIGHT,
                    DotPosition.BOTTOM_LEFT, DotPosition.BOTTOM_RIGHT);
        }
    }

    public void rollDice() {
        faceValue = rand.nextInt(6) + 1;
        repaint();
    }

    public int getFaceValue() {
        return faceValue;
    }
}