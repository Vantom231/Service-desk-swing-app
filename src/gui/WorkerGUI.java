package gui;

import dao.ReportsDao;
import entities.Reports;
import entities.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class WorkerGUI extends JFrame {
    private JButton homeButton;
    private JButton threadButton;
    private JButton openThreadButton;
    private JPanel mainPanel;
    private JPanel homePanel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel;
    private JList messageList;
    private DefaultListModel messageModel = (DefaultListModel) messageList.getModel();
    private JLabel threadTitleText;
    private JScrollPane scrollPanel;

    //others
    private Users currentUser;
    private List<Reports> threadList;
    private int listIndex = -1;

    WorkerGUI(Users current){
        setGui();
        this.currentUser = current;
    }



    private void setGui(){
        this.setContentPane(panel);
        this.setMinimumSize(new Dimension(700,500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        scrollPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        messageList.setCellRenderer(getCellBorderRenderer());
        messageList.setFixedCellHeight(-1);
        messageList.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.BLACK));

        //Card Layout
        CardLayout cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.add(homePanel,"home");
        mainPanel.add(panel1,"panel1");
        mainPanel.add(panel2,"panel2");

        //Buttons
        homeButton.addActionListener(e -> cardLayout.show(mainPanel,"home"));
        threadButton.addActionListener(e -> {
            threadListFiller();
            messageList.addMouseListener(threadListMouseListener());
            cardLayout.show(mainPanel,"panel1");
        });

        homeButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        openThreadButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        threadButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));

        homeButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(homeButton));
        openThreadButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(openThreadButton));
        threadButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(threadButton));
    }
    private void threadListFiller(){
        threadTitleText.setText("Wątki w toku");
        threadList = ReportsDao.getReportsByWorkerID(currentUser.getId());
        messageModel.clear();
        for (Reports thread : threadList) {
            messageModel.addElement(thread.getTitle());
        }
    }
    private MouseAdapter threadListMouseListener(){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("in develop");
            }
        };
    }


    private DefaultListCellRenderer getCellBorderRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JTextArea textArea = new JTextArea();
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                textArea.setForeground(Color.decode("#cccccc"));
                if (listIndex == index) textArea.setBackground(Color.decode("#485566"));
                else textArea.setBackground(Color.decode("#606060"));
                textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                textArea.setText((String) value);

                JPanel tempPanel = new JPanel();
                BoxLayout layout = new BoxLayout(tempPanel, BoxLayout.Y_AXIS);
                tempPanel.setLayout(layout);
                tempPanel.add(textArea);
                tempPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

                return tempPanel;
            }
        };
    }

}

