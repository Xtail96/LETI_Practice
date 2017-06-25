package ru.xtails;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        try {
            Graph g = readGraph();
            BiGraph bg = new BiGraph(g);
            System.out.print(bg);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        JFrame frame = new JFrame("App");
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



    }

    /**
     * Считывает граф
     * @return считанный граф
     */
    static Graph readGraph() {
        //добавить реализацию
        Graph g = new Graph();
        g.addEdge("A", "1");
        g.addEdge("B", "1");
        g.addEdge("B", "2");
        g.addEdge("C", "3");
        g.addEdge("C", "4");
        g.addEdge("D", "2");
        g.addEdge("E", "1");
        g.addEdge("E", "4");
        g.addVertex("F");
        return g;
    }
}
