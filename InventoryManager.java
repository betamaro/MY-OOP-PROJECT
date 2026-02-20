package coffee;

import java.io.*;
import java.util.*;

public class InventoryManager {
    private List<Product> products;
    private List<Customer> customers;
    private final String productFile = "products.dat";
    private final String customerFile = "customers.dat";

    private int dailyPurchases = 0;
    private double dailyTotal = 0;

    public InventoryManager() {
        products = loadProducts();
        customers = loadCustomers();
    }

    public void addProduct(Product p) {
        if (p != null) {
            products.add(p);
            saveProducts();
        }
    }

    public void removeProduct(String name) {
        if (name != null && !name.isEmpty()) {
            products.removeIf(p -> p.getName().equalsIgnoreCase(name));
            saveProducts();
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addCustomer(Customer c) {
        if (c != null) {
            customers.add(c);
            saveCustomers();
        }
    }

    public void recordPurchase(String customerName, String productName) {
        if (customerName == null || productName == null) return;

        for (Customer c : customers) {
            if (c.getName().equalsIgnoreCase(customerName)) {
                c.addPurchase(productName);
                Product purchased = findProductByName(productName);
                if (purchased != null) {
                    dailyPurchases++;
                    dailyTotal += purchased.getPrice();
                }
                break;
            }
        }
        saveCustomers();
    }

    private Product findProductByName(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public int getDailyPurchases() {
        return dailyPurchases;
    }

    public double getDailyTotal() {
        return dailyTotal;
    }

    public void saveProducts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(productFile))) {
            oos.writeObject(products);
        } catch (Exception e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }

    public List<Product> loadProducts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(productFile))) {
            return (List<Product>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void saveCustomers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(customerFile))) {
            oos.writeObject(customers);
        } catch (Exception e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }

    public List<Customer> loadCustomers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(customerFile))) {
            return (List<Customer>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
