import com.example.mrm.conn;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Consulter_patient extends JFrame{
    private JLabel title_label;
    private JTextField nom_rech_tf;
    private JTextField prenom_rech_tf;
    private JLabel nom_rech_label;
    private JLabel prenom_rech_label;
    private JPanel right_panel;
    private JLabel nom_label;
    private JTextField nom_tf;
    private JLabel prenom_label;
    private JTextField prenom_tf;
    private JComboBox genreComboBox;
    private JTextField num_tf;
    private JLabel genre_label;
    private JLabel num_label;
    private JLabel com_label;
    private JTextField com_tf;
    private JLabel ddn_label;
    private JPanel ddn_panel;
    private JLabel adm_label;
    private JPanel admission_panel;
    private JLabel sortie_label;
    private JPanel sortie_panel;
    private JButton btn_modifier;
    private JButton btn_del;
    private JPanel display_panel;
    private JButton btn_plansoins;
    private JButton rechercherButton;
    private JPanel main_panel;
    private JLabel id_label;
    private JLabel id_tf;
    private JButton quitterButton;
    private JButton retourButton;
    private JPanel bottom_panel;

    JDateChooser ddn_dateChooser = new JDateChooser();
    JDateChooser admission_dateChooser = new JDateChooser();
    JDateChooser sortie_dateChooser = new JDateChooser();
    public Consulter_patient()
    {
        Patient patient = new Patient();
        setTitle("Consulter Fiche Patient");
        setContentPane(main_panel);
        setMinimumSize(new Dimension(800, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ddn_panel.add(ddn_dateChooser);
        ddn_dateChooser.setDateFormatString("dd/MM/yyyy");
        admission_panel.add(admission_dateChooser);
        admission_dateChooser.setDateFormatString("dd/MM/yyyy");
        sortie_panel.add(sortie_dateChooser);
        sortie_dateChooser.setDateFormatString("dd/MM/yyyy");
        genreComboBox.addItem("Femme");
        genreComboBox.addItem("Homme");
        retourButton.setVisible(true);
        setVisible(true);
        display_panel.setVisible(false);
        bottom_panel.setVisible(false);
        rechercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nom_rech_tf.getText().isEmpty() && !prenom_rech_tf.getText().isEmpty())
                {
                    //JOptionPane.showMessageDialog(Consulter_patient.this, "Tous les champs sont remplis ! yay ! ");
                    Patient patient = recherchepatient(nom_rech_tf.getText(),prenom_rech_tf.getText());
                    if (patient != null)
                    {
                        JOptionPane.showMessageDialog(Consulter_patient.this, "patient trouve : " + patient.nom);
                        id_tf.setText(String.valueOf(patient.idPatient));
                        nom_tf.setText(patient.nom);
                        prenom_tf.setText(patient.prenom);
                        ddn_dateChooser.setDate(patient.dateNaissance);
                        genreComboBox.setSelectedItem(patient.genre);
                        admission_dateChooser.setDate(patient.admission);
                        sortie_dateChooser.setDate(patient.sortie);
                        num_tf.setText(String.valueOf(patient.numChambre));
                        com_tf.setText(patient.commentaire);
                        display_panel.setVisible(true);
                        bottom_panel.setVisible(true);
                    }
                    else
                        JOptionPane.showMessageDialog(Consulter_patient.this, "Patient introuvable. Vérfiez votre saisie.");



                }
                else {
                    JOptionPane.showMessageDialog(Consulter_patient.this, "Veuillez Remplir tous les champs. ");

                }
            }
        });
        btn_modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date ddn_sql = new Date(ddn_dateChooser.getDate().getTime());
                Date admission_sql = new Date(admission_dateChooser.getDate().getTime());
                Date sortie_sql = new Date(sortie_dateChooser.getDate().getTime());
                insert(Integer.parseInt(id_tf.getText()), nom_tf.getText(), prenom_tf.getText(), ddn_sql,genreComboBox.getSelectedItem().toString(), admission_sql ,sortie_sql, Integer.parseInt(num_tf.getText()), com_tf.getText());
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
        btn_del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connexion = conn.connect();
                try
                {
                    Statement stmt = connexion.createStatement();
                    stmt.executeUpdate( "update patient set status =\"archivé\" where id_patient = " + Integer.parseInt(id_tf.getText()) + ";");// the query requires 2 parameters, later given -- avoid sql injection
                    model.unsetpatient();
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new Home();
                        }
                    });

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        quitterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btn_plansoins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new plan_traitement();
                        }
                    });

            }
        });
    }
    Patient patient = new Patient();
    public void insert(int id, String nom, String prenom, Date ddn, String genre, Date admission, Date sortie, int num, String com)
    {
        Connection connexion = conn.connect();
        try {
            String query = "UPDATE patient SET nom = ?, prenom = ?, ddn = ?, Genre = ?, admission = ?, sortie = ?, num_chambre = ?, commentaire = ? WHERE id_patient = " +id;

            try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, prenom);
                preparedStatement.setDate(3, ddn);
                preparedStatement.setString(4, genre);
                preparedStatement.setDate(5, admission);
                preparedStatement.setDate(6, sortie);
                preparedStatement.setInt(7, num);
                preparedStatement.setString(8, com);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    patient.nom=nom;
                    patient.prenom=prenom;
                    patient.dateNaissance=ddn;
                    patient.genre=genre;
                    patient.admission=admission;
                    patient.sortie=sortie;
                    patient.numChambre=num;
                    patient.commentaire=com;
                    model.setPatient(patient);
                    System.out.println("model updated : " + model.getPatient());
                    JOptionPane.showMessageDialog(Consulter_patient.this, "Patient modifié avec succès!");
                } else {
                    JOptionPane.showMessageDialog(Consulter_patient.this, "Aucun enregistrement correspondant trouvé pour la mise à jour.");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } finally {
            conn.disconnect(connexion);
        }


    }
    public Patient recherchepatient(String nom, String prenom)
    {
        Connection connexion = conn.connect();
        patient = null;
        try
        {
            Statement stmt = connexion.createStatement();
            String query = "SELECT * FROM patient WHERE nom like \""+ nom +"\" AND prenom like \"" + prenom + "\" and status = \"actif\";"; // the query requires 2 parameters, later given -- avoid sql injection
            System.out.println(query);
            Statement statement= connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next())
            {
                patient = new Patient();
                System.out.println("trouvé");
                JOptionPane.showMessageDialog(Consulter_patient.this, "Trouvé");
                patient.idPatient= resultSet.getInt("id_patient");
                patient.nom=resultSet.getString("nom");
                patient.prenom=resultSet.getString("prenom");
                patient.dateNaissance=resultSet.getDate("ddn");
                patient.genre=resultSet.getString("Genre");
                patient.admission=resultSet.getDate("admission");
                patient.sortie=resultSet.getDate("sortie");
                patient.numChambre=resultSet.getInt("num_chambre");
                patient.commentaire=resultSet.getString("commentaire");
                System.out.println("fct  patient \n" + patient.toString());
                model.setPatient(patient);
                System.out.println("model set : " + model.getPatient());
            }
            else
                System.out.println("Patient pas trouvé.");
            stmt.close();
        }catch(Exception e)
        {
            e.printStackTrace();        }
        finally {
            conn.disconnect(connexion);
        }
        return patient;
    }
    public static void main(String[] args) {
        Consulter_patient cp = new Consulter_patient();
    }
}
