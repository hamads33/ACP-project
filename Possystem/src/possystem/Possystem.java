/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package possystem;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
/**
 *
 * @author hammadi
 */
public class Possystem {
    private static ArrayList<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String USER_FILE = "users.txt";

    public static void main(String[] args) {
        loadUsers(); 
        //System.out.println("Current working directory: " + new File(".").getAbsolutePath());

        while (true) {
            System.out.println("1. Register Admin");
            System.out.println("2. Register Worker");
            System.out.println("3. Login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    registerAdmin();
                    break;
                case 2:
                    registerWorker();
                    break;
                case 3:
                    login();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    saveUsers(); // Save users before exiting
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerAdmin() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        users.add(new Admin(username, password));
        System.out.println("Registered successfully.");
    }

    private static void registerWorker() {
        System.out.print("Enter worker username: ");
        String username = scanner.nextLine();
        System.out.print("Enter worker password: ");
        String password = scanner.nextLine();
        users.add(new Worker(username, password));
        System.out.println("Registered successfully.");
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = findUser(username, password);

        if (loggedInUser != null) {
            System.out.println(loggedInUser.getUsername() + " logged in successfully as " + loggedInUser.getRole() + ".");
            loggedInUser.menu();
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    private static User findUser(String username, String password) {
        for (User user : users) {
            if (user.login(username, password)) {
                return user;
            }
        }
        return null; 
    }

    private static void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                writer.newLine();
            }
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String password = parts[1];
                String role = parts[2];
                if (role.equals("Admin")) {
                    users.add(new Admin(username, password));
                } else if (role.equals("Worker")) {
                    users.add(new Worker(username, password));
                }
            }
            System.out.println("Users loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }
}
