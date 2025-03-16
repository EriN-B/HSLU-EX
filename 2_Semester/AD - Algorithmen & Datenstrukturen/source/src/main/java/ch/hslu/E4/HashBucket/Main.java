package ch.hslu.E4.HashBucket;

public class Main {
    public static void main(String[] args) {
        HashBucket<Integer> hashBucket = new HashBucket<>(10);

        hashBucket.put(1);
        hashBucket.put(2);
        hashBucket.put(3);
        hashBucket.put(4);
        hashBucket.put(5);
        hashBucket.put(6);
        hashBucket.put(7);
        hashBucket.put(8);
        hashBucket.put(9);
        hashBucket.put(10);
        hashBucket.put(11);
        hashBucket.put(12);
        hashBucket.put(13);
        hashBucket.put(21);

        System.out.println(hashBucket);
    }
}
