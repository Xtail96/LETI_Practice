package ru.xtails;

import javax.swing.*;

public class MaximalMatching extends JFrame {
    MaximalMatching() {
        try {
            Graph g = readGraph();
            BiGraph bg = new BiGraph(g);
            System.out.print(bg);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        setTitle("Maximal Matching");
        setContentPane(new MainWindow().mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }
    /**
     * Считывает граф
     * @return считанный граф
     */
    Graph readGraph() {
        // добавить реализацию
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
