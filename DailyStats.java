package coffee;

public class DailyStats {
    private int purchaseCount;
    private double totalAmount;

    public void recordPurchase(double amount) {
        purchaseCount++;
        totalAmount += amount;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String toString() {
        return "Total Purchases Today: " + purchaseCount + "\nTotal Amount Generated: Rs. " + totalAmount;
    }
}
