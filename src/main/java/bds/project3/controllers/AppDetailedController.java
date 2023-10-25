package bds.project3.controllers;

import bds.project3.api.AppDetailedView;
import bds.project3.data.AppRepository;
import bds.project3.services.AppService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bds.project3.api.AppDetailedView;
import bds.project3.data.AppRepository;
import bds.project3.services.AppService;

public class AppDetailedController
{
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @FXML
    private TextField tf_member_id;
    @FXML
    private TextField tf_first_name;
    @FXML
    private TextField tf_last_name;
    @FXML
    private TextField tf_birthday;
    @FXML
    private TextField tf_gender;
    @FXML
    private TextField tf_city;
    @FXML
    private TextField tf_email;

    public Stage stage;

    private AppService appDetailedService;
    private AppRepository appDetailedRepository;


    public AppDetailedController()
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
        loadWantedData();
        logger.info("AppDetailed Controller initialized");
    }

    private void loadWantedData()
    {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof AppDetailedView)
            {
                AppDetailedView appDetailedView = (AppDetailedView) stage.getUserData();

                tf_member_id.setText(String.valueOf(appDetailedView.getId()));
                tf_first_name.setText(appDetailedView.getFirstName());
                tf_last_name.setText(String.valueOf(appDetailedView.getLastName()));
                tf_birthday.setText(String.valueOf(appDetailedView.getBirthday()));
                tf_gender.setText(appDetailedView.getGender());
                tf_city.setText(appDetailedView.getCity());
                tf_email.setText(String.valueOf(appDetailedView.getEmail()));
            }
    }
}