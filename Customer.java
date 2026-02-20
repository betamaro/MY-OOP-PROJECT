package coffee;

import java.util.*;

import java.util.*;

public class Customer {
    private String name;
    private String phone;
    private List<String> purchases;

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.purchases = new ArrayList<>();
    }

    public void addPurchase(String product) {
        purchases.add(product);
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public List<String> getPurchases() { return purchases; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer: ").append(name).append("\n");
        sb.append("Phone: ").append(phone).append("\n");
        sb.append("Purchases: ").append(purchases.toString());
        return sb.toString();
    }
}
