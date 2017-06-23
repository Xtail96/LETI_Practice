package ru.xtails;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

/**
 * Класс, представляющий неориентированный невзвешанный двудольный граф
 */
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
     * Добавляет вершину addedVertex в долю, противоположную opposedVertex
     * @param addedVertex добавляемая вершина
     * @param opposedVertex противоположная вершина
     * @return возвращает добавленную вершину
     */
    public Vertex addVertexToOppositePart(String addedVertex, String opposedVertex) {
        Vertex v = new Vertex(addedVertex);
        int part = getVertexPart(opposedVertex);

        if (part == 1)
            return addVertexToPart2(v);
        else
            if (part == 2)
                return addVertexToPart1(v);
            else
                throw new IllegalStateException( String.format("Неизвестный номер доли {%d} у вершины {%s}", part, opposedVertex) );
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
     * Проверяет вхождение вершины в 1-ю долю
     * @param name Имя вершины
     * @return true, если вершина содержится в 1-й доле, иначе false
     */
    public boolean isVertexInPart1(String name) {
        return part1.containsKey(name);
    }

    /**
     * Проверяет вхождение вершины во 2-ю долю
     * @param name Имя вершины
     * @return true, если вершина содержится во 2-й доле, иначе false
     */
    public boolean isVertexInPart2(String name) {
        return part2.containsKey(name);
    }

    /**
     * Проверяет вхождение вершины в 1-ю долю
     * @param v исходная вершина
     * @return true, если вершина содержится в 1-й доле, иначе false
     */
    public boolean isVertexInPart1(Vertex v) {
        return part1.containsValue(v);
    }

    /**
     * Проверяет вхождение вершины во 2-ю долю
     * @param v исходная вершина
     * @return true, если вершина содержится во 2-й доле, иначе false
     */
    public boolean isVertexInPart2(Vertex v) {
        return part2.containsValue(v);
    }

    /**
     * Возвращает номер доли, которой принадлежит вершина.
     * Если вершина не принадлежит ни одной доле, возвращает 0
     * @param name Имя исходной вершины
     * @return номер доли - (1, 2, 0)
     */
    public int getVertexPart(String name) {
        if (hasVertex(name)) {
            if (isVertexInPart1(name)) {
                return 1;
            }

            if (isVertexInPart2(name)) {
                return 2;
            }

            return 0;
        } else {
            return 0;
        }
    }

    /**
     * @return возвращает итератор на вершины 1-й доли
     */
    public Iterable<Vertex> getPart1Vertices() {
        return part1.values();
    }

    /**
     * @return возвращает итератор на вершины 2-й доли
     */
    public Iterable<Vertex> getPart2Vertices() {
        return part2.values();
    }

    /**
     * Добавляет ребро в граф.
     * Если вершины находятся в одной доле, бросает исключение
     * Не добавляет ребро, если такое же ребро уже существует.
     * Если какой-либо вершины нет в графе, добавляет ее
     * @param from Исходная вершина
     * @param to Конечная вершина
     */
    @Override
    public void addEdge(String from, String to) {
        int fromPart = getVertexPart(from);
        int toPart = getVertexPart(to);

        // добавляем вершины в нужные доли, если необходимо
        if (fromPart > 0) {
            if (toPart > 0) {
                // нельзя соединить вершины из одной доли
                if (fromPart == toPart) {
                    throw new IllegalArgumentException("Невозможно соединить вершины, находящиеся в одной доле");
                }
            } else {
                // добавляем to в долю, противоположную from
                addVertexToOppositePart(to, from);
            }
        } else {
            if (toPart > 0) {
                // добавляем from в долю, противоположную to
                addVertexToOppositePart(from, to);
            } else {
                // добавляем обе вершины в граф
                addVertexToPart1(from);
                addVertexToPart2(to);
            }
        }

        // соединяем их ребром
        super.addEdge(from, to);
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

    /**
     * @return Возвращает строковое представление графа
     */
    @Override
    public String toString() {
        String s = super.toString() + NEWLINE;

        s += "part1: { ";
        for (Vertex p1 : getPart1Vertices()) {
            s += p1 + ", ";
        }
        s += " }" + NEWLINE;

        s += "part2: { ";
        for (Vertex p2 : getPart2Vertices()) {
            s += p2 + ", ";
        }
        s += " }";

        return s;
    }
}
