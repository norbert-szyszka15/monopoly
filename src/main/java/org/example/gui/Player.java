package org.example.gui;

import org.example.logic.Board;
import org.example.logic.Card;
import org.example.logic.SquareInfo;
import org.example.logic.strategySpecialField.SquareAction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;import java.util.HashMap;

public class Player extends JPanel {
    private final GuiMain guiMain;
    JLabel labelPlayerNumber;

    static int totalPlayers; // może się przydać później, do rozpatrzenia
    private final int playerNumber;
    private int currentPlayerPosition = 0; // lokalizacja gracza na planszy
    private int wallet = 3200; // początkowa ilość gotówki gracza

    static HashMap<Integer, Integer> landAndMortgageRegister = new HashMap<>();
    private final HashMap<String, Integer> ownedPropertiesGroupCount = new HashMap<>();
    private final HashMap<Integer,Integer> housesOnProperty = new HashMap<>();
    private final ArrayList<Integer> ownedProperties = new ArrayList<>(); // posiadane akty własności

    private boolean skipNextTurn = false; // flaga czy gracz skipuje kolejke
    public boolean houseBuiltThisTurn = false;

    public Player(int playerNumber, Color color,GuiMain guiMain) {
        this.guiMain = guiMain;
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

    public int getCurrentPlayerPosition() {
        return currentPlayerPosition;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setSkipNextTurn(boolean skip) {
        this.skipNextTurn = skip;
    }

    public void setPosition(int position) {
        this.currentPlayerPosition = position;

        // Aktualizacja pozycji graficznej
        Square targetSquare = Board.getInstance().getSquareAtPosition(position);
        int x = targetSquare.getX() + (playerNumber == 1 ? 15 : 45);
        int y = targetSquare.getY() + 15;
        this.setLocation(x, y);
    }

    public boolean shouldSkipNextTurn() {
        return skipNextTurn;
    }

    public void withdrawMoneyFromWallet(int withdrawalAmount) {
        if (withdrawalAmount > wallet) {
            setVisible(false);
            int winner = (playerNumber == 1) ? 2 : 1;
            guiMain.endGame(winner);
        } else {
            wallet -= withdrawalAmount;
        }
    }

    public void depositMoneyToWallet(int depositAmount) {
        wallet += depositAmount;
        System.out.println("Gracz " + playerNumber + " zarobił trochę pieniędzy!");
    }

    public boolean canBuildHouse(int position) {
        SquareInfo info = SquareInfo.getBoardOrder()[position];
        String group = info.getPropertyGroup();

        if ("COMPANY".equals(group) || "STATION".equals(group)) {
            return false;
        }

        if (houseBuiltThisTurn) {
            return false;
        }
        // gracz musi być właścicielem
        if (!ownedProperties.contains(position)) {
            return false;
        }

        // musi mieć pełny monopol na grupę
        int ownedInGroup = ownedPropertiesGroupCount.getOrDefault(group, 0);
        int groupSize = SquareInfo.getPropertiesByGroup(group).size();
        if (ownedInGroup < groupSize) return false;

        // max 4 domki
        int currentHouses = housesOnProperty.getOrDefault(position, 0);
        return currentHouses < 4;
    }

    public void buildHouse(int position) {
        if (!canBuildHouse(position)) {
            throw new IllegalStateException("Nie możesz postawić domu na tej nieruchomości.");
        }
        housesOnProperty.merge(position, 1, Integer::sum);
        houseBuiltThisTurn = true;

        int cost = 100;
        withdrawMoneyFromWallet(cost);
    }

    public void resetHouseBuiltFlag() {
        houseBuiltThisTurn = false;
    }

    public void buyProperty(int position) {
        SquareInfo info = SquareInfo.getBoardOrder()[position];
        String group = info.getPropertyGroup();

        if (landAndMortgageRegister.containsKey(position)) {
            System.out.print("Lokacja jest już zakupiona przez innego gracza!");
        } else {
            ownedProperties.add(this.getCurrentPlayerPosition());
            // zapisanie w księdze wieczystej numeru gracza i pozycji aktu własności, który kupił
            landAndMortgageRegister.put(position, this.getPlayerNumber());
            ownedPropertiesGroupCount.merge(group, 1, Integer::sum);
        }
    }

    public int calculateRent(int position, int rollDiceSum) {
        SquareInfo info = SquareInfo.getBoardOrder()[position];
        String group = info.getPropertyGroup();

        // CZYNSZ DLA PÓL STATION: 50 x liczba posiadanych pól STAION
        if ("STATION".equals(group)) {
            int ownedStations = ownedPropertiesGroupCount.getOrDefault("STATION", 0);
            return 50 * ownedStations;
        }

        // CZYNSZ DLA PÓL COMPANY: 5x lub 10x liczba wyrzuconych oczek
        if ("COMPANY".equals(group)) {
            int ownedCompanies =  ownedPropertiesGroupCount.getOrDefault(group, 0);
            int rentMultiplier = (ownedCompanies == 2) ? 10 : 5;
            return rollDiceSum * rentMultiplier;
        }

        // CZYNSZ DLA ZWYKŁYCH POSIADŁOŚCI: czynsz standardowy związany z liczbą domków
        int houses = housesOnProperty.getOrDefault(position, 0);
        int baseRent = info.getRent();

        // jeżeli brak domków, ale monopol – czynsz podwojony
        if (houses == 0) {
            int ownedInGroup = ownedPropertiesGroupCount.getOrDefault(group, 0);
            if (ownedInGroup == SquareInfo.getPropertiesByGroup(group).size()) {
                return baseRent * 2;  // klasyczne Monopoly: czynsz x2 za monopol bez domków
            }
            return baseRent;
        }

        // z domkami: czynsz rośnie liniowo (np. baseRent * (houses+1))
        return baseRent * (houses + 1);
    }

    public HashMap<Integer,Integer> getHousesOnPropertyMap() {
        return new HashMap<>(housesOnProperty);
    }

    public void payRentTo(Player owner, int rollDiceSum) {
        // Pobieramy aktualna pozycje do pózniejszej weryfikacji rent
        int position = this.currentPlayerPosition;

        // Pobieramy kwote czynszu
        int rentAmount = owner.calculateRent(position,  rollDiceSum);
        System.out.println(rentAmount);

        // Wykonujemy transakcje
        this.withdrawMoneyFromWallet(rentAmount);
        owner.depositMoneyToWallet(rentAmount);
    }

    public String drawCard() {
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
        setPosition(targetPosition);

        // Sprawdź i wykonaj akcję dla pola
        SquareAction action = Board.getInstance().getSquareAction(targetPosition);
        if (action != null) {
            action.execute(this, targetPosition);
        }

        if (landAndMortgageRegister.containsKey(currentPlayerPosition)) {
            GuiMain.infoConsole.setText("This property belongs to player " + landAndMortgageRegister.get(currentPlayerPosition));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
