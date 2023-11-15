public class Main {
    public static void main(String[] args) {
        AVLTree<String, Integer> BBST = new AVLTree<>();
        BBST.put("A", 1);
        BBST.put("B", 2);
        BBST.put("C", 3);
        BBST.put("D", 4);
        BBST.put("E", 5);
        BBST.put("F", 6);
        BBST.put("G", 7);
        BBST.put("H", 8);
        BBST.put("I", 9);
        BBST.put("J", 10);
        BBST.put("K", 11);
        BBST.put("L", 12);
        BBST.put("M", 13);
        BBST.put("N", 14);
        BBST.put("O", 15);
        BBST.put("P", 16);
        BBST.put("Q", 17);
        BBST.put("R", 18);

        quebraLinha("E) Impressão da árvore balanceada com o fator de balanceamento em cada nó");
        BBST.printTree();

        System.out.println(BBST.getResultantBalance());

        BBST.printTraversal();
    }

    public static void quebraLinha(String msg) {
        System.out.println();
        System.out.println("**********************************************");
        System.out.println(msg.toUpperCase());
        System.out.println("**********************************************");
    }

}
