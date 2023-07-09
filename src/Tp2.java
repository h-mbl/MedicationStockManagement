import java.io.*;
//import java.lang.foreign.ValueLayout;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import java.util.*;



public class Tp2 {
    private static TreeSet<Medicament> arbreCommande = new TreeSet<>();
    private static TreeSet<Medicament> stock = new TreeSet<>();
    public static  Date dateCourante = new Date(2000,01,01);

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
                        //instancie l'arbre
                        dateCourante = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                        //?? ajoute a l'arbre commande
                        //??utilisation du meme nom
                        dateCourante = date(dateCourante, arbreCommande, outputFile);
                        break;
                    case "PRESCRIPTION":
                        //liste des prescriptions
                        List<Prescription> prescriptionList = new ArrayList<>();
                        //prescriptionList est une liste et readPrescription retourne une liste
                        prescriptionList = readPrescription(bufferedReader, line);
                        for (Prescription prescription: prescriptionList) {
                            String nomMedicament = prescription.getNomMedicament();
                            int doseTraitement = prescription.getDoseTraitement();
                            int repetition = prescription.getRepetition();
                            System.out.println( prescription.getNomMedicament());
                            prescription(prescription, doseTraitement, repetition, stock, arbreCommande, outputFile);
                        }
                        break;
                    case "APPROV":
                        List<Medicament> approvListr = new ArrayList<>();
                        approvListr = readApprov(bufferedReader, line) ;
                       // System.out.println(approvListr);
                        for (Medicament medicament:approvListr) {
                            System.out.println("je suis ici 1");
                            System.out.println(medicament);
                            String nomMedicament = medicament.getNom();
                            int quantite= medicament.getQuantiteCommande();
                            Date dateExpiration= medicament.getDateExpiration();
                            //ajoute à l'arbre
                            stock.add(medicament);
                        }
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {

                            writer.write("APPROV OK");
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

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
        while ((line = bufferedReader.readLine()) != null && !line.equals(";")){
            String[] prescriptionLine = line.split("\\s+");
           // System.out.println(prescriptionLine[0]);
            prescriptionsList.add(new Prescription(prescriptionLine[0], Integer.parseInt(prescriptionLine[1]), Integer.parseInt(prescriptionLine[2])));
        }
        //System.out.println(prescriptionsList);
        return prescriptionsList;
    }
    public static boolean datePasse(Date dateAVerifie, Date dateCourante) {
        if (dateAVerifie.getYear() < dateCourante.getYear()) {
        }
        return false;
    }
    public static List<Medicament> readApprov (BufferedReader bufferedReader, String line) throws IOException {
        List<Medicament> approvList = new ArrayList<>();
        while (!(line = bufferedReader.readLine()).equals(";")) {
            String[] approvLine = line.split(" ");
            Date dateExpiration= new Date( Integer.parseInt(approvLine[2]), Integer.parseInt(approvLine[3]),Integer.parseInt(approvLine[4]));
            System.out.println("je suis ici 2");
            System.out.println(approvLine);
            approvList.add(new Medicament(approvLine[0], Integer.parseInt(approvLine[1]),dateExpiration));

        }
        return approvList;
    }
    public static boolean datePassee(Date dateAVerifie, Date dateCourante ){
        if (dateAVerifie.getYear() < dateCourante.getYear()){
            return true;
        } else if (dateAVerifie.getYear() == dateCourante.getYear() && dateAVerifie.getMonth() < dateCourante.getMonth()) {
            return true;
        } else if (dateAVerifie.getYear() == dateCourante.getYear() && dateAVerifie.getMonth() == dateCourante.getMonth() && dateAVerifie.getDay() < dateCourante.getDay()) {
            return true;
        } else {
            return false;
        }
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
    public static void prescription(Prescription medicamentPrescription, int doseTraitement, int repetition, TreeSet<Medicament> stock, TreeSet<Medicament> commande, String file){
        //Medicament medicamentPrescris = new Medicament();
       // System.out.println("-----------");
       // System.out.println(nomMedicament);
       // System.out.println(medicamentPrescris.getNom());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (stock.isEmpty()){
                writer.write(medicamentPrescription.getNomMedicament() + " " + doseTraitement + " " + repetition + " COMMANDE");
                commande.add(medicamentPrescription);
            }
            for (Medicament medicament : stock) {
                if (medicament.getNom().equals(medicamentPrescription.getNomMedicament())) {
                   // medicamentPrescription = medicament;
                    if (!datePasse(medicamentPrescription.getDateExpiration(), dateCourante)){
                        if (medicamentPrescription.getQuantite() >= doseTraitement * repetition){
                            writer.write(medicamentPrescription.getNomMedicament() + " " + doseTraitement + " " + repetition + " OK");
                            break;
                        }
                        else {
                            writer.write(medicamentPrescription.getNomMedicament()  + " " + doseTraitement + " " + repetition + " COMMANDE");
                            commande.add(medicamentPrescription );
                            break;
                        }
                    }
                    else {
                        writer.write(medicamentPrescription.getNomMedicament()  + " " + doseTraitement + " " + repetition + " COMMANDE");
                        commande.add(medicamentPrescription);
                        break;
                    }
                }
                else {
                    writer.write(medicamentPrescription.getNomMedicament()  + " " + doseTraitement + " " + repetition + " COMMANDE");
                    commande.add(medicamentPrescription);
                    break;
                }
            }

            if (medicamentPrescription.getQuantite() < doseTraitement * repetition) {

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //return new Prescription();
    }
    public static TreeSet approv(String nomMedicament, int quantite, Date dateExpiration, TreeSet<Medicament> stock){
        //Medicament medicamentPrescris = new Medicament();


        //return new Prescription();
        return stock;
    }

}
