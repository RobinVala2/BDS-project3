package bds.project3.controllers;

import bds.project3.api.AppBasicView;
import bds.project3.api.AppDetailedView;
import bds.project3.api.AppEditView;
import bds.project3.data.AppRepository;
import bds.project3.services.AppService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.Optional;


public class AppEditController
{

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    public Stage stage;
    @FXML
    private Button edit_button;
    @FXML
    private TextField tf_memberId;
    @FXML
    private TextField tf_firstname;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField tf_birthday;
    @FXML
    private TextField tf_gender;
    @FXML
    private TextField tf_city;
    @FXML
    private TextField tf_email;

    private AppService appDetailedService;
    private AppRepository appDetailedRepository;

    private ValidationSupport validation;


    public AppEditController()
    {
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void initialize()
    {

        appDetailedRepository = new AppRepository();
        appDetailedService = new AppService(appDetailedRepository);

        System.out.println("Edit controller initialized");

        validation = new ValidationSupport();

        tf_memberId.setEditable(false);
        validation.registerValidator(tf_firstname, Validator.createEmptyValidator("First name must not be empty"));
        validation.registerValidator(tf_lastname, Validator.createEmptyValidator("Last name must not be empty"));
        validation.registerValidator(tf_birthday, Validator.createEmptyValidator("Birthday must not be empty"));
        validation.registerValidator(tf_gender, Validator.createEmptyValidator("Gender must not be empty"));
        edit_button.disableProperty().bind(validation.invalidProperty());

        loadWantedData();
        logger.info("AppEdit Controller initialized");
    }

    private void loadWantedData()
    {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof AppDetailedView)
            {

                AppDetailedView appDetailedView = (AppDetailedView) stage.getUserData();

                tf_memberId.setText(String.valueOf(appDetailedView.getId()));
                tf_firstname.setText(appDetailedView.getFirstName());
                tf_lastname.setText(String.valueOf(appDetailedView.getLastName()));
                tf_birthday.setText(appDetailedView.getBirthday());
                tf_gender.setText(appDetailedView.getGender());

            }
    }

    @FXML
    public void handleEditMemberButton(ActionEvent event)
    {
        AppEditView appEditView = new AppEditView();
        appEditView.setId(Long.valueOf(tf_memberId.getText()));
        appEditView.setFirstName(tf_firstname.getText());
        appEditView.setLastName(tf_lastname.getText());
        appEditView.setBirthday(Date.valueOf(tf_birthday.getText()));
        appEditView.setGender(tf_gender.getText());
        appDetailedService.editMember(appEditView);
        editConfirmation();
    }

    private void editConfirmation()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Member Edited");
        alert.setHeaderText("Member Edited");
        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>()
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
