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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Optional;
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
    private void openFile(ActionEvent av) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open tabulated function document");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Document (*.json)", "*.json"));
        fileChooser.setInitialDirectory(new File(".\\"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null)
            return;
        try {
            Main.tabFDoc.loadFunction(file.getAbsolutePath());
        } catch (Throwable e) {
            showErrorMessage("Failed to load function!");
        }
    }

    @FXML
    private void saveAsFile(ActionEvent av) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save tabulated function as...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Document (*.json)", "*.json"));
        fileChooser.setInitialDirectory(new File(".\\"));
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file == null)
            return;
        try {
            Main.tabFDoc.saveFunctionAs(file.getAbsolutePath());
        } catch (Throwable e) {
            showErrorMessage("Failed to save function!");
        }
    }
    @FXML
    private void integrate(ActionEvent av){

    }

    @FXML
    private void saveFile(ActionEvent av) {
        if (Main.tabFDoc.getFileName() != null)
            try {
                Main.tabFDoc.saveFunction();
            } catch (Throwable e) {
                showErrorMessage("Failed to save function!");
            }
        else
            saveAsFile(av);
    }

    @FXML
    private void loadFunction(ActionEvent av) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open function file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Function file", "*"));
        fileChooser.setInitialDirectory(new File(".\\"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null)
            return;
    }

    @FXML
    private void tabulateFunction(ActionEvent av) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tabulate function");
        dialog.setHeaderText("Tabulate function");
        dialog.setContentText("Enter x out of scope:");
        dialog.setResizable(false);
        TextInputDialog dialogResult = new TextInputDialog();
        dialogResult.setTitle("Function value at x");
        dialogResult.setHeaderText("Function value at x");
        dialogResult.setContentText("Function value at x:");
        dialogResult.setResizable(false);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                dialogResult.getEditor().setText(Double.toString(Main.tabFDoc.getFunctionValue(Double.parseDouble(result.get()))));
                dialogResult.getEditor().setEditable(false);
                dialogResult.show();
            } catch (Exception e) {
                showErrorMessage("Illegal format!!!");
            }
        }
    }
    @FXML
    private void addPoint(ActionEvent av) {
        try {
            Main.tabFDoc
                    .addPoint(new FunctionPoint(Double.parseDouble(edX.getText()), Double.parseDouble(edY.getText())));
        } catch (Throwable e) {
            showErrorMessage("Cannot add point!!!");
        }
    }

    @FXML
    private void deletePoint(ActionEvent av) {
        try {
            Main.tabFDoc.deletePoint(Main.tabFDoc.getPointsCount() - 1);
        } catch (Throwable e) {
            showErrorMessage("Cannot delete point!!!");
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

    private void showErrorMessage(String message) {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setHeaderText("Error");
        errorMessage.setContentText(message);
        errorMessage.setResizable(false);
        errorMessage.showAndWait();
    }

}