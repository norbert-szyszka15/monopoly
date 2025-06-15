package org.example.gui;

import org.example.logic.Board;
import org.example.logic.Card;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends JPanel {
    static int totalPlayers; // może się przydać później, do rozpatrzenia
    static HashMap<Integer, Integer> landAndMortgageRegister = new HashMap<>();
    JLabel labelPlayerNumber;
    private int playerNumber;
    private boolean skipNextTurn = false; // flaga czy gracz skipuje kolejke
    private int currentPlayerPosition = 0; // lokalizacja gracza na planszy
    private ArrayList<Integer> ownedProperties = new ArrayList<>(); // posiadane akty własności
    private int wallet = 3200; // początkowa ilość gotówki gracza

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
        this.setBounds(playerNumber * 30, 33, 20, 28); // do rozbudowania tak, żeby liczba graczy mogła być zmienialna i dynamiczna
        totalPlayers++;
    }

    public ArrayList<Integer> getOwnedProperties() {
        return ownedProperties;
    }

    public int getWallet() {
        return wallet;
    }

    public void setSkipNextTurn(boolean skip) {
        this.skipNextTurn = skip;
    }

    public boolean shouldSkipNextTurn() {
        return skipNextTurn;
    }

    public void withdrawMoneyFromWallet(int withdrawalAmount) {
        if (withdrawalAmount > wallet) {
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
        if (landAndMortgageRegister.containsKey(position)) {
            System.out.print("Lokacja jest już zakupiona przez innego gracza!");
        } else {
            ownedProperties.add(this.getCurrentPlayerPosition());
            // zapisanie w księdze wieczystej numeru gracza i pozycji aktu własności, który kupił
            landAndMortgageRegister.put(position, this.getPlayerNumber());
        }
    }

    public void payRentTo(Player owner, Board gameBoard) {
        // Pobieramy aktualna pozycje do pózniejszej weryfikacji rent
        int position = this.currentPlayerPosition;

        // Pobieramy kwote czynszu
        int rentAmount = gameBoard.getAllSquares().get(position).getRentPrice();
        System.out.println(rentAmount);

        // Wykonujemy transakcje
        this.withdrawMoneyFromWallet(rentAmount);
        owner.depositMoneyToWallet(rentAmount);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public String drawChanceCard() {
        Card drawnCard = Card.getRandomCard();
        System.out.println("Gracz " + playerNumber + " wylosował kartę: " + drawnCard.getDescription());
        drawnCard.execute(this);
        return drawnCard.getDescription(); // Zwróć opis do wyświetlenia
    }

    public void move(int sumOfDiceValues) {
        if (currentPlayerPosition + sumOfDiceValues >= 40) {
            depositMoneyToWallet(200);
        }
        int targetPosition = (currentPlayerPosition + sumOfDiceValues) % 40;
        currentPlayerPosition = targetPosition;

        // Aktualizacja pozycji
        Square targetSquare = Board.getInstance().getSquareAtPosition(targetPosition);
        int x = targetSquare.getX() + (playerNumber == 1 ? 15 : 45);
        int y = targetSquare.getY() + 15;
        this.setLocation(x, y);

        // Sprawdź czy pole to Szansa
        if (Board.getInstance().isChanceSquare(targetPosition)) {
            drawChanceCard();
        }

        if (landAndMortgageRegister.containsKey(this.getCurrentPlayerPosition())) {
            GuiMain.infoConsole.setText("This property belongs to player " + landAndMortgageRegister.get(this.getCurrentPlayerPosition()));
        }
    }
}
