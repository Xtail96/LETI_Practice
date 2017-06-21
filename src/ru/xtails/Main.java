package ru.xtails;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        JFrame frame = new JFrame("App");
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
