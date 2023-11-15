public class Main {
    public static void main(String[] args) {
        quebraLinha("A) Permitir a Criação de uma àrvore AVL \n" +
            "F) Para cada inserção de nó, a arvore deve ser impressa \n" +
            "e o nó mostrar o balanceamento");
        AVLTree<String, Integer> BBST = getAvlTree();

        quebraLinha("B) Os métodos para realizar o balanceamento nesse algoritmo são:");
        System.out.println("update() -> atualiza os dados de cada nó \n" +
            "balance() -> verifica se o nó está precisando de balanceamento \n" +
            "leftRotation() -> aplica o algoritmo de rotação para a esquerda \n" +
            "rightRotation() -> aplica o algoritmo de rotação para a direita \n" +
            "OBS: os algortimos de rotação dupla são feitos utilizando a técnica de rotacionar \n" +
            "um filho primeiro e depois rotacionar o nó principal.");

        quebraLinha("D) TRAVESSIA PÓS, PRÉ E IN ORDER");
        BBST.printTraversal();

        quebraLinha("E) Impressão da árvore balanceada com o fator de balanceamento em cada nó");
        BBST.printTree();

        quebraLinha("C) Fator de Balanceamento Resultante da árvore inteira");
        System.out.println(BBST.balanceFactor());
    }

    private static AVLTree<String, Integer> getAvlTree() {
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
        return BBST;
    }

    public static void quebraLinha(String msg) {
        System.out.println();
        System.out.println("**********************************************");
        System.out.println(msg.toUpperCase());
        System.out.println("**********************************************");
    }

}
