package org.example.gui.dialog;

import javax.swing.*;
import java.awt.*;

public class EndGameDialog extends JDialog {

    public EndGameDialog(JFrame parentFrame, int winnerPlayerNumber) {
        super(parentFrame, "Koniec gry", true);
        initDialogComponents(winnerPlayerNumber);
        pack();
        setResizable(false);
        setLocationRelativeTo(parentFrame);
    }

    private void initDialogComponents(int winnerPlayerNumber) {
        JPanel content = new JPanel(new BorderLayout(10,10));
        content.setBackground(new Color(30, 30, 30));
        content.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        JLabel lbl = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "Koniec gry!<br>"
                        + "Wygrywa gracz <b>" + winnerPlayerNumber + "</b>"
                        + "</div></html>"
        );
        lbl.setForeground(Color.WHITE);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 16f));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        JButton ok = new JButton("OK");
        ok.setFocusPainted(false);
        ok.setBackground(new Color(70,130,180));
        ok.setForeground(Color.WHITE);
        ok.addActionListener(e -> dispose());

        content.add(lbl, BorderLayout.CENTER);
        content.add(ok, BorderLayout.SOUTH);
        content.setPreferredSize(new Dimension(320, 140));

        setContentPane(content);
    }
}
