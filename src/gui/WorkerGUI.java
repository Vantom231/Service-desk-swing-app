package gui;

import dao.*;
import entities.*;
import org.davidmoten.text.utils.WordWrap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
    private JPanel newMessagePanel;
    private JTextArea newMessageText;
    private JButton newMessageBackButton;
    private JButton newMessageSendButton;
    private JScrollPane scrollPanel3;
    private JButton archiwumPanelButton;
    private JPanel archivePanel;
    private JList archiveList;
    DefaultListModel archiveModel = (DefaultListModel) archiveList.getModel();
    private JScrollPane scrollPanel4;
    private JButton searchPanelButton;
    private JButton searchAddButton;
    private JTextField searchText;
    private JButton searchButton;
    private JScrollPane scrollPanel5;
    private JPanel searchPanel;
    private JList searchList;
    private JPanel anwserAddPanel;
    private JTextField searchTitleField;
    private JTextField searchCategoryField;
    private JTextField searchSubCategoryField;
    private JTextArea searchDescriptionField;
    private JButton searchSaveButton;
    private JPanel anwserPanel;
    private JTextArea searchDescriptionText;
    private JLabel searchTitleText;
    private JLabel searchCategoryText;
    private JLabel searchIdText;
    private JLabel searchSubcategoryText;
    private JTextField tagsField;
    private final DefaultListModel searchModel = (DefaultListModel) searchList.getModel();

    //others
    CardLayout cardLayout = new CardLayout();
    private Users currentUser;
    private List<Reports> threadList;
    private List<ReportsArchive> archiveThreadList;
    private int listIndex = -1;
    ArrayList<Question> qArr;


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

        //Card Layout
        mainPanel.setLayout(cardLayout);
        mainPanel.add(homePanel,"home");
        mainPanel.add(panel1,"panel1");
        mainPanel.add(messagePanel,"messagePanel");
        mainPanel.add(newMessagePanel,"newMessagePanel");
        mainPanel.add(archivePanel, "archivePanel");
        mainPanel.add(searchPanel, "searchPanel");
        mainPanel.add(anwserAddPanel, "anwserAddPanel");
        mainPanel.add(anwserPanel, "anwserPanel");

        scrollPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        messageList.setCellRenderer(getCellBorderRenderer());
        messageList.setFixedCellHeight(-1);
        messageList.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.BLACK));

        scrollPanel2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        subMessageList.setCellRenderer(getCellBorderRenderer());
        subMessageList.setFixedCellHeight(-1);
        subMessageList.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.black));

        archiveList.setCellRenderer(getCellBorderRenderer());
        archiveList.setFixedCellHeight(-1);
        archiveList.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.black));
        archiveList.addMouseListener(archiveThreadListMouseListener());

        searchList.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        searchList.setCellRenderer(getCellBorderRenderer());
        searchList.setFixedCellHeight(-1);
        searchList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                anwserFiller(searchList.getSelectedIndex());
                cardLayout.show(mainPanel, "anwserPanel");
            }
        });



        scrollPanel3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        scrollPanel4.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        scrollPanel5.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));



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

        archiveButton.addActionListener(e -> {
            ReportsDao.archive(threadList.get(messageList.getSelectedIndex()));
            threadListFiller();
            cardLayout.show(mainPanel,"panel1");
        });
        searchPanelButton.addActionListener(e -> {
            searchFiller();
            cardLayout.show(mainPanel,"searchPanel");
        });

        backButton.addActionListener(e -> {
            threadListFiller();
            cardLayout.show(mainPanel,"panel1");
        });

        newMessageBackButton.addActionListener(e -> {
            subMessageFiller(true);
            cardLayout.show(mainPanel,"panel1");
        });
        newMessageSendButton.addActionListener(e -> {
            MessagesDAO.addMessage(threadList.get(messageList.getSelectedIndex()).getId(),1,newMessageText.getText());
            subMessageFiller(true);
            cardLayout.show(mainPanel,"messagePanel");
        });

        archiwumPanelButton.addActionListener(e -> {
            archiveListFiller();
            cardLayout.show(mainPanel,"archivePanel");
        });
        searchButton.addActionListener(e -> search());
        searchAddButton.addActionListener(e -> cardLayout.show(mainPanel,"anwserAddPanel"));
        searchSaveButton.addActionListener(e -> {
            Question temp = new Question(AnwsersDAO.getLastId()+1,
                    searchCategoryField.getText(),
                    searchSubCategoryField.getText(),
                    searchTitleField.getText(),
                    searchDescriptionField.getText(),
                    stringToTable(tagsField.getText()));
            try {
                AnwsersDAO.addQuestion(temp);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"nie udało się wstawić odpowiedzi");
            }
            searchFiller();
            cardLayout.show(mainPanel, "searchPanel");
        });

        homeButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        openThreadButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        threadButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        newMessageSendButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        newMessageBackButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        archiwumPanelButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        searchPanelButton.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        searchButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        searchAddButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        searchText.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        searchCategoryField.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        searchSubCategoryField.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        searchTitleField.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        searchDescriptionField.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        searchSaveButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        tagsField.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        homeButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(homeButton));
        openThreadButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(openThreadButton));
        threadButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(threadButton));
        newMessageSendButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(newMessageSendButton));
        newMessageBackButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(newMessageBackButton));
        archiwumPanelButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(archiwumPanelButton));
        searchPanelButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(searchPanelButton));
        searchButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(searchButton));
        searchAddButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(searchAddButton));
        searchSaveButton.addMouseListener(DesignHandlers.getButtonMouseAdapter(searchSaveButton));

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
    private void archiveListFiller(){
        archiveThreadList = ReportsArchiveDAO.getReportsByWorkerID(currentUser.getId());

        archiveModel.clear();
        for (ReportsArchive reportsArchive : archiveThreadList) {
            archiveModel.addElement(reportsArchive.getTitle());
        }
    }
    private void searchFiller(){

        try {
            AnwsersDAO.load();
            searchModel.clear();

            searchButton.setEnabled(true);
            searchText.setEditable(true);
            searchAddButton.setEnabled(true);
            searchText.setText("");
        } catch (FileNotFoundException e) {
            searchButton.setEnabled(false);
            searchText.setEditable(false);
            searchAddButton.setEnabled(false);
            searchText.setText("Wyszukiwanie nie dostępne");
        }
    }
    private void search(){
        qArr = AnwsersDAO.searchByTags(searchText.getText());
        searchModel.clear();

        for (Question question : qArr) {
            searchModel.addElement(question.getTitle());
        }
    }
    private void anwserFiller(int index){
        Question curr = qArr.get(index);
        searchTitleText.setText("Tytuł: "+ curr.getTitle());
        searchCategoryText.setText("Kategoria: "+curr.getCategory());
        searchSubcategoryText.setText("Podkategoria: " + curr.getSubcategory());
        searchIdText.setText("Numer referencyjny: " +curr.getId());
        searchDescriptionText.setText(curr.getDescription());
    }
    private String [] stringToTable(String str){
        String [] temp = str.split("\\s+");
        for (int i = 1; i < temp.length; i++) {
//            temp[i] = temp[i].substring(1);
        }
        return temp;
    }
    private MouseAdapter threadListMouseListener(boolean activeThread) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                subMessageFiller(activeThread);
            }
        };
    }
    private MouseAdapter archiveThreadListMouseListener(){
      return new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
              ReportsArchive temp = archiveThreadList.get(archiveList.getSelectedIndex());
              archiveButton.setVisible(false);
              newSubMessageButton.setVisible(false);
              backButton.setVisible(false);

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

              subMessageModel.clear();
              List<MessagesArchive> allMessages = MessagesArchiveDAO.getAllMessagesByReportID(temp.getId());
              for (MessagesArchive mess : allMessages) {
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
    private void subMessageFiller(boolean activeThread){
            Reports temp = threadList.get(messageList.getSelectedIndex());
            newSubMessageButton.setVisible(true);
            backButton.setVisible(true);
        for (ActionListener a : newSubMessageButton.getActionListeners()) {
            newSubMessageButton.removeActionListener(a);
            System.out.println("usuwanie");
        }

            ActionListener newMessageActionListener = e -> cardLayout.show(mainPanel,"newMessagePanel");
            ActionListener takeMessageActionListener = e -> {
                temp.setWorkerId(currentUser.getId());
                ReportsDao.takeForWorker(temp);
                System.out.println("dziala2");
                openThreadListFiller();
                cardLayout.show(mainPanel, "panel1");
            };

            if(activeThread) {
                newSubMessageButton.setText("Nowa Wiadomość");
                newSubMessageButton.addActionListener(newMessageActionListener);
                archiveButton.setVisible(true);

            }else{
                newSubMessageButton.setText("Przyjmij");
                newSubMessageButton.addActionListener(takeMessageActionListener);
                System.out.println("dziala1");
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

