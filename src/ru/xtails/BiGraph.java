package ru.xtails;

import java.util.HashMap;

public class BiGraph extends Graph {

    /**
     * Доли графа
     */
    private HashMap<String, Vertex> part1, part2;

    /**
     * Создает пустой двудольный граф
     */
    public BiGraph() {
        part1 = new HashMap<>();
        part2 = new HashMap<>();
    }

    /**
     * Создает двудольный граф по заданному графу
     *
     * @param g исходный граф
     */
    public BiGraph(Graph g) throws IllegalArgumentException {
    }
}
