package ru.xtails;

public class Vertex {
    /**
     * Метка вершины
     */
    public String name;
    /**
     * Предшественник вершины
     */
    public Vertex predecessor;
    /**
     * Цвет вершины
     */
    public int color;

    /**
     * Создает вершину
     * @param v Имя вершины
     */
    public Vertex(String v) {
        name = v;
        predecessor = null;
        color = 0;
    }
}
