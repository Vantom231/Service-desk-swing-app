package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WorkerGUI extends JFrame {
    private JButton homeButton;
    private JButton panel2Button;
    private JButton panel1Button;
    private JPanel mainPanel;
    private JPanel homePanel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel;

    public static void main(String[] args) {
        new WorkerGUI();
    }

    WorkerGUI(){
        setGui();
    }

    private MouseAdapter buttonMouseAdapter(JButton bt){
        MouseAdapter temp = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bt.setBackground(Color.decode("#485566"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bt.setBackground(Color.decode("#384455"));
            }
        };
        return temp;
    }

    private void setGui(){
        this.setContentPane(panel);
        this.setMinimumSize(new Dimension(700,500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        //Card Layout
        CardLayout cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.add(homePanel,"home");
        mainPanel.add(panel1,"panel1");
        mainPanel.add(panel2,"panel2");

        //Buttons
        homeButton.addActionListener(e -> cardLayout.show(mainPanel,"home"));
        panel1Button.addActionListener(e -> cardLayout.show(mainPanel, "panel1"));
        panel2Button.addActionListener(e -> cardLayout.show(mainPanel,"panel2"));

        homeButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        panel1Button.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        panel2Button.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));

        homeButton.addMouseListener(buttonMouseAdapter(homeButton));
        panel1Button.addMouseListener(buttonMouseAdapter(panel1Button));
        panel2Button.addMouseListener(buttonMouseAdapter(panel2Button));
    }
}

