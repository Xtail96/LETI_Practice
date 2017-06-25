package ru.xtails;

import java.util.HashMap;

/**
 * Реализация алгоритма Куна для поиска наибольшего паросочетания в графе (в отдельном потоке)
 */
public class MaximalMatchingKuhn implements Runnable {
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    // исходный граф
    BiGraph graph;
    // текущее паросочетание
    HashMap<Vertex, Vertex> matching;

    public MaximalMatchingKuhn(BiGraph g) {
        graph = g;
    }

    /**
     * Запуск потока
     */
    @Override
    public void run() {
        // для каждой вершины пытаемся найти увеличивающуюся цепь
        for (Vertex v : graph.getVertices()) {
            if (running) {
                graph.reset();
                dfs(v);
            } else {
                // завершаемся
                return;
            }
        }
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
     * Приостанавливает выполнение алгоритма, если поле paused == true
     */
    private void checkForPaused() {
        synchronized (pauseLock) {
            while (paused) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    break;
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
