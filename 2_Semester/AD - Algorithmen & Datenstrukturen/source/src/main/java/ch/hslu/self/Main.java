package ch.hslu.self;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>(1);
        tree.getRoot().addChild(new Node<>(2));
        tree.getRoot().addChild(new Node<>(3));
        tree.getRoot().getChildren().getLast().addChild(new Node<>(4));


    }
}
