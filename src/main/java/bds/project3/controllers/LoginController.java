package bds.project3.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bds.project3.App;
import bds.project3.data.LoginRepository;
import bds.project3.services.LoginService;

import java.io.IOException;


public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    private Label usernameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button signInButton;
    
    private LoginRepository loginRepository;
    private LoginService loginService;

    @FXML
    private void initialize() {
        initializeServices();

        passwordText.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER) {
                handleSignIn();
            }
        });
        ValidationSupport validation = new ValidationSupport();
        validation.registerValidator(usernameText, Validator.createEmptyValidator("Username must not be empty"));
        validation.registerValidator(passwordText, Validator.createEmptyValidator("The password must not be empty"));
        signInButton.disableProperty().bind(validation.invalidProperty());

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleSignIn();
            }
        });
        logger.info("LoginController initialized");
    }

    private void initializeServices() {
        loginRepository = new LoginRepository();
        loginService = new LoginService(loginRepository);
    }

    private void handleSignIn() {
        String username = usernameText.getText();
        String password = passwordText.getText();

        if (loginService.login(username, password)) {
            logger.info(username + " logged in");
            handleGoodLogin();
        } else {
            showBadLogin();
            logger.info("Bad login attempt with username " + username + ".");
        }
    }

    private void showBadLogin() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wrong login");
        alert.setHeaderText("Username or password is not valid");
        alert.showAndWait();
    }

    private void handleGoodLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("App.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 880, 600);
            Stage stage = new Stage();
            stage.setTitle("Your members");
            stage.setScene(scene);
            Stage stageOld = (Stage) signInButton.getScene().getWindow();
            stageOld.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(String.format("FXML loading error\nMessage: %s", e.getMessage()));
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login successful");
        alert.setHeaderText("Login successful!");
        alert.setContentText("You may continue to the application.");

        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        alert.showAndWait();
    }
}
