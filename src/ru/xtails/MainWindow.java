package ru.xtails;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainWindow {
    JButton openButton;
    JPanel mainPanel;
    private JTextArea inputTextArea;
    private JButton acceptButton;
    private JTextArea resultTextArea;
    private JButton saveButton;
    private JTabbedPane modeTab;
    private JPanel inputTab;
    private JPanel displayTab;
    private JPanel resultTab;
    private JButton beginVisualizationButton;
    private JTextArea hintTextArea;
    private JButton nextStepButton;
    private JCheckBox continiousCheckBox;
    private JLabel visualizationHintLabel;
    private Visualizer visualizer1;
    private JButton stopButton;

    public MainWindow() {
        nextStepButton.setEnabled(false);
        stopButton.setEnabled(false);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "Hi Bro");
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text", "txt");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(null);

                if(result == JFileChooser.APPROVE_OPTION){
                    String name = fileChooser.getSelectedFile().getName();
                    String absolutePath = fileChooser.getSelectedFile().getAbsolutePath();

                    File file = new File(absolutePath);
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new FileReader(file));
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    String content = "";
                    String line;
                    try {
                        while((line = reader.readLine()) != null)
                        {
                            content += line;
                            content += '\n';
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    inputTextArea.setText(content);
                }
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // проверка на то, что данные не пусты
                String text = inputTextArea.getText();
                if (text.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Файл пустой!");
                }
                else {
                    acceptGraph();
                }
            }
        });


        continiousCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(continiousCheckBox.isSelected()) {
                    nextStepButton.setEnabled(false);
                }
            }
        });
        beginVisualizationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean continuousRunning = continiousCheckBox.isSelected();

                continiousCheckBox.setEnabled(false);
                beginVisualizationButton.setEnabled(false);
                stopButton.setEnabled(true);
                if(!continiousCheckBox.isSelected()){
                    nextStepButton.setEnabled(true);
                }

                visualizer1.start(continuousRunning);
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continiousCheckBox.setEnabled(true);
                beginVisualizationButton.setEnabled(true);
                stopButton.setEnabled(false);
                nextStepButton.setEnabled(false);

                visualizer1.stop();
            }
        });
        nextStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizer1.step();
            }
        });
    }

    /**
     * Считывает граф
     * @return считанный граф
     */
    private Graph readGraph() {
        Graph g = new Graph();
        String str = inputTextArea.getText();
        String lines[] = str.split("\\r?\\n");
        for (String line:lines){
            String elements[] = line.split("\\s+");
            if (elements.length == 2) {
                g.addEdge(elements[0], elements[1]);
            }
            else {
                throw new IllegalArgumentException("Неверный формат ввода");
            }
        }
        return g;
    }

    private void acceptGraph(){
        try {
            Graph g = readGraph();
            BiGraph result = new BiGraph(g);
            visualizer1.setGraph(result);
        } catch(IllegalArgumentException e) {
            // вывод сообщения
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
}
