public class Node {
    //objet medicament,pour creer des noeuds des medicaments
    Medicament value;
    Node left;
    Node right;
    int height;

    public Node(Medicament value) {
        this.value = value;
        left = null;
        right = null;
        height = 1;
    }
}
