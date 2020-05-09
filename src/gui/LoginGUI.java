package gui;

import db.LibrarianDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI implements ActionListener {

    private static JLabel gebruikersLlabel;
    private static JLabel wachtwoordLabel;
    private static JTextField gebruiker;
    private static JPasswordField wachtwoord;
    private static JButton button;
    private static JLabel succes;
    private static JPanel panel;
    private static JFrame frame;


    public static void showLoginScreen() {
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(400,250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel);

        panel.setLayout(null);

        gebruikersLlabel = new JLabel("Gebruikersnaam?");
        gebruikersLlabel.setBounds(10,20,150,25);
        panel.add(gebruikersLlabel);

        wachtwoordLabel = new JLabel("wachtwoord?");
        wachtwoordLabel.setBounds(10,50,150,25);
        panel.add(wachtwoordLabel);

        gebruiker = new JTextField(20);
        gebruiker.setBounds(150,20,165,25);
        panel.add(gebruiker);

        wachtwoord = new JPasswordField();
        wachtwoord.setBounds(150,50,165,25);
        panel.add(wachtwoord);

        button = new JButton("Aanmelden");
        button.setBounds(10,80,125,25);
        button.addActionListener(new LoginGUI());
        panel.add(button);

        succes = new JLabel("");
        succes.setBounds(10,110,300,25);
        panel.add(succes);


        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = gebruiker.getText();
        String password = wachtwoord.getText();

        LibrarianDAO ldao = new LibrarianDAO();

        if (ldao.verifyUserPassword(user, password)) {
            succes.setText("Aanmelden succesvol!");
        } else
            succes.setText("Foutieve gebruikersnaam of wachtwoord!");
    }
}
