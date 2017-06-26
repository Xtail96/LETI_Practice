package ru.xtails;

import java.util.HashMap;

/**
 * Событие, которое происходит при изменении следующих состоянии алгоритма
 */
public interface AlgorithmEvent {
    /**
     * Пояснение к алгоритму
     * @param hint пояснение
     */
    void hintEvent(String hint);

    /**
     * Выполнен шаг алгоритма
     */
    void stepEvent();

    /**
     * Произошла смена активного ребра
     */
    void activeEdgeChanged(Vertex v1, Vertex v2);

    /**
     * Произошла смена паросочетания
     */
    void matchingChanged(final HashMap<Vertex, Vertex> matching);

    /**
     * Алгоритм завершил работу
     */
    void finishEvent();
}
