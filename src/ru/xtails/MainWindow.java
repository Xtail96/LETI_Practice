package ru.xtails;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private JPanel drawPanel;
    private JButton начатьButton;
    private JTextArea textArea1;
    private JButton шагВпередButton;
    private JCheckBox выполнятьНепрерывноCheckBox;

    public MainWindow() {
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
    }
}