public final class Room {
    private final int number;
    private final String type; // "Standard", "Deluxe", "Suite"
    private final double pricePerNight;

    public Room(int number, String type, double pricePerNight) {
        if (number <= 0) throw new IllegalArgumentException("Room number must be positive");
        if (pricePerNight < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.number = number;
        this.type = type;
        this.pricePerNight = pricePerNight;
    }

    public int getNumber() { return number; }
    public String getType() { return type; }
    public double getPricePerNight() { return pricePerNight; }

    @Override
    public String toString() {
        return "Room #" + number + " (" + type + ") - " + pricePerNight + "€/night";
    }
}
