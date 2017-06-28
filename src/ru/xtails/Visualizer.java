package ru.xtails;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class Visualizer extends JPanel {

    private final int leftPartX = 150;
    private final int rightPartX = 600;
    private final int vertexRadius = 25;
    private final int vertexDiameter = 2 * vertexRadius;

    private BiGraph graph = new BiGraph();
    private MaximalMatchingKuhn algorithm;
    private Thread algorithmThread;

    private Vertex activeEdgeV1, activeEdgeV2;
    private HashMap<Vertex, Vertex> currentMatching;


    Visualizer() {

    }

    private void drawVertex(Graphics2D graphics, Vertex v) {
        int leftX, upperY;
        leftX = v.x - vertexRadius;
        upperY = v.y - vertexRadius;

        Color vertexBorderColor = new Color(0, 0, 0);
        Color vertexBodyColor = new Color(185, 221, 252);
        Color textColor = new Color(0, 0, 0);

        graphics.setColor(vertexBodyColor);
        graphics.fillOval(leftX, upperY, vertexDiameter, vertexDiameter);
        graphics.setColor(vertexBorderColor);
        graphics.drawOval(leftX, upperY, vertexDiameter, vertexDiameter);

        FontMetrics fm = graphics.getFontMetrics();
        String vertexName = v.name;

        int strWidth = fm.stringWidth(vertexName);
        int strHeight = fm.getAscent();
        graphics.setColor(textColor);
        graphics.drawString(vertexName, v.x - strWidth/2, v.y + strHeight/2);
    }

    public void drawEdge(Graphics2D graphics, Vertex src, Vertex dst) {
        graphics.setColor(getEdgeColor(src, dst));
        graphics.drawLine(src.x, src.y, dst.x, dst.y);
    }

    public void setActiveEdge(Vertex v1, Vertex v2){
        activeEdgeV1 = v1;
        activeEdgeV2 = v2;
    }

    public void setCurrentMatching(HashMap<Vertex, Vertex> matching){
        currentMatching = matching;
    }

    public Color getEdgeColor(Vertex v1, Vertex v2){
        Color unvisitedEdgeColor = new Color(50, 50, 50);
        Color currentEdgeColor = new Color(0, 0, 255);
        Color notMatchingEdgeColor = new Color(187, 0, 0);
        Color matchingEdgeColor = new Color(0, 187, 0);
        Color c = unvisitedEdgeColor;

        if ((algorithm != null) && (algorithm.isFinished())) {
            c = notMatchingEdgeColor;
        }

        if ( (v1 == activeEdgeV1 && v2 == activeEdgeV2) || (v1 == activeEdgeV2 && v2 == activeEdgeV1) ) {
            c = currentEdgeColor;
        } else {
            if(currentMatching != null) {
                boolean isInMatching = currentMatching.containsKey(v1) && (currentMatching.get(v1) == v2);
                isInMatching |= currentMatching.containsKey(v2) && (currentMatching.get(v2) == v1);

                if (isInMatching) {
                    c = matchingEdgeColor;
                }
            }
        }

        return c;
    }

    public void paintComponent(Graphics graphics){
        Graphics2D graphics2D = (Graphics2D) graphics;
        super.paintComponent(graphics);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(graph != null) {
            // подсчитываем координаты графа
            int y1 = vertexDiameter;
            int y2 = y1;

            for(Vertex v : graph.getVertices()) {
                if (graph.isVertexInPart1(v)) {
                    // находится в первой доле
                    v.x = leftPartX;
                    v.y = y1;
                    y1 += 3 * vertexRadius;
                } else {
                    // находится во второй доле
                    v.x = rightPartX;
                    v.y = y2;
                    y2 += 3 * vertexRadius;
                }
            }

            // рисуем ребра
            for (Vertex v1 : graph.getVertices()) {
                for (Vertex v2 : graph.getNeighbours(v1)) {
                    drawEdge(graphics2D, v1, v2);
                }
            }

            // рисуем сами вершины
            for (Vertex v : graph.getVertices()) {
                drawVertex(graphics2D, v);
            }
        }
    }

    public void setGraph(BiGraph bg) {
        graph = bg;
        algorithm = null;
        algorithmThread = null;
        revalidate();
        repaint();
    }

    public void start(boolean continuous, AlgorithmEvent listener) {
        if (algorithm != null) {
            // останавливаем поток, если он был запущен
            algorithm.stop();
        }

        algorithm = new MaximalMatchingKuhn(graph, continuous);
        algorithm.addListener(listener);
        algorithmThread = new Thread(algorithm);
        algorithmThread.start();
        revalidate();
        repaint();
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
