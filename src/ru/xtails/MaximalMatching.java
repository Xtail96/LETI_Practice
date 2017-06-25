package ru.xtails;

import javax.swing.*;

public class MaximalMatching extends JFrame {
    MaximalMatching() {
        setTitle("Maximal Matching");
        setContentPane(new MainWindow().mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }

}
