package gui;

import dao.*;
import entities.*;
import org.davidmoten.text.utils.WordWrap;
import utils.HibernateUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserGUI extends JFrame {
    //main
    private JPanel panel;
    private JButton homeButton;
    private JButton panel2Button;
    private JButton panel1Button;
    private JPanel mainPanel;

    //home panel
    private JPanel homePanel;

    //threadPanel
    private JPanel panel1;
    private JList messageList;
    private final DefaultListModel messageModel = (DefaultListModel) messageList.getModel();
    private JScrollPane scrollPanel;
    private JButton newMessageButton;


    //messagePanel
    private JPanel messagePanel;
    private JButton messageBackButton;
    private JButton newSubMessageButton;
    private JButton archiveButton;
    private JList subMessageList;
    private final DefaultListModel subMessageModel = (DefaultListModel) subMessageList.getModel();
    private JScrollPane scrollPanel2;
    private JLabel titleText;
    private JLabel dateText;
    private JLabel categoryText;
    private JLabel workerText;

    //newMessagePanel
    private JPanel newMessagePanel;
    private JTextPane newMessageTextPane;
    private JButton sendNewMessageButton;
    private JButton backToSubMessageButton;
    private JButton button1;
    private JScrollPane scrollPane3;
    private JPanel newThreadPanel;

    //new Thread Panel
    private JTextField newTitleText;
    private JComboBox categoryBox;
    private JTextArea newThreadText;
    private JButton newThreadBackButton;
    private JButton newThreadSendButton;
    private JButton button4;
    private JScrollPane scrollpane4;
    private JLabel messagePanelTitle;

    //others
    private static Users currentUser = null;
    private List<Reports> reportsList= null;
    private List<ReportsArchive> archiveReportsList = null;
    private final CardLayout cardLayout = new CardLayout();
    private int subMessageListIndex = -1;
    private int selectedReportIndex = 0;

    UserGUI(Users current){
        currentUser = current;
        setGui();
    }

    private void setGui(){
        this.setContentPane(panel);
        this.setMinimumSize(new Dimension(700,500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        Design();
        this.setVisible(true);

        //CardLayout setup
        mainPanel.setLayout(cardLayout);
        mainPanel.add(homePanel,"home");
        mainPanel.add(panel1,"panel1");
        mainPanel.add(messagePanel, "messagePanel");
        mainPanel.add(newMessagePanel, "newMessagePanel");
        mainPanel.add(newThreadPanel, "newThreadPanel");

        //Buttons Listeners
        ActionListener currentActionListener = e -> {
            listFiller();
            cardLayout.show(mainPanel, "panel1");
        };

        ActionListener archiveActionListener = e -> {
            archiveListFiller();
            cardLayout.show(mainPanel, "panel1");
        };

        //menu
        homeButton.addActionListener(e -> cardLayout.show(mainPanel,"home"));

        panel1Button.addActionListener(e -> {
            listFiller();
            messageList.addMouseListener(messageListMouseListener());
            messageBackButton.removeActionListener(archiveActionListener);
            messageBackButton.addActionListener(currentActionListener);
            cardLayout.show(mainPanel, "panel1");
        });

        panel2Button.addActionListener(e -> {
            archiveListFiller();
            messageList.addMouseListener(archiveMessageListMouseListener());
            messageBackButton.removeActionListener(currentActionListener);
            messageBackButton.addActionListener(archiveActionListener);
            cardLayout.show(mainPanel,"panel1");
        });

        //newMessage Panel
        newMessageButton.addActionListener(e -> cardLayout.show(mainPanel,"newThreadPanel"));

        //subMessagePanel
        newSubMessageButton.addActionListener(e -> cardLayout.show(mainPanel,"newMessagePanel"));

        archiveButton.addActionListener(e -> {
            ReportsDao.archive(reportsList.get(messageList.getSelectedIndex()));
            listFiller();
            cardLayout.show(mainPanel, "panel1");
        });

        //newMessagePanel
        backToSubMessageButton.addActionListener(e -> messagePanelFiller());

        sendNewMessageButton.addActionListener(e -> {
            MessagesDAO.addMessage(selectedReportIndex,0,newMessageTextPane.getText());
            messagePanelFiller();
        });

        //newThreadPanel
        newThreadBackButton.addActionListener(e -> {
            listFiller();
            cardLayout.show(mainPanel,"panel1");
        });

        newThreadSendButton.addActionListener(e -> newThread());
    }

    private void newThread(){
        if(!newTitleText.getText().contentEquals("")&&!newThreadText.getText().contentEquals("")) {
            String category = "brak";

            switch (categoryBox.getSelectedIndex()) {
                case 0 -> category = "cat1";
                case 1 -> category = "cat2";
                case 2 -> category = "cat3";
                case 3 -> category = "cat4";
            }

            ReportsDao.addReport(currentUser.getId(), newTitleText.getText(), 0, category, 0);
            MessagesDAO.addMessage(HibernateUtils.getLastID(),0,newThreadText.getText());

        }else JOptionPane.showMessageDialog(null,"Prosze wypełnić wszystkie pola");

        listFiller();
        cardLayout.show(mainPanel,"panel1");
    }
    private void listFiller(){
        messagePanelTitle.setText("Wątki w toku");
        newMessageButton.setVisible(true);
        reportsList = ReportsDao.getReportsByUserID(currentUser.getId());
        messageModel.clear();
        for (Reports reports : reportsList) {
            messageModel.addElement(reports.getTitle());
        }
    }
    private void archiveListFiller(){
        messagePanelTitle.setText("Archiwalne wątki");
        newMessageButton.setVisible(false);
        archiveReportsList = ReportsArchiveDAO.getReportsByUserID(currentUser.getId());
        messageModel.clear();
        for (ReportsArchive rep : archiveReportsList) {
            messageModel.addElement(rep.getTitle());
        }
    }

    private void messagePanelFiller(){
        String workerName = "";
        Reports temp = reportsList.get(messageList.getSelectedIndex());

        newSubMessageButton.setVisible(true);
        archiveButton.setVisible(true);

        selectedReportIndex = temp.getId();
        titleText.setText("Tytuł: " + temp.getTitle());
        dateText.setText(temp.getPostDate().toString());
        categoryText.setText("Kategoria: " + temp.getCategory());

        try {
            workerName = UsersDAO.getUserNameByID(temp.getWorkerId());
            workerText.setText("Serwisant: " + workerName);

        }catch(NullPointerException en){
            workerText.setText("zgłoszenie w trakcie rozpatrywania");
        }

        subMessageModel.clear();
        List<Messeges> allMessages = MessagesDAO.getAllMessagesByReportID(temp.getId());
        for (Messeges mess : allMessages) {
            String str = mess.getMessage();
            if(mess.getSender() == 1)
                str = WordWrap.from(workerName + ":\n"+str).maxWidth(90).wrap();
            else str = WordWrap.from(currentUser.getUsername() +":\n" + str).maxWidth(90).wrap();
            subMessageModel.addElement(str);
        }
        cardLayout.show(mainPanel,"messagePanel");
    }
    private void archiveMessagePanelFiller(){
        ReportsArchive temp = archiveReportsList.get(messageList.getSelectedIndex());

        newSubMessageButton.setVisible(false);
        archiveButton.setVisible(false);

        selectedReportIndex = temp.getId();
        titleText.setText("Tytuł: " + temp.getTitle());
        dateText.setText(temp.getPostDate().toString());
        categoryText.setText("Kategoria: " + temp.getCategory());

        String workerName = UsersDAO.getUserNameByID(temp.getWorkerId());
        workerText.setText("Serwisant: " + workerName);

        subMessageModel.clear();
        List<MessagesArchive> allMessages = MessagesArchiveDAO.getAllMessagesByReportID(temp.getId());
        for (MessagesArchive mess : allMessages) {
            String str = mess.getMessage();
            if(mess.getSender() == 1)
                str = WordWrap.from(workerName + ":\n"+str).maxWidth(90).wrap();
            else str = WordWrap.from(currentUser.getUsername() +":\n" + str).maxWidth(90).wrap();
            subMessageModel.addElement(str);
        }
        cardLayout.show(mainPanel,"messagePanel");
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

    private MouseMotionAdapter listMouseMotionListener(JList list){
      return new MouseMotionAdapter(){
          @Override
          public void mouseMoved(MouseEvent e) {
              int temp = list.locationToIndex(e.getPoint());
              if(subMessageListIndex != temp){
                  subMessageListIndex = temp;
                  list.repaint();
              }
          }
      };
    }

    private MouseAdapter messageListMouseListener(){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messagePanelFiller();
            }
        };
    }
    private MouseAdapter archiveMessageListMouseListener(){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                archiveMessagePanelFiller();
            }
        };
    }

    private DefaultListCellRenderer cellBorderRenderer(){
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JTextArea textArea = new JTextArea();
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                textArea.setForeground(Color.decode("#cccccc"));
                if(subMessageListIndex == index) textArea.setBackground(Color.decode("#485566"));
                else textArea.setBackground(Color.decode("#606060"));
                textArea.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                textArea.setText((String) value);

                JPanel tempPanel = new JPanel();
                BoxLayout layout = new BoxLayout(tempPanel, BoxLayout.Y_AXIS);
                tempPanel.setLayout(layout);
                tempPanel.add(textArea);
                tempPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK));

                return tempPanel;
            }
        };
    }

    private void Design(){
        //messagePanel
        scrollPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        messageList.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.BLACK));
        messageList.setCellRenderer(cellBorderRenderer());
        messageList.addMouseMotionListener(listMouseMotionListener(messageList));
        messageList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                subMessageListIndex = -1;
                messageList.repaint();
            }});
        //subMessagePanel
        scrollPanel2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        subMessageList.setCellRenderer(cellBorderRenderer());
        subMessageList.setFixedCellHeight(-1);
        subMessageList.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.BLACK));
        subMessageListIndex = -1;
        subMessageList.repaint();

        //newMessagePanel
        scrollPane3.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.decode("#333333")));

        //newThreadPanel
        scrollpane4.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        newTitleText.setBorder(BorderFactory.createEmptyBorder(5,3,5,3));
        categoryBox.setBorder(BorderFactory.createEmptyBorder(5,3,5,3));
        for (int i = 0; i < categoryBox.getComponentCount(); i++)
        {
            if (categoryBox.getComponent(i) instanceof JComponent) {
                ((JComponent) categoryBox.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
            }

            if (categoryBox.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) categoryBox.getComponent(i)).setBorderPainted(false);
            }
        }
        //Buttons design
        homeButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        panel1Button.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        panel2Button.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        newMessageButton.setBorder(BorderFactory.createEmptyBorder(7,10,10,10));
        messageBackButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        newSubMessageButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        archiveButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        backToSubMessageButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        sendNewMessageButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        newThreadBackButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        newThreadSendButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        homeButton.addMouseListener(buttonMouseAdapter(homeButton));
        panel1Button.addMouseListener(buttonMouseAdapter(panel1Button));
        panel2Button.addMouseListener(buttonMouseAdapter(panel2Button));
        newMessageButton.addMouseListener(buttonMouseAdapter(newMessageButton));
        messageBackButton.addMouseListener(buttonMouseAdapter(messageBackButton));
        newSubMessageButton.addMouseListener(buttonMouseAdapter(newSubMessageButton));
        archiveButton.addMouseListener(buttonMouseAdapter(archiveButton));
        backToSubMessageButton.addMouseListener(buttonMouseAdapter(backToSubMessageButton));
        sendNewMessageButton.addMouseListener(buttonMouseAdapter(sendNewMessageButton));
        newThreadSendButton.addMouseListener(buttonMouseAdapter(newThreadSendButton));
        newThreadBackButton.addMouseListener(buttonMouseAdapter(newThreadBackButton));
    }
}
