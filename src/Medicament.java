
public class Medicament implements Comparable<Medicament> {
    public String nom;
    private int quantite;
    private Date dateExpiration;
    private Date dateCourante;

    private int quantiteCommande;


    public Medicament() {
    }
    public Medicament(String nom, int quantite, Date dateExpiration, Date dateCourante, int quantiteCommande) {}
    public Medicament(String nom, int quantite, Date dateExpiration) {
        this.nom = nom;
        this.quantite = quantite;
        this.dateExpiration = dateExpiration;
        this.dateCourante = dateCourante;
        this.quantiteCommande = quantiteCommande;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Date getDateCourante() {
        return dateCourante;
    }

    public void setDateCourante(Date dateCourante) {
        this.dateCourante = dateCourante;
    }

    @Override
    public int compareTo(Medicament other) {
        int nameComparison = getNom().compareTo(other.getNom());
        if (nameComparison != 0) {
            return nameComparison;
        }

        int expirationComparison = getDateExpiration().compareTo(other.getDateExpiration());
        if (expirationComparison != 0) {
            return expirationComparison;
        }

        int currentComparison = getDateCourante().compareTo(other.getDateCourante());
        if (currentComparison != 0) {
            return currentComparison;
        }

        return Integer.compare(getQuantiteCommande(), other.getQuantiteCommande());
    }

}