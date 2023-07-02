public class Prescription extends Medicament {
    private String nomMedicament;
    private int doseTraitement;
    private int repetition;
    public Prescription(String nomMedicament,int doseTraitement,int repetition){
        super(nomMedicament, 0, 0, 0, 0);
        this.nomMedicament=nomMedicament;
        this.doseTraitement=doseTraitement;
        this.repetition=repetition;
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
