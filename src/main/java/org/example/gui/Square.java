package org.example.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

public class Square extends JPanel {
    private static int totalSquares = 0;
    private final int number;

    private final String name;
    private final JLabel nameLabel;

    private int price;

    public Square(String labelString) {
        number = totalSquares++;
        name = labelString;
        setBorder(new LineBorder(Color.BLACK));
        setLayout(null);
        setOpaque(false); // by plansza pod spodem była widoczna

        // Ustawienie etykiety
        nameLabel = new JLabel(getDisplayLabel());
        nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER); // dla wieloliniowego
        nameLabel.setBounds(0, 5, this.getWidth(), this.getHeight()); // może być dopasowane
        this.add(nameLabel);


    }

    public String getDisplayLabel() {
        if (price > 0) {
            return "<html><div style='text-align:center'>" + name.replace("\n", "<br>") + "<br><b>" + price + " zł</b></div></html>";
        }
        return "<html><div style='text-align:center'>" + name.replace("\n", "<br>") + "</div></html>";
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setRentPrice() { }

    public void setPrice(int price) {
        this.price = price;
    }

    public void refreshLabel() {
        nameLabel.setText(getDisplayLabel());
    }

    @Override
    public void doLayout() {
        // nameLabel zajmuje całą powierzchnię, z marginesem
        nameLabel.setBounds(0, 0, getWidth(), getHeight());
        float fontScale = Math.min(getWidth(), getHeight()) / 10f; //ustawienie skalowania czcionki 12f mniejsza, 8f wieksza
        nameLabel.setFont(nameLabel.getFont().deriveFont(fontScale));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        int w = getWidth();
        int h = getHeight();
        int t = (int) (Math.min(w, h) * 0.2); // grubość paska
        // TŁO CAŁEGO POLA
        g.setColor(new Color(230, 230, 230)); // lub dowolny kolor
        g.fillRect(0, 0, w, h);

        switch (number) {
            // TOP
            case 1, 3 -> g.setColor(new Color(146, 84, 53));
            case 6, 8, 9 -> g.setColor(new Color(168, 226, 248));
            // RIGHT
            case 11, 13, 14 -> g.setColor(new Color(217, 58, 149));
            case 16, 17, 19 -> g.setColor(new Color(245, 149, 28));
            // BOTTOM
            case 21, 23, 24 -> g.setColor(new Color(235, 28, 34));
            case 26, 27, 29 -> g.setColor(new Color(253, 243, 0));
            // LEFT
            case 31, 32, 34 -> g.setColor(new Color(29, 180, 88));
            case 37, 39 -> g.setColor(new Color(0, 115, 186));
            default -> {
                return;
            }
        }

        if (number <= 10) {
            g.fillRect(0, h - t, w, t);
        } // top
        else if (number <= 20) {
            g.fillRect(0, 0, t, h);
        } // right
        else if (number <= 30) {
            g.fillRect(0, 0, w, t);
        } // bottom
        else {
            g.fillRect(w - t, 0, t, h);
        } // left
    }
}
