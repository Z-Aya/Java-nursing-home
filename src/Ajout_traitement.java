import com.example.mrm.conn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Ajout_traitement extends JFrame{
    private JPanel main_panel;
    private JLabel title_label;
    private JLabel nom_label;
    private JTextField traitement_tf;
    private JLabel desc_label;
    private JTextArea desc_textarea;
    private JButton ajout_btn;
    private JButton retourButton;

    public Ajout_traitement() {
        setTitle("Ajouter Traitement A La Base De Données");
        setContentPane(main_panel);
        setMinimumSize(new Dimension(450, 475));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        ajout_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connexion = conn.connect();
                try
                {
                    Statement stmt = connexion.createStatement();
                    String query = "insert into traitement (nom, description ) values (?,?);"; // the query requires 2 parameters, later given -- avoid sql injection
                    try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
                        preparedStatement.setString(1, traitement_tf.getText());
                        preparedStatement.setString(2, desc_textarea.getText());
                        int rowsUpdated = preparedStatement.executeUpdate();

                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(Ajout_traitement.this, "Traitement créé avec succès!");
                            dispose();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    new Home();
                                }
                            });
                        } else {
                            JOptionPane.showMessageDialog(Ajout_traitement.this, "Aucun enregistrement correspondant trouvé pour la mise à jour.");
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                    finally {
                        conn.disconnect(connexion);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }



});
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Home();
                    }
                });
            }
        });
    }
    public static void main(String[] args) {
        Ajout_traitement at = new Ajout_traitement();
        at.setVisible(true);
    }}