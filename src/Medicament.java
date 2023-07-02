public class Medicament {
    public String nom;
    private int quantite;
    private int aaaa;
    private int mm;
    private int jj;

    public Medicament(String nom, int quantite, int aaaa, int mm, int jj) {
        this.nom = nom;
        this.quantite = quantite;
        this.aaaa = aaaa;
        this.mm = mm;
        this.jj = jj;
    }


    // Getters et setters (méthodes d'accès) pour les attributs

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

}

