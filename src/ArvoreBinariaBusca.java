
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArvoreBinariaBusca<Key extends Comparable<Key>, Value> {
    public Node root;             // root of BST

    private ArrayList<Node> cache = new ArrayList<>();

    private ArrayList<Key> NODES_WITH_ONE_CHILD = new ArrayList<>();

    private ArrayList<Key> LRN_POS_ORDEM = new ArrayList<>();
    private ArrayList<Key> NLR_PRE_ORDEM = new ArrayList<>();
    private ArrayList<Key> LNR_IN_ORDEM = new ArrayList<>();


    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree
        private int degree;        // number of childs the node has

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.degree = 0;
        }
    }

    /*
     * AUXILIARY METHODS
     * these methods are used to create the other methods, they usually return raw information about the BST
     */
    /**
     * Initializes an empty symbol table.
     */
    public ArvoreBinariaBusca() {
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at node
    private int size(Node node) {
        if (node == null) return 0;
        else return node.size;
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param low minimum endpoint
     * @param high maximum endpoint
     * @return the number of keys in the symbol table between {@code low}
     * (inclusive) and {@code high} (inclusive)
     * @throws IllegalArgumentException if either {@code low} or {@code high}
     *                                  is {@code null}
     */
    public int size(Key low, Key high) {
        if (low == null) throw new IllegalArgumentException("Primeiro argumento 'Low' em size() é nulo");
        if (high == null) throw new IllegalArgumentException("segundo argumento 'high' em size() é nulo");

        if (low.compareTo(high) > 0) return 0;
        if (contains(high)) return rank(high) - rank(low) + 1;
        else return rank(high) - rank(low);
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("método contains() recebeu uma chave nula como argumento");
        return get(key) != null;
    }

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("método min() recebeu uma Árvore vazia");
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("método max() recebeu uma Árvore vazia");
        return max(root).key;
    }

    private Node max(Node node) {
        if (node.right == null) return node;
        else return max(node.right);
    }

    /**
     * Returns the height of the BST (for debugging).
     *
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * returns the level of a node relative to the root
     * @param node
     * @return the distance from the root
     */
    public int level(Node node) {
        return level(root, node);
    }
    private int level(Node levelZero, Node node) {
        if (node == levelZero) return 0;
        else if (levelZero == null) return 0;

        int compareKey = node.key.compareTo(levelZero.key);
        if (compareKey < 0) {
            return 1 + level(levelZero.left, node);
        } else if (compareKey > 0) {
            return 1 + level(levelZero.right, node);
        } else {
            return 0;
        }
    }

    /*
     * READING, INSERTING AND DELETING METHODS
     * the operations with the BST happen here
     */

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (key == null) throw new IllegalArgumentException("método get() recebeu uma chave nula");
        if (node == null) return null;

        this.cache.add(node);

        int compareKey = key.compareTo(node.key);
        if (compareKey < 0) {
            return get(node.left, key);
        }
        else if (compareKey > 0) {
            return get(node.right, key);
        }
        else {
            return node.val;
        }
    }

    public Node getNode(Key key) {
        return getNode(root, key);
    }

    private Node getNode(Node node, Key key) {
        if (key == null) throw new IllegalArgumentException("método getNode() recebeu uma chave nula");
        if (node == null) return null;

        this.cache.add(node);

        int compareKey = key.compareTo(node.key);
        if (compareKey < 0) {
            return getNode(node.left, key);
        }
        else if (compareKey > 0) {
            return getNode(node.right, key);
        }
        else {
            return node;
        }
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("método put() recebeu uma chave nula");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null) { return new Node(key, val, 1); }
        int compareKey = key.compareTo(node.key);
        if (compareKey < 0) { node.left = put(node.left, key, val); }
        else if (compareKey > 0) { node.right = put(node.right, key, val); }
        else {
            node.val = val;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }


    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("método deleteMin() recebeu uma Árvore vazia");
        root = deleteMin(root);
        assert check();
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("método deleteMax() recebeu uma Árvore vazia");
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node node) {
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("método delete() recebeu uma chave nula");
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;

        int compareKey = key.compareTo(node.key);
        if (compareKey < 0) node.left = delete(node.left, key);
        else if (compareKey > 0) node.right = delete(node.right, key);
        else {
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;
            Node t = node;
            node = min(t.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    /*
    * SPECIAL DATA Methods
    * data about the BST, but you can filter them
    *
    */

    /**
     * ME parte 1
     * Prints important information about the BST
     * for study purposes
     * @param key
     */
    public void printNodeData(Key key) {
        Node node = getNode(key);

        System.out.println("Altura: " + height(node));
        System.out.println("Nível: " + level(node) );
        System.out.println("Profundidade: " + level(node));

        Node parentNode = root;

        for (int i = 0; i < this.cache.size() - 1; i++) {
            parentNode = this.cache.get(i);
            if (parentNode.equals(node)){
                parentNode = this.cache.get(i - 1);
            }
        }

        if (parentNode.right == node){
            System.out.println("Pai: " + parentNode.key.toString());
            System.out.println("É filho direito");
        } else if (parentNode.left == node) {
            System.out.println("Pai: " + parentNode.key.toString());
            System.out.println("É filho esquerdo");
        } else if (parentNode == root) {
            System.out.println("Não tem pai");
            System.out.println("Nó raiz");
        } else {
            System.out.println("Erro");
        }
        System.out.println("Valor do Node: " + node.val);
    }

    //ME PARTE 2

    /**
     * MÉTODO 1 - PERCORRER ARVORE
     * Prints all 3 different types of traversals in a binary tree
     */
    public void printTraversal() {
        if(isEmpty()) throw new NoSuchElementException("método printTraversal() não encontrou árvore alguma");

        nlr(root);
        System.out.println("Pré-Ordem(NLR): " + this.NLR_PRE_ORDEM);
        lnr(root);
        System.out.println("In-Ordem(LNR): " + this.LNR_IN_ORDEM);
        lrn(root);
        System.out.println("Pós-Ordem(LRN): " + this.LRN_POS_ORDEM);
    }

    /**
     * MÉTODO 1 - PERCORRER ARVORE
     * PREORDER TRAVERSAL OF THE TREE (NLR)
     * @param node
     */
    private void nlr(Node node) { //PRE-ORDEM
        if(node == null) {
            return;
        }

        this.NLR_PRE_ORDEM.add(node.key);

        //left subtree
        nlr(node.left);

        //right subtree
        nlr(node.right);

    }

    /**
     * MÉTODO 1 - PERCORRER ARVORE
     * INORDER TRAVERSAL OF THE TREE (LNR)
     * @param node
     */
    private void lnr(Node node) { //IN-ORDEM
        if (node == null) {
            return;
        }
        //left subtree
        lnr(node.left);

        this.LNR_IN_ORDEM.add(node.key);

        //right subtree
        lnr(node.right);
    }

    /**
     * MÉTODO 1 - PERCORRER ARVORE
     * POSTORDER TRAVERSAL OF THE TREE (LRN)
     * @param node
     */
    private void lrn(Node node) { //POS-ORDEM
        if (node == null) {
            return;
        }
        //left subtree
        lrn(node.left);

        //right subtree
        lrn(node.right);

        this.LRN_POS_ORDEM.add(node.key);
    }

    /**
     * MÉTODO 2 - Imprimir Desenho da árvore
     * Imprime todos os elementos da arvore identados
     */
    public void printTree(){
        System.out.println(treeStringBuilder(root));
    }

    private void treeStringBuilder(StringBuilder sb,
                                   String padding,
                                   String pointer,
                                   Node node,
                                   boolean hasRight){
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.key);

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRight) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (node.right != null) ? "├──" : "└──";

            treeStringBuilder(sb,paddingForBoth, pointerForLeft, node.left, node.right != null);
            treeStringBuilder(sb,paddingForBoth, pointerForRight, node.right,false);
        }
    }

    private String treeStringBuilder(Node root) {
        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.key);

        String pointerRight = "└──";
        String pointerLeft = (root.right != null) ? "├──" : "└──";

        treeStringBuilder(sb, "", pointerLeft, root.left, root.right != null);
        treeStringBuilder(sb, "", pointerRight, root.right, false);

        return sb.toString();
    }


    /**
     * Método 3 - Tamanho da árvore de forma recursiva
     * counting the number of nodes, but with recursion
     * @return number of nodes in the BST
     */
    public int recursiveCount() {
        return recursiveCount(root);
    }
    private int recursiveCount(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + recursiveCount(node.left) + recursiveCount(node.right);
    }

    /**
     * MÉTODO 6 - CLASSIFICAR ÁRVORE
     * Dizer se é binária ou Binária de busca
     */
    public boolean isBinarySearchTree() {
        return isBST();
    }

    public boolean isOnlyBinaryTree() {
        return isBinaryTree(root);
    }

    private boolean isBinaryTree(Node node){
        if (node == null) return true;
        int countChild = 0;
        return isBinaryTree(node.left) && isBinaryTree(node.right);
    }

    /**
     * Método 7 - NÓS COM SOMENTE UM FILHO
     * Prints an array with the nodes that have only one child
     * @return array with all the keys of the nodes that have only one child
     */
    public String printNodesWithOnlyOneChild() {
        nodesWithOnlyOneChild();
        return this.NODES_WITH_ONE_CHILD.toString();
    }

    /**
     * Método 7 - NÓS COM SOMENTE UM FILHO
     * prints the size of the array nodesWithOnlyOneChild
     * @return Integer with the count
     */
    public int sizeNodesWithOnlyOneChild() {
        nodesWithOnlyOneChild();
        return this.NODES_WITH_ONE_CHILD.size();
    }

    /**
     * Método 7 - NÓS COM SOMENTE UM FILHO
     * Maps all nodes with One child
     */
    private void nodesWithOnlyOneChild() {
        this.NODES_WITH_ONE_CHILD.clear(); //making sure the array is empty
        keys().forEach(key -> {
            Node node = getNode(key);

            boolean hasChild = !(node.left == null && node.right == null);
            boolean andOnlyOneChild = node.left == null || node.right == null;
            boolean hasOnlyOneChild = hasChild && andOnlyOneChild;

            if (hasOnlyOneChild) {
                this.NODES_WITH_ONE_CHILD.add(node.key);
            }
        });
    }

    /**
     * Return the key in the symbol table of a given {@code rank}.
     * This key has the property that there are {@code rank} keys in
     * the symbol table that are smaller. In other words, this key is the
     * ({@code rank}+1)st smallest key in the symbol table.
     *
     * @param rank the order statistic
     * @return the key in the symbol table of given {@code rank}
     * @throws IllegalArgumentException unless {@code rank} is between 0 and
     *                                  <em>n</em>–1
     */
    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("método select() recebeu um rank inválido " + rank);
        }
        return select(root, rank);
    }

    // Return key in BST rooted at node of given rank.
    // Precondition: rank is in legal range.
    private Key select(Node node, int rank) {
        if (node == null) return null;
        int leftSize = size(node.left);
        if (leftSize > rank) return select(node.left, rank);
        else if (leftSize < rank) return select(node.right, rank - leftSize - 1);
        else return node.key;
    }

    /**
     * Return the number of >keys< in the symbol table strictly less than {@code key}.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("método rank() recebeu chave nula");
        return rank(key, root);
    }

    // Number of keys in the subtree less than key.
    private int rank(Key key, Node node) {
        if (node == null) return 0;
        int compareKey = key.compareTo(node.key);
        if (compareKey < 0) return rank(key, node.left);
        else if (compareKey > 0) return 1 + size(node.left) + rank(key, node.right);
        else return size(node.left);
    }

    /**
     * Returns all keys in the symbol table in ascending order,
     * as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table in ascending order
     */
    public Iterable<Key> keys() {
        if (isEmpty()) { return new ArrayList<>(); }
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range
     * in ascending order, as an {@code Iterable}.
     *
     * @param low  minimum endpoint
     * @param high maximum endpoint
     * @return all keys in the symbol table between {@code low}
     * (inclusive) and {@code high} (inclusive) in ascending order
     * @throws IllegalArgumentException if either {@code low} or {@code high}
     *                                  is {@code null}
     */
    public Iterable<Key> keys(Key low, Key high) {
        if (low == null) throw new IllegalArgumentException("Primeiro argumento 'Low' em keys() é nulo");
        if (high == null) throw new IllegalArgumentException("Segundo argumento 'high' em keys() é nulo");

        ArrayList<Key> list = new ArrayList<>();
        keys(root, list, low, high);
        return list;
    }

    private void keys(Node node, ArrayList<Key> list, Key low, Key high) {
        if (node == null) return;
        int compareLow = low.compareTo(node.key);
        int compareHigh = high.compareTo(node.key);
        if (compareLow < 0) keys(node.left, list, low, high);
        if (compareLow <= 0 && compareHigh >= 0) list.add(node.key);
        if (compareHigh > 0) keys(node.right, list, low, high);
    }

    /*
    * CHECKING INTEGRITY METHODS
    * keeps the BST consistent and reliable
     */
    /*************************************************************************
     *  Check integrity of BST data structure.
     ***************************************************************************/
    private boolean check() {
        if (!isBST()) System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
        if (!isRankConsistent()) System.out.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: elegant solution due to Bob Dondero
    private boolean isBST(Node node, Key min, Key max) {
        if (node == null) return true;
        if (min != null && node.key.compareTo(min) <= 0) return false;
        if (max != null && node.key.compareTo(max) >= 0) return false;
        return isBST(node.left, min, node.key) && isBST(node.right, node.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node node) {
        if (node == null) return true;
        if (node.size != size(node.left) + size(node.right) + 1) return false;
        return isSizeConsistent(node.left) && isSizeConsistent(node.right);
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            //the index must be equal to the rank in the BST
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            // the key also should be consistent
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }
}