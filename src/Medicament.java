public class Medicament implements Comparable<Medicament> {
    public String nom;
    private int quantite;
    private int aaaa;
    private int mm;
    private int jj;
    private int quantiteCommande;

    public Medicament(String nom, int quantite, int aaaa, int mm, int jj) {
        this.nom = nom;
        this.quantite = quantite;
        this.aaaa = aaaa;
        this.mm = mm;
        this.jj = jj;
    }


    // Getters et setters (méthodes d'accès) pour les attributs

    public int getQuantiteCommande() {
        return quantiteCommande;
    }

    public void setQuantiteCommande(int quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }


    public int getAaaa() {
        return aaaa;
    }



    public int getMm() {
        return mm;
    }


    public int getJj() {
        return jj;
    }
    @Override
    public int compareTo(Medicament other) {
        int nameComparison = getNom().compareTo(other.getNom());
        if (nameComparison != 0) {
            return nameComparison;
        }

        int yearComparison = Integer.compare(getAaaa(), other.getAaaa());
        if (yearComparison != 0) {
            return yearComparison;
        }

        int monthComparison = Integer.compare(getMm(), other.getMm());
        if (monthComparison != 0) {
            return monthComparison;
        }

        return Integer.compare(getJj(), other.getJj());
    }

}