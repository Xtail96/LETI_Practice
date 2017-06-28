package ru.xtails;

import javax.swing.*;
import java.awt.*;

public class MaximalMatching extends JFrame {
    public MaximalMatching() {
        setTitle("Наибольшее паросочетания в двудольном графе");
        setContentPane(new MainWindow().mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        pack();
        setVisible(true);
    }
}
