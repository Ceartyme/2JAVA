package service.swing;

import model.User;

import javax.swing.*;
import java.awt.*;

public class SwingService extends JFrame {
    private User user;
    private final Dimension subPanelSize = new Dimension(300, 60);

    public SwingService(){
        this.setSize(600,400);
        this.setResizable(false);
        this.setTitle("IStore");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.startPanel();
        this.setVisible(true);
    }

    private void startPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(e -> this.loginDisplay());
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));


        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(registerButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private void loginDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JPanel emailPanel = new JPanel();
        emailPanel.setMaximumSize(this.subPanelSize);
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));

        JLabel emailLabel = new JLabel("Email : ");
        JTextField emailTextField = new JTextField();
        setPlaceholder(emailTextField,"example@example.com");
        emailPanel.add(emailLabel);
        emailPanel.add(emailTextField);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setMaximumSize(this.subPanelSize);
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));

        JLabel passwordLabel = new JLabel("Password : ");
        JTextField passwordTextField = new JTextField();
        emptyListener(passwordTextField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTextField);

        JButton logInButton = new JButton("Log In");
        logInButton.addActionListener(e -> this.login(emailTextField.getText(),passwordTextField.getText()));
        JButton backButton = new JButton("<- Back");
        backButton.addActionListener(e -> this.startPanel());


        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(emailPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(logInButton);
        panel.add(Box.createVerticalStrut(50));
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private void login(String email, String password){
        try{
            this.user=UserService.login(email, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
    }


    //listeners methods
    private static void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    textField.setBorder(UIManager.getBorder("TextField.border"));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    textField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
            }
        });
    }

    private static void emptyListener(JTextField textField){
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(UIManager.getBorder("TextField.border"));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
            }
        });
    }
}
