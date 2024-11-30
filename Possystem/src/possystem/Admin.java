/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package possystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hammadi
 */
public class Admin extends User {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Product> products = new ArrayList<>();
    private static final String PRODUCT_FILE = "products.txt";

    public Admin(String username, String password) {
        super(username, password);
        loadProducts();
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public void menu() {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
             System.out.println("3. Delete Product");
              System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                case 4:
            
            {
                try {
                    saveProducts(); // Save products before logging out
                } catch (IOException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                
                    adminRunning = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addProduct() {
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter product price (in Rs): ");
        double productPrice = scanner.nextDouble();
        System.out.print("Enter product quantity: ");
        int productQuantity = scanner.nextInt();
        products.add(new Product(productName, productPrice, productQuantity));
        System.out.println("Product added successfully.");
    }
    private void updateProduct(){
        System.out.println("Enter product number to update");
        int Index=scanner.nextInt()-1;
        if(Index<0||Index>=products.size()){
            System.out.println("Invalid Selection");  
        }
      
            Product product=products.get(Index);
            System.out.println("Updating product: " + product.getName());
        System.out.print("Enter new product name : ");
        String newName = scanner.next();
        if (!newName.isEmpty()) {
            product.setName(newName);
        }
        System.out.print("Enter new product price : ");
        double newPrice = scanner.nextDouble();
        if (newPrice >= 0) {
            product.setPrice(newPrice);
        }
         System.out.print("Enter new product quantity : ");
        int newQuantity = scanner.nextInt();
        if (newQuantity >= 0) {
            product.setQuantity(newQuantity);
        }
          System.out.println("Product updated successfully.");
    }
    private void deleteProduct() {
        System.out.print("Enter the product number to delete: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); 

        if (index < 0 || index >= products.size()) {
            System.out.println("Invalid product number.");
            return;
        }

        products.remove(index);
        System.out.println("Product deleted successfully.");
    }

    
    public static ArrayList<Product> getProducts() {
        return products;
    }
    public static void saveProducts() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE))) {
            for (Product product : products) {
                writer.write(product.getName() + "," + product.getPrice() + "," + product.getQuantity());
                writer.newLine();
            }
            System.out.println("Products saved successfully.");
        }    }

    public static void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                products.add(new Product(name, price, quantity));
            }
            System.out.println("Products loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }
}
