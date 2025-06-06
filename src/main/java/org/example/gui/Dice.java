package org.example.gui;

import java.awt.Color;
import java.awt.Graphics;

import java.util.Random;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Dice extends JPanel {
    Random rand = new Random();
    int faceValue = 1;

    public Dice(int xValue, int yValue, int width, int height) {
        setBorder(new LineBorder(Color.BLACK));
        setBounds(xValue, yValue, width, height);
    }

    public void printValueOne(Graphics graphics) {
        graphics.fillOval(getWidth()/2 - 5/2, getHeight()/2 - 5/2, 5, 5);
    }

    public void printValueTwo(Graphics graphics) {
        graphics.fillOval(getWidth()/2 - 15, getHeight()/2 + 10, 5, 5);
        graphics.fillOval(getWidth()/2 + 10, getHeight()/2 - 15, 5, 5);
    }

    public void printValueThree(Graphics graphics) {
        graphics.fillOval(getWidth()/2 - 15, getHeight()/2 + 10, 5, 5);
        graphics.fillOval(getWidth()/2 + 10, getHeight()/2 - 15, 5, 5);
        graphics.fillOval(getWidth()/2 - 5/2, getHeight()/2 - 5/2, 5, 5);
    }

    public void printValueFour(Graphics graphics) {
        graphics.fillOval(getWidth()/2 - 15, getHeight()/2 + 10, 5, 5);
        graphics.fillOval(getWidth()/2 + 10, getHeight()/2 - 15, 5, 5);
        graphics.fillOval(getWidth()/2 - 15, getHeight()/2 - 15, 5, 5);
        graphics.fillOval(getWidth()/2 + 10, getHeight()/2 + 10, 5, 5);
    }

    public void printValueFive(Graphics graphics) {
        graphics.fillOval(getWidth()/2 - 15, getHeight()/2 + 10, 5, 5);
        graphics.fillOval(getWidth()/2 + 10, getHeight()/2 - 15, 5, 5);
        graphics.fillOval(getWidth()/2 - 15, getHeight()/2 - 15, 5, 5);
        graphics.fillOval(getWidth()/2 + 10, getHeight()/2 + 10, 5, 5);
        graphics.fillOval(getWidth()/2 - 5/2, getHeight()/2 - 5/2, 5, 5);
    }

    public void printValueSix(Graphics graphics) {
        graphics.fillOval(getWidth()/2 - 15, getHeight()/2 + 10, 5, 5);
        graphics.fillOval(getWidth()/2 + 10, getHeight()/2 - 15, 5, 5);
        graphics.fillOval(getWidth()/2 - 15, getHeight()/2 - 15, 5, 5);
        graphics.fillOval(getWidth()/2 + 10, getHeight()/2 + 10, 5, 5);
        graphics.fillOval(getWidth()/2 - 15, getHeight()/2 - 5/2, 5, 5);
        graphics.fillOval(getWidth()/2 + 10, getHeight()/2 - 5/2, 5, 5);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if(faceValue == 1) {
            printValueOne(graphics);
        } else if(faceValue == 2) {
            printValueTwo(graphics);
        } else if(faceValue == 3) {
            printValueThree(graphics);
        } else if(faceValue == 4) {
            printValueFour(graphics);
        } else if(faceValue == 5) {
            printValueFive(graphics);
        } else {
            printValueSix(graphics);
        }
    }

    public void rollDice() {
        faceValue = rand.nextInt(6) +1;
        repaint();
    }

    public int getFaceValue() {
        return faceValue;
    }

    public Dice(int xValue, int yValue, int width, int height, String label) {
        setBorder(new LineBorder(Color.BLACK));
        setBounds(xValue, yValue, width, height);
    }
}
