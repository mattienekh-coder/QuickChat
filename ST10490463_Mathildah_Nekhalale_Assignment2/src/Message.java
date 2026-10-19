/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mathildah
 */
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Represents a message in the QuickChat application.
 */
public final class Message {

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;

    private String messageHash;
    private String status;

    private static int totalMessagesSent = 0;

    /**
     * Creates a new message.
     * @param messageNumber
     * @param recipient
     * @param messageText
     */
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = "";
        this.status = "Pending";
    }

    /**
     * Generates a message ID containing 10 digits.
     * @return 
     */
    public String generateMessageID() {
        SecureRandom random = new SecureRandom();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }

        return id.toString();
    }

    /**
     * Checks the message ID length.
     * @return 
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    /**
     * Validates the South African cellphone number.
     * @return 
     */
    public boolean checkRecipientCell() {
        return recipient != null
                && recipient.matches("\\+27\\d{9}");
    }

    /**
     * Checks that the message does not exceed 250 characters.
     * @return 
     */
    public boolean checkMessageLength() {
        return messageText != null
                && messageText.length() <= 250;
    }

    /**
     * Generates a SHA-256 hash for the message.
     * @return 
     */
    public String createMessageHash() {

        if (messageText == null || messageText.trim().isEmpty()) {
            return "";
        }

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String hashInput = messageID.substring(0, 2)
                + ":" + messageNumber
                + ":" + firstWord
                + lastWord;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(
                    hashInput.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder result = new StringBuilder();

            for (byte b : hashBytes) {
                result.append(String.format("%02X", b));
            }

            messageHash = result.toString();

        } catch (java.security.NoSuchAlgorithmException e) {
            messageHash = "";
        }

        return messageHash;
    }

    /**
     * Sends, stores or disregards the message.
     * @param choice
     * @return 
     */
    public String sendMessage(int choice) {

        if (!checkRecipientCell()) {
            return "Cell phone number is incorrectly formatted "
                    + "or does not contain international code.";
        }

        if (!checkMessageLength()) {
            return "Message exceeds 250 characters.";
        }

        switch (choice) {

            case 1 -> {
                status = "Sent";
                totalMessagesSent++;
                createMessageHash();
                return "Message successfully sent.";
            }

            case 2 -> {
                status = "Stored";
                createMessageHash();
                return "Message successfully stored.";
            }

            case 3 -> {
                status = "Disregarded";
                return "Message disregarded.";
            }

            default -> {
                return "Invalid choice.";
            }
        }
    }

    /**
     * Returns the total number of messages sent.
     * @return 
     */
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getStatus() {
        return status;
    }
}