package ru.xtails;

public class Vertex implements Comparable<Vertex> {
    /**
     * Метка вершины
     */
    public String name;
    /**
     * Предшественник вершины
     */
    public Vertex predecessor;
    /**
     * Была ли посещена вершина
     */
    public boolean visited;
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
        visited = false;
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
     * Метод, необходимый для сортировки вершин в HashMap
     * Сравнивает текущую вершину с вершиной other
     * @param other вершина для сравнения с текущей
     * @return Возвращает 0, если текущая вершина совпадает с other,
     * отрицательное число, если текущая вершина "меньше" other,
     * положительное число, если текущая вершина "больше" other
     */
    @Override
    public int compareTo(Vertex other) {
        return name.compareTo(other.name);
    }

    /**
     * @return Возвращает имя вершины
     */
    @Override
    public String toString() {
        return name;
    }
}
