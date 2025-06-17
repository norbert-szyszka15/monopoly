package org.example.gui.dialog;

import javax.swing.*;

import java.awt.*;

public class TaxDialog extends JDialog {

    public TaxDialog(Component parentComponent, int playerNumber, int taxAmount) {
        super(
                (Frame) SwingUtilities.getWindowAncestor(parentComponent),
                "Podatek",
                true
        );
        initDialogComponents(playerNumber, taxAmount);
        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    private void initDialogComponents(int playerNumber, int taxAmount) {
        JPanel content = new JPanel(new BorderLayout(10,10));
        content.setBackground(new Color(30, 30, 30));
        content.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        JLabel lbl = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "Gracz <b>" + playerNumber + "</b><br>"
                        + "płaci podatek: <b>" + taxAmount + " zł</b>"
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
        content.setPreferredSize(new Dimension(300, 120));

        setContentPane(content);
    }
}
