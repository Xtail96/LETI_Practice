package ru.xtails;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

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
        super(g);

        part1 = new HashMap<>();
        part2 = new HashMap<>();

        // окрашиваем вершины
        paintGraph(this);

        for (Vertex v : getVertices()) {
            if (v.color % 2 == 0) {
                // четные вершины добавляем в 1 долю
                part1.put(v.name, v);
            } else {
                // нечетные во 2 долю
                part2.put(v.name, v);
            }

        }
    }

    /**
     * Добавляет новую вершину по имени в 1-ю долю графа
     * Вершина не добавляется в граф, если в графе уже есть вершина с таким же именем
     * @param name имя вершины
     * @return возвращает добавленную вершину, либо уже существующую в графе вершину
     */
    public Vertex addVertexToPart1(String name) {
        Vertex v = new Vertex(name);
        return addVertexToPart1(v);
    }

    /**
     * Добавляет вершину в 1-ю долю графа
     * Вершина не добавляется в граф, если в графе уже есть вершина с таким же именем
     * @param v вершина
     * @return возвращает добавленную вершину, либо уже существующую в графе вершину
     */
    public Vertex addVertexToPart1(Vertex v) {
        Vertex w = vertices.get(v.name);

        // если вершины нет в списке, добавляем
        if (w == null) {
            vertices.put(v.name, v);
            adjList.put(v, new TreeSet<>());
            part1.put(v.name, v);
            V++;
        }

        return w;
    }

    /**
     * Добавляет новую вершину по имени во 2-ю долю графа
     * Вершина не добавляется в граф, если в графе уже есть вершина с таким же именем
     * @param name имя вершины
     * @return возвращает добавленную вершину, либо уже существующую в графе вершину
     */
    public Vertex addVertexToPart2(String name) {
        Vertex v = new Vertex(name);
        return addVertexToPart2(v);
    }

    /**
     * Добавляет вершину во 2-ю долю графа
     * Вершина не добавляется в граф, если в графе уже есть вершина с таким же именем
     * @param v вершина
     * @return возвращает добавленную вершину, либо уже существующую в графе вершину
     */
    public Vertex addVertexToPart2(Vertex v) {
        Vertex w = vertices.get(v.name);

        // если вершины нет в списке, добавляем
        if (w == null) {
            vertices.put(v.name, v);
            adjList.put(v, new TreeSet<>());
            part2.put(v.name, v);
            V++;
        }

        return w;
    }

    /**
     * Добавляет вершину в 1-ю долю графа
     * Вершина не добавляется в граф, если в графе уже есть вершина с таким же именем
     * @param v вершина
     * @return возвращает добавленную вершину, либо уже существующую в графе вершину
     */
    @Override
    public Vertex addVertex(Vertex v) {
        return addVertexToPart1(v);
    }

    /**
     * Раскрашивает заданный граф по правилу:
     *      цвет вершины = цвет предыдущей вершины + 1
     * @param g граф, который нужно раскрасить
     */
    private void paintGraph(Graph g) {
        Vertex v;
        // запускаем bfs для всех непосещенных вершин
        while ((v = g.getUnvisitedVertex()) != null) {
            bfs(g, v);
        }
    }

    /**
     * Выполняет обход графа в ширину и раскрашивает вершины
     * @param g исходный граф
     * @param root стартовая вершина
     */
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
