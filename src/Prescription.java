public class Prescription  {
    private String nomMedicament;
    private int doseTraitement;
    private int repetition;
    public Prescription(String nomMedicament,int doseTraitement,int repetition){
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
