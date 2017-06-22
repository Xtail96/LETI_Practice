package ru.xtails;

import java.util.HashMap;
import java.util.TreeSet;


/**
 * Класс, представляющий ориентированный невзвешанный граф вершин,
 * со строковыми именами
 *
 * Он поддерживает следующие операции:
 *  - Добавление ребра
 *  - Добавление вершины
 *  - Получение списка всех вершин
 *  - Получение списка соседей вершины
 *  - Проверка наличия вершины в графе
 *  - Проверка наличия ребра в графе
 *
 * Мультиребра и петли запрещены
 *
 * В данной реализации граф представлен списком ребер
 */
public class Graph {
    protected static final String NEWLINE = System.getProperty("line.separator");

    protected HashMap<Vertex, TreeSet<Vertex>> adjList;
    protected HashMap<String, Vertex> vertices;

    protected int V;
    protected int E;

    /**
     * Создает пустой граф
     */
    public Graph() {
        adjList = new HashMap<>();
        vertices = new HashMap<>();
        V = E = 0;
    }

    /**
     * Добавляет новую вершину без соседей по имени (если в графе нет вершины с таким же именем)
     * @param name Имя добавляемой вершины
     * @return Возвращает добавленную вершину, либо уже существующую в графе вершину
     */
    public Vertex addVertex(String name) {
        Vertex v = vertices.get(name);

        // если вершины нет в списке, добавляем
        if (v == null) {
            v = new Vertex(name);
            vertices.put(name, v);
            adjList.put(v, new TreeSet<>());
            V++;
        }

        return v;
    }

    /**
     * Добавляет в граф вершину без соседей (если в графе нет вершины с таким же именем)
     * @param v Добавляемая вершина
     * @return Возвращает добавленную вершину, либо уже существующую в графе вершину
     */
    public Vertex addVertex(Vertex v) {
        Vertex w = vertices.get(v.name);

        // если вершины нет в списке, добавляем
        if (w == null) {
            vertices.put(v.name, v);
            adjList.put(v, new TreeSet<>());
            V++;
        }

        return w;
    }

    /**
     * Возвращает вершину по соответствующему имени
     * @param name Имя вершины
     * @return Возвращает соответствующую вершину в графе, либо null,
     * если такой вершины нет в графе
     */
    public Vertex getVertex(String name) {
        return vertices.get(name);
    }

    /**
     * Проверяет вхождение вершины в граф
     * @param name Имя вершины
     * @return Возвращает true, если вершина содержится в графе, иначе false
     */
    public boolean hasVertex(String name) {
        return vertices.containsKey(name);
    }

    /**
     * Проверяет вхождение вершины в граф
     * @param v Исходная вершина
     * @return Возвращает true, если вершина содержится в графе, иначе false
     */
    public boolean hasVertex(Vertex v) {
        return adjList.containsKey(v);
    }

    /**
     * Добавляет ребро в граф.
     * Не добавляет ребро, если такое же ребро уже существует.
     * Если какой-либо вершины нет в графе, добавляет ее
     * @param from Исходная вершина
     * @param to Конечная вершина
     */
    public void addEdge(String from, String to) {
        // если ребро существует, возвращаемся
        if (hasEdge(from, to)) {
            return;
        }

        // добавляем вершины, если они еще не добавлены
        Vertex v = addVertex(from);
        Vertex w = addVertex(to);

        // добавляем ребро
        adjList.get(v).add(w);
        E++;
    }

    /**
     * Проверяет наличие ребра from-to в графе
     * @param from Исходная вершина
     * @param to Конечная вершина
     * @return Возвращает true, если ребро from-to есть в графе, иначе false
     */
    public boolean hasEdge(Vertex from, Vertex to) {
        if (hasVertex(from) && hasVertex(to)) {
            return adjList.get(from).contains(to);
        } else {
            // одной из вершин нет в графе
            return false;
        }
    }

    /**
     * Проверяет наличие ребра from-to в графе
     * @param from Имя исходной вершины
     * @param to Имя конечной вершины
     * @return Возвращает true, если ребро from-to есть в графе, иначе false
     */
    public boolean hasEdge(String from, String to) {
        Vertex v = getVertex(from);
        Vertex w = getVertex(to);

        return hasEdge(v, w);
    }

    /**
     * @return Возвращает итератор по всем вершинам графа
     */
    public Iterable<Vertex> getVertices() {
        return vertices.values();
    }

    /**
     * Возвращает список соседей заданной вершины
     * @param v Исходная вершина
     * @return Возвращает итератор по всем соседям вершины v,
     * либо пустое множество, если вершина не содержится в графе
     */
    public Iterable<Vertex> getNeighbours(Vertex v) {
        if (adjList.containsKey(v))
            return adjList.get(v);
        else
            return new TreeSet<>();
    }

    /**
     * Возвращает список соседей вершины с заданным именем
     * @param name Имя вершины
     * @return Возвращает итератор по всем соседям вершины,
     * либо пустое множество, если вершина не содержится в графе
     */
    public Iterable<Vertex> getNeighbours(String name) {
        Vertex v = getVertex(name);
        return getNeighbours(v);
    }

    /**
     * @return Возвращает число вершин в графе
     */
    public int numVertices() {
        return V;
    }

    /**
     * @return Возвращает число ребер в графе
     */
    public int numEdges() {
        return E;
    }

    /**
     * Возвращает строковое представление графа в виде списка смежности
     */
    @Override
    public String toString() {
        String graph = "{" + NEWLINE;

        for (Vertex v : vertices.values()) {
            graph += "\t" + v + ": { ";

            for (Vertex w : adjList.get(v)) {
                graph += w + ", ";
            }

            graph += " }" + NEWLINE;
        }

        graph += "}";

        return graph;
    }
}
