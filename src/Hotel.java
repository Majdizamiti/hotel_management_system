public class Hotel implements Asset {
    private final String name;
    private final double income;
    private final String streetName;

    public Hotel(String name, double income, String streetName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Hotel name cannot be null or empty.");
        }
        if (income < 0) {
            throw new IllegalArgumentException("Income cannot be negative.");
        }
        if (streetName == null || streetName.trim().isEmpty()) {
            throw new IllegalArgumentException("Street name cannot be null or empty.");
        }

        this.name = name;
        this.income = income;
        this.streetName = streetName;
    }

    @Override
    public double getIncome() { return income; }

    @Override
    public String getStreetName() { return streetName; }

    @Override
    public double calculateIncomeWithTax() { return income * 1.10; }

    @Override
    public boolean isPalace() { return false; }

    public String getName() { return name; }

    @Override
    public String toString() {
        return "Hotel{name='" + name + "', income=" + income + ", street='" + streetName + "'}";
    }
}
