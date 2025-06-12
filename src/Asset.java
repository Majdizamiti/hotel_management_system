public interface Asset {
    double getIncome();
    String getStreetName();
    double calculateIncomeWithTax();
    boolean isPalace();
    @Override
    String toString();
}

