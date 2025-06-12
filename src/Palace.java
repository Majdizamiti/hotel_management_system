

public final class Palace implements Asset {
    private final double income;
    private final String streetName;
    private final boolean vip;

    public Palace(double income, String streetName, boolean vip) {
        if (income < 0) throw new IllegalArgumentException("Income cannot be negative");
        if (streetName == null) throw new NullPointerException("Street name cannot be null");
        this.income = income;
        this.streetName = streetName;
        this.vip = vip;
    }

    @Override
    public double income() { return income; }

    @Override
    public String streetName() { return streetName; }

    @Override
    public double calculateIncomeWithTax() {
        double threshold = vip ? 100_000 : 50_000;
        return (income > threshold) ? income * 0.95 : income;
    }

    @Override
    public boolean isPalace() { return true; }

    @Override
    public String toString() {
        return streetName + " = " + income + " (Palace" + (vip ? " VIP)" : ")");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Palace)) return false;
        Palace other = (Palace) obj;
        return streetName.equals(other.streetName) && vip == other.vip;
    }
}
