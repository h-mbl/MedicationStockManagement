import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CompareFiles {
    public static void main(String[] args) {
        String file1 = "tests/exemple2+.txt";
        String file2 = "testCode/ex1Out.txt";

        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
             BufferedReader reader2 = new BufferedReader(new FileReader(file2))) {

            String line1, line2 = null;
            int lineNumber = 1;

            while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
                String[] elements1 = line1.split(" ");
                String[] elements2 = line2.split(" ");

                if (!areArraysEqual(elements1, elements2)) {
                    System.out.println("Les fichiers diffèrent à la ligne " + lineNumber + ":");
                    System.out.println("Fichier 1: " + line1);
                    System.out.println("Fichier 2: " + line2);
                     // Arrête le programme si une différence est trouvée
                }
                lineNumber++;
            }

            // Vérifie si les fichiers ont la même longueur
            if (line1 != null || line2 != null) {
                System.out.println("Les fichiers diffèrent en longueur.");
                System.out.println("Fichier 1 a plus de lignes que fichier 2.");
            } else {
                System.out.println("Les fichiers sont identiques.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean areArraysEqual(String[] arr1, String[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equals(arr2[i])) {
                return false;
            }
        }

        return true;
    }
}
