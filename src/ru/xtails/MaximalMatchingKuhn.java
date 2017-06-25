package ru.xtails;

import java.util.HashMap;

/**
 * Реализация алгоритма Куна для поиска наибольшего паросочетания в графе (в отдельном потоке)
 */
public class MaximalMatchingKuhn implements Runnable {
    private volatile boolean running = true;
    private volatile boolean paused = true;
    private final boolean continuous;
    private final Object pauseLock = new Object();
    // исходный граф
    private BiGraph graph;
    // текущее паросочетание
    private HashMap<Vertex, Vertex> matching;

    public MaximalMatchingKuhn(BiGraph g, boolean continuous) {
        graph = g;
        this.continuous = continuous;
    }

    /**
     * Запуск потока
     */
    @Override
    public void run() {
        System.out.println("I'm running!");
        matching = new HashMap<>();

        // для каждой вершины пытаемся найти увеличивающуюся цепь
        for (Vertex v : graph.getVertices()) {
            if (running) {
                System.out.println("Vertex " + v);
                graph.reset();
                dfs(v);
                checkForPaused();
            } else {
                // завершаемся
                break;
            }
        }

        System.out.println("Finished");
    }

    /**
     * Обход графа в глубину и улучшение текущего паросочетания
     * @param root вершина, из которой будет запущен обход
     * @return true, если удалось улучшеть текущее паросочетание, иначе false
     */
    private boolean dfs(Vertex root) {
        if (root.visited)
            return false;
        root.visited = true;

        for (Vertex to : graph.getNeighbours(root)) {
            if (!matching.containsKey(to) || dfs(matching.get(to))) {
                // если удалось найти увеличивающуюся цепь, добавляем ребро в текущее паросочетание
                matching.put(to, root);
                return true;
            }
        }
        return false;
    }

    /**
     * Приостанавливает пошаговое выполнение алгоритма, если поле paused == true
     */
    private void checkForPaused() {
        if (!continuous) {
            synchronized (pauseLock) {
                if (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {

                    }
                }
            }
        }
    }

    /**
     * Выставляет флаг приостановки алгоритма
     */
    public void pause() {
        paused = true;
    }

    /**
     * Продолжает выполнение алгоритма
     */
    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notify();
        }
    }

    /**
     * Выставляет флаг завершения алгоритма
     */
    public void stop() {
        running = false;
        resume();
    }

    /**
     * Выполняет 1 шаг алгоритма
     */
    public void step() {
        synchronized (pauseLock) {
            paused = true;
            pauseLock.notify();
        }
    }
}
