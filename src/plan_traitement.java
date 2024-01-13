import com.example.mrm.conn;
import com.toedter.calendar.JDateChooser;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class plan_traitement extends JFrame{
    private JPanel main_panel;
    private JLabel title_label;
    private JPanel saisie_panel;
    private JPanel btn_panel;
    private JPanel table_panel;
    private JLabel Traitement_label;
    private JTextField traitement_tf;
    private JLabel patient_label;
    private JLabel debut_label;
    private JPanel debut_panel;
    private JLabel fin_label;
    private JPanel fin_panel;
    private JButton add_btn;
    private JButton quitterButton;
    private JButton annulerButton;
    private JPanel right_panel;
    private JTable traitements;
    private JLabel patient_tf;
    private JScrollPane table_1;
    private JDateChooser debut_chooser = new JDateChooser(), fin_chooser = new JDateChooser();

    public plan_traitement()
    {
        setTitle("Consulter Fiche Patient");
        setContentPane(main_panel);
        debut_chooser = new JDateChooser();
        debut_panel.add(debut_chooser);
        debut_chooser.setDateFormatString("dd/MM/yyyy");
        fin_chooser = new JDateChooser();
        fin_panel.add(fin_chooser);
        patient_tf.setText(String.valueOf(model.getpatientid()));
        fin_chooser.setDateFormatString("dd/MM/yyyy");
        setMinimumSize(new Dimension(800, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        getElements();
        title_label.setText("Informations de traitements de patient : "+ model.getpatientnom() + " " + model.getpatientprenom());
        add_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String traitement = traitement_tf.getText();
                int patient = model.getpatientid();
                java.util.Date debut = debut_chooser.getDate();
                java.sql.Date debut_sql = new java.sql.Date(debut.getTime());
                java.util.Date fin = fin_chooser.getDate();
                java.sql.Date fin_sql = new java.sql.Date(fin.getTime());

                Connection connexion = conn.connect();
                try
                {
                    Statement stmt = connexion.createStatement();
                    String query = "insert into plan_traitement (traitement, patient, debut, fin ) values (?,?,?,?);"; // the query requires 2 parameters, later given -- avoid sql injection
                    try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
                        preparedStatement.setString(1, traitement);
                        preparedStatement.setInt(2, patient);
                        preparedStatement.setDate(3, debut_sql);
                        preparedStatement.setDate(4,fin_sql);
                        int rowsUpdated = preparedStatement.executeUpdate();

                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(plan_traitement.this, "plan_traitement créée avec succès!");
                            getElements();
                        } else {
                            JOptionPane.showMessageDialog(plan_traitement.this, "Aucun enregistrement correspondant trouvé pour la mise à jour.");
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
        quitterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Consulter_patient();
                    }
                });
            }
        });
    }

    public void getElements()
    {
        Connection connexion = conn.connect();
        try
        {
            Statement stmt = connexion.createStatement();
            String query = "select * from plan_traitement where patient = " + model.getpatientid() + ";";
            System.out.println(query);
            Statement statement= connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            traitements.setModel(DbUtils.resultSetToTableModel(resultSet));
            stmt.close();
        }catch(Exception e)
        {
            e.printStackTrace();        }
        finally {
            conn.disconnect(connexion);
        }
    }
    public static void main(String[] args) {
        plan_traitement pt = new plan_traitement();
    }
}
