package org.example.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Player extends JPanel {
    private int playerNumber;
    JLabel labelPlayerNumber;
    static int totalPlayers; // może się przydać później, do rozpatrzenia
    static HashMap<Integer, Integer> landAndMortgageRegister = new HashMap<>();

    private int currentPlayerPosition = 0; // lokalizacja gracza na planszy
    private ArrayList<Integer> ownedProperties = new ArrayList<>(); // posiadane akty własności
    private int wallet = 3200; // początkowa ilość gotówki gracza

    public ArrayList<Integer> getOwnedProperties() {
        return ownedProperties;
    }

    public int getWallet() {
        return wallet;
    }

    public void withdrawMoneyFromWallet(int withdrawalAmount) {
        if(withdrawalAmount > wallet) {
            setVisible(false);
            System.out.println("Gracz " + playerNumber + " zbankrutował!");
        } else {
            wallet -= withdrawalAmount;
        }
    }

    public void depositMoneyToWallet(int depositAmount) {
        wallet += depositAmount;
        System.out.println("Gracz " + playerNumber + " zarobił trochę pieniędzy!");
    }

    public int getCurrentPlayerPosition() {
        return currentPlayerPosition;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean hasOwnedProperties() {
        return ownedProperties.contains(playerNumber);
    }

    public void buyProperty(int position) {
        if(landAndMortgageRegister.containsKey(position)) {
            System.out.print("Lokacja jest już zakupiona przez innego gracza!");
        } else {
            ownedProperties.add(this.getCurrentPlayerPosition());
            // zapisanie w księdze wieczystej numeru gracza i pozycji aktu własności, który kupił
            landAndMortgageRegister.put(position, this.getPlayerNumber());
        }
    }

    public Player(int xValue, int yValue, int width, int height) {
        setBorder(new LineBorder(Color.BLACK));
        setBounds(xValue, yValue, 20, 20);
        this.setLayout(null);
    }

    public Player(int playerNumber, Color color) {
        this.playerNumber = playerNumber;
        this.setBackground(color);
        labelPlayerNumber = new JLabel("" + playerNumber);
        labelPlayerNumber.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        labelPlayerNumber.setForeground(Color.WHITE);
        this.add(labelPlayerNumber);
        this.setBounds(playerNumber*30, 33, 20, 28); // do rozbudowania tak, żeby liczba graczy mogła być zmienialna i dynamiczna
        totalPlayers++;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    int[] xLocationsOfPlayer1 = {31, 131, 231, 331, 431, 531,
            531, 531, 531, 531, 531,
            431, 331, 231, 131, 31,
            31, 31, 31, 31};

    int[] yLocationsOfPlayer1 = {33, 33, 33, 33, 33, 33,
            133, 233, 333, 433, 533,
            533, 533, 533, 533, 533,
            433, 333, 233, 133};

    int[] xLocationsOfPlayer2 = {61, 191, 291, 361, 461, 561,
            561, 561, 561, 561, 561,
            461, 361, 261, 161, 61,
            61, 61, 61, 61};

    int[] yLocationsOfPlayer2 = {33, 33, 33, 33, 33, 33,
            133, 233, 333, 433, 533,
            533, 533, 533, 533, 533,
            433, 333, 233, 133};

    public void move(int sumOfDiceValues) {
        if(currentPlayerPosition + sumOfDiceValues > 19) {
            depositMoneyToWallet(200);
        }
        int targetPosition = (currentPlayerPosition + sumOfDiceValues) % 20;
        currentPlayerPosition = targetPosition;

        if(GuiMain.nowPlaying == 0) {
            this.setLocation(xLocationsOfPlayer1[targetPosition], yLocationsOfPlayer1[targetPosition]);
        } else {
            this.setLocation(xLocationsOfPlayer2[targetPosition], yLocationsOfPlayer2[targetPosition]);
        }

        if(landAndMortgageRegister.containsKey(this.getCurrentPlayerPosition())) {
            GuiMain.infoConsole.setText("This property belongs to player " + landAndMortgageRegister.get(this.getCurrentPlayerPosition()));
        }
        //ledger.put(this.getCurrentSquareNumber(), this.getPlayerNumber());
    }
}
