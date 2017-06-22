package ru.xtails;

import java.util.HashMap;

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
    private static final String NEWLINE = System.getProperty("line.separator");

    private HashMap<Vertex, TreeSet<Vertex>> adjList;
    private HashMap<String, Vertex> vertices;

    private int V;
    private int E;

    /**
     * Создает пустой граф
     */
    public Graph() {
        adjList = new HashMap<Vertex, TreeSet<Vertex>>();
        vertices = new HashMap<String, Vertex>();
        V = E = 0;
    }
}
