package ru.xtails;

import java.util.HashMap;

/**
 * Класс, представляющий неориентированный невзвешанный граф вершин,
 * со строковыми именами
 */
public class Graph extends DirectedGraph {
    /**
     * Создает пустой граф
     */
    public Graph() {
        adjList = new HashMap<>();
        vertices = new HashMap<>();
        V = E = 0;
    }

    /**
     * Создает копию заданного графа
     * @param g исходный граф
     */
    public Graph(Graph g) {
        adjList = new HashMap<>(g.adjList);
        vertices = new HashMap<>(g.vertices);
        V = g.V;
        E = g.E;
    }

    /**
     * Добавляет ребро в граф.
     * Не добавляет ребро, если такое же ребро уже существует.
     * Если какой-либо вершины нет в графе, добавляет ее
     * @param from Исходная вершина
     * @param to Конечная вершина
     */
    @Override
    public void addEdge(String from, String to) {
        super.addEdge(from, to);
        super.addEdge(to, from);
        // считаем 2 ребра за одно
        E--;
    }
}
