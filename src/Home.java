import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame{
    private JPanel homepanel;
    private JPanel rightpanel;
    private JPanel leftpanel;
    private JLabel label_bjr;
    private JLabel name_label;
    private JButton btn_quitter;
    private JButton btn_nvpatient;
    private JButton btn_consulterpatient;
    private JButton btn_activite;
    private JButton ajouterTraitementButton;

    public Home() {
        setTitle("home");
        setContentPane(homepanel);
        setMinimumSize(new Dimension(450, 475));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        name_label.setText("admin"/*model.getpersonnelnom()*/);
        btn_quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new login();
                    }
                });
            }
        });
        btn_nvpatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Ajout_Patient();
                    }
                });
            }
        });
        btn_consulterpatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Consulter_patient();
                    }
                });

            }
        });
        ajouterTraitementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Ajout_traitement();
                    }
                });
            }
        });
        btn_activite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Planifier_activite();
                    }
                });
            }
        });
    }
    public static void main(String[] args) {

        Home home = new Home();
    }
}
