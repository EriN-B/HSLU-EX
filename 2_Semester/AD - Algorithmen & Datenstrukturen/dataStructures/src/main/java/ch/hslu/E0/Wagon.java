package ch.hslu.E0;

public class Wagon {
    private int seats;
    private String id;
    private Wagon subsequentWagon;

    public Wagon(int seats, String id, Wagon subsequentWagon) {
        this.seats = seats;
        this.id = id;
        this.subsequentWagon = subsequentWagon;
    }

    public Wagon(int seats, String id) {
        this.seats = seats;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wagon wagon = (Wagon) o;
        return id == wagon.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public int getSeats() {
        return seats;
    }

    public String getId() {
        return id;
    }

    public Wagon getSubsequentWagon() {
        if(subsequentWagon == null){
            return null;
        }
        return subsequentWagon;
    }

    public void setSubsequentWagon(Wagon subsequentWagon) {
        this.subsequentWagon = subsequentWagon;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static int getSubsequentPlacesAmount(Wagon wagon) {
        int totalSeats = 0;

        while (wagon != null) {
            totalSeats += wagon.getSeats();
            wagon = wagon.getSubsequentWagon();
        }

        return totalSeats;
    }
}
