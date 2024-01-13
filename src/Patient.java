import java.util.Date;
public class Patient {
    public int idPatient;
    public String nom;
    public String prenom;
    public Date dateNaissance;
    public String genre;
    public Date admission;
    public Date sortie;
    public int numChambre;
    public String commentaire;

    @Override
    public String toString() {
        return "Patient{" +
                "idPatient=" + idPatient +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", genre='" + genre + '\'' +
                ", admission=" + admission +
                ", sortie=" + sortie +
                ", numChambre=" + numChambre +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }

    public String getNom() {
        return nom;
    }
}
