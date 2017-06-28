package ru.xtails;

import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Реализация алгоритма Куна для поиска наибольшего паросочетания в графе (в отдельном потоке)
 */
public class MaximalMatchingKuhn implements Runnable {
    private ArrayList<AlgorithmEvent> listeners = new ArrayList<>();

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
        sendHint("Алгоритм запущен", 0);
        matching = new HashMap<>();

        if (graph != null) {
            // для каждой вершины 1-й доли пытаемся найти увеличивающуюся цепь
            for (Vertex v : graph.getPart1Vertices()) {
                if (running) {
                    sendHint(System.lineSeparator() + "Запускаем DFS из вершины " + v, 0);

                    graph.reset();
                    if (dfs(v, 0)) {
                        sendHint("Текущее паросочетание", 0);
                        sendHint(getMatching(), 0);
                    }

                } else {
                    // завершаемся
                    break;
                }
            }
        }

        sendHint(System.lineSeparator() + "Алгоритм завершен", 0);
        sendActiveEdgeChanged(null, null);
        sendFinished();
    }

    /**
     * Обход графа в глубину и улучшение текущего паросочетания
     * @param root вершина, из которой будет запущен обход
     * @return true, если удалось улучшеть текущее паросочетание, иначе false
     */
    private boolean dfs(Vertex root, int depth) {
        if (root.visited) {
            sendHint("Вершина " + root + " уже была посещена, не рассматриваем", depth);
            return false;
        }
        sendHint("Рассматриваем вершину " + root, depth);
        // помечаем вершину как отмеченную
        root.visited = true;

        for (Vertex to : graph.getNeighbours(root)) {
            sendActiveEdgeChanged(root, to);
            sendHint("Пытаемся найти увеличивающуюся цепь через вершину " + to, depth);
            sendHint("Текущее ребро: " + root + " " + to, depth);

            if (!matching.containsKey(to) || dfs(matching.get(to), depth + 1)) {
                // если удалось найти увеличивающуюся цепь, добавляем ребро в текущее паросочетание
                sendHint("Удалось найти увеличивающуюся цепь. Добавляем ребро " + root + " " + to + " в паросочетание", depth);
                matching.put(to, root);
                sendMatchingChanged();
                return true;
            } else {
                sendHint("НЕ удалось найти увеличивающуюся цепь. Ребро " + root + " " + to + " в паросочетание НЕ добавлено", depth);
            }
        }
        return false;
    }

    public void addListener(AlgorithmEvent listener) {
        System.out.println(listener);
        listeners.add(listener);
    }

    /**
     * @return текущее паросочетание
     */
    public String getMatching() {
        String s = "";

        for (Vertex v1 : graph.getVertices()) {
            if (matching.containsKey(v1)) {
                Vertex src = v1, dst = matching.get(v1);
                if (src.compareTo(dst) > 0) {
                    src = dst;
                    dst = v1;
                }

                s += (src + " " + dst + System.lineSeparator());
            }
        }

        return s;
    }

    /**
     * Приостанавливает пошаговое выполнение алгоритма, если поле paused == true
     */
    private void checkForPaused() {
        if (!continuous) {
            sendStep();

            synchronized (pauseLock) {
                if (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {

                    }
                }
            }
        } else {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    /**
     * Посылает событие - пояснение к алгоритму
     * @param hint пояснение
     */
    private void sendHint(String hint, int depth) {
        for(int i = 0; i < depth; i++) {
            hint = "| " + hint;
        }

        for (AlgorithmEvent listener : listeners) {
            listener.hintEvent(hint);
        }

        checkForPaused();
    }

    private void sendFinished() {
        for (AlgorithmEvent listener : listeners) {
            listener.finishEvent();
        }
    }

    private void sendStep() {
        for (AlgorithmEvent listener : listeners) {
            listener.stepEvent();
        }
    }

    private void sendActiveEdgeChanged(Vertex v1, Vertex v2) {
        for (AlgorithmEvent listener : listeners) {
            listener.activeEdgeChanged(v1, v2);
        }
    }

    private void sendMatchingChanged() {
        for (AlgorithmEvent listener : listeners) {
            listener.matchingChanged(matching);
        }
    }
}
