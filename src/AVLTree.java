public class AVLTree {
    private Node root;

    public AVLTree(ArbreGen Arbre) {
        this.ArbreGen=
        root = null;
    }

    // Méthode pour obtenir la hauteur d'un nœud
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    // Méthode pour obtenir le facteur d'équilibre d'un nœud
    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    // Méthode pour mettre à jour la hauteur d'un nœud
    private void updateHeight(Node node) {
        if (node == null) {
            return;
        }
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    // Méthode pour effectuer une rotation simple à gauche
    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    // Méthode pour effectuer une rotation simple à droite
    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    // Méthode pour effectuer l'équilibrage d'un nœud
    private Node balanceNode(Node node) {
        updateHeight(node);

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            // Cas de déséquilibre à gauche
            if (getBalanceFactor(node.left) < 0) {
                // Rotation gauche-droite
                node.left = rotateLeft(node.left);
            }
            // Rotation simple à droite
            return rotateRight(node);
        }

        if (balanceFactor < -1) {
            // Cas de déséquilibre à droite
            if (getBalanceFactor(node.right) > 0) {
                // Rotation droite-gauche
                node.right = rotateRight(node.right);
            }
            // Rotation simple à gauche
            return rotateLeft(node);
        }

        return node; // Nœud équilibré
    }
}
