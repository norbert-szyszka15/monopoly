package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Dice extends JPanel {
    private final Random rand = new Random();
    private int faceValue = 1;

    private enum DotPosition {
        TOP_LEFT    (-1,  1),
        TOP_RIGHT   ( 1,  1),
        CENTER      ( 0,  0),
        BOTTOM_LEFT (-1, -1),
        BOTTOM_RIGHT( 1, -1),
        MID_LEFT    (-1,  0),
        MID_RIGHT   ( 1,  0);

        final int xDir;
        final int yDir;

        DotPosition(int xDir, int yDir) {
            this.xDir = xDir;
            this.yDir = yDir;
        }
    }

    public Dice() {
        setBackground(new Color(240, 240, 240));
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    private void drawDot(Graphics g, DotPosition pos, int dotSize, int offset) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int x = getWidth() / 2 + (pos.xDir * offset) - dotSize / 2;
        int y = getHeight() / 2 + (pos.yDir * offset) - dotSize / 2;

        g2d.fillOval(x, y, dotSize, dotSize);
    }

    private void drawFace(Graphics g, int dotSize, int offset, DotPosition... positions) {
        g.setColor(Color.BLACK);
        for (DotPosition pos : positions) {
            drawDot(g, pos, dotSize, offset);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int min = Math.min(getWidth(), getHeight());
        int dotSize = min / 6;           // proporcjonalna wielkość kropki
        int offset = min / 3;            // odległość od środka

        switch (faceValue) {
            case 1 -> drawFace(g, dotSize, offset, DotPosition.CENTER);
            case 2 -> drawFace(g, dotSize, offset, DotPosition.TOP_LEFT, DotPosition.BOTTOM_RIGHT);
            case 3 -> drawFace(g, dotSize, offset, DotPosition.TOP_LEFT, DotPosition.CENTER, DotPosition.BOTTOM_RIGHT);
            case 4 -> drawFace(g, dotSize, offset,
                    DotPosition.TOP_LEFT, DotPosition.TOP_RIGHT,
                    DotPosition.BOTTOM_LEFT, DotPosition.BOTTOM_RIGHT);
            case 5 -> drawFace(g, dotSize, offset,
                    DotPosition.TOP_LEFT, DotPosition.TOP_RIGHT,
                    DotPosition.CENTER,
                    DotPosition.BOTTOM_LEFT, DotPosition.BOTTOM_RIGHT);
            case 6 -> drawFace(g, dotSize, offset,
                    DotPosition.TOP_LEFT, DotPosition.TOP_RIGHT,
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
