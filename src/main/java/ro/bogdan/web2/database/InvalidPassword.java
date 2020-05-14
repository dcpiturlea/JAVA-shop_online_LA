package ro.bogdan.web2.database;

public class InvalidPassword extends Exception {

    public InvalidPassword(String message) {
        super(message);
    }
}
