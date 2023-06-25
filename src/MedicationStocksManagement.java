import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MedicationStocksManagement {
    public static String Date;
    public static String currentCategory;
   // public static String[] parties;
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("provide input or output file names.");
            return;
        }
        String inputFile = "tests/" + args[0];
        String outputFile = "tests/" + args[1];
        try {
            readFile(inputFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void readFile(String inputFile) throws FileNotFoundException {
        File file = new File(inputFile);
        Scanner scanner = new Scanner(file);
        currentCategory = ""; // Tracks the current category being processed

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.indexOf("APPROV") == 0) {
                currentCategory = "APPROV";
            } else if (line.indexOf("STOCK") == 0) {
                currentCategory = "STOCK";
                parseFichierDate(line);
            } else if (line.indexOf("DATE") == 0) {
                currentCategory = "DATE";
                parseFichierDate(line);
            } else if (line.indexOf("PRESCRIPTION") == 0) {
                currentCategory = "PRESCRIPTION";
            } else if (line.indexOf(";") == 0){
                continue;
            }else{
                switch (currentCategory) {
                    case "APPROV", "STOCK":
                        parseFichier(line);
                        break;
                    case "DATE":
                        break;
                    case "PRESCRIPTION":
                        System.out.println("Prescription " + line);
                        break;
                    default:
                        System.out.println("format invalide");
                        break;
                }
            }
        }
    }
    public static void parseFichier(String line){
        line = line.replace("-", " ");
        line = line.trim().replaceAll("\\s+", " ");
        String[] parties = line.split(" ");
        int compteur = 0;
        String nomMedicament = "";
        int quantite = 0;
        int aaaa = 0;
        int mm=0;
        int jj=0;
        switch (currentCategory)  {
              case "APPROV", "STOCK":
                  for (String donnee : parties)
                     if (compteur == 0) {
                         nomMedicament = donnee;
                         compteur++;
                     } else if (compteur == 1) {
                        quantite = Integer.parseInt(donnee);
                        compteur++;
                     }else if (compteur == 2) {
                         aaaa = Integer.parseInt(donnee);
                        compteur++;
                    }else if (compteur == 3) {
                         mm = Integer.parseInt(donnee);
                         compteur++;
                    }else {
                        jj =Integer.parseInt(donnee);
                         compteur = 0;
            }

        }
    }
    public static void parseFichierDate(String line){
        line = line.replace("-", " ");
        line = line.trim().replaceAll("\\s+", " ");
        String[] parties = line.split(" ");
        if (parties.length >2) {
            int compteur = 0;
            int aaaa = 0;
            int mm = 0;
            int jj = 0;
            for (String donnee : parties) {
                if (compteur == 0) {
                    compteur++;
                } else if (compteur == 1) {
                    aaaa = Integer.parseInt(donnee);
                    compteur++;
                } else if (compteur == 2) {
                    mm = Integer.parseInt(donnee);
                    compteur++;
                } else if (compteur == 3){
                    jj = Integer.parseInt(donnee);
                    compteur = 0;
                }else {
                    continue;
                }
            }
        }
    }
}