import com.example.mrm.conn;
import com.toedter.calendar.JDateChooser;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;

public class Ajout_Patient extends JFrame{
    private JPanel ajoutpanel;
    private JLabel title_label;
    private JPanel left_panel;
    private JPanel right_panel;
    private JTextField nom_tf;
    private JTextField prenom_tf;
    private JTextField num_tf;
    private JComboBox genreComboBox;
    private JLabel nom_label;
    private JLabel prenom_label;
    private JLabel genre_label;
    private JTextField com_tf;
    private JButton enregistrerButton;
    private JButton quitterButton;
    private JButton annulerButton;
    private JLabel num_label;
    private JLabel com_label;
    private JLabel ddn_label;
    private JPanel ddn_panel;
    private JLabel adm_label;
    private JPanel admission_panel;
    private JLabel sortie_label;
    private JPanel sortie_panel;
    private JLabel model_labelfield;
    private JLabel ajout_label;


    JDateChooser ddn_dateChooser = new JDateChooser();
    JDateChooser admission_dateChooser = new JDateChooser();
    JDateChooser sortie_dateChooser = new JDateChooser();
    public Ajout_Patient()
    {
        setTitle("Fiche Ajout Patient");
        setContentPane(ajoutpanel);
        setMinimumSize(new Dimension(450, 475));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ajout_label.setText("Ajout réalisé par :  "  + model.getpersonnelnom());
        genreComboBox.addItem("Femme");
        genreComboBox.addItem("Homme");
        // calendar
        ddn_panel.add(ddn_dateChooser);
        ddn_dateChooser.setDateFormatString("dd/MM/yyyy");
        admission_panel.add(admission_dateChooser);
        admission_dateChooser.setDateFormatString("dd/MM/yyyy");
        sortie_panel.add(sortie_dateChooser);
        sortie_dateChooser.setDateFormatString("dd/MM/yyyy");
        setVisible(true);
        quitterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        annulerButton.addActionListener(new ActionListener() {
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
        enregistrerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nom_tf.getText().isEmpty() && !prenom_tf.getText().isEmpty() && ddn_dateChooser.getDate()!=null && admission_dateChooser.getDate()!=null && !num_tf.getText().isEmpty() )
                {
                    //JOptionPane.showMessageDialog(Ajout_Patient.this, "Tous les champs sont remplis ! yay ! ");
                    java.util.Date ddnDate = ddn_dateChooser.getDate();
                    java.util.Date admissionDate = admission_dateChooser.getDate();
                    java.util.Date sortieDate = sortie_dateChooser.getDate();

                    // Convert java.util.Date to java.sql.Date
                    Date ddnSqlDate = new Date(ddnDate.getTime());
                    Date admissionSqlDate = new Date(admissionDate.getTime());
                    Date sortieSqlDate = (sortieDate != null) ? new Date(sortieDate.getTime()) : null;
                    insert(nom_tf.getText(), prenom_tf.getText(),ddnSqlDate,admissionSqlDate,Integer.parseInt(num_tf.getText()), sortieSqlDate, com_tf.getText(), genreComboBox.getSelectedItem().toString());

                }
                else
                {
                    JOptionPane.showMessageDialog(Ajout_Patient.this, "Veuillez Remplir les champs nécessaires. ");

                }
            }
        });
    }
    public void insert(String nom, String prenom, Date ddn, Date admission, int num, Date sortie, String comm, String genre)
    {
        Connection connexion = conn.connect();
        try
        {
            Statement stmt = connexion.createStatement();
            // prepared statement pour protéger contre les injections sql
            String sql = "INSERT INTO patient (nom, prenom, ddn, Genre, admission, sortie, num_chambre, commentaire, personnel) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

            try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, prenom);
                preparedStatement.setDate(3,ddn);
                preparedStatement.setString(4, genre);
                preparedStatement.setDate(5, admission);
                preparedStatement.setDate(6, sortie);
                preparedStatement.setInt(7, num);
                preparedStatement.setString(8, comm);
                preparedStatement.setInt(9, model.getpersonnelConnecteId());

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(Ajout_Patient.this, "Patient ajouté avec succés. ");

            }
            catch (SQLException e)
            {
                System.out.println(e);
            }

        } catch (SQLException e) {
                throw new RuntimeException(e);
            }


    }

    public static void main(String[] args) {
        Ajout_Patient ap = new Ajout_Patient();
    }
}
