import com.example.mrm.conn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JFrame {
    private JPanel Loginpanel;
    private JPasswordField ps_pwd;
    private JTextField tf_id;
    private JLabel label_id;
    private JLabel label_mdp;
    private JButton btn_submit;
    private JPanel rightpanel;
    private JPanel leftpanel;
    private JPanel rightpanel2;
    private JPanel toppanel;
    private JPanel bottompanel;
    private JLabel label1;
    private JLabel label2;


    public login()
{
    setTitle("login");
    setContentPane(Loginpanel);
    setMinimumSize(new Dimension(450, 475));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    btn_submit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //JOptionPane.showMessageDialog(login.this, "Succès");
            String nom =  tf_id.getText();
            //System.out.println("nom :  " + nom);
            String mdp = String.valueOf(ps_pwd.getPassword());
            //System.out.println("appel authentifier_personnel");
            personnel = authentifier_personnel(nom, mdp);

            if (personnel != null)
            {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Home();
                    }
                });
            }
            else
            {
                JOptionPane.showMessageDialog(login.this, "Identifiant ou mot de passe invalide.", "Réessayez",JOptionPane.ERROR_MESSAGE);

            }
        }
    });

    setVisible(true);
}
public Personnel personnel;
private Personnel authentifier_personnel(String nom, String mdp) // returns valid user if user exists in database
{
    Personnel personnel = null;
    Connection connexion = conn.connect();
    try
    {
        Statement stmt = connexion.createStatement();
        String query = "SELECT * FROM personnel WHERE nom=? AND mot_de_passe=?"; // the query requires 2 parameters, later given
        PreparedStatement prstmt = connexion.prepareStatement(query);
        prstmt.setString(1,nom); // on passe les paramètres
        prstmt.setString(2,mdp);

        ResultSet resultSet = prstmt.executeQuery();
        if (resultSet.next())
        {
            //System.out.println("trouvé");
            personnel = new Personnel();
            personnel.id_personnel= resultSet.getInt("id_personnel");
            personnel.nom=resultSet.getString("nom");
            personnel.prenom=resultSet.getString("prenom");
            personnel.ddn=resultSet.getDate("ddn");
            personnel.genre=resultSet.getString("genre");
            personnel.Telephone=resultSet.getString("téléphone");
            personnel.role=resultSet.getString("Role");
            personnel.embauche=resultSet.getDate("embauche");
            personnel.mot_de_passe=resultSet.getString("mot_de_passe");
            System.out.println("fct allocation personnel \n" + personnel.toString());
            model.setPersonnelConnecte(personnel);
        }
        stmt.close();
    }catch(Exception e)
    {
        System.out.println(e);
    }
    finally {
        conn.disconnect(connexion);
    }
  return personnel;
}

    public static void main(String[] args) {

        login l = new login();
        Personnel personnel = l.personnel;
        if (personnel != null)
        {
            System.out.println("Connexion réussie -- main : " + personnel.nom + " " + personnel.prenom);
        }
        else
        {
            System.out.println("Connexion a échoué -- main");
        }
    }

}
