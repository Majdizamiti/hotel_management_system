

public final class Hotel implements Asset {
    private final double income;
    private final String streetName;

    public Hotel(double income, String streetName) {
        if (income < 0) throw new IllegalArgumentException("Income cannot be negative");
        if (streetName == null) throw new NullPointerException("Street name cannot be null");
        this.income = income;
        this.streetName = streetName;
    }

    @Override
    public double income() { return income; }

    @Override
    public String streetName() { return streetName; }

    @Override
    public double calculateIncomeWithTax() {
        return (income > 10_000) ? income * 0.8 : income;
    }

    @Override
    public boolean isPalace() { return false; }

    @Override
    public String toString() {
        return streetName + " = " + income + " (Hotel)";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Hotel)) return false;
        Hotel other = (Hotel) obj;
        return streetName.equals(other.streetName);
    }
}
