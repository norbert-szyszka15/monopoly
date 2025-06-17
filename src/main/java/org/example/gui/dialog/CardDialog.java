package org.example.gui.dialog;

import javax.swing.*;

import java.awt.*;

public class CardDialog extends JDialog {
    public CardDialog(Component parentComponent, String title, String message) {
        super(
                (Frame) SwingUtilities.getWindowAncestor(parentComponent),
                title,
                true
        );
        initDialogComponents(message);
        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    private void initDialogComponents(String message) {
        JPanel content = new JPanel();
        content.setBackground(new Color(30, 30, 30));
        content.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        content.setLayout(new BorderLayout(10,10));

        JLabel lbl = new JLabel("<html><div style='text-align:center;'>" + message.replace("\n", "<br>") + "</div></html>");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 14f));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        JButton ok = new JButton("OK");
        ok.setFocusPainted(false);
        ok.setBackground(new Color(70,130,180));
        ok.setForeground(Color.WHITE);
        ok.addActionListener(e -> dispose());

        content.add(lbl, BorderLayout.CENTER);
        content.add(ok, BorderLayout.SOUTH);
        content.setPreferredSize(new Dimension(300, 150));

        setContentPane(content);
    }
}
