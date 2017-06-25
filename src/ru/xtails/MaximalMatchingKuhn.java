package ru.xtails;

import java.util.HashMap;

/**
 * Реализация алгоритма Куна для поиска наибольшего паросочетания в графе (в отдельном потоке)
 */
public class MaximalMatchingKuhn implements Runnable {
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    // исходный граф
    BiGraph graph;
    // текущее паросочетание
    HashMap<Vertex, Vertex> matching;

    public MaximalMatchingKuhn(BiGraph g) {
        graph = g;
    }

    @Override
    public void run() {
        for (Vertex v : graph.getVertices()) {
            graph.reset();
            dfs(v);
        }
    }

    private boolean dfs(Vertex root) {
        if (root.visited)
            return false;
        root.visited = true;

        for (Vertex to : graph.getNeighbours(root)) {
            if (!matching.containsKey(to) || dfs(matching.get(to))) {
                matching.put(to, root);
                return true;
            }
        }
        return false;
    }

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

    public void pause() {
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }
}
