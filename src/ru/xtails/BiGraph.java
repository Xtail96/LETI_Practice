package ru.xtails;

import java.util.HashMap;

public class BiGraph extends Graph {

    /**
     * Доли графа
     */
    private HashMap<String, Vertex> part1, part2;

    public BiGraph() {
        part1 = new HashMap<>();
        part2 = new HashMap<>();
    }
}
