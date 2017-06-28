package ru.xtails;

public class Vertex implements Comparable<Vertex> {
    /**
     * Метка вершины
     */
    public String name = "";
    /**
     * Координаты вершины
     */
    int x = 0, y = 0;
    /**
     * Предшественник вершины
     */
    public Vertex predecessor = null;
    /**
     * Была ли посещена вершина
     */
    public boolean visited = false;
    /**
     * Цвет вершины
     */
    public int color = 0;

    /**
     * Создает вершину
     * @param v Имя вершины
     */
    public Vertex(String v) {
        name = v;
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
