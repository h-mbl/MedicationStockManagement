import java.util.TreeSet;

public class Prescription extends Medicament {
    private String nomMedicament;
    private int doseTraitement;
    private int repetition;
    private TreeSet<Medicament> commande;

    public Prescription(){

    }
    public Prescription(String nom, int quantite, int aaaa, int mm, int jj, String nomMedicament, int doseTraitement, int repetition, TreeSet<Medicament> commande) {
        super(nom, quantite, aaaa, mm, jj);
        this.nomMedicament = nomMedicament;
        this.doseTraitement = doseTraitement;
        this.repetition = repetition;
        this.commande = commande;
    }

    public TreeSet<Medicament> getCommande() {
        return commande;
    }

    public void setCommande(TreeSet<Medicament> commande) {
        this.commande = commande;
    }

    public void setNomMedicament(String nomMedicament) {
        this.nomMedicament = nomMedicament;
    }
    public int getDoseTraitement() {
        return doseTraitement;
    }

    public int getRepetition() {
        return repetition;
    }

    public String getNomMedicament() {
        return nomMedicament;
    }
}
