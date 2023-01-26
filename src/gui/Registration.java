package gui;

import dao.UsersDAO;

import javax.persistence.PersistenceException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Registration extends JFrame{
    private JButton submitButton;
    private JButton returnButton;
    private JTextField loginField;
    private JTextField emailField;
    private JPasswordField passwordField;
    @SuppressWarnings("rawtypes")
    private JComboBox roleBox;
    private JPanel panel;

    public Registration(){
        setGui();


    }

    private void createUser(){
        String login = loginField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String email = emailField.getText();
        int role = roleBox.getSelectedIndex();
        if(!login.contentEquals("") && !password.contentEquals("") && !email.contentEquals(""))
            try{
                UsersDAO.addUser(login,password,role,email,1);
                System.out.println("dziala");
                new Main();
                this.dispose();
            }catch(PersistenceException e){
                JOptionPane.showMessageDialog(this,"Użytkownik o takim loginie już istnieje");
            }
        else JOptionPane.showMessageDialog(this,"Wszytkie pola muszą być uzupełnione");

    }
    private void setGui(){
        //background: #555555
        //Font-Color: #cccccc
        //inputs-Color: #444444
        //button-bg: #112244
        this.setContentPane(panel);
        this.setSize(400,300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //removing borders of inputs and buttons
        loginField.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));
        passwordField.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));
        emailField.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));
        roleBox.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));
        returnButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        submitButton.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        setBoxCellBorder(roleBox);

        //changing caret color
        loginField.setCaretColor(Color.white);
        passwordField.setCaretColor(Color.white);
        emailField.setCaretColor(Color.white);

        //Button listeners
        submitButton.addActionListener(e -> createUser());
        returnButton.addActionListener(e -> {
            new Main();
            this.dispose();
        });
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
}
