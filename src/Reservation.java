import java.time.LocalDate;

public final class Reservation {
    private final Client client;
    private final Room room;
    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public Reservation(Client client, Room room, LocalDate checkIn, LocalDate checkOut) {
        if (checkOut.isBefore(checkIn)) throw new IllegalArgumentException("Invalid dates");
        this.client = client;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Client getClient() { return client; }
    public Room getRoom() { return room; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }

    public double getTotalPrice() {
        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        return nights * room.getPricePerNight();
    }

    @Override
    public String toString() {
        return client + " → " + room + " (" + checkIn + " to " + checkOut + ")";
    }
}