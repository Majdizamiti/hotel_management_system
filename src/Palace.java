public class Palace implements Asset {
    private final String name;
    private final double income;
    private final String streetName;
    private final boolean isVip;

    public Palace(String name, double income, String streetName, boolean isVip) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Palace name cannot be null or empty.");
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
        this.isVip = isVip;
    }

    @Override
    public double getIncome() { return income; }

    @Override
    public String getStreetName() { return streetName; }

    @Override
    public double calculateIncomeWithTax() { return isVip ? income * 1.15 : income * 1.10; }

    @Override
    public boolean isPalace() { return true; }

    public String getName() { return name; }

    public boolean isVip() { return isVip; }

    @Override
    public String toString() {
        return "Palace{name='" + name + "', VIP=" + isVip + ", income=" + income + ", street='" + streetName + "'}";
    }
}
