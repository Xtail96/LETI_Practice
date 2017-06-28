package ru.xtails;

import javax.swing.*;

public class MaximalMatching extends JFrame {
    public MaximalMatching() {
        setTitle("Наибольшее паросочетания в двудольном графе");
        setContentPane(new MainWindow().mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
