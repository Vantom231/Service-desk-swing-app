package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class test extends JFrame{
    private JPanel panel;
    private JButton tab1Button;
    private JButton tab2Button;
    private JPanel p1;
    private JPanel card;
    private JPanel p2;

    public static void main(String[] args) {
        new test();
    }
    test(){
        this.setSize(400,400);
        this.setContentPane(panel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        CardLayout c = new CardLayout();
        card.setLayout(c);
        card.add(p1,"1");
        card.add(p2,"2");
        c.show(card,"2");

        tab1Button.addActionListener(e -> c.show(card,"1"));
        tab2Button.addActionListener(e -> c.show(card,"2"));
        tab1Button.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        tab2Button.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        tab1Button.setBackground(Color.decode("#666666"));
        tab2Button.setBackground(Color.LIGHT_GRAY);

        tab1Button.addMouseListener(buttonMouseAdapter(tab1Button));
        tab2Button.addMouseListener(buttonMouseAdapter(tab2Button));



    }

    private MouseAdapter buttonMouseAdapter(JButton button){
        MouseAdapter temp = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(Color.pink);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(Color.lightGray);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.gray);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.lightGray);
            }
        };
        return temp;
    }
}
