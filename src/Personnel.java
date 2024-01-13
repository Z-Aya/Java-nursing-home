import java.util.Date;

public class Personnel {
    public int id_personnel;
    public String nom;
    public String prenom;
    public Date ddn;
    public String genre; // look into enum
    public String Telephone;
    public String role; // look into enum
    public Date embauche;
    public String mot_de_passe;

    @Override
    public String toString() {
        return "Personnel{" +
                "id_personnel=" + id_personnel +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", ddn=" + ddn +
                ", genre='" + genre + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", role='" + role + '\'' +
                ", embauche=" + embauche +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                '}';
    }
}
