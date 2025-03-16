package ch.hslu.E4.HashTable;

public class Main {
    public static void main(String[] args) {
        HashTableArray<Car> hashTableArray = new HashTableArray<>(14);
        Car car1 = new Car(101, 4, "Honda", "Civic");
        Car car2 = new Car(202, 5, "Ford", "Focus");
        Car car3 = new Car(303, 2, "Chevrolet", "Camaro");
        Car car4 = new Car(111, 2, "AC", "AA");


        hashTableArray.put(car1);
        hashTableArray.put(car2);
        hashTableArray.put(car3);
        System.out.println(hashTableArray);
        hashTableArray.remove(car2);
        hashTableArray.remove(car3);
        hashTableArray.put(car3);
        System.out.println(hashTableArray);
    }
}
