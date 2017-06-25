package ru.xtails;

import javax.swing.*;
import java.awt.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Visualizer extends JPanel {

    public Visualizer() {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();

            Object v1 = graph.insertVertex(parent, "A", null, 20, 20, 80, 30);
            Object v2 = graph.insertVertex(parent, "B", null, 240, 150, 80, 30);
            graph.insertEdge(parent, "E", null, v1, v2);

        graph.getModel().endUpdate();

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        add(graphComponent);
    }

    public void paintComponent(Graphics g){
        this.setBackground(Color.white);
        /*
        g.setColor(Color.green);
        g.drawLine(10, 20, 100, 50);
        g.setColor(Color.blue);
        g.drawRect(25, 50, 100, 50);
        g.setColor(Color.gray);
        g.fillOval(10, 90, 100, 50);*/
    }
}
