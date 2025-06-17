package org.example.gui;

import org.example.gui.dialog.EndGameDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.util.ArrayList;

public class GuiMain extends JFrame {
    static JTextArea infoConsole;
    static int nowPlaying = 0, lastRollDiceSum = 0;

    private final ArrayList<Player> players = new ArrayList<>();
    private final CardLayout c1 = new CardLayout();

    private JButton buttonNextTurn;
    private final JButton buttonRollDice, buttonPayRent, buttonBuy, buttonBuildHouse;

    private final JPanel playerAssetsPanel;
    private final JTextArea panelPlayer1TextArea, panelPlayer2TextArea;

    private final Player player1, player2;
    private boolean doubleDiceforPlayer1 = false;
    private boolean doubleDiceforPlayer2 = false;

    private final BoardPanel boardPanel;

    public GuiMain() {
        setTitle("Monopoly");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1560, 1174);
        setMinimumSize(new Dimension(1280, 720));

        JPanel contentIncluder = new JPanel();
        contentIncluder.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentIncluder);
        contentIncluder.setLayout(null);

        boardPanel = new BoardPanel(this);
        contentIncluder.add(boardPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setBorder(new LineBorder(Color.BLACK));
        rightPanel.setLayout(null);
        contentIncluder.add(rightPanel);

        contentIncluder.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = contentIncluder.getSize();
                int padding = 6;
                int boardSize = size.height - 2 * padding;
                int minRightPanelWidth = (int) (size.width * 0.27);
                int availableRightPanelWidth = size.width - boardSize - 3 * padding;
                int rightPanelWidth = Math.max(minRightPanelWidth, availableRightPanelWidth);
                if (rightPanelWidth + boardSize + 3 * padding > size.width) {
                    boardSize = size.width - rightPanelWidth - 3 * padding;
                }

                boardPanel.setBounds(padding, padding, boardSize, boardSize);

                rightPanel.setBounds(padding * 2 + boardSize, padding, rightPanelWidth, boardSize);
            }
        });

        player1 = boardPanel.getPlayer1View();
        player2 = boardPanel.getPlayer2View();
        players.add(player1);
        players.add(player2);

        Dice dice1 = boardPanel.getDice1();
        Dice dice2 = boardPanel.getDice2();

        // --- BUTTONS ---

        buttonBuy = new JButton("BUY");
        buttonBuy.setBounds(81, 818, 117, 29);
        buttonBuy.setEnabled(false);
        buttonBuy.addActionListener(_ -> {
            Player currentPlayer = players.get(nowPlaying);
            var square = boardPanel.getGameBoard().getAllSquares().get(currentPlayer.getCurrentPlayerPosition());
            infoConsole.setText("Zakupiłeś " + square.getName());
            currentPlayer.buyProperty(currentPlayer.getCurrentPlayerPosition());
            currentPlayer.withdrawMoneyFromWallet(square.getPrice());
            buttonBuy.setEnabled(false);
            updatePanelPlayer1TextArea();
            updatePanelPlayer2TextArea();
        });
        rightPanel.add(buttonBuy);

        buttonBuildHouse = new JButton("BUILD HOUSE");
        buttonBuildHouse.setBounds(81, 854, 246, 29);
        buttonBuildHouse.setEnabled(false);
        buttonBuildHouse.addActionListener(_ -> {
            Player currentPlayer = players.get(nowPlaying);
            int pos = currentPlayer.getCurrentPlayerPosition();
            currentPlayer.buildHouse(pos);
            boardPanel.repaint();
            infoConsole.setText("Postawiłeś domek na polu " + boardPanel.getGameBoard().getAllSquares().get(pos).getName());
            buttonBuildHouse.setEnabled(currentPlayer.canBuildHouse(pos));
            boardPanel.repaint();
            updatePanelPlayer1TextArea();
            updatePanelPlayer2TextArea();
        });
        rightPanel.add(buttonBuildHouse);

        buttonPayRent = new JButton("PAY RENT");
        buttonPayRent.setBounds(210, 818, 117, 29);
        buttonPayRent.setEnabled(false);
        buttonPayRent.addActionListener(_ -> {
            Player currentPlayer = players.get(nowPlaying);
            int pos = currentPlayer.getCurrentPlayerPosition();
            int owner = Player.landAndMortgageRegister.get(pos);
            Player squareOwner = players.get(owner == 1 ? 0 : 1);
            currentPlayer.payRentTo(squareOwner, lastRollDiceSum);
            infoConsole.setText("Zapłaciłeś czynsz graczowi " + squareOwner.getPlayerNumber());
            buttonNextTurn.setEnabled(true);
            buttonPayRent.setEnabled(false);
            updatePanelPlayer1TextArea();
            updatePanelPlayer2TextArea();
        });
        rightPanel.add(buttonPayRent);

        buttonRollDice = new JButton("ROLL DICE");
        buttonRollDice.setBounds(81, 728, 246, 53);
        buttonRollDice.addActionListener(_ -> {
            Player currentPlayer = players.get(nowPlaying);

            //sprawdzenie czy gracz powinien skipnąc kolejke
            if (shouldPlayerSkipTurn(currentPlayer)) return;

            dice1.rollDice();
            dice2.rollDice();
            int total = dice1.getFaceValue() + dice2.getFaceValue();
            lastRollDiceSum = total;
            boolean isDouble = dice1.getFaceValue() == dice2.getFaceValue();

            currentPlayer.move(total);

            if (nowPlaying == 0) doubleDiceforPlayer1 = isDouble;
            else doubleDiceforPlayer2 = isDouble;

            int pos = currentPlayer.getCurrentPlayerPosition();
            var board = boardPanel.getGameBoard();

            if (Player.landAndMortgageRegister.containsKey(pos)) {
                if (Player.landAndMortgageRegister.get(pos) != currentPlayer.getPlayerNumber()) {
                    buttonBuy.setEnabled(false);
                    buttonRollDice.setEnabled(false);
                    buttonNextTurn.setEnabled(false);
                    buttonPayRent.setEnabled(true);
                } else {
                    buttonBuy.setEnabled(false);
                    buttonRollDice.setEnabled(false);
                    buttonNextTurn.setEnabled(true);
                }
            } else if (board.getUnbuyableSquares().contains(board.getAllSquares().get(pos))) {
                buttonBuy.setEnabled(false);
                buttonNextTurn.setEnabled(true);
            } else {
                buttonBuy.setEnabled(true);
                buttonNextTurn.setEnabled(true);
                buttonPayRent.setEnabled(false);
            }

            // sprawdź, czy możemy budować domek na obecnej pozycji
            buttonBuildHouse.setEnabled(currentPlayer.canBuildHouse(pos));

            buttonRollDice.setEnabled(false);

            if (isDouble) {
                infoConsole.setText("Gracz " + (nowPlaying + 1) + " rzucił dublet! Może rzucać ponownie.");
            } else {
                infoConsole.setText("Tura gracza " + (nowPlaying == 0 ? 2 : 1));
            }

            board.repaint();
            updateGameUI();
        });
        rightPanel.add(buttonRollDice);

        buttonNextTurn = new JButton("NEXT TURN");
        buttonNextTurn.setBounds(81, 919, 246, 53);
        buttonNextTurn.setEnabled(false);
        buttonNextTurn.addActionListener(_ -> {
            buttonRollDice.setEnabled(true);
            buttonNextTurn.setEnabled(false);
            buttonPayRent.setEnabled(false);
            buttonBuy.setEnabled(false);
            buttonBuildHouse.setEnabled(false);

            if ((nowPlaying == 0 && doubleDiceforPlayer1) || (nowPlaying == 1 && doubleDiceforPlayer2)) {
                if (nowPlaying == 0) doubleDiceforPlayer1 = false;
                else doubleDiceforPlayer2 = false;
            } else {
                nowPlaying = (nowPlaying + 1) % players.size();
                if (players.get(nowPlaying).shouldSkipNextTurn()) {
                    players.get(nowPlaying).setSkipNextTurn(false);
                    nowPlaying = (nowPlaying + 1) % players.size();
                }
            }
            Player p = players.get(nowPlaying);
            p.resetHouseBuiltFlag();

            updateGameUI();
        });
        rightPanel.add(buttonNextTurn);
        buttonBuildHouse.setEnabled(false);

        // Player Info
        playerAssetsPanel = new JPanel();
        playerAssetsPanel.setBounds(81, 28, 246, 589);
        playerAssetsPanel.setLayout(c1);
        rightPanel.add(playerAssetsPanel);

        JPanel panelPlayer1 = new JPanel();
        panelPlayer1.setBackground(Color.RED);
        panelPlayer1.setLayout(null);
        playerAssetsPanel.add(panelPlayer1, "1");


        JLabel panelPlayer1Title = new JLabel("Player 1 All Wealth");
        panelPlayer1Title.setForeground(Color.WHITE);
        panelPlayer1Title.setHorizontalAlignment(SwingConstants.CENTER);
        panelPlayer1Title.setBounds(0, 5, 240, 16);
        panelPlayer1.add(panelPlayer1Title);

        panelPlayer1TextArea = new JTextArea();
        panelPlayer1TextArea.setBounds(10, 34, 230, 550);
        panelPlayer1.add(panelPlayer1TextArea);

        JPanel panelPlayer2 = new JPanel();
        panelPlayer2.setBackground(Color.BLUE);
        panelPlayer2.setLayout(null);
        playerAssetsPanel.add(panelPlayer2, "2");


        JLabel panelPlayer2Title = new JLabel("Player 2 All Wealth");
        panelPlayer2Title.setForeground(Color.WHITE);
        panelPlayer2Title.setHorizontalAlignment(SwingConstants.CENTER);
        panelPlayer2Title.setBounds(0, 5, 240, 16);
        panelPlayer2.add(panelPlayer2Title);

        panelPlayer2TextArea = new JTextArea();
        panelPlayer2TextArea.setBounds(10, 34, 230, 550);
        panelPlayer2.add(panelPlayer2TextArea);

        // Info console
        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(81, 652, 246, 68);
        infoPanel.setLayout(null);
        rightPanel.add(infoPanel);

        infoConsole = new JTextArea("Gracz 1 zaczyna grę – kliknij ROLL DICE!");
        infoConsole.setBounds(6, 6, 234, 56);
        infoConsole.setLineWrap(true);
        infoConsole.setWrapStyleWord(true);
        infoPanel.add(infoConsole);

        updatePanelPlayer1TextArea();
        updatePanelPlayer2TextArea();

        addWindowStateListener(e -> {
            if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                SwingUtilities.invokeLater(() -> {
                    contentIncluder.revalidate();
                    contentIncluder.repaint();
                });
            }
        });
    }

    //metoda sprawdzajaca czy gracz jest na miasteczku i czy  powinien pominąć ture.
    private boolean shouldPlayerSkipTurn(Player currentPlayer) {
        if (currentPlayer.shouldSkipNextTurn()) {
            currentPlayer.setSkipNextTurn(false);
            nowPlaying = (nowPlaying + 1) % players.size();
            resetButtons();
            updateGameUI();
            infoConsole.setText("Gracz " + currentPlayer.getPlayerNumber() + " pomija turę! Teraz gracz: " + (nowPlaying + 1));
            return true;
        }
        return false;
    }
    private void resetButtons() {
        buttonRollDice.setEnabled(true);
        buttonNextTurn.setEnabled(false);
        buttonPayRent.setEnabled(false);
        buttonBuy.setEnabled(false);
        buttonBuildHouse.setEnabled(false);
    }

    private void updateGameUI() {
        c1.show(playerAssetsPanel, "" + (nowPlaying + 1));
        updatePanelPlayer1TextArea();
        updatePanelPlayer2TextArea();
        infoConsole.setText("Runda gracza: " + (nowPlaying + 1));
        boardPanel.getPlayer1View().setPosition(player1.getCurrentPlayerPosition());
        boardPanel.getPlayer2View().setPosition(player2.getCurrentPlayerPosition());
    }

    private void updatePanelPlayer1TextArea() {
        StringBuilder result = new StringBuilder("Środki w portfelu: " + player1.getWallet() + "\nAkty własności:\n");
        for (int id : player1.getOwnedProperties()) {
            result.append(" - ").append(boardPanel.getGameBoard().getAllSquares().get(id).getName()).append("\n");
        }
        panelPlayer1TextArea.setText(result.toString());
    }

    private void updatePanelPlayer2TextArea() {
        StringBuilder result = new StringBuilder("Środki w portfelu: " + player2.getWallet() + "\nAkty własności:\n");
        for (int id : player2.getOwnedProperties()) {
            result.append(" - ").append(boardPanel.getGameBoard().getAllSquares().get(id).getName()).append("\n");
        }
        panelPlayer2TextArea.setText(result.toString());
    }

    public void endGame(int winnerPlayerNumber) {
        // Wyłącz przyciski
        buttonRollDice.setEnabled(false);
        buttonNextTurn.setEnabled(false);
        buttonBuy.setEnabled(false);
        buttonPayRent.setEnabled(false);

        // Wyświetl okno dialogowe
        System.out.println("Gracz " + winnerPlayerNumber + " wygrywa grę, przeciwnik zbankrutował!");
        EndGameDialog endGameDialog = new EndGameDialog(this, winnerPlayerNumber);
        endGameDialog.setVisible(true);

        // Zamknij aplikację
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuiMain mainFrame = new GuiMain();
            mainFrame.setVisible(true);
        });
    }
}
