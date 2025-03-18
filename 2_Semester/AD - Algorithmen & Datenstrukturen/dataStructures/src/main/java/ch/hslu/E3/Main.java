package ch.hslu.E3;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>(10);
        tree.insert(5);
        tree.insert(6);
        tree.insert(11);
        tree.insert(2);

        System.out.println(tree);
    }
}
