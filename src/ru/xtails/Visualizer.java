package ru.xtails;

import javax.swing.*;
import java.awt.*;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.scene.shape.Circle;

public class Visualizer extends JPanel {

    public Visualizer() {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();



        graph.getModel().beginUpdate();

            Object v1 = graph.insertVertex(parent, null, "A", 20, 20, 80, 30, "shape=ellipse");
            Object v2 = graph.insertVertex(parent, null, "B", 240, 150, 80, 30, "shape=ellipse");
            graph.insertEdge(parent, null, "", v1, v2);


        graph.getModel().endUpdate();


        graph.setLabelsVisible(true);
        graph.setEnabled(false);

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        add(graphComponent);
        Dimension dimension = new Dimension(800, 600);
        graphComponent.setPreferredSize(dimension);

    }

    public void paintComponent(Graphics g){
        //this.setBackground(Color.white);
        /*
        g.setColor(Color.green);
        g.drawLine(10, 20, 100, 50);
        g.setColor(Color.blue);
        g.drawRect(25, 50, 100, 50);
        g.setColor(Color.gray);
        g.fillOval(10, 90, 100, 50);*/
    }
}
