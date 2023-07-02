public class ArbreGen {
    Node root;

    public ArbreGen() {
        root = null;
    }

    //la méthode publique que vous appelez pour insérer une valeur donnée dans l'arbre en toute securite
    //elle démarre le processus d'insertion de privée insertNode(Node root, Medicament value)
    // pour effectuer réellement l'insertion en traversant l'arbre
    public void insert(Medicament value) {
        root = insertNode(root, value);
    }

    private Node insertNode(Node root, Medicament value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }
        //compare le nom de medicament
        if (value.getNom().compareTo(root.value.getNom()) < 0) {
            root.left = insertNode(root.left, value);
        } else if (value.getNom().compareTo(root.value.getNom()) > 0) {
            root.right = insertNode(root.right, value);
            //debut du comparateur si un medicament a les memes noms que celui insere
        } else {
            //compare l'annee
            if (value.getAaaa() < root.value.getAaaa()) {
                root.left = insertNode(root.left, value);
            } else if (value.getAaaa() > root.value.getAaaa()) {
                root.right = insertNode(root.right, value);
            } else {
                //compare le mois
                if (value.getMm() < root.value.getMm()) {
                    root.left = insertNode(root.left, value);
                } else if (value.getMm() > root.value.getMm()) {
                    root.right = insertNode(root.right, value);
                } else {
                    //compare le jour
                    if (value.getJj() < root.value.getJj()) {
                        root.left = insertNode(root.left, value);
                    } else if (value.getJj() > root.value.getJj()) {
                        root.right = insertNode(root.right, value);
                    }
                }
            }
        }
        //un arbre pareil sera forcement desequilibree a cause du nom de medicament indentique
        //mais cela nous permettra de lire l'arbre grace a un parcours,pour trouver les medicaments le plus anciens
        //le parcours doit commencer en bas a gauche pour ensuite alle a la racine et faire par la suite la partie
        //droite.(pour la recherche)
        return root;
    }

    public void balanceTree() {
        root = balanceTree(root);
    }

    private Node balanceTree(Node root) {
        if (root == null) {
            return null;
        }

        int balanceFactor = getBalanceFactor(root);

        if (balanceFactor > 1) {
            if (getBalanceFactor(root.left) >= 0) {
                root = rotateRight(root);
            } else {
                root.left = rotateLeft(root.left);
                root = rotateRight(root);
            }
        } else if (balanceFactor < -1) {
            if (getBalanceFactor(root.right) <= 0) {
                root = rotateLeft(root);
            } else {
                root.right = rotateRight(root.right);
                root = rotateLeft(root);
            }
        }

        root.left = balanceTree(root.left);
        root.right = balanceTree(root.right);

        return root;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;
        return newRoot;
    }

    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;
        return newRoot;
    }

    public Prescription search(Prescription value) {
        return searchNode(root, value);
    }

    private Prescription searchNode(Node root, Prescription value) {
        if (root == null) {
            return null; // Élément non trouvé
        }

        if (value.getNomMedicament().compareTo(root.value.getNom()) < 0) {
            return searchNode(root.left, value);
        } else if (value.getNomMedicament().compareTo(root.value.getNom()) > 0) {
            return searchNode(root.right, value);
        }else{
        return (Prescription) root.value; // Élément trouvé// }
        }
    }
}

