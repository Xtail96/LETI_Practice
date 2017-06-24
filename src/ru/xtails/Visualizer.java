package ru.xtails;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Xtail on 24.06.17.
 */
public class Visualizer extends JPanel{
    public void paintComponent(Graphics g){
        this.setBackground(Color.white);
        g.setColor(Color.green);
        g.drawLine(10, 20, 100, 50);
        g.setColor(Color.blue);
        g.drawRect(25, 50, 100, 50);
        g.setColor(Color.gray);
        g.fillOval(10, 90, 100, 50);
    }
}
