public class Main {
    public static void main(String[] args) {
        ArvoreBinariaBusca<String, Integer> BST = new ArvoreBinariaBusca<>();
        BST.put("F", 1);
        BST.put("C", 2);
        BST.put("I", 3);
        BST.put("A", 4);
        BST.put("B", 5);
        BST.put("G", 6);
        BST.put("H", 7);
        BST.put("D", 8);
        BST.put("E", 9);
        BST.put("J", 10);
        BST.put("K", 11);

        // Dados de qualquer nó
        quebraLinha("ME parte 1");

        BST.printNodeData("K");

        //Percursos Pré, pós e in-ordem
        quebraLinha("ME PARTE 2");
        quebraLinha("Método 1 - Percorrer Árvore");
        BST.printTraversal();

        //Desenho da Árvore
        quebraLinha("Método 2 - Identação da Árvore");

        BST.printTree();

        //Numero de Nós
        quebraLinha("Método 3 - Tamanho da Árvore");

        System.out.println("Número de nós na árvore com size(): " + BST.size());
        System.out.println("Número de nós da árvore com recursiveCount(): " + BST.recursiveCount());

        // menor elemento da arvore
        quebraLinha("Método 4 - Menor Elemento na Árvore");

        System.out.println("Menor Elemento: " + BST.min());

        // maior elemento da arvore
        quebraLinha("Método 5 - Maior elemento na Árvore");

        System.out.println("Maior Elemento: " + BST.max());

        // Classificar como binária de busca ou binária
        quebraLinha("Método 6 - Classificar Árvore");

        // Quantos nós tem somente um filho
        quebraLinha("Método 7 - Localizar Nós com somente um filho");

        System.out.println("Nós com somente um filho: " + BST.sizeNodesWithOnlyOneChild() );
        System.out.println("Esses Nós são: " + BST.printNodesWithOnlyOneChild());
        }

        public static void quebraLinha(String msg) {
            System.out.println();
            System.out.println("**********************************************");
            System.out.println(msg.toUpperCase());
            System.out.println("**********************************************");
        }

    }