package service.swing;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingService extends JFrame {
    private User user;
    private final Dimension subPanelSize = new Dimension(300, 75);

    public SwingService(){

        this.setResizable(false);
        this.setTitle("IStore");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.startDisplay();
        this.setVisible(true);
    }

    private void startDisplay(){
        this.setSize(600,400);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(e -> this.loginDisplay());
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> this.registerDisplay());
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
        backButton.addActionListener(e -> this.startDisplay());


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
            this.menuDispatch();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    private void registerDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setMaximumSize(this.subPanelSize);
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Username : ");
        JTextField usernameTextField = new JTextField();
        emptyListener(usernameTextField);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);

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

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> this.register(usernameTextField.getText(),emailTextField.getText(),passwordTextField.getText()));
        JButton backButton = new JButton("<- Back");
        backButton.addActionListener(e -> this.startDisplay());

        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(usernamePanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(emailPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(registerButton);
        panel.add(Box.createVerticalStrut(50));
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private void register(String username, String email, String password){
        try{
            this.user=UserService.register(username,email, password);
            this.menuDispatch();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    private void menuDispatch(){
        this.setSize(850,700);
        switch (this.user.getRole()){
            case USER -> menuUserDisplay();
            case ADMIN -> menuAdminDisplay();
            case EMPLOYEE -> menuEmployeeDisplay();
        }
    }

    private void menuUserDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JButton button = profileButton();


        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());

        panel.add(Box.createVerticalStrut(30));
        panel.add(button);
        panel.add(Box.createVerticalGlue());

        this.setContentPane(panel);
        this.setVisible(true);
    }
    private void menuAdminDisplay(){
        System.out.println("admin");
    }
    private void menuEmployeeDisplay(){
        System.out.println("employee");
    }

    private void profileDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setMaximumSize(this.subPanelSize);
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        JLabel usernameLabel = new JLabel("Username : ");
        JTextField usernameTextField = new JTextField();
        usernameTextField.setText(this.user.getUsername());
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);

        JPanel emailPanel = new JPanel();
        emailPanel.setMaximumSize(this.subPanelSize);
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        JLabel emailLabel = new JLabel("Email : ");
        JTextField emailTextField = new JTextField();
        emailTextField.setText(this.user.getEmail());
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailPanel.add(emailLabel);
        emailPanel.add(emailTextField);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setMaximumSize(this.subPanelSize);
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        JLabel passwordLabel = new JLabel("Password :");
        JLabel passwordLabel2 = new JLabel("(if empty your password won't get changed)");
        JTextField passwordTextField = new JTextField();
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordLabel2);
        passwordPanel.add(passwordTextField);

        JButton validateButton = this.buttonMaker("Validate changes","src/img/icons/delete.png");
        validateButton.addActionListener(e -> this.updateAccount(usernameTextField.getText(),emailTextField.getText(),passwordTextField.getText()));
        validateButton.setForeground(Color.BLACK);
        validateButton.setBackground(new Color(144, 238, 144));
        validateButton.setOpaque(true);
        validateButton.setBorderPainted(false);

        JButton disconnectButton = this.buttonMaker("Disconnect","src/img/icons/profile.png");
        disconnectButton.addActionListener(e -> this.startDisplay());

        JButton deleteButton = this.buttonMaker("Delete your Account","src/img/icons/delete.png");
        deleteButton.addActionListener(e -> this.deleteAccount());
        deleteButton.setForeground(Color.GRAY);
        deleteButton.setBackground(new Color(139, 0, 0));
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);

        JButton backButton = buttonMaker("Back","src/img/icons/profile.png");
        backButton.addActionListener(e -> this.menuDispatch());

        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        disconnectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        validateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(usernamePanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(emailPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(validateButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(disconnectButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(deleteButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private void deleteAccount(){
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account.\nAfter doing so you'll be disconnected and you will never be able to access your account", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            UserService.deleteUser(this.user);
            JOptionPane.showMessageDialog(this, "Your account has been deleted");
            this.startDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Action cancelled");
        }
    }

    private void updateAccount(String username, String email, String password){
        try{
            this.user = UserService.update(username,email,password,this.user);
            this.menuDispatch();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }

    }

    //methods to create objects that come often
    private JButton buttonMaker(String text, String filename){
        ImageIcon originalIcon = new ImageIcon(filename);
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JButton profileButton = new JButton(text,resizedIcon);
        profileButton.setPreferredSize(new Dimension(80,60));
        profileButton.setFont(new Font("Arial", Font.BOLD, 18));

        profileButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        profileButton.setVerticalTextPosition(SwingConstants.CENTER);

        return profileButton;
    }

    private JButton profileButton(){
        JButton profileButton = this.buttonMaker("Profile","src/img/icons/profile.png");
        profileButton.addActionListener(e -> this.profileDisplay());
        return profileButton;
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