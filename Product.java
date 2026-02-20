package coffee;

import java.io.Serializable;

public class Product implements Serializable {
    protected String name;
    protected double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    public String toString() {
        return name + " - Rs. " + price;
    }
}
