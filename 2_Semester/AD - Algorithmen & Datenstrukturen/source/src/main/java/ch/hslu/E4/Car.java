package ch.hslu.E4;

import java.util.Objects;

public class Car {
    private final int serialNumber;
    private final int seats;
    private final String brand;
    private final String model;

    public Car(final int serialNumber, final int seats, final String brand, final String model) {
        this.serialNumber = serialNumber;
        this.seats = seats;
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s)", brand, model, serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, seats, brand, model);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Car car = (Car) obj;
        return
                serialNumber == car.serialNumber
                        && Objects.equals(brand, car.brand)
                        && Objects.equals(model, car.model);
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public int getSeats() {
        return seats;
    }

    public String getBrand() {
        return brand;
    }

}
