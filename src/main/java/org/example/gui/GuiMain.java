package org.example.gui;

import org.example.logic.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class GuiMain extends JFrame {
    static JTextArea infoConsole;
    static int turnCounter = 0;
    static int nowPlaying = 0;
    JPanel playerAssetsPanel;
    CardLayout c1 = new CardLayout();
    ArrayList<Player> players = new ArrayList<Player>();
    JButton buttonNextTurn;
    JButton buttonRollDice;
    JButton buttonPayRent;
    JButton buttonBuy;
    JTextArea panelPlayer1TextArea;
    JTextArea panelPlayer2TextArea;
    Board gameBoard;
    Player player1;
    Player player2;
    Boolean doubleDiceforPlayer1 = false;
    Boolean doubleDiceforPlayer2 = false;
    private JPanel contentIncluder;

    public GuiMain() {

        setTitle("Monopoly");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setSize(1560, 1174);
        contentIncluder = new JPanel();
        contentIncluder.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentIncluder);
        contentIncluder.setLayout(null);


// Panel planszy
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBorder(new LineBorder(Color.BLACK));
        contentIncluder.add(layeredPane);

        gameBoard = new Board(6, 6, 1112, 1112);
        gameBoard.setBackground(new Color(51, 255, 153));
        layeredPane.add(gameBoard, Integer.valueOf(0));

// Panel boczny
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setBorder(new LineBorder(Color.BLACK));
        rightPanel.setLayout(null); // nadal null, jeśli chcesz ręcznie dodawać elementy
        contentIncluder.add(rightPanel);

// Listener który ustawia komponenty proporcjonalnie
        contentIncluder.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = contentIncluder.getSize();

                int padding = 6;


                // wysokość całego okna (czyli maksymalna wielkość planszy)
                int boardSize = size.height - 2 * padding;

                // minimalna szerokość panelu bocznego (27% szerokości okna)
                int minRightPanelWidth = (int) (size.width * 0.27);

                // maksymalna szerokość panelu to tyle, ile zostaje po planszy i odstępach
                int availableRightPanelWidth = size.width - boardSize - 3 * padding;

                // faktyczna szerokość panelu – większa z dwóch (żeby mieć minimum 27%)
                int rightPanelWidth = Math.max(minRightPanelWidth, availableRightPanelWidth);

                // gdyby się nie mieściło w oknie, trzeba dodatkowo zmniejszyć boardSize
                if (rightPanelWidth + boardSize + 3 * padding > size.width) {
                    boardSize = size.width - rightPanelWidth - 3 * padding;
                }

                int boardAreaSize = size.height - 2 * padding;    // maksymalny kwadrat, ograniczony wysokością

                if (boardAreaSize + rightPanelWidth + 3 * padding > size.width) {
                    // jeśli plansza się nie zmieści, zmniejszamy ją
                    boardAreaSize = size.width - rightPanelWidth - 3 * padding;
                }


                layeredPane.setBounds(padding, padding, boardAreaSize, boardAreaSize);

                // ustawienia samej planszy w środku layeredPane
                gameBoard.setBounds(0, 0, boardAreaSize, boardAreaSize);

                // ustawienia panelu bocznego
                rightPanel.setBounds(
                        padding * 2 + boardAreaSize, // x
                        padding,                     // y
                        rightPanelWidth,            // width
                        boardAreaSize               // height
                );


            }
        });


        player1 = new Player(1, Color.RED);
        players.add(player1);
        layeredPane.add(player1, Integer.valueOf(1));

        player2 = new Player(2, Color.BLUE);
        players.add(player2);
        layeredPane.add(player2, Integer.valueOf(1));

        buttonBuy = new JButton("BUY");
        buttonBuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Player currentPlayer = players.get(nowPlaying);
                infoConsole.setText("Zakupiłeś " + gameBoard.getAllSquares().get(currentPlayer.getCurrentPlayerPosition()).getName());
                currentPlayer.buyProperty(currentPlayer.getCurrentPlayerPosition());
                int withdrawAmount = gameBoard.getAllSquares().get(currentPlayer.getCurrentPlayerPosition()).getPrice();
                currentPlayer.withdrawMoneyFromWallet(withdrawAmount);
                buttonBuy.setEnabled(false);
                updatePanelPlayer1TextArea();
                updatePanelPlayer2TextArea();
            }
        });


        buttonBuy.setBounds(81, 478, 117, 29);
        rightPanel.add(buttonBuy);
        buttonBuy.setEnabled(false);

        buttonPayRent = new JButton("PAY RENT");
        buttonPayRent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Player currentPlayer = players.get(nowPlaying);


                Player squareOwner = players.get((Player.landAndMortgageRegister.get(currentPlayer.getCurrentPlayerPosition())) == 1 ? 0 : 1);

                currentPlayer.payRentTo(squareOwner, gameBoard);// transakcja owner dostaje current oddaje
                infoConsole.setText("Zapłaciłeś czynsz graczowi " + squareOwner.getPlayerNumber());


                buttonNextTurn.setEnabled(true);
                buttonPayRent.setEnabled(false);
                updatePanelPlayer1TextArea();
                updatePanelPlayer2TextArea();
            }
        });
        buttonPayRent.setBounds(210, 478, 117, 29);
        rightPanel.add(buttonPayRent);
        buttonPayRent.setEnabled(false);

        Dice dice1 = new Dice(497, 900, 60, 60);
        layeredPane.add(dice1, Integer.valueOf(1));

        Dice dice2 = new Dice(567, 900, 60, 60);
        layeredPane.add(dice2, Integer.valueOf(1));

        //stara wersja
       /* buttonRollDice = new JButton("ROLL DICE");


        buttonRollDice = new JButton("ROLL DICE");
        buttonRollDice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Player currentPlayer = players.get(nowPlaying);

                if (currentPlayer.shouldSkipNextTurn()) {
                    currentPlayer.setSkipNextTurn(false);
                    nowPlaying = (nowPlaying + 1) % players.size();

                    // Reset przycisków
                    buttonRollDice.setEnabled(true);
                    buttonNextTurn.setEnabled(false);
                    buttonPayRent.setEnabled(false);
                    buttonBuy.setEnabled(false);

                    // Aktualizuj UI
                    updateGameUI();
                    infoConsole.setText("Gracz " + currentPlayer.getPlayerNumber() +
                            " pomija turę! Teraz gracz: " + (nowPlaying + 1));
                    return;
                }
                if (nowPlaying == 0) {
                    // RUNDA GRACZA NR 1
                    int dice1OldValue = dice1.getFaceValue();
                    int dice2OldValue = dice2.getFaceValue();
                    dice1.rollDice();
                    dice2.rollDice();
                    int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue();
                    if (dice1.getFaceValue() == dice2.getFaceValue()) {
                        doubleDiceforPlayer1 = true;
                    } else {
                        doubleDiceforPlayer1 = false;
                    }

                    player1.move(dicesTotal);

                    if (Player.landAndMortgageRegister.containsKey(player1.getCurrentPlayerPosition())
                            && Player.landAndMortgageRegister.get(player1.getCurrentPlayerPosition()) != player1.getPlayerNumber()) {
                        buttonBuy.setEnabled(false);
                        buttonRollDice.setEnabled(false);
                        buttonNextTurn.setEnabled(false);
                        buttonPayRent.setEnabled(true);
                    }
                    if (Player.landAndMortgageRegister.containsKey(player1.getCurrentPlayerPosition())
                            && Player.landAndMortgageRegister.get(player1.getCurrentPlayerPosition()) == player1.getPlayerNumber()) {
                        buttonBuy.setEnabled(false);
                        buttonRollDice.setEnabled(false);
                        buttonNextTurn.setEnabled(true);
                    }
                    if (gameBoard.getUnbuyableSquares().contains(gameBoard.getAllSquares().get(player1.getCurrentPlayerPosition()))) {
                        buttonBuy.setEnabled(false);
                        buttonNextTurn.setEnabled(true);
                    } else if (!Player.landAndMortgageRegister.containsKey(player1.getCurrentPlayerPosition())) {
                        buttonBuy.setEnabled(true);
                        buttonNextTurn.setEnabled(true);
                        buttonPayRent.setEnabled(false);
                    }
                } else {
                    // RUNDA GRACZA NR 2
                    int dice1OldValue = dice1.getFaceValue();
                    int dice2OldValue = dice2.getFaceValue();
                    dice1.rollDice();
                    dice2.rollDice();
                    int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue();
                    if (dice1.getFaceValue() == dice2.getFaceValue()) {
                        doubleDiceforPlayer2 = true;
                    } else {
                        doubleDiceforPlayer2 = false;
                    }


                    player2.move(dicesTotal);


                    if (Player.landAndMortgageRegister.containsKey(player2.getCurrentPlayerPosition())
                            && Player.landAndMortgageRegister.get(player2.getCurrentPlayerPosition()) != player2.getPlayerNumber()) {
                        buttonBuy.setEnabled(false);
                        buttonRollDice.setEnabled(false);
                        buttonNextTurn.setEnabled(false);
                        buttonPayRent.setEnabled(true);
                    }
                    if (Player.landAndMortgageRegister.containsKey(player2.getCurrentPlayerPosition())
                            && Player.landAndMortgageRegister.get(player2.getCurrentPlayerPosition()) == player2.getPlayerNumber()) {
                        buttonBuy.setEnabled(false);
                        buttonRollDice.setEnabled(false);
                        buttonNextTurn.setEnabled(true);
                    }
                    if (gameBoard.getUnbuyableSquares().contains(gameBoard.getAllSquares().get(player2.getCurrentPlayerPosition()))) {
                        buttonBuy.setEnabled(false);
                        buttonNextTurn.setEnabled(true);
                    } else if (!Player.landAndMortgageRegister.containsKey(player2.getCurrentPlayerPosition())) {
                        buttonBuy.setEnabled(true);
                        buttonNextTurn.setEnabled(true);
                        buttonPayRent.setEnabled(false);
                    }
                }

                buttonRollDice.setEnabled(false);
                if (doubleDiceforPlayer1 || doubleDiceforPlayer2) {
                    infoConsole.setText("Kliknij przycisk by gracz " + (nowPlaying == 0 ? 1 : 2) + "mógł rzucić kośćmi!");
                } else {
                    infoConsole.setText("Kliknij przycisk by gracz " + (nowPlaying == 0 ? 2 : 1) + "mógł rzucić kośćmi!");
                }

                layeredPane.remove(gameBoard);
                layeredPane.add(gameBoard, Integer.valueOf(0));

                updatePanelPlayer1TextArea();
                updatePanelPlayer2TextArea();
            }
        });*/
        buttonRollDice = new JButton("ROLL DICE");
        buttonRollDice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Player currentPlayer = players.get(nowPlaying);

                if (handleSkipTurn(currentPlayer)) {
                    return;
                }

                boolean isDouble = rollDiceAndMove(currentPlayer, dice1, dice2);
                setDoubleDiceFlag(isDouble);
                updateButtonsAfterMove(currentPlayer);
                updateGameStateUI(isDouble);
            }
        });


        buttonRollDice.setBounds(81, 413, 246, 53);
        rightPanel.add(buttonRollDice);

        buttonNextTurn = new JButton("NEXT TURN");
        buttonNextTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonRollDice.setEnabled(true);
                buttonNextTurn.setEnabled(false);
                buttonPayRent.setEnabled(false);
                buttonBuy.setEnabled(false);

                // Obsługa rzutów podwójnych
                if (nowPlaying == 0 && doubleDiceforPlayer1) {
                    doubleDiceforPlayer1 = false;
                } else if (nowPlaying == 1 && doubleDiceforPlayer2) {
                    doubleDiceforPlayer2 = false;
                } else {
                    nowPlaying = (nowPlaying + 1) % players.size();

                    // Sprawdź czy nowy gracz ma pominąć turę
                    Player nextPlayer = players.get(nowPlaying);
                    if (nextPlayer.shouldSkipNextTurn()) {
                        nextPlayer.setSkipNextTurn(false);
                        nowPlaying = (nowPlaying + 1) % players.size();
                    }
                }

                // Aktualizacja UI
                updateGameUI();
            }
        });



        buttonNextTurn.setBounds(81, 519, 246, 53);
        rightPanel.add(buttonNextTurn);
        buttonNextTurn.setEnabled(false);

        JPanel test = new JPanel();
        test.setBounds(81, 312, 246, 68);
        rightPanel.add(test);
        test.setLayout(null);

        playerAssetsPanel = new JPanel();
        playerAssetsPanel.setBounds(81, 28, 246, 189);
        rightPanel.add(playerAssetsPanel);
        playerAssetsPanel.setLayout(c1);

        JPanel panelPlayer1 = new JPanel();
        panelPlayer1.setBackground(Color.RED);
        playerAssetsPanel.add(panelPlayer1, "1");
        panelPlayer1.setLayout(null);

        JLabel panelPlayer1Title = new JLabel("Player 1 All Wealth");
        panelPlayer1Title.setForeground(Color.WHITE);
        panelPlayer1Title.setHorizontalAlignment(SwingConstants.CENTER);
        panelPlayer1Title.setBounds(0, 6, 240, 16);
        panelPlayer1.add(panelPlayer1Title);

        panelPlayer1TextArea = new JTextArea();
        panelPlayer1TextArea.setBounds(10, 34, 230, 149);
        panelPlayer1.add(panelPlayer1TextArea);

        JPanel panelPlayer2 = new JPanel();
        panelPlayer2.setBackground(Color.BLUE);
        playerAssetsPanel.add(panelPlayer2, "2");
        panelPlayer2.setLayout(null);
        c1.show(playerAssetsPanel, "" + nowPlaying);

        JLabel panelPlayer2Title = new JLabel("Player 2 All Wealth");
        panelPlayer2Title.setForeground(Color.WHITE);
        panelPlayer2Title.setHorizontalAlignment(SwingConstants.CENTER);
        panelPlayer2Title.setBounds(0, 6, 240, 16);
        panelPlayer2.add(panelPlayer2Title);

        panelPlayer2TextArea = new JTextArea();
        panelPlayer2TextArea.setBounds(10, 34, 230, 149);
        panelPlayer2.add(panelPlayer2TextArea);

        updatePanelPlayer1TextArea();
        updatePanelPlayer2TextArea();

        infoConsole = new JTextArea();
        infoConsole.setColumns(20);
        infoConsole.setRows(5);
        infoConsole.setBounds(6, 6, 234, 56);
        test.add(infoConsole);
        infoConsole.setLineWrap(true);
        infoConsole.setText("PLayer 1 starts the game by clicking Roll Dice!");


    }


    public static void errorBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        GuiMain mainFrame = new GuiMain();
        mainFrame.setVisible(true);
    }

    private boolean handleSkipTurn(Player currentPlayer) {
        if (currentPlayer.shouldSkipNextTurn()) {
            currentPlayer.setSkipNextTurn(false);
            nowPlaying = (nowPlaying + 1) % players.size();

            resetButtons();
            updateGameUI();
            infoConsole.setText("Gracz " + currentPlayer.getPlayerNumber() +
                    " pomija turę! Teraz gracz: " + (nowPlaying + 1));
            return true;
        }
        return false;
    }

    private boolean rollDiceAndMove(Player player, Dice dice1, Dice dice2) {
        dice1.rollDice();
        dice2.rollDice();
        int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue();
        boolean isDouble = (dice1.getFaceValue() == dice2.getFaceValue());

        player.move(dicesTotal);
        return isDouble;
    }

    private void setDoubleDiceFlag(boolean isDouble) {
        if (nowPlaying == 0) {
            doubleDiceforPlayer1 = isDouble;
        } else {
            doubleDiceforPlayer2 = isDouble;
        }
    }

    private void updateButtonsAfterMove(Player player) {
        int position = player.getCurrentPlayerPosition();

        if (isPropertyOwnedByOtherPlayer(player, position)) {
            buttonBuy.setEnabled(false);
            buttonRollDice.setEnabled(false);
            buttonNextTurn.setEnabled(false);
            buttonPayRent.setEnabled(true);
        } else if (isPropertyOwnedByCurrentPlayer(player, position)) {
            buttonBuy.setEnabled(false);
            buttonRollDice.setEnabled(false);
            buttonNextTurn.setEnabled(true);
        } else if (isUnbuyableSquare(position)) {
            buttonBuy.setEnabled(false);
            buttonNextTurn.setEnabled(true);
        } else {
            buttonBuy.setEnabled(true);
            buttonNextTurn.setEnabled(true);
            buttonPayRent.setEnabled(false);
        }
    }

    private boolean isPropertyOwnedByOtherPlayer(Player player, int position) {
        return Player.landAndMortgageRegister.containsKey(position) &&
                Player.landAndMortgageRegister.get(position) != player.getPlayerNumber();
    }

    private boolean isPropertyOwnedByCurrentPlayer(Player player, int position) {
        return Player.landAndMortgageRegister.containsKey(position) &&
                Player.landAndMortgageRegister.get(position) == player.getPlayerNumber();
    }

    private boolean isUnbuyableSquare(int position) {
        return gameBoard.getUnbuyableSquares().contains(gameBoard.getAllSquares().get(position));
    }

    private void updateGameStateUI(boolean isDouble) {
        buttonRollDice.setEnabled(false);

        if (isDouble) {
            infoConsole.setText("Kliknij przycisk by gracz " + (nowPlaying + 1) + " mógł rzucić kośćmi ponownie!");
        } else {
            infoConsole.setText("Kliknij przycisk by gracz " + (nowPlaying == 0 ? 2 : 1) + " mógł rzucić kośćmi!");
        }

        gameBoard.repaint();
        updateGameUI();
    }

    private void resetButtons() {
        buttonRollDice.setEnabled(true);
        buttonNextTurn.setEnabled(false);
        buttonPayRent.setEnabled(false);
        buttonBuy.setEnabled(false);
    }

    public void updatePanelPlayer1TextArea() {
        String result = "";
        result += "Środki w portfelu: " + player1.getWallet() + "\n";

        result += "Akty własności: \n";
        for (int i = 0; i < player1.getOwnedProperties().size(); i++) {
            result += " - " + gameBoard.getAllSquares().get(player1.getOwnedProperties().get(i)).getName() + "\n";
        }

        panelPlayer1TextArea.setText(result);
    }

    public void updatePanelPlayer2TextArea() {
        String result = "";
        result += "Środki w portfelu: " + player2.getWallet() + "\n";

        result += "Akty własności: \n";
        for (int i = 0; i < player2.getOwnedProperties().size(); i++) {
            result += " - " + gameBoard.getAllSquares().get(player2.getOwnedProperties().get(i)).getName() + "\n";
        }

        panelPlayer2TextArea.setText(result);
    }

    private void updateGameUI() {
        // Aktualizuj panel gracza
        c1.show(playerAssetsPanel, "" + (nowPlaying + 1));

        // Aktualizuj informacje o graczach
        updatePanelPlayer1TextArea();
        updatePanelPlayer2TextArea();

        // Aktualizuj komunikat
        infoConsole.setText("Runda gracza: " + (nowPlaying + 1));

        // Wymuś odświeżenie pozycji graczy
        player1.setPosition(player1.getCurrentPlayerPosition());
        player2.setPosition(player2.getCurrentPlayerPosition());
    }
}
