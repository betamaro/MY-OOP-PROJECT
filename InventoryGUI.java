package coffee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InventoryGUI {
    private InventoryManager manager;
    private DailyStats stats;

    public InventoryGUI() {
        manager = new InventoryManager();
        stats = new DailyStats();

        JFrame frame = new JFrame("Coffee Shop Inventory");
        frame.setSize(500, 600);
        frame.setLayout(new GridLayout(0, 1, 10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton addProduct = new JButton("Add Coffee Product");
        JButton removeProduct = new JButton("Remove Product");
        JButton addCustomer = new JButton("Add Customer with Purchase");
        JButton recordPurchase = new JButton("Record Purchase");
        JButton viewProducts = new JButton("View Products");
        JButton viewCustomers = new JButton("View Customers");
        JButton generateReceipt = new JButton("Generate Receipt");
        JButton viewStats = new JButton("View Daily Stats");

        addProduct.addActionListener(e -> {
            try {
                String name = JOptionPane.showInputDialog("Enter product name:");
                String priceStr = JOptionPane.showInputDialog("Enter price:");
                String type = JOptionPane.showInputDialog("Enter type:");
                double price = Double.parseDouble(priceStr);

                manager.addProduct(new Coffee(name, price, type));
                JOptionPane.showMessageDialog(null, "Product added.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input.");
            }
        });

        removeProduct.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter product name to remove:");
            if (name != null && !name.isEmpty()) {
                manager.removeProduct(name);
                JOptionPane.showMessageDialog(null, "Product removed.");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid product name.");
            }
        });

        addCustomer.addActionListener(e -> {
            try {
                String name = JOptionPane.showInputDialog("Enter customer name:");
                String phone = JOptionPane.showInputDialog("Enter customer phone:");
                String coffeeName = JOptionPane.showInputDialog("Enter coffee name:");
                String coffeeType = JOptionPane.showInputDialog("Enter coffee type:");
                String priceStr = JOptionPane.showInputDialog("Enter coffee price:");
                double price = Double.parseDouble(priceStr);

                Coffee coffee = new Coffee(coffeeName, price, coffeeType);
                manager.addProduct(coffee);

                Customer customer = new Customer(name, phone);
                customer.addPurchase(coffee.getName());
                manager.addCustomer(customer);

                manager.saveProducts();
                manager.saveCustomers();
                stats.recordPurchase(price);

                String receipt = "======= Receipt =======\n";
                receipt += "Customer: " + name + "\n";
                receipt += "Phone: " + phone + "\n";
                receipt += "Purchase: " + coffeeName + " (" + coffeeType + ") - Rs." + price + "\n";
                receipt += "=======================\n";

                JOptionPane.showMessageDialog(null, receipt, "Receipt", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input.");
            }
        });

        recordPurchase.addActionListener(e -> {
            try {
                String customerName = JOptionPane.showInputDialog("Enter customer name:");
                String productName = JOptionPane.showInputDialog("Enter product name:");
                manager.recordPurchase(customerName, productName);

                for (Product p : manager.getProducts()) {
                    if (p.getName().equalsIgnoreCase(productName)) {
                        stats.recordPurchase(p.getPrice());
                        break;
                    }
                }

                JOptionPane.showMessageDialog(null, "Purchase recorded.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error recording purchase.");
            }
        });

        viewProducts.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Product p : manager.getProducts()) {
                sb.append(p.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        });

        viewCustomers.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Customer c : manager.getCustomers()) {
                sb.append(c.toString()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        });

        generateReceipt.addActionListener(e -> {
            try {
                String customerName = JOptionPane.showInputDialog("Enter customer name:");
                for (Customer c : manager.getCustomers()) {
                    if (c.getName().equalsIgnoreCase(customerName)) {
                        StringBuilder receipt = new StringBuilder();
                        receipt.append("======= Receipt =======\n");
                        receipt.append("Customer: ").append(c.getName()).append("\n");
                        receipt.append("Phone: ").append(c.getPhone()).append("\n");
                        receipt.append("Purchases:\n");
                        for (String item : c.getPurchases()) {
                            receipt.append(" - ").append(item).append("\n");
                        }
                        receipt.append("=======================\n");
                        JOptionPane.showMessageDialog(null, receipt.toString());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Customer not found.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error generating receipt.");
            }
        });

        viewStats.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, stats.toString(), "Daily Stats", JOptionPane.INFORMATION_MESSAGE);
        });

        frame.add(addProduct);
        frame.add(removeProduct);
        frame.add(addCustomer);
        frame.add(recordPurchase);
        frame.add(viewProducts);
        frame.add(viewCustomers);
        frame.add(generateReceipt);
        frame.add(viewStats);

        frame.setVisible(true);
    }
}
