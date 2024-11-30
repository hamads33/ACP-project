/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package possystem;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author hammadi
 */
public class Worker extends User {
    private static Scanner scanner = new Scanner(System.in);
    private ArrayList<Product> currentBill = new ArrayList<>();

    public Worker(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Worker";
    }

    @Override
    public void menu() {
        boolean workerRunning = true;
        while (workerRunning) {
            System.out.println("\nWorker Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Create Bill");
            System.out.println("3. View Bill");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    createBill();
                    break;
                case 3:
                    viewCurrentBill();
                    break;
                case 4:
                    workerRunning = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewProducts() {
        System.out.println("Product List:");
        int index = 1;
        for (Product product : Admin.getProducts()) {
            System.out.println(index + ". " + product);
            index++;
        }
    }

    private void createBill() {
        boolean purchasing = true;

        while (purchasing) {
            viewProducts(); // Display products with numbers
            System.out.print("Enter product number to purchase (or type 'done' to finish): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                purchasing = false;
                continue;
            }

            try {
                int productIndex = Integer.parseInt(input) - 1; 
                Product product = Admin.getProducts().get(productIndex);

                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); 

                if (quantity <= product.getQuantity()) {
                    currentBill.add(new Product(product.getName(), product.getPrice(), quantity));
                    product.setQuantity(product.getQuantity() - quantity);
                    System.out.println("Added " + quantity + " of " + product.getName() + " to the bill.");
                } else {
                    System.out.println("Not enough quantity available.");
                }
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Invalid product number.");
            }
        }
    }

    private void viewCurrentBill() {
        if (currentBill.isEmpty()) {
            System.out.println("No products in the bill.");
            return;
        }

        double total = 0;
        System.out.println("Current Bill:");
        for (Product p : currentBill) {
            total += p.getPrice() * p.getQuantity();
            System.out.println(p.getName() + " - Quantity: " + p.getQuantity() + " - Price: Rs" + p.getPrice());
        }
        System.out.println("Total Amount: Rs" + total);
    }
}
