import com.example.mrm.conn;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Planifier_activite extends JFrame{
    private JPanel main_panel;
    private JLabel title_label;
    private JLabel intitutle_label;
    private JTextField intitule_tf;
    private JLabel desc_label;
    private JTextArea desc_textarea;
    private JLabel date_label;
    private JPanel debut_panel;
    private JButton planifierActiviteButton;
    private JButton retourButton;
    private JButton quitterButton;
    private JDateChooser debut = new JDateChooser();

    public Planifier_activite() {
        setTitle("Planification Activité");
        setContentPane(main_panel);
        setMinimumSize(new Dimension(450, 475));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        debut_panel.add(debut);
        debut.setDateFormatString("dd/MM/yyyy");
        setVisible(true);
        quitterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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
        planifierActiviteButton.addActionListener(new ActionListener() {
            //Date debut_sql = new Date(debut.getDate().getTime());
            @Override
            public void actionPerformed(ActionEvent e) {
                Date debut_sql = null;
                if (debut.getDate()!= null)
                {
                    debut_sql= new Date(debut.getDate().getTime());
                }
                else
                {
                    JOptionPane.showMessageDialog(Planifier_activite.this, "Veuillez sélectionner une date.");

                }
                Connection connexion = conn.connect();
                try
                {
                    Statement stmt = connexion.createStatement();
                    String query = "insert into activité (titre, description, date_activité, personnel ) values (?,?,?,?);"; // the query requires 2 parameters, later given -- avoid sql injection
                    try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
                        preparedStatement.setString(1, intitule_tf.getText());
                        preparedStatement.setString(2, desc_textarea.getText());
                        preparedStatement.setDate(3, debut_sql);
                        preparedStatement.setInt(4, model.getpersonnelConnecteId());
                        int rowsUpdated = preparedStatement.executeUpdate();

                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(Planifier_activite.this, "Activité créée avec succès!");
                            dispose();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    new Home();
                                }
                            });
                        } else {
                            JOptionPane.showMessageDialog(Planifier_activite.this, "Aucun enregistrement correspondant trouvé pour la mise à jour.");
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
    }

    public static void main(String[] args) {
        Planifier_activite pa = new Planifier_activite();
    }
}
