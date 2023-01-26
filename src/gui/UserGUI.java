package gui;

import dao.*;
import entities.*;
import org.davidmoten.text.utils.WordWrap;
import utils.HibernateUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class UserGUI extends JFrame {
    //main
    private JPanel panel;
    private JButton homeButton;
    private JButton panel2Button;
    private JButton panel1Button;
    private JButton searchPanelButton;
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
    //search Panel
    private JPanel searchPanel;
    private JTextField searchText;
    private JButton searchButton;
    private JList anwserList;
    //anwserPanel
    private JPanel anwserPanel;
    private JTextArea anwserText;
    private JLabel searchTitleText;
    private JLabel searchCategoryText;
    private JLabel searchIdText;
    private JLabel searchSubCategoryText;
    private JScrollPane scrollPanel4;
    private final DefaultListModel anwserModel = (DefaultListModel) anwserList.getModel();

    //others
    private static Users currentUser = null;
    private List<Reports> reportsList= null;
    private List<ReportsArchive> archiveReportsList = null;
    private final CardLayout cardLayout = new CardLayout();
    private int subMessageListIndex = -1;
    private int selectedReportIndex = -1;
    private ArrayList<Question> anwArr;
    MouseAdapter messageListMouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            messagePanelFiller();
        }
    };
    MouseAdapter archiveMessageListMouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            archiveMessagePanelFiller();
        }
    };

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
        mainPanel.add(searchPanel, "searchPanel");
        mainPanel.add(anwserPanel, "anwserPanel");

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
            messageList.removeMouseListener(archiveMessageListMouseListener);
            messageList.addMouseListener(messageListMouseListener);
            messageBackButton.removeActionListener(archiveActionListener);
            messageBackButton.addActionListener(currentActionListener);
            cardLayout.show(mainPanel, "panel1");
        });
        panel2Button.addActionListener(e -> {
            archiveListFiller();
            messageList.removeMouseListener(messageListMouseListener);
            messageList.addMouseListener(archiveMessageListMouseListener);
            messageBackButton.removeActionListener(currentActionListener);
            messageBackButton.addActionListener(archiveActionListener);
            cardLayout.show(mainPanel,"panel1");
        });
        searchPanelButton.addActionListener(e -> {
            searchPanelFiller();
            cardLayout.show(mainPanel,"searchPanel");
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

        //searchPanel
        searchButton.addActionListener(e -> search());
        anwserList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                anwserPanelFiller(anwserList.getSelectedIndex());
                cardLayout.show(mainPanel,"anwserPanel");
            }
        });
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
        subMessageListIndex = -1;
        subMessageList.repaint();
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

        subMessageListIndex = -1;
        subMessageList.repaint();

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
    private void searchPanelFiller(){
        anwserModel.clear();
        try {
            AnwsersDAO.load();
            searchButton.setEnabled(true);
            searchText.setEditable(true);
            searchText.setText("");
        }catch(FileNotFoundException e){
            searchButton.setEnabled(false);
            searchText.setEditable(false);
            searchText.setText("wyszukiwanie nie dostępne");
        }
    }
    private void search(){
        anwserModel.clear();
        anwArr = AnwsersDAO.searchByTags(searchText.getText());
        for (Question question : anwArr) {
            anwserModel.addElement(question.getTitle());
        }
    }
    private void anwserPanelFiller(int id){
        Question curr = anwArr.get(id);
        searchIdText.setText("Numer referencyjny: "+curr.getId());
        searchTitleText.setText(curr.getTitle());
        searchCategoryText.setText("Kategoria: " + curr.getCategory());
        searchSubCategoryText.setText("Podkategoria: " + curr.getSubcategory());
        anwserText.setText(curr.getDescription());
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
    private DefaultListCellRenderer getCellBorderRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JTextArea textArea = new JTextArea();
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                textArea.setForeground(Color.decode("#cccccc"));
                if (subMessageListIndex == index) textArea.setBackground(Color.decode("#485566"));
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
    private void setBoxCellBorder(JComboBox categoryBox) {
        for (int i = 0; i < categoryBox.getComponentCount(); i++)
        {
            if (categoryBox.getComponent(i) instanceof JComponent) {
                ((JComponent) categoryBox.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
            }

            if (categoryBox.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) categoryBox.getComponent(i)).setBorderPainted(false);
            }
        }
    }
    private void Design(){
        //messagePanel
        scrollPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        messageList.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.BLACK));
        messageList.setCellRenderer(getCellBorderRenderer());
        messageList.addMouseMotionListener(listMouseMotionListener(messageList));
        messageList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                subMessageListIndex = -1;
                messageList.repaint();
            }});
        //subMessagePanel
        scrollPanel2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        subMessageList.setCellRenderer(getCellBorderRenderer());
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
        setBoxCellBorder(categoryBox);

        //searchPanel
        searchText.setBorder(BorderFactory.createEmptyBorder(5,3,5,3));
        anwserList.setCellRenderer(getCellBorderRenderer());
        anwserList.setFixedCellHeight(-1);
        anwserList.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.BLACK));
        scrollPanel4.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        anwserText.setLineWrap(true);

        //Buttons design
        homeButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        panel1Button.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        panel2Button.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        searchPanelButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        newMessageButton.setBorder(BorderFactory.createEmptyBorder(7,10,10,10));
        messageBackButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        newSubMessageButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        archiveButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        backToSubMessageButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        sendNewMessageButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        newThreadBackButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        newThreadSendButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        searchButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        homeButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(homeButton));
        panel1Button.addMouseListener(DesignHandlers.getButtonMouseAdapter(panel1Button));
        panel2Button.addMouseListener(DesignHandlers.getButtonMouseAdapter(panel2Button));
        newMessageButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(newMessageButton));
        messageBackButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(messageBackButton));
        newSubMessageButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(newSubMessageButton));
        archiveButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(archiveButton));
        backToSubMessageButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(backToSubMessageButton));
        sendNewMessageButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(sendNewMessageButton));
        newThreadSendButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(newThreadSendButton));
        newThreadBackButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(newThreadBackButton));
        searchButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(searchButton));
        searchPanelButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(searchPanelButton));
    }
}
