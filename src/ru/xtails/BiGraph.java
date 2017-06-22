package ru.xtails;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

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
        // создаем копию исходного графа
        Graph h = new Graph(g);
        // окрашиваем вершины
        paintGraph(h);
    }

    private void paintGraph(Graph g) {
        Vertex v;
        // запускаем bfs для всех непосещенных вершин
        while ((v = g.getUnvisitedVertex()) != null) {
            bfs(g, v);
        }
    }

    private void bfs(Graph g, Vertex root) {
        Queue<Vertex> queue = new LinkedList<>();
        root.color = 0;
        root.visited = true;
        queue.add(root);

        while (!queue.isEmpty()) {
            // достаем очередную вершину
            Vertex v = queue.remove();
            int newColor = v.color + 1;

            Iterable<Vertex> neighbours = g.getNeighbours(v);
            for (Vertex w : neighbours) {
                if (w.visited) {
                    // если кратности вершин не совпадают, разбиение на 2 доли невозможно
                    if ((w.color % 2) != (newColor % 2)) {
                        throw new IllegalArgumentException("Для данного графа разбиение на 2 доли невозможно");
                    }
                } else {
                    // окрашиваем вершину
                    w.color = newColor;
                    w.visited = true;
                    w.predecessor = v;
                    queue.add(w);
                }
            }
        }
    }
}
