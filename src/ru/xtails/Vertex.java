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

    /**
     * Имя вершины уникально в графе, поэтому
     * оно используется в качестве хеша
     *
     * @return Возвращает хеш данной вершины
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * @return Возвращает имя вершины
     */
    @Override
    public String toString() {
        return name;
    }
}
