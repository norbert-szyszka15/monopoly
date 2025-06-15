package org.example.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Square extends JPanel {
    int number;
    private final String name;
    String description;
    JLabel nameLabel;
    static int totalSquares = 0;
    private int price;
    private int rentPrice;

    public void setRentPrice(int renPrice) {
        this.rentPrice = renPrice;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Square(int xValue, int yValue, int width, int height, String labelString, int rotationDegrees) {
        number = totalSquares;
        totalSquares++;
        setBorder(new LineBorder(Color.BLACK));
        setBounds(xValue, yValue, width, height);
        name = labelString;
        this.setLayout(null);

        if(rotationDegrees == 0) {
            nameLabel = new JLabel(labelString);
            nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            nameLabel.setBounds(0,20,this.getWidth(),20);
            this.add(nameLabel);
        } else {
            nameLabel = new JLabel(labelString) {
                protected void paintComponent(Graphics graphics) {
                    Graphics2D graphics2 = (Graphics2D) graphics;
                    graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    AffineTransform at = graphics2.getTransform();
                    Shape oldShape = graphics2.getClip();
                    double x = getWidth() / 2.0;
                    double y = getHeight() / 2.0;
                    at.rotate(Math.toRadians(rotationDegrees), x, y);
                    graphics2.setTransform(at);
                    graphics2.setClip(oldShape);
                    super.paintComponent(graphics2);
                }
            };

            if(rotationDegrees == 90) {
                nameLabel.setBounds(20, 0, this.getWidth(), this.getHeight());
            }
            if(rotationDegrees == -90) {
                nameLabel.setBounds(-10, 0, this.getWidth(), this.getHeight());
            }
            if(rotationDegrees == 180) {
                nameLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
            }
            if(rotationDegrees == 135 || rotationDegrees == -135 || rotationDegrees == -45 || rotationDegrees == 45) {
                nameLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
            }
            nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            this.add(nameLabel);
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if(this.number == 1 || this.number == 3 || this.number == 4) {
            graphics.drawRect(0, this.getHeight()-20, this.getWidth(), 20);
            graphics.setColor(Color.BLUE);
            graphics.fillRect(0, this.getHeight()-20, this.getWidth(), 20);
        }
        if(this.number == 6 || this.number == 8 || this.number == 9) {
            graphics.drawRect(0, 0, 20, this.getHeight());
            graphics.setColor(Color.PINK);
            graphics.fillRect(0, 0, 20, this.getHeight());
        }
        if(this.number == 11 || this.number == 13 || this.number == 14) {
            graphics.drawRect(0, 0, this.getWidth(), 20);
            graphics.setColor(Color.ORANGE);
            graphics.fillRect(0, 0, this.getWidth(), 20);
        }
        if(this.number == 16 || this.number == 17 || this.number == 19) {
            graphics.drawRect(this.getWidth()-20, 0, 20, this.getHeight());
            graphics.setColor(Color.GREEN);
            graphics.fillRect(this.getWidth()-20, 0, 20, this.getHeight());
        }
    }

    private boolean isRentPaid = false;
    public boolean isRentPaid() {
        return isRentPaid;
    }
    public void setRentPaid(boolean rentPaid) {
        isRentPaid = rentPaid;
    }
}
