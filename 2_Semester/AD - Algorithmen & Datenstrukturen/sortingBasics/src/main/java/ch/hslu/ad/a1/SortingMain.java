package ch.hslu.ad.a1;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.hslu.ad.a1.animation.SortingAnimation;

public class SortingMain {
    /**
     * Liefert ein Array mit den Zahlen 1 bis size in zufälliger Reihenfolge.
     *
     * @param size die Anzahl der Zahlen
     *
     */
    static int[] getShuffledNumbers(int size) {
        List<Integer> numbers = IntStream.range(1, size + 1).boxed().collect(Collectors.toList());
        Collections.shuffle(numbers);
        return numbers.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Liefert ein Array mit den Zahlen 1 bis size in aufsteigender Reihenfolge.
     *
     * @param size die Anzahl der Zahlen
     */
    static int[] getAscendingNumbers(int size) {
        return IntStream.range(1, size + 1).toArray();
    }

    /**
     * Liefert ein Array mit den Zahlen 1 bis size in absteigender Reihenfolge.
     *
     * @param size die Anzahl der Zahlen
     */
    static int[] getDescendingNumbers(int size) {
        return IntStream.range(1, size + 1).map(i -> size - i + 1).toArray();
    }

    public static void main(String[] args) {
        // Beispiel: Zahlen von 1 bis 50 in zufälliger Reihenfolge
        int[] numbers = getShuffledNumbers(50);

        // Für die Nutzung der Animation hier zwei Beispiele, die aber
        // noch nichts mit Sortieralgorithmen zu tun haben.

        // Demo 1 - 10 mal zufällige Zahlen vertauschen
        for (int i = 0; i < 10; i++) {
            // zwei zufällige Indizes auswählen
            int index1 = (int) (Math.random() * numbers.length);
            int index2 = (int) (Math.random() * numbers.length);

            // vertauschen ...
            int temp = numbers[index1];
            numbers[index1] = numbers[index2];
            numbers[index2] = temp;

            // Zustand des Arrays anzeigen, 1s warten
            SortingAnimation.instance().showArray(numbers, 1000);
        }

        // Demo 2 - 10 mal zufälligen Index hervorheben
        for (int i = 0; i < 10; i++) {
            // zufälligen Index auswählen
            int index = (int) (Math.random() * numbers.length);

            // Zustand des Arrays anzeigen mit Hervorhebung, 1s warten
            SortingAnimation.instance().showArray(numbers, 1000, index);
        }
    }
}
