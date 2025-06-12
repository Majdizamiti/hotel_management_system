public interface Asset {
    double income();
    String streetName();
    double calculateIncomeWithTax();
    boolean isPalace();
    @Override
    String toString();
}
