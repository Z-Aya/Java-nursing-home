public class model {
    private static Personnel personnelConnecte;
    private static Patient patient;
    public static Patient getPatient()  { return patient; }

    public static String getpatientnom() { return patient.nom;}
    public static int getpersonnelConnecteId() { return personnelConnecte.id_personnel;}
    public static void unsetpatient()
    {
        patient = null;
    }
    public static int getpatientid() { return patient.idPatient;}
    public static void setPatient(Patient patientt) {
        patient = patientt;
    }
    public static String getpatientprenom() { return patient.prenom;}
    public static Personnel getPersonnelConnecte() {
        return personnelConnecte;
    }
    public static String getpersonnelnom () { return personnelConnecte.nom; }
    public static void setPersonnelConnecte(Personnel personnel) {
        personnelConnecte = personnel;
    }
}
