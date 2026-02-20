package coffee;

public class Coffee extends Product {
    private String type;

    public Coffee(String name, double price, String type) {
        super(name, price);
        this.type = type;
    }

    public String getType() { return type; }

    public String toString() {
        return name + " (" + type + ") - Rs. " + price;
    }
}
