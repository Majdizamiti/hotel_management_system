import java.util.ArrayList;
import java.util.List;

public class Ledger {
    private final List<Asset> assets = new ArrayList<>();

    // Add an asset to the ledger
    public void add(Asset asset) {
        if (asset == null) throw new IllegalArgumentException("Cannot add a null asset!");
        assets.add(asset);
    }

    // Remove an asset by name
    public boolean removeByName(String name) {
        return assets.removeIf(asset -> asset.toString().contains(name));
    }

    // Search for an asset by name
    public Asset findByName(String name) {
        return assets.stream()
                .filter(asset -> asset.toString().contains(name))
                .findFirst()
                .orElse(null);
    }

    // Calculate total income (optionally with tax)
    public double totalIncome(boolean withTax) {
        return assets.stream()
                .mapToDouble(asset -> withTax ? asset.calculateIncomeWithTax() : asset.getIncome())
                .sum();
    }

    // Count palaces in the ledger
    public long numberOfPalaces() {
        return assets.stream().filter(Asset::isPalace).count();
    }

    // Display all assets
    @Override
    public String toString() {
        if (assets.isEmpty()) return "No assets in the ledger.";
        StringBuilder sb = new StringBuilder("=== LEDGER ===\n");
        assets.forEach(asset -> sb.append(asset).append("\n"));
        return sb.toString();
    }

    // Retrieve all assets (for display purposes)
    public List<Asset> getAssets() {
        return new ArrayList<>(assets);
    }
}
