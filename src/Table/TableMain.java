package Table;

import calculator.Entries;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class TableMain extends Application {

    private File file;

    private TableColumn Measure, Error, Units, Name, Type;

    private Button createButton = new Button("Create");
    private TextField searchField = new TextField();
    private Button searchButton = new Button("Search");
    private Button deleteButton = new Button("Delete");
    private Button updateButton = new Button("Update");
    private Button exitButton = new Button("Exit");
    private Label errMessageLabel = new Label();

    public static final TableView table = new TableView<>();

    @Override
    public void start(Stage stage) {
//        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = (
//                TableColumn<Person, String> p) -> new EditingCell();

        Scene scene = new Scene(new Group());
        stage.setWidth(480);
        stage.setHeight(600);

        table.setEditable(true);

        Name = new TableColumn<>("Name");
        Name.setMinWidth(100);

        Type = new TableColumn<>("Type");
        Type.setMinWidth(100);


//        table.getColumns().addAll(Name, Value, Type);
        Measure = new TableColumn("Measure Value");
        Error = new TableColumn("Error Term");
        Units = new TableColumn("Units");

        table.getColumns().addAll(Name, Measure, Error, Units, Type);

        final HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10, 0, 0, 10));
        hBox.getChildren().addAll(createButton, updateButton, deleteButton, exitButton);

        final HBox searchLayout = new HBox();
        searchLayout.setSpacing(5);
        searchLayout.setPadding(new Insets(10, 0, 0, 10));
        searchLayout.getChildren().addAll(searchField, searchButton);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table, searchLayout, hBox, errMessageLabel);

        errMessageLabel.setFont(Font.font("Arial",18));
        errMessageLabel.setTextFill(Color.web("#FF0000"));

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

        searchButton.setOnAction(event -> {
            Entries.hilight(table,"iii7");
        });

        table.setStyle("-fx-background-color: tomato;");

        file = new File(System.getProperty("user.dir") + "//repo.txt");

//        Entries.hilight(table,"iii7");


        Name.setCellFactory(TextFieldTableCell.forTableColumn());
        Name.setOnEditCommit(
                new EventHandler<CellEditEvent<Entries, String>>() {
                    @Override
                    public void handle(CellEditEvent<Entries, String> t) {
                        ((Entries) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }
        );


        addData();
    }

    private void addData() {
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Measure.setCellValueFactory(new PropertyValueFactory<>("measuredValue"));
        Error.setCellValueFactory(new PropertyValueFactory<>("errorValue"));
        Units.setCellValueFactory(new PropertyValueFactory<>("unit"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));

        ArrayList<Entries> entries = Entries.readRepo();
        for (int i = 0; i <= entries.size()-1; i++) {
            table.getItems().add(entries.get(i));
        }
    }

}