package ch.hslu.E0;

import static ch.hslu.E0.Wagon.getSubsequentPlacesAmount;

public class Demo {
    public static void main(String[] args) {
        Wagon wagon = new Wagon(60, "W001", new Wagon(40, "W002", new Wagon(80, "W003")));
        System.out.println(getSubsequentPlacesAmount(wagon));
    }
}
