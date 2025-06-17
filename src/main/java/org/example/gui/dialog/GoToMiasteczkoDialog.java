package org.example.gui.dialog;

import javax.swing.*;

import java.awt.*;

public class GoToMiasteczkoDialog extends JDialog {

    public GoToMiasteczkoDialog(Component parentComponent, int playerNumber) {
        super(
                (Frame) SwingUtilities.getWindowAncestor(parentComponent),
                "Miasteczko Studenckie",
                true
        );
        initDialogComponents(playerNumber);
        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    private void initDialogComponents(int playerNumber) {
        JPanel content = new JPanel();
        content.setBackground(new Color(30, 30, 30));
        content.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        content.setLayout(new BorderLayout(10,10));

        JLabel lbl = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "Gracz <b>" + playerNumber + "</b><br>"
                        + "idzie na Miasteczko Studenckie!<br>"
                        + "Następna tura zostaje pominięta."
                        + "</div></html>"
        );
        lbl.setForeground(Color.WHITE);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 14f));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        JButton ok = new JButton("OK");
        ok.setFocusPainted(false);
        ok.setBackground(new Color(70,130,180));
        ok.setForeground(Color.WHITE);
        ok.addActionListener(_ -> dispose());

        content.add(lbl, BorderLayout.CENTER);
        content.add(ok, BorderLayout.SOUTH);
        content.setPreferredSize(new Dimension(350, 150));

        setContentPane(content);
    }
}
