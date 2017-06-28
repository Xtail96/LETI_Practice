package ru.xtails;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Visualizer extends JPanel {

    private final int leftPartX = 150;
    private final int rightPartX = 600;
    private final int vertexRadius = 25;
    private final int vertexDiametr = 2 * vertexRadius;

    private BiGraph g = new BiGraph();
    private mxGraphComponent graphComponent;
    private MaximalMatchingKuhn algorithm;
    private Thread algorithmThread;

    private Vertex activeEdgeV1, activeEdgeV2;
    private HashMap<Vertex, Vertex> currentMatching;


    Visualizer() {
        mxGraph graph = new mxGraph();

        graph.setCellsSelectable(false);
        graph.setCellsBendable(false);
        graph.setCellsEditable(false);

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);
        graphComponent.setAutoScroll(true);
        graphComponent.setPreferredSize(new Dimension(800, 600));

        add(graphComponent);

        //update();
    }

    public void update(){
        if(g != null) {
            mxGraph graph = graphComponent.getGraph();
            Object parent = graph.getDefaultParent();

            // удаляем все элементы графа
            graph.removeCells(graph.getChildCells(graph.getDefaultParent(), true, true));
            // начинаем перерисовывать граф
            graph.getModel().beginUpdate();

            HashMap<Vertex, Object> graphToGui = new HashMap<>();

            // рисуем вершины
            int y1 = vertexRadius;
            int y2 = y1;

            for(Vertex v1 : g.getVertices()) {
                int x = 0;
                int y = 0;

                if (g.isVertexInPart1(v1)) {
                    // находится в первой доле
                    x = leftPartX;
                    y = y1;
                    y1 += 3 * vertexRadius;
                } else {
                    // находится во второй доле
                    x = rightPartX;
                    y = y2;
                    y2 += 3 * vertexRadius;
                }

                // отрисовываем вершину
                Object vertex = graph.insertVertex(parent, null, v1.name, x, y, vertexDiametr, vertexDiametr, "shape=ellipse");
                graphToGui.put(v1, vertex);
            }

            // соединяем вершины ребрами
            for (Vertex v1 : g.getVertices()) {
                for (Vertex v2 : g.getNeighbours(v1)) {
                    Object vertex1 = graphToGui.get(v1), vertex2 = graphToGui.get(v2);
                    graph.insertEdge(parent, null, "", vertex1, vertex2, "endArrow=none;strokeColor=" + getEdgeColor(v1, v2));
                }
            }

            g.reset();

            graph.getModel().endUpdate();
        }
    }

    public void setActiveEdge(Vertex v1, Vertex v2){
        activeEdgeV1 = v1;
        activeEdgeV2 = v2;
    }

    public void setCurrentMatching(HashMap<Vertex, Vertex> matching){
        currentMatching = matching;
    }

    public String getEdgeColor(Vertex v1, Vertex v2){
        String color = "#555";

        if ((algorithmThread != null) && (algorithmThread.getState() == Thread.State.TERMINATED)) {
            color = "#BB00000";
        }

        if( (v1 == activeEdgeV1 && v2 == activeEdgeV2) || (v1 == activeEdgeV2 && v2 == activeEdgeV1) ){
            color = "#0000FF";
        }
        else{
            if(currentMatching != null) {
                boolean isInMatching = currentMatching.containsKey(v1) && (currentMatching.get(v1) == v2);
                isInMatching |= currentMatching.containsKey(v2) && (currentMatching.get(v2) == v1);

                if (isInMatching) {
                    color = "#00BB00";
                }
            }
        }
        return color;
    }

    public void paintComponent(Graphics g){
        update();
    }

    public void setGraph(BiGraph bg) {
        g = bg;
        update();
    }

    public void start(boolean continuous, AlgorithmEvent listener) {
        if (algorithm != null) {
            // останавливаем поток, если он был запущен
            algorithm.stop();
        }

        algorithm = new MaximalMatchingKuhn(g, continuous);
        algorithm.addListener(listener);
        algorithmThread = new Thread(algorithm);
        algorithmThread.start();
    }

    public void stop() {
        algorithm.stop();
    }

    public void step() {
        algorithm.step();
    }

    public String getAlgorithmResult() {
        return algorithm.getMatching();
    }
}
