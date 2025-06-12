import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final Ledger ledger = new Ledger();
    private static final List<Room> rooms = new ArrayList<>();
    private static final List<Client> clients = new ArrayList<>();
    private static final List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        // Sample data (optional)
        rooms.add(new Room(101, "Standard", 100));
        rooms.add(new Room(102, "Deluxe", 200));
        clients.add(new Client("Admin", "admin@hotel.com"));

        JFrame frame = new JFrame("Hotel Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Admin (Asset Management)
        tabbedPane.addTab("Admin", buildAdminPanel());

        // Tab 2: Clients
        tabbedPane.addTab("Clients", buildClientPanel());

        // Tab 3: Rooms
        tabbedPane.addTab("Rooms", buildRoomPanel());

        // Tab 4: Reservations
        tabbedPane.addTab("Reservations", buildReservationPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    // ====================== ADMIN PANEL (HOTELS/PALACES) ======================
    private static JPanel buildAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JComboBox<String> assetType = new JComboBox<>(new String[]{"Hotel", "Palace", "Palace VIP"});
        JTextField incomeField = new JTextField();
        JTextField streetField = new JTextField();

        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(assetType);
        inputPanel.add(new JLabel("Income:"));
        inputPanel.add(incomeField);
        inputPanel.add(new JLabel("Street:"));
        inputPanel.add(streetField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Asset");
        JButton showButton = new JButton("Show Ledger");
        JButton totalNoTaxButton = new JButton("Total Income (No Tax)");
        JButton totalWithTaxButton = new JButton("Total Income (With Tax)");
        JButton countPalacesButton = new JButton("Count Palaces");

        addButton.addActionListener(e -> {
            try {
                double income = Double.parseDouble(incomeField.getText());
                String street = streetField.getText();
                Asset asset;
                switch (assetType.getSelectedIndex()) {
                    case 0 -> asset = new Hotel(income, street);
                    case 1 -> asset = new Palace(income, street, false);
                    case 2 -> asset = new Palace(income, street, true);
                    default -> throw new IllegalArgumentException("Invalid type");
                }
                ledger.add(asset);
                outputArea.append("Added: " + asset + "\n");
            } catch (Exception ex) {
                outputArea.append("❌ Error: " + ex.getMessage() + "\n");
            }
        });

        showButton.addActionListener(e -> outputArea.setText("=== LEDGER ===\n" + ledger));
        totalNoTaxButton.addActionListener(e -> outputArea.append("Total Income (No Tax): " + ledger.totalIncome(false) + "\n"));
        totalWithTaxButton.addActionListener(e -> outputArea.append("Total Income (With Tax): " + ledger.totalIncome(true) + "\n"));
        countPalacesButton.addActionListener(e -> outputArea.append("Palaces Count: " + ledger.numberOfPalaces() + "\n"));

        buttonPanel.add(addButton);
        buttonPanel.add(showButton);
        buttonPanel.add(totalNoTaxButton);
        buttonPanel.add(totalWithTaxButton);
        buttonPanel.add(countPalacesButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ====================== CLIENT MANAGEMENT ======================
    private static JPanel buildClientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Client");
        JButton deleteButton = new JButton("Delete Client");
        JButton searchButton = new JButton("Search by Email");
        JButton showAllButton = new JButton("Show All Clients");

        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            if (name.isEmpty() || email.isEmpty()) {
                outputArea.append("❌ Name/Email cannot be empty!\n");
                return;
            }
            clients.add(new Client(name, email));
            outputArea.append("✅ Added: " + name + " (" + email + ")\n");
        });

        deleteButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            boolean removed = clients.removeIf(c -> c.getEmail().equals(email));
            outputArea.append(removed ? "✅ Deleted: " + email + "\n" : "❌ Client not found!\n");
        });

        searchButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            clients.stream()
                    .filter(c -> c.getEmail().equals(email))
                    .findFirst()
                    .ifPresentOrElse(
                            c -> outputArea.append("🔍 Found: " + c + "\n"),
                            () -> outputArea.append("❌ Client not found!\n"));
        });

        showAllButton.addActionListener(e -> {
            outputArea.setText("=== CLIENTS ===\n");
            clients.forEach(c -> outputArea.append(c + "\n"));
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(showAllButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ====================== ROOM MANAGEMENT ======================
    private static JPanel buildRoomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField numberField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField priceField = new JTextField();

        inputPanel.add(new JLabel("Room #:"));
        inputPanel.add(numberField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeField);
        inputPanel.add(new JLabel("Price/Night:"));
        inputPanel.add(priceField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Room");
        JButton deleteButton = new JButton("Delete Room");
        JButton searchButton = new JButton("Search by #");
        JButton showAllButton = new JButton("Show All Rooms");

        addButton.addActionListener(e -> {
            try {
                int number = Integer.parseInt(numberField.getText());
                String type = typeField.getText().trim();
                double price = Double.parseDouble(priceField.getText());
                rooms.add(new Room(number, type, price));
                outputArea.append("✅ Added: Room #" + number + "\n");
            } catch (Exception ex) {
                outputArea.append("❌ Invalid input!\n");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int number = Integer.parseInt(numberField.getText());
                boolean removed = rooms.removeIf(r -> r.getNumber() == number);
                outputArea.append(removed ? "✅ Deleted: Room #" + number + "\n" : "❌ Room not found!\n");
            } catch (Exception ex) {
                outputArea.append("❌ Invalid room number!\n");
            }
        });

        searchButton.addActionListener(e -> {
            try {
                int number = Integer.parseInt(numberField.getText());
                rooms.stream()
                        .filter(r -> r.getNumber() == number)
                        .findFirst()
                        .ifPresentOrElse(
                                r -> outputArea.append("🔍 Found: " + r + "\n"),
                                () -> outputArea.append("❌ Room not found!\n"));
            } catch (Exception ex) {
                outputArea.append("❌ Invalid room number!\n");
            }
        });

        showAllButton.addActionListener(e -> {
            outputArea.setText("=== ROOMS ===\n");
            rooms.forEach(r -> outputArea.append(r + "\n"));
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(showAllButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ====================== RESERVATION SYSTEM ======================
    private static JPanel buildReservationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField clientEmailField = new JTextField();
        JTextField roomNumberField = new JTextField();
        JTextField checkInField = new JTextField(LocalDate.now().toString());
        JTextField checkOutField = new JTextField(LocalDate.now().plusDays(1).toString());

        inputPanel.add(new JLabel("Client Email:"));
        inputPanel.add(clientEmailField);
        inputPanel.add(new JLabel("Room #:"));
        inputPanel.add(roomNumberField);
        inputPanel.add(new JLabel("Check-In (YYYY-MM-DD):"));
        inputPanel.add(checkInField);
        inputPanel.add(new JLabel("Check-Out (YYYY-MM-DD):"));
        inputPanel.add(checkOutField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton bookButton = new JButton("Book Room");
        JButton showReservationsButton = new JButton("Show All Reservations");
        JButton showAvailableButton = new JButton("Show Available Rooms");

        bookButton.addActionListener(e -> {
            try {
                String email = clientEmailField.getText().trim();
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                LocalDate checkIn = LocalDate.parse(checkInField.getText());
                LocalDate checkOut = LocalDate.parse(checkOutField.getText());

                // Validate dates
                if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
                    throw new ClientException("Check-out date must be after check-in date!");
                }

                Client client = clients.stream()
                        .filter(c -> c.getEmail().equals(email))
                        .findFirst()
                        .orElseThrow(() -> new ClientException("Client not found with email: " + email));

                Room room = rooms.stream()
                        .filter(r -> r.getNumber() == roomNumber)
                        .findFirst()
                        .orElseThrow(() -> new ClientException("Room #" + roomNumber + " not found!"));

                // Check if room is already booked for these dates
                boolean isBooked = reservations.stream()
                        .anyMatch(r -> r.getRoom().getNumber() == roomNumber &&
                                !(checkOut.isBefore(r.getCheckIn()) || checkIn.isAfter(r.getCheckOut())));

                if (isBooked) {
                    throw new ClientException("Room #" + roomNumber + " is already booked for the selected dates!");
                }

                Reservation reservation = new Reservation(client, room, checkIn, checkOut);
                reservations.add(reservation);
                outputArea.append("✅ Booked: " + reservation + "\n");
                outputArea.append("💰 Total: " + reservation.getTotalPrice() + "€\n\n");
            } catch (DateTimeParseException ex) {
                outputArea.append("❌ Invalid date format! Use YYYY-MM-DD.\n");
            } catch (ClientException ex) {
                outputArea.append("❌ Booking Error: " + ex.getMessage() + "\n");
            } catch (Exception ex) {
                outputArea.append("❌ Unexpected Error: " + ex.getMessage() + "\n");
            }
        });

        showReservationsButton.addActionListener(e -> {
            outputArea.setText("=== RESERVATIONS ===\n");
            reservations.forEach(r -> outputArea.append(r + "\n"));
        });

        showAvailableButton.addActionListener(e -> {
            try {
                LocalDate checkIn = LocalDate.parse(checkInField.getText());
                LocalDate checkOut = LocalDate.parse(checkOutField.getText());

                outputArea.setText("=== AVAILABLE ROOMS ===\n");
                rooms.stream()
                        .filter(room -> reservations.stream()
                                .noneMatch(res -> res.getRoom().getNumber() == room.getNumber() &&
                                        !(checkOut.isBefore(res.getCheckIn()) || checkIn.isAfter(res.getCheckOut()))))
                        .forEach(room -> outputArea.append(room + "\n"));
            } catch (DateTimeParseException ex) {
                outputArea.append("❌ Invalid date format! Use YYYY-MM-DD.\n");
            } catch (Exception ex) {
                outputArea.append("❌ Error: " + ex.getMessage() + "\n");
            }
        });

        buttonPanel.add(bookButton);
        buttonPanel.add(showReservationsButton);
        buttonPanel.add(showAvailableButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}