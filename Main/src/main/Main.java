package main;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        User user;
        String registrationStatus;

        // Registration loop
        do {
            String username = JOptionPane.showInputDialog("Register: Enter a username:");
            if (username == null) {
                JOptionPane.showMessageDialog(null, "Registration cancelled.");
                return;
            }

            String password = JOptionPane.showInputDialog("Register: Enter a password:");
            if (password == null) {
                JOptionPane.showMessageDialog(null, "Registration cancelled.");
                return;
            }

            String phone = JOptionPane.showInputDialog("Register: Enter your SA phone number (e.g. +27835551234):");
            if (phone == null) {
                JOptionPane.showMessageDialog(null, "Registration cancelled.");
                return;
            }

            user = new User(username.trim(), password.trim(), phone.trim());
            registrationStatus = user.registerUser();
            JOptionPane.showMessageDialog(null, registrationStatus);
        } while (!registrationStatus.equals("User successfully registered."));

        // Login loop
        boolean loggedIn = false;
        while (!loggedIn) {
            String loginUser = JOptionPane.showInputDialog("Login: Enter username:");
            if (loginUser == null) {
                JOptionPane.showMessageDialog(null, "Login cancelled.");
                return;
            }

            String loginPass = JOptionPane.showInputDialog("Login: Enter password:");
            if (loginPass == null) {
                JOptionPane.showMessageDialog(null, "Login cancelled.");
                return;
            }

            loggedIn = user.login(loginUser.trim(), loginPass.trim());
            JOptionPane.showMessageDialog(null, user.returnLoginStatus(loggedIn));
        }

        JOptionPane.showMessageDialog(null, "Welcome, " + user.getUsername() + "!");

        // Messaging service
        MessageService msgService = new MessageService();

        // Load and Save messages
        msgService.loadMessagesFromJson("src/messages.json");

        boolean running = true;
        while (running) {
            String option = JOptionPane.showInputDialog(
                "QuickChat Options:\n" +
                "1) Send Messages\n" +
                "2) Display Sender & Recipient\n" +
                "3) Longest Message\n" +
                "4) Search by Message ID\n" +
                "5) Search by Recipient\n" +
                "6) Delete by Hash\n" +
                "7) Display Report\n" +
                "8) Edit Message by ID\n" +
                "9) Quit");

            if (option == null) {
                int confirmExit = JOptionPane.showConfirmDialog(null, "Exit QuickChat?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (confirmExit == JOptionPane.YES_OPTION) {
                    running = false;
                    continue;
                } else {
                    continue;
                }
            }

            switch (option.trim()) {
                case "1":
                    int count = 0;
                    boolean validCount = false;
                    while (!validCount) {
                        String countStr = JOptionPane.showInputDialog("How many messages do you want to send?");
                        if (countStr == null) break;
                        try {
                            count = Integer.parseInt(countStr.trim());
                            if (count <= 0) {
                                JOptionPane.showMessageDialog(null, "Please enter a positive number.");
                            } else {
                                validCount = true;
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid number entered.");
                        }
                    }
                    if (validCount) {
                        msgService.addCustomMessagesWithGUI(count);
                    }
                    break;

                case "2":
                    msgService.displaySenderAndRecipientGUI();
                    break;

                case "3":
                    msgService.displayLongestSentMessageGUI();
                    break;

                case "4":
                    String id = JOptionPane.showInputDialog("Enter message ID to search:");
                    msgService.searchByIdGUI(id);
                    break;

                case "5":
                    String recipient = JOptionPane.showInputDialog("Enter recipient to search messages:");
                    msgService.searchByRecipientGUI(recipient);
                    break;

                case "6":
                    String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
                    msgService.deleteByHashGUI(hash);
                    break;

                case "7":
                    msgService.displayReportGUI();
                    break;

                case "8":
                    String editId = JOptionPane.showInputDialog("Enter message ID to edit:");
                    msgService.editMessageByIdGUI(editId);
                    break;

                case "9":
                    running = false;
                    JOptionPane.showMessageDialog(null, "Exiting QuickChat.");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }

        // Save before exiting
        msgService.saveMessagesToJson("src/messages.json");
    }
}

