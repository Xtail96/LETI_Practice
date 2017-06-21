package ru.xtails;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    JButton openButton;
    JPanel mainPanel;
    private JTextArea inputTextArea;
    private JButton acceptButton;
    private JTextArea resultTextArea;
    private JButton saveButton;
    private JTabbedPane modeTab;
    private JPanel inputTab;
    private JPanel displayTab;
    private JPanel resultTab;

    public MainWindow() {
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hi Bro");
            }
        });
    }
}
