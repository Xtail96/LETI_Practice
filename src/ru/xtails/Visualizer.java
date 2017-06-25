package ru.xtails;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Visualizer extends JPanel {

    private final int leftPartX = 150;
    private final int rightPartX = 600;
    private final int vertexRadius = 50;
    private BiGraph g;
    private mxGraphComponent graphComponent;

    /**
     * Считывает граф
     * @return считанный граф
     */
    public BiGraph readGraph() {
        // добавить реализацию
        g.addEdge("A", "1");
        g.addEdge("B", "1");
        g.addEdge("B", "2");
        g.addEdge("C", "3");
        g.addEdge("C", "4");
        g.addEdge("D", "2");
        g.addEdge("E", "1");
        g.addEdge("E", "4");
        g.addVertex("F");
        return g;
    }

    public Visualizer() {
        g = new BiGraph();
        g = readGraph();

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
        mxGraph graph = graphComponent.getGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();

        HashMap<Vertex, Object> graphToGui = new HashMap<>();

        // рисуем 1 долю
        int y = vertexRadius;
        for(Vertex v1 : g.getPart1Vertices()) {
            Object vertex = graph.insertVertex(parent, null, v1.name, leftPartX, y, vertexRadius, vertexRadius, "shape=ellipse");
            graphToGui.put(v1, vertex);
            y += 2 * vertexRadius;
        }

        // рисуем 2 долю
        y = vertexRadius;
        for(Vertex v2 : g.getPart2Vertices()){
            Object vertex = graph.insertVertex(parent, null, v2.name, rightPartX, y, vertexRadius, vertexRadius, "shape=ellipse");
            graphToGui.put(v2, vertex);
            y += 2 * vertexRadius;
        }

        // соединяем вершины ребрами
        for(Vertex v1 : g.getPart1Vertices()){
            for(Vertex v2 : g.getNeighbours(v1)){
                Object vertex1 = graphToGui.get(v1), vertex2 = graphToGui.get(v2);
                graph.insertEdge(parent, null, "", vertex1, vertex2, "endArrow=none;strokeColor=#00bb00");
            }
        }

        graph.getModel().endUpdate();
    }

    public void paintComponent(Graphics g){
        update();
    }
}
