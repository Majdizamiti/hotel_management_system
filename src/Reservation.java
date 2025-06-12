import java.time.LocalDate;

public class Reservation {
    private final Client client;
    private final Room room;
    private final LocalDate checkIn;
    private final LocalDate checkOut;


    public Reservation(Client client, Room room, LocalDate checkIn, LocalDate checkOut, String hotelName) throws ClientException {
        if (client == null || room == null ) {
            throw new ClientException("Invalid reservation details: Client, Room, or Property Type cannot be null/empty!");
        }
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new ClientException("Check-out date must be after check-in date!");
        }

        this.client = client;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;

    }


    public Room getRoom() { return room; }
    public LocalDate getCheckOut() { return checkOut; }
    public LocalDate getCheckIn() { return checkIn; }
    public Client getClient() { return client; }

    public double getTotalPrice() {
        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        return nights * room.getPricePerNight();
    }
    @Override
    public String toString() {
        return "Reservation{client=" + client.getName() +
                ", room=" + room.getNumber() +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}
