import java.io.*;
import java.util.*;

public class MedicationStocksManagement {
    private static String date = null;
    private static String currentCategory;
    private static String nomMedicament;
    private static int quantite;
    private static int aaaa;
    private static int mm;
    private static int jj;
    private static int doseTraitement;
    private static int repetition;
    //cette liste enregistrera l'ordre des actions à effectuer
    private static Queue<Object> fileOperation= new LinkedList<>();
    static  = new ArbreGen();
    public static  Date dateCourante = new Date(2000,01,01);



    // public static String[] parties;
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("provide input or output file names.");
            return;
        }
        String inputFile = "tests/" + args[0];
        String outputFile = "tests/" + args[1];
        try {
            //lit le fichier en entree
            readFile(inputFile);
            //nous passons a travers les elements de la file
            for (Object obj : fileOperation) {
                if  (obj instanceof Medicament){
                    //lit la valeur contenu dans l'objet venant de la file d'attente
                    Medicament valueObj = (Medicament) obj;
                    //ajoute l'objet a l'arbre
                    ArbreGen.insert(valueObj);
                }
                else if (obj instanceof Prescription){
                    Prescription valueObjPrescription = (Prescription) obj;
                    //equilibre l'arbre
                    ArbreGen.balanceTree();
                    Prescription found = ArbreGen.search(valueObjPrescription);
                    if (found != null) {
                        System.out.println("L'élément est présent dans l'arbre : " + found);
                    } else {
                        System.out.println("L'élément n'est pas présent dans l'arbre.");
                    }

                }
                else{}
                fileOperation.remove();
            }
            //System.out.println(ABRTree);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void readFile(String inputFile) throws FileNotFoundException {

        File file = new File(inputFile);
        Scanner scanner = new Scanner(file);
        //enregistre la categorie du bloc de ligne
        currentCategory = "";

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            //indexOf[0] verifie si le premier element de l'objet line est le mot cherche
            if (line.indexOf("APPROV") == 0) {
                currentCategory = "APPROV";
            } else if (line.indexOf("STOCK") == 0) {
                // Marquer l'étape comme le stock initial
                Stock stockState = new Stock(date);
                fileOperation.add(stockState);
            } else if (line.indexOf("DATE") == 0) {
                //currentCategory = "DATE";
                //je place ceci ici car la date est ecrit : DATE: JJ-MM-AAAA
                date = readDate(line);
            } else if (line.indexOf("PRESCRIPTION") == 0) {
                currentCategory = "PRESCRIPTION";
                //lorsque la ligne n'est qu'un ;
                //note : pense a efface cette condition sans bug
            } else if (line.indexOf(";") == 0) {
                continue;
            } else {
                switch (currentCategory) {
                    case "APPROV":
                        parseFichier(line);
                        Medicament medicament = new Medicament(nomMedicament, quantite, aaaa, mm, jj);
                        fileOperation.add(medicament);
                        break;

                    case "PRESCRIPTION":
                        parseFichier(line);
                        Prescription prescription= new Prescription(nomMedicament,doseTraitement,repetition);
                        fileOperation.add(prescription);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void parseFichier(String line) {
        line = line.replace("-", " ");
        line = line.replace(";", "");
        line = line.trim().replaceAll("\\s+", " ");
        String[] parties = line.split(" ");
        int compteur = 0;
        switch (currentCategory) {
            case "APPROV":
                for (String donnee : parties) {
                    if (compteur == 0) {
                        nomMedicament = donnee;
                        compteur++;
                    } else if (compteur == 1) {
                        quantite = Integer.parseInt(donnee);
                        compteur++;
                    } else if (compteur == 2) {
                        aaaa = Integer.parseInt(donnee); //"2017 10 26"
                        compteur++;
                    } else if (compteur == 3) {
                        mm = Integer.parseInt(donnee);
                        compteur++;
                    } else if (compteur == 4 ) {
                        jj = Integer.parseInt(donnee);
                        compteur = 0;
                    }
                }

            case "PRESCRIPTION":
                for (String donnee : parties) {
                    if (compteur == 0) {
                        nomMedicament = donnee;
                        compteur++;
                    } else if (compteur == 1) {
                        doseTraitement = Integer.parseInt(donnee);
                        compteur++;
                    } else if (compteur == 2) {
                        repetition = Integer.parseInt(donnee);
                        compteur++;
                    }
                }
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
    public static int nbrJrMm(int month, int year){
        int[] nbrJr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2){
            return 29;
        }
        else {
            return nbrJr[month-1];
        }
    }

    public static Date date(Date date, TreeSet<Medicament> treeSet, File file){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (treeSet.isEmpty()){
                writer.write(date.getYear() + "-" + date.getMonth() + "-" + date.getDay() + "OK");
            }
            else {
                writer.write(date.getYear() + "-" + date.getMonth() + "-" + date.getDay() + " COMMANDES :");
                for (Medicament medicament : treeSet) {
                    writer.write(medicament.getNom() + " " + medicament.getQuantiteCommande());
                }
                treeSet.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return date;
    }

}
