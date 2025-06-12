

import java.util.*;

public final class Ledger {
    private final List<Asset> assets = new ArrayList<>();

    public void add(Asset asset) {
        assets.add(Objects.requireNonNull(asset));
    }

    public void remove(Asset asset) {
        if (!assets.remove(asset)) {
            throw new IllegalStateException("Asset not found in ledger");
        }
    }

    public double totalIncome(boolean withTax) {
        return assets.stream()
                .mapToDouble(a -> withTax ? a.calculateIncomeWithTax() : a.income())
                .sum();
    }

    public int numberOfPalaces() {
        return (int) assets.stream().filter(Asset::isPalace).count();
    }

    public Map<String, List<Asset>> assetsByStreetName() {
        Map<String, List<Asset>> map = new HashMap<>();
        assets.forEach(a -> map.computeIfAbsent(a.streetName(), k -> new ArrayList<>()).add(a));
        return map;
    }

    public static boolean sameAssets(Ledger ledger1, Ledger ledger2) {
        return new HashSet<>(ledger1.assets).equals(new HashSet<>(ledger2.assets));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        assets.forEach(a -> sb.append(a).append("\n"));
        return sb.toString();
    }
}