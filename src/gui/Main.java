package gui;

import dao.ReportsArchiveDAO;
import dao.ReportsDao;
import dao.UsersDAO;
import entities.Reports;
import entities.ReportsArchive;
import entities.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.List;

public class Main extends JFrame{
    private JPanel panel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registrationButton;

    public static void main(String[] args) {
        if(!HibernateUtils.connecToDB()){
            JOptionPane.showMessageDialog(null,"Połączenie z bazą danych nie powiodło się");
        }else {


            List<ReportsArchive> temp = ReportsArchiveDAO.getAllReports();
            for (ReportsArchive reportsArchive : temp) {
                System.out.println(reportsArchive.toString());
            }
            //debugging help
            List<Users> list = UsersDAO.getAllUsers();
            for (Users a : list) {
                System.out.println(a.toString());
            }
            System.out.println();
            List<Reports> rList = ReportsDao.getAllReports();
            for (Reports reports : rList) {
                System.out.println(reports.toString());
            }

            System.out.println("nie przyjęte");
            Session session = HibernateUtils.getSessionFactory().openSession();
            Query query = session.createQuery("From Reports where workerId is null");

            for (Object o : query.list()) {
                System.out.println(o);
            }

            new Main();
        }
    }
    public Main(){
        setGui();

    }
    private void setGui(){
        //background: #555555
        //Font-Color: #cccccc
        //inputs-Color: #444444
        //button-bg: #112244
        this.setContentPane(panel);
        this.setSize(400,250);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //removing borders of inputs and buttons
        loginField.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));
        passwordField.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));
        loginButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        registrationButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        //changing caret color
        loginField.setCaretColor(Color.white);
        passwordField.setCaretColor(Color.white);

        //listeners
        loginButton.addActionListener(e -> login());
        registrationButton.addActionListener(e -> {
            new Registration();
            this.dispose();
        });
        loginField.addActionListener(e -> login());
        passwordField.addActionListener(e -> login());
    }
    private void login(){
        String login = loginField.getText();
        String password = String.valueOf(passwordField.getPassword());
        try{
            Users user = UsersDAO.checkLogin(login,password);
            System.out.println();
            System.out.println("Zalogowano na:");
            System.out.println(user.toString());
            if(user.getAccountLvl() == 1) new WorkerGUI();
            else new UserGUI(user);
            this.dispose();
        }catch(IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null,"Błędny Login Lub Hasło");
        }
    }
}
