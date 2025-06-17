package main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;


public class MessageService {
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> disregardedMessages = new ArrayList<>();
    private List<Message> storedMessages = new ArrayList<>();
    private List<String> messageHashes = new ArrayList<>();
    private List<String> messageIds = new ArrayList<>();
    @SuppressWarnings("unchecked")
public void loadMessagesFromJson(String filePath) {
    try (FileReader reader = new FileReader(filePath)) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(reader);

        for (Object obj : jsonArray) {
            JSONObject jsonMsg = (JSONObject) obj;

            String recipient = (String) jsonMsg.get("recipient");
            String content = (String) jsonMsg.get("content");
            String status = (String) jsonMsg.get("status");
            String id = (String) jsonMsg.get("id");
            String hash = (String) jsonMsg.get("hash");

            Message message = new Message(recipient, content, status);
            // Manually override auto-generated values
            sentMessages.add(message);
            messageIds.add(id);
            messageHashes.add(hash);
        }

        JOptionPane.showMessageDialog(null, "Messages loaded from " + filePath);
    } catch (IOException | ParseException e) {
        JOptionPane.showMessageDialog(null, "Could not load messages:\n" + e.getMessage());
    }
}

    public void saveMessagesToJson(String filePath) {
    JSONArray jsonArray = new JSONArray();

    for (Message msg : sentMessages) {
        JSONObject obj = new JSONObject();
        obj.put("recipient", msg.getRecipient());
        obj.put("content", msg.getContent());
        obj.put("status", msg.getStatus());
        obj.put("id", msg.getId());
        obj.put("hash", msg.getHash());
        jsonArray.add(obj);
    }

    try (FileWriter file = new FileWriter(filePath)) {
        file.write(jsonArray.toJSONString());
        file.flush();
        JOptionPane.showMessageDialog(null, "Messages saved to " + filePath);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Failed to save messages:\n" + e.getMessage());
    }
}


    public void addCustomMessagesWithGUI(int count) {
        for (int i = 0; i < count; i++) {
            String recipient = JOptionPane.showInputDialog("Enter recipient for message " + (i + 1) + ":");
            String content = JOptionPane.showInputDialog("Enter message content for message " + (i + 1) + ":");

            if (recipient == null || content == null) {
                JOptionPane.showMessageDialog(null, "Cancelled");
                return;
            }

            Message msg = new Message(recipient, content, content.length() > 250 ? "Disregarded" : "Sent");

            if (msg.getContent().length() > 250) {
                JOptionPane.showMessageDialog(null, "Failure: Message too long by " + (content.length() - 250) + " characters.");
                disregardedMessages.add(msg);
                i--; // retry input
            } else {
                JOptionPane.showMessageDialog(null, "Success: Message accepted.");
                sentMessages.add(msg);
                messageHashes.add(msg.getHash());
                messageIds.add(msg.getId());
            }
        }
    }

    public void displaySenderAndRecipientGUI() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages to display.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Message msg : sentMessages) {
            sb.append("Sender: System, Recipient: ").append(msg.getRecipient()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void displayLongestSentMessageGUI() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages found.");
            return;
        }

        Message longest = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getContent().length() > longest.getContent().length()) {
                longest = msg;
            }
        }

        JOptionPane.showMessageDialog(null, "Longest Message:\n" + longest.getContent());
    }

    public void searchByIdGUI(String id) {
        for (Message msg : sentMessages) {
            if (msg.getId().equals(id)) {
                JOptionPane.showMessageDialog(null, "Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getContent());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }

    public void searchByRecipientGUI(String recipient) {
        List<Message> combined = new ArrayList<>();
        combined.addAll(sentMessages);
        combined.addAll(storedMessages);

        StringBuilder sb = new StringBuilder();
        for (Message msg : combined) {
            if (msg.getRecipient().equalsIgnoreCase(recipient)) {
                sb.append(msg.getContent()).append("\n");
            }
        }

        JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No messages found for recipient: " + recipient);
    }

    public void deleteByHashGUI(String hash) {
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            if (msg.getHash().equals(hash)) {
                sentMessages.remove(i);
                messageHashes.remove(hash);
                messageIds.remove(msg.getId());
                JOptionPane.showMessageDialog(null, "Message \"" + msg.getContent() + "\" successfully deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message not found.");
    }

    public void displayReportGUI() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages to report.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Message msg : sentMessages) {
            sb.append("Hash: ").append(msg.getHash()).append("\n");
            sb.append("ID: ").append(msg.getId()).append("\n");
            sb.append("Recipient: ").append(msg.getRecipient()).append("\n");
            sb.append("Message: ").append(msg.getContent()).append("\n");
            sb.append("-----------\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Getters
    public List<Message> getSentMessages() { return sentMessages; }
    public List<Message> getDisregardedMessages() { return disregardedMessages; }
    public List<Message> getStoredMessages() { return storedMessages; }
    public List<String> getMessageHashes() { return messageHashes; }
    public List<String> getMessageIds() { return messageIds; }

    void editMessageByIdGUI(String editId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


