package service.swing;

import model.Item;
import model.Role;
import model.Store;
import model.User;
import repository.EmailRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Objects;

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

        JButton loginButton = buttonMaker("Log In","src/img/icons/delete.png" );
        loginButton.addActionListener(_ -> this.loginDisplay());
        JButton registerButton =buttonMaker("Register","src/img/icons/delete.png" );
        registerButton.addActionListener(_ -> this.registerDisplay());
        JButton exitButton = this.exitButton();

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
        setPlaceholder(emailTextField);
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
        logInButton.addActionListener(_ -> this.login(emailTextField.getText(),passwordTextField.getText()));
        JButton backButton = new JButton("<- Back");
        backButton.addActionListener(_ -> this.startDisplay());


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
            this.menuDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    private void registerDisplay(){
        createUserDisplay(false);
    }

    private void createUserDisplay(boolean accessedByAdmin) {
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
        setPlaceholder(emailTextField);
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

        JPanel rolePanel = null;
        JComboBox<Role> roleCombobox;
        if (accessedByAdmin) {
            rolePanel = new JPanel();
            rolePanel.setMaximumSize(this.subPanelSize);
            rolePanel.setLayout(new BoxLayout(rolePanel, BoxLayout.Y_AXIS));
            JLabel roleLabel = new JLabel("Role :");
            roleCombobox = new JComboBox<>(Role.values());
            roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            rolePanel.add(roleLabel);
            rolePanel.add(roleCombobox);
        } else {
            roleCombobox = null;
        }

        String buttonText = "Register";
        if (accessedByAdmin) buttonText = "Create User";
        JButton createButton = new JButton(buttonText);
        if (!accessedByAdmin) {
            createButton.addActionListener(_ -> this.register(usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText()));
        } else {
            createButton.addActionListener(_ -> this.createUser(usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText(),(Role) roleCombobox.getSelectedItem()));
        }

        JButton backButton = new JButton("<- Back");
        if(!accessedByAdmin) {
            backButton.addActionListener(_ -> this.startDisplay());
        } else {
            backButton.addActionListener(_ -> this.userMenuDisplay());
        }

        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (accessedByAdmin) rolePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(usernamePanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(emailPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(20));
        if(accessedByAdmin){
            panel.add(rolePanel);
            panel.add(Box.createVerticalStrut(20));
        }
        panel.add(createButton);
        panel.add(Box.createVerticalStrut(50));
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private void createUser(String username, String email, String password, Role role) {
        try{
            if(!EmailService.isWhitelisted(email)){
                int response = JOptionPane.showConfirmDialog(this,"Going forward will automatically whitelist that email","email whitelisting",JOptionPane.OK_CANCEL_OPTION);
                if(response==JOptionPane.CANCEL_OPTION){
                    return;
                }
                EmailService.whitelistEmail(email);
            }
            UserService.createUser(username,email, password,role);
            JOptionPane.showMessageDialog(this, "User created successfully ","Success",JOptionPane.INFORMATION_MESSAGE );
            this.userMenuDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    private void register(String username, String email, String password){
        try{
            this.user=UserService.createUser(username,email, password,Role.USER);
            this.menuDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    private void menuDisplay(){
        this.setSize(1000,1000);
        boolean isAdmin = this.user.getRole()==Role.ADMIN;
        boolean isEmployee = this.user.getRole()==Role.EMPLOYEE;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton itemMenuButton = null;
        JButton emailMenuButton = null;
        if(isAdmin) {
            itemMenuButton = buttonMaker("Item menu", "src/img/icons/delete.png");
            itemMenuButton.addActionListener(_ -> itemMenuDisplay());

            emailMenuButton = buttonMaker("Email menu", "src/img/icons/delete.png");
            emailMenuButton.addActionListener(_ -> emailMenuDisplay());
        }
        JButton userMenuButton = buttonMaker("User menu", "src/img/icons/delete.png");
        userMenuButton.addActionListener(_ -> userMenuDisplay());
        JButton profileButton = profileButton();
        JButton disconnectButton = this.buttonMaker("Disconnect","src/img/icons/profile.png");
        disconnectButton.addActionListener(_ -> this.startDisplay());
        JButton exitButton = this.exitButton();

        if (isAdmin) {
            itemMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            emailMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        userMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        disconnectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        if(isAdmin) {
            panel.add(itemMenuButton);
            panel.add(Box.createVerticalStrut(30));
            panel.add(emailMenuButton);
            panel.add(Box.createVerticalStrut(30));
        }
        panel.add(userMenuButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(profileButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(disconnectButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());

        this.setContentPane(panel);
        this.setVisible(true);
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
        validateButton.addActionListener(_ -> this.updateAccount(usernameTextField.getText(),emailTextField.getText(),passwordTextField.getText()));
        validateButton.setForeground(Color.BLACK);
        validateButton.setBackground(new Color(144, 238, 144));
        validateButton.setOpaque(true);

        JButton disconnectButton = this.buttonMaker("Disconnect","src/img/icons/profile.png");
        disconnectButton.addActionListener(_ -> this.startDisplay());

        JButton deleteButton = this.buttonMaker("Delete your Account","src/img/icons/delete.png");
        deleteButton.addActionListener(_ -> this.deleteAccount());
        deleteButton.setForeground(Color.GRAY);
        deleteButton.setBackground(new Color(139, 0, 0));
        deleteButton.setOpaque(true);

        JButton backButton = this.backToMenuButton();

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

    private void modifyUserDisplay(User userDisplayed){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setMaximumSize(this.subPanelSize);
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        JLabel usernameLabel = new JLabel("Username : ");
        JTextField usernameTextField = new JTextField();
        usernameTextField.setText(userDisplayed.getUsername());
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);

        JPanel emailPanel = new JPanel();
        emailPanel.setMaximumSize(this.subPanelSize);
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        JLabel emailLabel = new JLabel("Email : ");
        JTextField emailTextField = new JTextField();
        emailTextField.setText(userDisplayed.getEmail());
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

        JPanel rolePanel = new JPanel();
        rolePanel.setMaximumSize(this.subPanelSize);
        rolePanel.setLayout(new BoxLayout(rolePanel, BoxLayout.Y_AXIS));
        JLabel roleLabel = new JLabel("Role :");
        JComboBox<Role> roleCombobox = new JComboBox<>(Role.values());
        roleCombobox.setSelectedItem(userDisplayed.getRole());
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rolePanel.add(roleLabel);
        rolePanel.add(roleCombobox);

        JButton validateButton = this.buttonMaker("Validate changes","src/img/icons/delete.png");
        validateButton.addActionListener(_ -> this.modifyUser(usernameTextField.getText(),emailTextField.getText(),passwordTextField.getText(),(Role) roleCombobox.getSelectedItem(),userDisplayed));
        validateButton.setForeground(Color.BLACK);
        validateButton.setBackground(new Color(144, 238, 144));
        validateButton.setOpaque(true);

        JButton deleteButton = this.buttonMaker("Delete the Account","src/img/icons/delete.png");
        deleteButton.addActionListener(_ -> this.deleteUser(userDisplayed));
        deleteButton.setForeground(Color.GRAY);
        deleteButton.setBackground(new Color(139, 0, 0));
        deleteButton.setOpaque(true);

        JButton backButton = this.buttonMaker("Back","src/img/icons/profile.png");
        backButton.addActionListener(_ -> this.userMenuDisplay());

        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rolePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        validateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(usernamePanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(emailPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(roleCombobox);
        panel.add(Box.createVerticalStrut(20));
        panel.add(validateButton);
        panel.add(Box.createVerticalStrut(30));
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
            UserService.update(username,email,password,this.user);
            JOptionPane.showMessageDialog(this, "Account updated successfully ","Success",JOptionPane.INFORMATION_MESSAGE );
            this.menuDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }

    }

    private void userMenuDisplay(){
        ArrayList<User> users = this.getAllUsers();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(0, 1));

        boolean isAdmin = this.user.getRole()==Role.ADMIN;
        int colAmount;
        if(isAdmin){
            colAmount=5;
        }else {
            colAmount=3;
        }
        //title of the table
        JPanel row = new JPanel(new GridLayout(1, colAmount));
        row.add(new JLabel("Username", SwingConstants.CENTER));
        row.add(new JLabel("Mail Address", SwingConstants.CENTER));
        row.add(new JLabel("Role", SwingConstants.CENTER));
        if(isAdmin) {
            row.add(new JLabel("Modify User", SwingConstants.CENTER));
            row.add(new JLabel("Delete User", SwingConstants.CENTER));
        }
        tablePanel.add(row);
        tablePanel.add(new JSeparator(JSeparator.HORIZONTAL));

        if(users!=null) {
            for (User userSelected : users) {
                row = new JPanel(new GridLayout(1, colAmount));
                JLabel nameLabel = new JLabel(userSelected.getUsername());
                row.add(nameLabel);
                JLabel emailLabel = new JLabel(userSelected.getEmail());
                row.add(emailLabel);
                JLabel rolePLabel= new JLabel(String.valueOf(userSelected.getRole()));
                row.add(rolePLabel);
                if(isAdmin) {
                    JButton modifyButton = buttonMaker("Modify", "src/img/icons/delete.png");
                    modifyButton.addActionListener(_ -> this.modifyUserDisplay(userSelected));
                    row.add(modifyButton);
                    JButton deleteButton = buttonMaker("Delete", "src/img/icons/delete.png");
                    deleteButton.addActionListener(_ -> {
                        this.deleteUser(userSelected);
                        this.userMenuDisplay();
                    });
                    row.add(deleteButton);
                }
                tablePanel.add(row);
            }
        }
        JButton createButton = null;
        if(isAdmin) {
            createButton = this.buttonMaker("Create a new User", "src/img/icons/profile.png");
            createButton.addActionListener(_ -> this.createUserDisplay(true));
        }
        JButton backButton = this.backToMenuButton();

        if(isAdmin){
            createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(tablePanel);
        panel.add(Box.createVerticalStrut(20));
        if(isAdmin) {
            panel.add(createButton);
            panel.add(Box.createVerticalStrut(20));
        }
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private void emailMenuDisplay(){
        ArrayList<String> emails = this.getAllEmail();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(0, 1));

        //title of the table
        JPanel row = new JPanel(new GridLayout(1, 2));
        row.add(new JLabel("Mail address", SwingConstants.CENTER));
        row.add(new JLabel("Delete Email", SwingConstants.CENTER));
        tablePanel.add(row);
        tablePanel.add(new JSeparator(JSeparator.HORIZONTAL));

        if(emails!=null) {
            for (String email : emails) {
                row = new JPanel(new GridLayout(1, 2));
                JTextField nameTextField = this.createTextField(email);
                row.add(nameTextField);
                JButton deleteButton = buttonMaker("Delete","src/img/icons/delete.png");
                deleteButton.addActionListener(_ -> this.deleteEmail(email));
                row.add(deleteButton);

                tablePanel.add(row);
            }
        }
        JButton createButton = this.buttonMaker("Whitelist a new Email","src/img/icons/profile.png");
        createButton.addActionListener(_ -> this.displayWhitelistEmail()); //createItem

        JButton backButton = this.backToMenuButton();

        tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(tablePanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(createButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private void deleteEmail(String email) {
        try{
            EmailService.deleteEmail(email);
            this.emailMenuDisplay();
            JOptionPane.showMessageDialog(this, "Email deleted successfully ","Success",JOptionPane.INFORMATION_MESSAGE );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    private void displayWhitelistEmail(){
        JDialog whitelistEmailDialog = new JDialog(this,"Whitelist Email",true);
        whitelistEmailDialog.setSize(300,300);
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel,BoxLayout.Y_AXIS));

        JPanel emailPanel = new JPanel();
        emailPanel.setMaximumSize(new Dimension(200,75));
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.X_AXIS));

        JLabel emailLabel = new JLabel("Email : ");
        JTextField emailTextField = new JTextField();
        setPlaceholder(emailTextField);
        emailPanel.add(emailLabel);
        emailPanel.add(emailTextField);

        JButton validateButton = buttonMaker("Validate","src/img/icons/delete.png");
        validateButton.addActionListener(_ -> this.createEmail(emailTextField.getText(),whitelistEmailDialog) );

        JButton cancelButton = buttonMaker("Cancel","src/img/icons/delete.png");
        cancelButton.addActionListener(_ -> whitelistEmailDialog.dispose());

        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        validateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        dialogPanel.add(Box.createVerticalGlue());
        dialogPanel.add(emailPanel);
        dialogPanel.add(Box.createVerticalStrut(20));
        dialogPanel.add(validateButton);
        dialogPanel.add(Box.createVerticalStrut(30));
        dialogPanel.add(cancelButton);
        dialogPanel.add(Box.createVerticalGlue());

        whitelistEmailDialog.add(dialogPanel);
        whitelistEmailDialog.setLocationRelativeTo(this);
        whitelistEmailDialog.setVisible(true);
    }

    private void createEmail(String email, JDialog dialog) {
        try{
            EmailService.whitelistEmail(email);
            JOptionPane.showMessageDialog(this, "Email whitelisted successfully ","Success",JOptionPane.INFORMATION_MESSAGE );
            dialog.dispose();
            this.emailMenuDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    private void itemMenuDisplay(){
        ArrayList<Item> items = this.getAllItems();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(0, 1));

        //title of the table
        JPanel row = new JPanel(new GridLayout(1, 5));
        row.add(new JLabel("Product Name", SwingConstants.CENTER));
        row.add(new JLabel("Price", SwingConstants.CENTER));
        row.add(new JLabel("Validate Changes", SwingConstants.CENTER));
        row.add(new JLabel("Delete Item", SwingConstants.CENTER));
        row.add(new JLabel("View stores that use this item", SwingConstants.CENTER));
        tablePanel.add(row);
        tablePanel.add(new JSeparator(JSeparator.HORIZONTAL));

        if(items!=null) {
            for (Item item : items) {
                row = new JPanel(new GridLayout(1, 5));
                JTextField nameTextField = this.createTextField(item.getName());
                row.add(nameTextField);
                JSpinner priceSpinner = this.createPriceSpinner(item.getPrice());
                row.add(priceSpinner);
                row.add(this.createValidateItemButton(nameTextField, priceSpinner, item));
                row.add(this.createDeleteItemButton(item));
                row.add(this.createViewStoresFromItemButton(item));

                tablePanel.add(row);
            }
        }
        JButton createButton = this.buttonMaker("Create a new Item","src/img/icons/profile.png");
        createButton.addActionListener(_ -> this.displayCreateItem());

        JButton backButton = this.backToMenuButton();

        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(tablePanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(createButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private ArrayList<User> getAllUsers(){
        try {
            return UserService.getAllUsers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error has occurred", "Error", JOptionPane.ERROR_MESSAGE);
            this.menuDisplay();
        }
        return null;
    }

    private ArrayList<String> getAllEmail(){
        try {
            return EmailService.getAllEmail();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error has occurred", "Error", JOptionPane.ERROR_MESSAGE);
            this.menuDisplay();
        }
        return null;
    }

    private ArrayList<Item> getAllItems(){
        try {
            return ItemService.getAllItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error has occurred", "Error", JOptionPane.ERROR_MESSAGE);
            this.menuDisplay();
        }
        return null;
    }

    private void updateItem(String name, double price, Item item){
        try{
            ItemService.updateItem(name,price,item);
            JOptionPane.showMessageDialog(this, "Item updated successfully ","Success",JOptionPane.INFORMATION_MESSAGE );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
            this.itemMenuDisplay();
        }
    }

    private void deleteItem(Item item){
        try{
            ItemService.deleteItem(item);
            this.itemMenuDisplay();
            JOptionPane.showMessageDialog(this, "item deleted successfully ","Success",JOptionPane.INFORMATION_MESSAGE );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    private ArrayList<Store> getStoresFromItem(Item item){
        try{
            return ItemService.getStoresFromItem(item);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
        return null;
    }

    private void displayStoresFromItem(Item item){
        JDialog storeDialog = new JDialog(this,"Stores for item : "+item.getName(),true);
        storeDialog.setSize(300,200);
        storeDialog.setLayout(new BorderLayout());

        ArrayList<Store> stores = getStoresFromItem(item);
        if(stores==null){return;}
        Object[][] data = new Object[stores.size()][1];
        for(int i = 0; i<stores.size();i++){
            data[i][0] = stores.get(i).getName();
        }
        String[] columnNames = {"Store Names"};

        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        storeDialog.add(scrollPane,BorderLayout.CENTER);

        JButton closeButton = buttonMaker("Close", "src/img/icons/delete.png");
        closeButton.addActionListener(_ -> storeDialog.dispose());
        storeDialog.add(closeButton,BorderLayout.SOUTH);

        storeDialog.setLocationRelativeTo(this);
        storeDialog.setVisible(true);
    }

    private void displayCreateItem(){
        JDialog createItemDialog = new JDialog(this,"Create Item",true);
        createItemDialog.setSize(300,300);
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel,BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setMaximumSize(new Dimension(200,75));
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));

        JLabel nameLabel = new JLabel("Name : ");
        JTextField nameTextField = new JTextField();
        emptyListener(nameTextField);
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);

        JPanel pricePanel = new JPanel();
        pricePanel.setMaximumSize(new Dimension(200,75));
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));

        JLabel priceLabel = new JLabel("Price : ");
        JSpinner priceSpinner = createPriceSpinner(0.00);
        pricePanel.add(priceLabel);
        pricePanel.add(priceSpinner);

        JButton validateButton = buttonMaker("Validate","src/img/icons/delete.png");
        validateButton.addActionListener(_ -> this.createItem(nameTextField.getText(),(double) priceSpinner.getValue(),createItemDialog) );

        JButton cancelButton = buttonMaker("Cancel","src/img/icons/delete.png");
        cancelButton.addActionListener(_ -> createItemDialog.dispose());

        pricePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        validateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        dialogPanel.add(Box.createVerticalGlue());
        dialogPanel.add(namePanel);
        dialogPanel.add(Box.createVerticalStrut(20));
        dialogPanel.add(pricePanel);
        dialogPanel.add(Box.createVerticalStrut(20));
        dialogPanel.add(validateButton);
        dialogPanel.add(Box.createVerticalStrut(30));
        dialogPanel.add(cancelButton);
        dialogPanel.add(Box.createVerticalGlue());

        createItemDialog.add(dialogPanel);
        createItemDialog.setLocationRelativeTo(this);
        createItemDialog.setVisible(true);
    }

    private void createItem(String name, double price,JDialog dialog) {
        try{
            ItemService.createItem(name,price);
            JOptionPane.showMessageDialog(this, "Item created successfully ","Success",JOptionPane.INFORMATION_MESSAGE );
            dialog.dispose();
            this.itemMenuDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
        }
    }

    private void deleteUser(User userDeleted){
        UserService.deleteUser(userDeleted);
        JOptionPane.showMessageDialog(this, "User deleted successfully ","Success",JOptionPane.INFORMATION_MESSAGE );
    }

    private void modifyUser(String username, String email, String password, Role role, User userModified){
        String oldEmail = userModified.getEmail();
        Role oldRole = userModified.getRole();
        EmailService.changeEmail(oldEmail,email);
        boolean success = updateUser(username,email,password,role,userModified);
        if(oldRole!=role && oldRole==Role.EMPLOYEE && success){
            int response = JOptionPane.showConfirmDialog(this, "Do you want to remove the shops linked to that account", "Modifying role", JOptionPane.YES_NO_OPTION);
            if(response==JOptionPane.YES_OPTION){
                UserService.removeStores(userModified);
            }
        } else if (oldRole!=role && role==Role.EMPLOYEE && UserService.getAmountStore(userModified)==0 && success) {
            int response = JOptionPane.showConfirmDialog(this, "Do you want to link a shop to that employee", "Modifying role", JOptionPane.YES_NO_OPTION);
            if(response==JOptionPane.YES_OPTION){
                this.dialogAddStore(userModified);
            }
        }
        JOptionPane.showMessageDialog(this, "User updated successfully ","Success",JOptionPane.INFORMATION_MESSAGE );
    }

    private void dialogAddStore(User userModified){
        ArrayList<Store> stores = StoreService.getAllStores();
        if(stores==null){
            JOptionPane.showMessageDialog(this, "There are no Stores in the database","Error",JOptionPane.ERROR_MESSAGE );
            return;
        }
        JDialog dialog = new JDialog(this, "Choose a Store", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());

        String[] options = new String[stores.size()];
        for(int i = 0; i<stores.size();i++){
            options[i]=stores.get(i).getName();
        }
        JComboBox<String> comboBox = new JComboBox<>(options);
        dialog.add(comboBox);

        JButton confirmButton = new JButton("OK");
        confirmButton.addActionListener(_ -> {
            StoreService.addStoreToEmployee(userModified,(String) comboBox.getSelectedItem());
            dialog.dispose();
        });
        dialog.add(confirmButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(_ -> dialog.dispose());
        dialog.add(closeButton);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private boolean updateUser(String username, String email, String password, Role role, User userModified){
        try{
            UserService.adminUpdate(username,email,password,role,userModified);
            return true;
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE );
            return false;
        }
    }

    //methods to create objects that come often
    private JButton buttonMaker(String text, String filename){
        ImageIcon originalIcon = new ImageIcon(filename);
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JButton button = new JButton(text,resizedIcon);
        button.setPreferredSize(new Dimension(80,60));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        return button;
    }

    private JButton profileButton(){
        JButton profileButton = this.buttonMaker("Profile","src/img/icons/profile.png");
        profileButton.addActionListener(_ -> this.profileDisplay());
        return profileButton;
    }

    private JButton exitButton(){
        JButton exitButton = buttonMaker("Exit","src/img/icons/delete.png" );
        exitButton.addActionListener(_ -> System.exit(0));
        return exitButton;
    }

    private JButton backToMenuButton(){
        JButton backButton = this.buttonMaker("Back","src/img/icons/profile.png");
        backButton.addActionListener(_ -> this.menuDisplay());
        return backButton;
    }

    private JTextField createTextField(String defaultText){
        JTextField textField = new JTextField();
        textField.setText(defaultText);
        textField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(defaultText);
                }
            }
        });
        return textField;
    }

    private JSpinner createPriceSpinner(double price){
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(price,0.00,1000000.00,0.10));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(priceSpinner,"0.00 €");
        priceSpinner.setEditor(editor);
        return priceSpinner;
    }

    private JButton createValidateItemButton(JTextField nameField, JSpinner priceSpinner, Item item){
        JButton validateButton = buttonMaker("Validate", "src/img/icons/delete.png");
        validateButton.setForeground(Color.BLACK);
        validateButton.setBackground(new Color(144, 238, 144));
        validateButton.setOpaque(true);
        validateButton.setBorderPainted(false);
        validateButton.addActionListener(_ -> {
            String name = nameField.getText();
            double price = (double) priceSpinner.getValue();
            this.updateItem(name,price,item);
        });
        return validateButton;
    }

    private JButton createDeleteItemButton(Item item){
        JButton deleteButton = buttonMaker("Delete", "src/img/icons/delete.png");
        deleteButton.setForeground(Color.GRAY);
        deleteButton.setBackground(new Color(139, 0, 0));
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);
        deleteButton.addActionListener(_ -> this.deleteItem(item));
        return deleteButton;
    }

    private JButton createViewStoresFromItemButton(Item item){
        JButton viewButton = buttonMaker("View","src/img/icons/delete.png");
        viewButton.addActionListener(_ -> displayStoresFromItem(item));
        return viewButton;
    }

    //listeners methods
    private static void setPlaceholder(JTextField textField) {
        textField.setText("example@example.com");
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (textField.getText().equals("example@example.com")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    textField.setBorder(UIManager.getBorder("TextField.border"));
                }
            }
            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText("example@example.com");
                    textField.setForeground(Color.GRAY);
                    textField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
            }
        });
    }

    private static void emptyListener(JTextField textField){
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                textField.setBorder(UIManager.getBorder("TextField.border"));
            }
            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
            }
        });
    }
}