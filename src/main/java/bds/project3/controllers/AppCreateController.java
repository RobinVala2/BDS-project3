package bds.project3.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bds.project3.api.AppCreateView;
import bds.project3.data.AppRepository;
import bds.project3.services.AppService;

import java.sql.Date;
import java.util.Optional;

public class AppCreateController
{

    private static final Logger logger = LoggerFactory.getLogger(AppCreateController.class);

    @FXML
    private Button button_add;
    @FXML
    private TextField tf_memberid;
    @FXML
    private TextField tf_firstname;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField tf_birthday;
    @FXML
    private TextField tf_gender;

       private AppService appService;
    private AppRepository appRepository;
    private ValidationSupport validation;

    @FXML
    public void initialize()
    {
        appRepository = new AppRepository();
        appService = new AppService(appRepository);

        validation = new ValidationSupport();
        validation.registerValidator(tf_firstname, Validator.createEmptyValidator("First name must not be empty"));
        validation.registerValidator(tf_lastname, Validator.createEmptyValidator("Last name must not be empty"));
        validation.registerValidator(tf_birthday, Validator.createEmptyValidator("Birthday must not be empty"));
        validation.registerValidator(tf_gender, Validator.createEmptyValidator("Gender must not be empty"));

        logger.info("AppCreateController initialized");
    }

    @FXML
    void handleCreateMember(ActionEvent event)
    {
        AppCreateView createView = new AppCreateView();
        createView.setFirstName(tf_firstname.getText());
        createView.setLastName(tf_lastname.getText());
        createView.setBirthday(Date.valueOf(tf_birthday.getText()));
        createView.setGender(tf_gender.getText());

        appService.createMember(createView);
        memberCreatedConfirmationDialog();
    }

    private void memberCreatedConfirmationDialog()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Member Created");
        alert.setHeaderText("Member Successfuly Created");
        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>()

        {
            @Override
            public void handle(ActionEvent event)
            {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }


}
