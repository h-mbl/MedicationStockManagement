import java.io.*;
import java.util.*;


public class Tp2 {

    private static TreeMap<String, Medicament> arbreCommande = new TreeMap<>();
    private static TreeSet<Medicament> stock = new TreeSet<>();
    public static Date dateCourante = new Date(2000, 01, 01);

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Fournissez les noms des fichiers d'entrée et de sortie.");
            return;
        }
        String inputFile = "tests/" + args[0];
        String outputFile = "testCode/" + args[1];
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileReader fileReader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            int compteurPrescription = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String commande = line.split(" ")[0];
                switch (commande) {
                    case "DATE":
                        String dateLine = readDate(line);
                        String[] date = dateLine.split(" ");
                        dateCourante = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                        dateCourante = date(dateCourante, arbreCommande, outputFile);
                        break;
                    case "PRESCRIPTION":
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
                            compteurPrescription += 1;
                            writer.write("PRESCRIPTION " + compteurPrescription + "\n");
                            List<Prescription> prescriptionList = new ArrayList<>();
                            prescriptionList = readPrescription(bufferedReader, line);
                            for (Prescription prescription : prescriptionList) {
                                String nomMedicament = prescription.getNomMedicament();
                                int doseTraitement = prescription.getDoseTraitement();
                                int repetition = prescription.getRepetition();
                                prescription(nomMedicament, doseTraitement, repetition, stock, arbreCommande, writer, compteurPrescription);
                            }
                            writer.write("\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        break;
                    case "APPROV":
                        List<Medicament> approvListr = new ArrayList<>();
                        approvListr = readApprov(bufferedReader, line);
                        for (Medicament medicament : approvListr) {
                            stock.add(medicament);
                        }
                        try (BufferedWriter writerApprov = new BufferedWriter(new FileWriter(outputFile, true))) {

                            writerApprov.write("APPROV OK");
                            writerApprov.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "STOCK":
                        try (BufferedWriter writerStock = new BufferedWriter(new FileWriter(outputFile, true))) {
                            //String.format("%02d", number) spécifie que le nombre doit être formaté comme un nombre (%d)
                            //  de 2 caractères (2), et qu'un zéro sera ajouté devant le nombre si le nombre <10.
                            writerStock.write("STOCK" + " " + dateCourante.getYear() + "-" + String.format("%02d", dateCourante.getMonth()) + "-" +
                                    String.format("%02d", dateCourante.getDay()));
                            writerStock.newLine();
                            Iterator<Medicament> iterator = stock.iterator();
                            while (iterator.hasNext()) {
                                Medicament medicament = iterator.next();
                                if (medicament.getDateExpiration().getYear() < dateCourante.getYear() &&
                                        medicament.getDateExpiration().getMonth() < dateCourante.getMonth() &&
                                        medicament.getDateExpiration().getDay() < dateCourante.getDay()) {
                                    iterator.remove();
                                } else {
                                    writerStock.write(medicament.getNom() + " " + medicament.getQuantite() + " " +
                                            medicament.getDateExpiration().getYear() + "-" + String.format("%02d", medicament.getDateExpiration().getMonth()) + "-" +
                                            String.format("%02d", medicament.getDateExpiration().getDay()));
                                    writerStock.newLine();
                                }
                            }
                            writerStock.write("\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readDate(String line) {
        line = line.replace("-", " ");
        line = line.replace("DATE", "");
        line = line.replace(";", "");
        line = line.trim().replaceAll("\\s+", " ");
        // String[] parties = line.split(" ");
        return line;
    }

    public static List<Prescription> readPrescription(BufferedReader bufferedReader, String line) throws IOException {
        List<Prescription> prescriptionsList = new ArrayList<>();
        while (!(line = bufferedReader.readLine()).equals(";")) {
            String[] prescriptionLine = line.split("\\s+");
            prescriptionsList.add(new Prescription(prescriptionLine[0], Integer.parseInt(prescriptionLine[1]), Integer.parseInt(prescriptionLine[2])));
        }

        return prescriptionsList;
    }

    public static List<Medicament> readApprov(BufferedReader bufferedReader, String line) throws IOException {
        List<Medicament> approvList = new ArrayList<>();
        while (!(line = bufferedReader.readLine()).equals(";")) {
            line = line.replace("-", " ");
            String[] approvLine = line.split("\\s+");
            Date dateExpiration = new Date(Integer.parseInt(approvLine[2]), Integer.parseInt(approvLine[3]), Integer.parseInt(approvLine[4]));
            approvList.add(new Medicament(approvLine[0], Integer.parseInt(approvLine[1]), dateExpiration));
        }
        return approvList;
    }

    public static boolean datePasse(Date dateAVerifie, Date dateCourante) {
        if (dateAVerifie.getYear() < dateCourante.getYear()) {
            return true;
        } else if (dateAVerifie.getYear() == dateCourante.getYear() && dateAVerifie.getMonth() < dateCourante.getMonth()) {
            return true;
        } else if (dateAVerifie.getYear() == dateCourante.getYear() && dateAVerifie.getMonth() == dateCourante.getMonth() && dateAVerifie.getDay() < dateCourante.getDay()) {
            return true;
        } else {
            return false;
        }
    }

    public static Date calculerJour(int days, int day, int month, int year) {
        int newDay = day + days;
        while (newDay > nbrJrMm(month, year)) {
            newDay -= month;
            if (month == 12) {
                month = 1;
                year += 1;
            } else {
                month += 1;
            }
        }
        return new Date(newDay, month, year);
    }

    public static int nbrJrMm(int month, int year) {
        int[] nbrJr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isBissextile(year)) {
            return 29;
        } else {
            return nbrJr[month - 1];
        }
    }

    public static boolean isBissextile(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String writeDate(Date date) {
        return (date.getYear() + "-" + String.format("%02d", date.getMonth()) + "-" + String.format("%02d", date.getDay()));
    }

    public static Date date(Date date, TreeMap<String, Medicament> commande, String file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (commande.isEmpty()) {
                writer.write(writeDate(date) + " OK\n");
            } else {
                writer.write(writeDate(date) + " COMMANDES :\n");
                for (String nomMedicament : commande.keySet()) {
                    Medicament medicament = commande.get(nomMedicament);
                    writer.write(medicament.getNom() + " " + medicament.getQuantiteCommande() + "\n");
                }
                commande.clear();
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void prescription(String nomMedicament, int doseTraitement, int repetition, TreeSet<Medicament> stock, TreeMap<String, Medicament> commande, BufferedWriter writer, int compteur) throws IOException {
        Medicament medicamentPrescris = new Medicament(nomMedicament, (doseTraitement * repetition));
        if (stock.isEmpty()) {
            writer.write(nomMedicament + " " + doseTraitement + " " + repetition + "  COMMANDE\n");
            String key = medicamentPrescris.getNom();
            if (commande.containsKey(key)) {
                // Changement du nombre de medicament donné à commander
                Medicament medicamentCommande = commande.get(key);
                medicamentCommande.setQuantiteCommande(medicamentCommande.getQuantiteCommande() + medicamentPrescris.getQuantiteCommande());
                commande.put(key, medicamentCommande);
            } else {
                commande.put(key, medicamentPrescris);
            }
        }
        if (stock.contains(medicamentPrescris)) {
            for (Medicament medicament : stock) {
                if (medicament.getNom().equals(nomMedicament)) {
                    medicamentPrescris = medicament;
                    if (!datePasse(medicamentPrescris.getDateExpiration(), dateCourante)) {

                        if (medicamentPrescris.getQuantite() >= doseTraitement * repetition) {
                            writer.write(nomMedicament + " " + doseTraitement + " " + repetition + "  OK\n");
                            medicament.setQuantite(medicament.getQuantite() - doseTraitement * repetition);
                            break;
                        } else {
                            writer.write(nomMedicament + " " + doseTraitement + " " + repetition + "  COMMANDE\n");
                            String key = medicamentPrescris.getNom();
                            if (commande.containsKey(key)) {
                                // Changement du nombre de medicament donné à commander
                                //yo
                                Medicament medicamentCommande = commande.get(key);
                                medicamentCommande.setQuantiteCommande(medicamentCommande.getQuantiteCommande() + medicamentPrescris.getQuantiteCommande());
                                commande.put(key, medicamentCommande);
                            } else {
                                commande.put(key, medicamentPrescris);
                            }
                            break;
                        }
                    } else {
                        writer.write(nomMedicament + " " + doseTraitement + " " + repetition + "  COMMANDE\n");
                        String key = medicamentPrescris.getNom();
                        if (commande.containsKey(key)) {
                            // Changement du nombre de medicament donné à commander
                            Medicament medicamentCommande = commande.get(key);
                            medicamentCommande.setQuantiteCommande(medicamentCommande.getQuantiteCommande() + medicamentPrescris.getQuantiteCommande());
                            commande.put(key, medicamentCommande);
                        } else {
                            commande.put(key, medicamentPrescris);
                        }
                        break;
                    }
                } else {
                    writer.write(nomMedicament + " " + doseTraitement + " " + repetition + "  COMMANDE\n");
                    String key = medicamentPrescris.getNom();
                    if (commande.containsKey(key)) {
                        // Changement du nombre de medicament donné à commandé
                        Medicament medicamentCommande = commande.get(key);
                        medicamentCommande.setQuantiteCommande(medicamentCommande.getQuantiteCommande() + medicamentPrescris.getQuantiteCommande());
                        commande.put(key, medicamentCommande);
                    } else {
                        commande.put(key, medicamentPrescris);
                    }
                    break;
                }
            }
        } else {
            writer.write(nomMedicament + " " + doseTraitement + " " + repetition + "  COMMANDE\n");
            String key = medicamentPrescris.getNom();
            if (commande.containsKey(key)) {
                // Changement du nombre de medicament donné à commandé
                Medicament medicamentCommande = commande.get(key);
                medicamentCommande.setQuantiteCommande(medicamentCommande.getQuantiteCommande() + medicamentPrescris.getQuantiteCommande());
                commande.put(key, medicamentCommande);
            } else {
                commande.put(key, medicamentPrescris);
            }
        }
    }
}

