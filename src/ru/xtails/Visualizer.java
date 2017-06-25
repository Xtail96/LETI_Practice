package ru.xtails;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Visualizer extends JPanel {

    private final int leftPartX = 150;
    private final int rightPartX = 600;
    private final int vertexDiametr = 50;
    private BiGraph g;
    private mxGraphComponent graphComponent;

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

    private void update(){
        if(g != null) {
            mxGraph graph = graphComponent.getGraph();
            Object parent = graph.getDefaultParent();

            graph.getModel().beginUpdate();

            HashMap<Vertex, Object> graphToGui = new HashMap<>();

            // рисуем 1 долю
            int y = vertexDiametr/2;
            for(Vertex v1 : g.getPart1Vertices()) {
                Object vertex = graph.insertVertex(parent, null, v1.name, leftPartX, y, vertexDiametr, vertexDiametr, "shape=ellipse");
                graphToGui.put(v1, vertex);
                y += 1.5 * vertexDiametr;
            }

            // рисуем 2 долю
            y = vertexDiametr/2;
            for(Vertex v2 : g.getPart2Vertices()){
                Object vertex = graph.insertVertex(parent, null, v2.name, rightPartX, y, vertexDiametr, vertexDiametr, "shape=ellipse");
                graphToGui.put(v2, vertex);
                y += 1.5 * vertexDiametr;
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
    }

    public void paintComponent(Graphics g){
        update();
    }

    public void setGraph(BiGraph bg) {
        g = bg;
        update();
    }

}
