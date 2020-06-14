package centus;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import centus.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @FXML
    private TextField userIdField;

    @FXML
    private PasswordField passwordField;

    public void LogIn(ActionEvent event) throws IOException {
        String user = userIdField.getText();
        String pass = passwordField.getText();

        if(user == null || user.equals("") || pass == null || pass.equals("")) {
            return;
        }

        User userModel = User.findFirst("username = ?", user);

        if (userModel == null) {
            return;
        }

        if (!User.verifyPassword(pass, userModel.get("password").toString())) {
            return;
        }

        App.user = userModel;
        App.switchTo("home");

        logger.log(Level.INFO, "User logged in");
    }

    public void signUp(ActionEvent event) throws IOException {
        String user = userIdField.getText();
        String pass = passwordField.getText();

        if(user == null || user.equals("") || pass == null || pass.equals("")) {
            return;
        }

        User userModel = new User(user, pass);
        userModel.register();

        App.user = userModel;
        App.switchTo("home");

        logger.log(Level.INFO, "User registered");
    }
}