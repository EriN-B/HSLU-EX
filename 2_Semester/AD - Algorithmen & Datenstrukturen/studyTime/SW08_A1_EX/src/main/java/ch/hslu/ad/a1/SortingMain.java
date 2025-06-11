package ch.hslu.ad.a1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import a2.FixedSizeHeap;
import a2.IntegerHeap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortingMain {


    private static Logger log =LoggerFactory.getLogger(SortingMain.class);

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

        FixedSizeHeap h = new FixedSizeHeap(6);

        h.insert(20);
        h.insert(10);
        h.insert(5);
        h.insert(12);
        h.insert(7);
        h.insert(50);
        System.out.println(h);

        h.sort();

        /*
        int[] sizes = {100_000_000};




        for(int size: sizes){
            int[] numbers = getShuffledNumbers(size);
            log.info("{} Elements", size);
            ArraySorter.quickSort(numbers, 0, numbers.length-1);
        }

         */
    }
}



class test {
    static boolean lol;

    public static void main(String[] args) {
        System.out.println(lol);
    }
}
