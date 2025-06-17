package main;

import java.util.UUID;

public class Message {
    private String recipient;
    private String content;
    private String status; // Sent, Stored, Disregarded
    private String id;
    private String hash;

    public Message(String recipient, String content, String status) {
        this.recipient = recipient;
        this.content = content;
        this.status = status;
        this.id = UUID.randomUUID().toString();
        this.hash = Integer.toString(content.hashCode());
    }

    // Getters and setters
    public String getRecipient() { return recipient; }
    public String getContent() { return content; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getId() { return id; }
    public String getHash() { return hash; }

    // Allow editing content (e.g., for message edit)
    public void setContent(String content) {
        this.content = content;
        this.hash = Integer.toString(content.hashCode()); // Update hash when content changes
    }
}
