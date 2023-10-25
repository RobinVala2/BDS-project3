package bds.project3.controllers;

import bds.project3.api.AppBasicView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bds.project3.App;
import bds.project3.api.AppDetailedView;
import bds.project3.data.AppRepository;
import bds.project3.services.AppService;

import java.io.IOException;

public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @FXML
    private Button ButtonCreateMember;
    @FXML
    private Button refreshButton;
    @FXML
    private TableView<AppBasicView> MemberTable;
    @FXML
    private TableColumn<AppBasicView, Long> IDColumn;
    @FXML
    private TableColumn<AppBasicView, String> FirstNameColumn;
    @FXML
    private TableColumn<AppBasicView, String> LastNameColumn;
    @FXML
    private TableColumn<AppBasicView, Integer> BirthdayColumn;
    @FXML
    private TableColumn<AppBasicView, Integer> GenderColumn;

    @FXML
    private Button searchButton;
    @FXML
    private TextField filterTextField;

    private AppService appService;
    private AppRepository appRepository;

    public AppController()
    {

    }

    @FXML
    private void initialize()
    {

        appRepository = new AppRepository();
        appService = new AppService(appRepository);

        //setting values from basic view
        IDColumn.setCellValueFactory(new PropertyValueFactory<AppBasicView, Long>("id"));
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<AppBasicView, String>("first_name"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<AppBasicView, String>("last_name"));
        BirthdayColumn.setCellValueFactory(new PropertyValueFactory<AppBasicView, Integer>("birthday"));
        GenderColumn.setCellValueFactory(new PropertyValueFactory<AppBasicView, Integer>("gender"));

        searchButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                handleSearchButton(event);
            }
        });

        ObservableList<AppBasicView> observableList = FXCollections.observableArrayList(appService.getMemberBasicView());
        MemberTable.setItems(observableList);

        MemberTable.getSortOrder().add(IDColumn);
        MenuSelect();
        logger.info("AppController initialized");
    }

    private void MenuSelect()
    {

        MenuItem detailedView = new MenuItem("Detailed information");
        MenuItem edit = new MenuItem("Edit member");
        MenuItem delete = new MenuItem("Delete member");

        detailedView.setOnAction((ActionEvent event) ->
        {
            AppBasicView abv = MemberTable.getSelectionModel().getSelectedItem();
            try
                {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("AppDetailedView.fxml"));
                    Stage stage = new Stage();

                    String last_name = abv.getLast_name();
                    AppDetailedView adv = appService.getSelectedMember(last_name);

                    stage.setUserData(adv);
                    stage.setTitle("Member Detailed View");
                    AppDetailedController controller = new AppDetailedController();
                    controller.setStage(stage);
                    fxmlLoader.setController(controller);

                    Scene scene = new Scene(fxmlLoader.load(), 450, 400);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex)
                {
                    ex.printStackTrace();
                    logger.error("Couldn't open Detailed View.");
                }
        });
        edit.setOnAction((ActionEvent event) ->
        {
            AppBasicView abv = MemberTable.getSelectionModel().getSelectedItem();

            try
                {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("AppEdit.fxml"));
                    Stage stage = new Stage();

                    AppDetailedView adv = appService.getSelectedMember(abv.getLast_name());

                    stage.setUserData(adv);
                    stage.setTitle("Member Edit view");
                    AppEditController controller = new AppEditController();
                    controller.setStage(stage);
                    fxmlLoader.setController(controller);

                    Scene scene = new Scene(fxmlLoader.load(), 400, 350);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex)
                {
                    ex.printStackTrace();
                    logger.error("Couldn't open Edit View");
                }
        });
        delete.setOnAction((ActionEvent event) ->
        {
            AppBasicView abv = MemberTable.getSelectionModel().getSelectedItem();
            appRepository.removeMember(abv.getId());
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().addAll(detailedView);
        menu.getItems().addAll(edit);
        menu.getItems().add(delete);
        MemberTable.setContextMenu(menu);
    }

    public void handleCreateMemberButton(ActionEvent actionEvent)
    {
        try
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("AppCreate.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 480, 400);
                Stage stage = new Stage();
                stage.setTitle("Create member");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex)
            {
                ex.printStackTrace();
                logger.error("Couldn't open create member window");
            }
    }

    public void handleRefreshButton(ActionEvent actionEvent)
    {
        ObservableList<AppBasicView> observableList = FXCollections.observableArrayList(appService.getMemberBasicView());
        MemberTable.setItems(observableList);
        MemberTable.refresh();
        MemberTable.sort();
    }

    public void handleSearchButton(ActionEvent actionEvent)
    {
        try
            {
                String last_name = String.valueOf(filterTextField.getText());
                ObservableList<AppBasicView> observableList = FXCollections.observableArrayList(appService.getFilteredView(last_name));
                MemberTable.setItems(observableList);
                MemberTable.refresh();
                MemberTable.sort();

            } catch (NumberFormatException ex)
            {
                ex.printStackTrace();
                logger.error("Couldn't filter members.");
            }
    }

    public void handleInjection(ActionEvent actionEvent)
    {
        try
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("Injection.fxml"));
                Stage stage = new Stage();
                InjectionController controller = new InjectionController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);
                Scene scene = new Scene(fxmlLoader.load(), 485, 400);
                stage.setTitle("SQL Injection");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e)
            {
                e.printStackTrace();
                logger.error("Failed to open Injection stage.");
            }
    }

    public void handleExitButton(ActionEvent actionEvent){
        System.exit(1);
    }

}
