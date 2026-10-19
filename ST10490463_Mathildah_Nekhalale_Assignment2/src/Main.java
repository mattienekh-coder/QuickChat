/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Mathildah
 */
import java.util.Scanner;

/**
 * Main class for the QuickChat messaging application.
 */
public class Main {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            int messageNumber = 0;
            
            System.out.println(" ");
            System.out.println("QUICKCHAT");
            System.out.println(" ");
            
            while (running) {
                
                System.out.println("\n1. Send Message");
                System.out.println("2. Show Total Messages Sent");
                System.out.println("3. Quit");
                System.out.print("Choose an option: ");
                
                String input = scanner.nextLine();
                
                switch (input) {
                    
                    case "1" -> {
                        System.out.print(
                                "Enter recipient number (+27...): "
                        );
                        
                        String recipient = scanner.nextLine();
                        
                        System.out.print("Enter your message: ");
                        
                        String messageText = scanner.nextLine();
                        
                        Message message = new Message(
                                ++messageNumber,
                                recipient,
                                messageText
                        );
                        
                        if (!message.checkRecipientCell()) {
                            System.out.println(
                                    "Cell phone number is incorrectly formatted."
                            );
                            break;
                        }
                        
                        if (!message.checkMessageLength()) {
                            System.out.println(
                                    "Message exceeds 250 characters."
                            );
                            break;
                        }
                        
                        System.out.println("\nChoose an action:");
                        System.out.println("1. Send Message");
                        System.out.println("2. Store Message");
                        System.out.println("3. Disregard Message");
                        System.out.print("Choice: ");
                        
                        try {
                            
                            int choice = Integer.parseInt(
                                    scanner.nextLine()
                            );
                            
                            String result = message.sendMessage(choice);
                            
                            System.out.println(result);
                            
                            if (choice == 1 || choice == 2) {
                                
                                System.out.println(
                                        "Message ID: "
                                                + message.getMessageID()
                                );
                                
                                System.out.println(
                                        "Message Hash: "
                                                + message.getMessageHash()
                                );
                            }
                            
                        } catch (NumberFormatException e) {
                            
                            System.out.println(
                                    "Please enter a valid number."
                            );
                        }
                    }
                        
                    case "2" -> System.out.println(
                                "Total messages sent: "
                                        + Message.returnTotalMessages()
                        );
                        
                    case "3" -> {
                        running = false;
                        
                        System.out.println(
                                "Thank you for using QuickChat."
                        );
                    }
                        
                    default -> System.out.println(
                                "Invalid option. Please choose 1, 2 or 3."
                        );
                }
            }
        }
    }
}