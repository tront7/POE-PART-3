package main;

public class User {
    private String username;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;

    public User(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    public boolean checkUserName() {
        return username.contains("_") && username.length() >= 5;
    }

    public boolean checkPasswordComplexity() {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$");
    }

    public boolean checkCellPhoneNumber() {
        return phoneNumber.matches("^\\+27\\d{9}$");
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is at least five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a number, an uppercase letter, a lowercase letter, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        return "User successfully registered.";
    }

    public String returnLoginStatus(boolean loggedIn) {
        return loggedIn ? "Welcome back! It is great to see you again." :
                          "Username or password incorrect, please try again.";
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
