package controllers;

import functions.FunctionPoint;
import UI.Controller;
import UI.FunctionPointT;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainFormController implements Initializable, Controller {

    private ObservableList<FunctionPointT> tabulatedFunction = FXCollections.observableArrayList();
    @FXML
    private TextField edY;

    @FXML
    private TextField edX;

    @FXML
    private Label labelPointNumber;

    @FXML
    private TableView<FunctionPointT> table = new TableView<FunctionPointT>();

    @FXML
    private TableColumn<FunctionPointT, Double> columnX = new TableColumn<FunctionPointT, Double>("X values");

    @FXML
    private TableColumn<FunctionPointT, Double> columnY = new TableColumn<FunctionPointT, Double>("Y values");

    @FXML
    private Button buttonAddPoint;

    @FXML
    private Button buttonDelete;

    @FXML
    private Label labelTextFieldX;

    @FXML
    private Label labelTextFieldY;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuFile;

    @FXML
    private Menu menuTabulate;

    private Stage primaryStage;

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    private void btNewClick(ActionEvent av) {
        edY.setText(edX.getText());
    }

    @FXML
    private void newDocument(ActionEvent av) {
        showDialog();
        tabulatedFunction = FXCollections.observableArrayList();
       for(int i = 0; i < Main.tabFDoc.getLength(); ++i){
           tabulatedFunction.add(new FunctionPointT(Main.tabFDoc.getPointX(i), Main.tabFDoc.getPointY(i)));
       }
       table.setItems(tabulatedFunction);
    }

    @FXML
    private void addPoint(ActionEvent av) {
        try {
            Main.tabFDoc
                    .addPoint(new FunctionPoint(Double.parseDouble(edX.getText()), Double.parseDouble(edY.getText())));
        } catch (Throwable e) {
            System.err.println(e.getClass().getName());
        }
    }

    @FXML
    private void deletePoint(ActionEvent av) {
        try {
            Main.tabFDoc.deletePoint(Main.tabFDoc.getPointsCount() - 1);
        } catch (Throwable e) {
            System.err.println(e.getClass().getName());
        }
    }

    public void redraw() {
        if (!table.getColumns().isEmpty())
            table.getItems().clear();
        for (int i = 0; i < Main.tabFDoc.getPointsCount(); ++i) {
            FunctionPointT point = new FunctionPointT(Main.tabFDoc.getPointX(i),
                    Main.tabFDoc.getPointY(i));
            tabulatedFunction.add(point);
        }
        table.setItems(tabulatedFunction);
        labelPointNumber.setText("Count of points: " + Main.tabFDoc.getPointsCount());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnX.setCellValueFactory(new PropertyValueFactory<FunctionPointT, Double>("x"));
        table.getColumns().add(columnX);
        columnY.setCellValueFactory(new PropertyValueFactory<FunctionPointT, Double>("y"));
        table.getColumns().add(columnY);
        labelPointNumber.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                labelPointNumber.setText("Count of points: " + Main.tabFDoc.getPointsCount());
            }
        });
        table.setRowFactory(tableView -> {
            TableRow<FunctionPointT> row = new TableRow<FunctionPointT>();
            row.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    if (row.getIndex() < Main.tabFDoc.getPointsCount())
                        labelPointNumber
                                .setText(String.format("Point %d of %d", row.getIndex() + 1,
                                        Main.tabFDoc.getPointsCount()));
                }
            });
            return row;
        });
        for (int i = 0; i < Main.tabFDoc.getPointsCount(); ++i) {
            FunctionPointT point = new FunctionPointT(Main.tabFDoc.getPointX(i),
                    Main.tabFDoc.getPointY(i));
            tabulatedFunction.add(point);
        }
        table.setItems(tabulatedFunction);
    }

    public int showDialog() {
        return FXMLNewDocFormController.showDialog(primaryStage);
    }

}