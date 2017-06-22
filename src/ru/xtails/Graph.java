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
            adjList.put(v, new TreeSet<Vertex>());
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
            adjList.put(v, new TreeSet<Vertex>());
            V++;
        }
    }
}
