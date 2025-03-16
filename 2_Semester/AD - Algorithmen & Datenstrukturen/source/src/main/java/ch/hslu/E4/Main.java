package ch.hslu.E4;

public class Main {
    public static void main(String[] args) {
        HashTableArray<Car> hashTableArray = new HashTableArray<>();
        Car car1 = new Car(101, 4, "Honda", "Civic");
        Car car2 = new Car(202, 5, "Ford", "Focus");
        Car car3 = new Car(303, 2, "Chevrolet", "Camaro");
        Car car4 = new Car(404, 7, "Nissan", "Pathfinder");
        Car car5 = new Car(505, 5, "BMW", "X5");
        Car car6 = new Car(606, 4, "Mercedes", "C-Class");
        Car car7 = new Car(707, 2, "Audi", "R8");
        Car car8 = new Car(808, 5, "Hyundai", "Tucson");
        Car car9 = new Car(909, 7, "Kia", "Sorento");
        Car car10 = new Car(1010, 4, "Volkswagen", "Golf");

        hashTableArray.put(car1);
        hashTableArray.put(car2);
        hashTableArray.put(car3);
        hashTableArray.put(car4);
        hashTableArray.put(car5);
        hashTableArray.put(car6);
        hashTableArray.put(car7);
        hashTableArray.put(car8);
        hashTableArray.put(car9);
        hashTableArray.put(car10);

        System.out.println(hashTableArray);

    }
}
