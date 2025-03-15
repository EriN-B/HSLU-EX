package ch.hslu.E4;

public class Main {
    public static void main(String[] args) {
        HashTableArray<Car> hashTableArray = new HashTableArray<>();
        Car car = new Car(123,5,"Toyota", "Verso");

        hashTableArray.put(car);
        hashTableArray.remove(car);

        System.out.println(hashTableArray);

    }
}
