import java.io.*;
//import java.lang.foreign.ValueLayout;
import java.util.*;
import java.util.Date;


public class Tp2 {
    /*
    private static String nomMedicament;
    private static int quantite;
    private static int aaaa;
    private static int mm;
    private static int jj;
    private static int doseTraitement;
    private static int repetition;

     */
    //cette liste enregistrera l'ordre des actions à effectuer
    /*
    private static Queue<Object> fileOperation= new LinkedList<>();
    private static Map<String, Medicament> treeMap = new TreeMap<>();
    private static TreeSet<Medicament> treeSet = new TreeSet<>();
     */
    private static TreeSet<Medicament> treeSet = new TreeSet<>();
    public static  Date dateCourante = new Date(2000,01,01);
    // public static String[] parties;
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("provide input or output file names.");
            return;
        }
        String inputFile = "testCode/" + args[0];
        String outputFile = "testCode/" + args[1];
        try (FileReader fileReader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String commande = line.split(" ")[0];
                switch (commande){
                    case "DATE":
                        String dateLine = readDate(line);
                        String[] date = dateLine.split(" ");
                        dateCourante = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                        dateCourante = date(dateCourante, treeSet, outputFile);
                        break;
                    case "PRESCRIPTION":
                        //readPrecription
                        //prescription(String nomMedicament, int doseTraitement,
                        // int repetition, TreeSet<Medicament> stock, TreeSet<Medicament> commande, File file)
                        //System.out.println("Prescription commande");

                        break;
                    case "APPROV":
                        //System.out.println("Approv commande");
                        break;
                    case "STOCK":
                        //System.out.println("Stock commande");
                        break;
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    //fonction pour lire la date
    public static String readDate (String line) {
        line = line.replace("-", " ");
        line = line.replace("DATE", "");
        line = line.replace(";", "");
        line = line.trim().replaceAll("\\s+", " ");
        // String[] parties = line.split(" ");
        return line;
    }
    //fonction pour lire la prescription
    public static List<Prescription> readPrescription (BufferedReader bufferedReader, String line) throws IOException {
        List<Prescription> prescriptionsList = new ArrayList<>();
        while ((line = bufferedReader.readLine()).equals(";")) {
            String[] prescriptionLine = line.split(" ");
            prescriptionsList.add(new Prescription(prescriptionLine[0], Integer.parseInt(prescriptionLine[1]), Integer.parseInt(prescriptionLine[2])));
        }

        return prescriptionsList;
    }
    public static boolean datePasse(Date dateAVerifie, Date dateCourante ){
        if (dateAVerifie.getYear() < dateCourante.getYear()){
            return true;
        } else if (dateAVerifie.getMonth() < dateCourante.getMonth()) {
            return true;
        } else if (dateAVerifie.getDay() < dateCourante.getDay()) {
            return true;
        }
        else {return false;}
    }
    public static Date calculerJour(int days, int day, int month, int year){
        int newDay = day + days;
        while(newDay > nbrJrMm(month,year)){
            newDay -= month;
            if (month == 12){month = 1; year += 1;}
            else {month +=1;}
        }
        return new Date(newDay,month,year);
    }
    public static int nbrJrMm(int month, int year){
        int[] nbrJr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isBissextile(year)){
            return 29;
        }
        else {
            return nbrJr[month-1];
        }
    }
    public static boolean isBissextile(int year){
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0){
            return true;
        }
        else {return false;}
    }
    public static String writeDate(Date date){
        return (date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
    }

    public static Date date(Date date, TreeSet<Medicament> commande, String file){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (commande.isEmpty()){
                writer.write(writeDate(date) + " OK\n");
            }
            else {
                writer.write(writeDate(date) + " COMMANDES :");
                for (Medicament medicament : commande) {
                    writer.write(medicament.getNom() + " " + medicament.getQuantiteCommande());
                }
                commande.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return date;
    }
    public static Prescription prescription(String nomMedicament, int doseTraitement, int repetition, TreeSet<Medicament> stock, TreeSet<Medicament> commande, File file){
        Medicament medicamentPrescris = new Medicament();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (Medicament medicament : stock) {
                if (medicament.getNom() == nomMedicament) {
                    medicamentPrescris = medicament;
                    break;
                }
            }
            if (medicamentPrescris.getQuantite() < doseTraitement * repetition) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //return new Prescription();
        return new Prescription();
    }

}
