package bds.project3.controllers;

import bds.project3.api.InjectionView;
import bds.project3.data.AppRepository;
import bds.project3.services.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bds.project3.api.InjectionView;
import bds.project3.data.AppRepository;
import bds.project3.services.AppService;

import java.util.List;

public class InjectionController
{
    private static final Logger logger = LoggerFactory.getLogger(InjectionController.class);

    @FXML
    private Button customButton;
    @FXML
    private TextField injectionTextField;
    @FXML
    private TableView<InjectionView> injectionTable;
    @FXML
    private TableColumn<InjectionView, Long> column_id;
    @FXML
    private TableColumn<InjectionView, String> column_nickname;
    @FXML
    private TableColumn<InjectionView, String> column_password;
    @FXML
    private TableColumn<InjectionView, String> column_email;


    private AppService appService;
    private AppRepository appRepository;
    private Stage stage;

    public InjectionController()
    {

    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void initialize()
    {
        appRepository = new AppRepository();
        appService = new AppService(appRepository);

        column_id.setCellValueFactory(new PropertyValueFactory<InjectionView, Long>("id"));
        column_nickname.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("nickname"));
        column_password.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("password"));
        column_email.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("email"));

        logger.info("InjectionController initalized");
    }

    private ObservableList<InjectionView> initData(String input)
    {

        List<InjectionView> dummy_table = appService.getInjectionView(input);
        return FXCollections.observableArrayList(dummy_table);
    }

    public void handleCustomButton(ActionEvent actionEvent)
    {
        String input = injectionTextField.getText();
        ObservableList<InjectionView> observableList = initData(input);
        injectionTable.setItems(observableList);
    }

    public void handleDropTable(ActionEvent actionEvent) {
        ObservableList<InjectionView> observableList = initData("1; DROP TABLE bds.dummy_table");
        injectionTable.setItems(observableList);
    }

    public void handleOrButton(ActionEvent actionEvent) {
        ObservableList<InjectionView> observableList = initData("1 OR 1=1");
        injectionTable.setItems(observableList);
    }
}
