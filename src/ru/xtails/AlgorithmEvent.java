package ru.xtails;

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
     * Алгоритм завершил работу
     */
    void finishEvent();
}
