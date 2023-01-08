package gui;

import dao.MessagesDAO;
import dao.ReportsDao;
import dao.UsersDAO;
import entities.Messeges;
import entities.Reports;
import entities.Users;
import org.davidmoten.text.utils.WordWrap;

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
    private JPanel messagePanel;
    private JPanel panel;
    private JList messageList;
    private DefaultListModel messageModel = (DefaultListModel) messageList.getModel();
    private JLabel threadTitleText;
    private JScrollPane scrollPanel;
    private JButton backButton;
    private JButton archiveButton;
    private JButton newSubMessageButton;
    private JList subMessageList;
    private DefaultListModel subMessageModel = (DefaultListModel) subMessageList.getModel();
    private JLabel titleText;
    private JLabel dateText;
    private JLabel categoryText;
    private JLabel userText;
    private JScrollPane scrollPanel2;

    //others
    CardLayout cardLayout = new CardLayout();
    private Users currentUser;
    private List<Reports> threadList;
    private List<Reports> openThreadList;
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

        scrollPanel2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        subMessageList.setCellRenderer(getCellBorderRenderer());
        subMessageList.setFixedCellHeight(-1);
        subMessageList.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.black));

        //Card Layout
        mainPanel.setLayout(cardLayout);
        mainPanel.add(homePanel,"home");
        mainPanel.add(panel1,"panel1");
        mainPanel.add(messagePanel,"messagePanel");

        //Buttons
        homeButton.addActionListener(e -> cardLayout.show(mainPanel,"home"));

        threadButton.addActionListener(e -> {
            threadListFiller();
            messageList.addMouseListener(threadListMouseListener(true));
            cardLayout.show(mainPanel,"panel1");
        });

        openThreadButton.addActionListener(e -> {
            openThreadListFiller();
            messageList.addMouseListener(threadListMouseListener(false));
            cardLayout.show(mainPanel, "panel1");
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
    private void openThreadListFiller(){
        threadTitleText.setText("Dostępne Wątki");
        threadList = ReportsDao.getOpenReports();

        messageModel.clear();
        for (Reports reports : threadList) {
            messageModel.addElement(reports.getTitle());
        }
    }
    private MouseAdapter threadListMouseListener(boolean activeThread){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Reports temp = threadList.get(messageList.getSelectedIndex());

                if(activeThread) {
                    newSubMessageButton.setVisible(true);
                    archiveButton.setVisible(true);
                }else{
                    newSubMessageButton.setVisible(false);
                    archiveButton.setVisible(false);
                }

                listIndex = temp.getId();
                titleText.setText("Tytuł: " + temp.getTitle());
                dateText.setText(temp.getPostDate().toString());
                categoryText.setText("Kategoria: " + temp.getCategory());

                String clientName = UsersDAO.getUserNameByID(temp.getUserId());
                userText.setText("Klient:  " + clientName);
                backButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
                newSubMessageButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
                archiveButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
                backButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(backButton));
                archiveButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(archiveButton));
                newSubMessageButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(newSubMessageButton));

                listIndex = -1;
                subMessageList.repaint();
                subMessageModel.clear();
                List<Messeges> allMessages = MessagesDAO.getAllMessagesByReportID(temp.getId());
                for (Messeges mess : allMessages) {
                    String str = mess.getMessage();
                    if(mess.getSender() == 1)
                        str = WordWrap.from(currentUser.getUsername() + ":\n"+str).maxWidth(90).wrap();
                    else str = WordWrap.from(clientName +":\n" + str).maxWidth(90).wrap();
                    subMessageModel.addElement(str);
                }
                cardLayout.show(mainPanel, "messagePanel");
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

