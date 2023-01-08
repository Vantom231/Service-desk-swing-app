package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DesignHandlers {


    public static MouseAdapter getButtonMouseAdapter(JButton bt){
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
}
