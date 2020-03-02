package Table;

import calculator.Entries;
import fsm.StateTextFieldTableCell;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
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

import java.io.*;
import java.util.ArrayList;

public class TableMain extends Application {

    private File file;

    private static int selectedRow = -1;
    private static Entries selectedEntries = null;

    private TableColumn Measure, Error, Units, Name, Type;

    private Button createButton = new Button("Create");
    private TextField searchField = new TextField();
    private Button searchButton = new Button("Search");
    private Button deleteButton = new Button("Delete");
    private Button updateButton = new Button("Update");
    private Button exitButton = new Button("Exit");
    private Label errMessageLabel = new Label();

    public final TableView table = new TableView<>();

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());
        stage.setWidth(480);
        stage.setHeight(600);

        table.setEditable(true);

        Name = new TableColumn<>("Name");
        Name.setMinWidth(100);

        Type = new TableColumn<>("Type");
        Type.setMinWidth(100);

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

        updateButton.setDisable(true);
        deleteButton.setDisable(true);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        exitButton.setOnAction(event -> stage.close());

        deleteButton.setOnAction(event -> {
            String toDelete = selectedEntries.getName() + "," +
                    selectedEntries.getMeasuredValue() + "," +
                    selectedEntries.getErrorValue() + "," +
                    selectedEntries.getUnit() + "," +
                    selectedEntries.getType();
            try {
                deleteFromFile(toDelete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        updateButton.setOnAction(event -> updateToFile());

        searchButton.setOnAction(event -> {
            selectedRow = -1;
            deleteButton.setDisable(true);
            updateButton.setDisable(true);
            ArrayList<Entries> list = Entries.readRepo();
            for (int i = 0; i <= list.size()-1; i++) {
                if (list.get(i).getName().equals(searchField.getText().trim())) {
                    table.getSelectionModel().select(i);
                    selectedRow = i;
                    selectedEntries = list.get(i);
                    System.out.println("Selected Row :" + selectedRow);
                    makeRowEditable();
                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                    return;
                }
            }
        });

        table.setStyle("-fx-background-color: tomato;");
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        file = new File(System.getProperty("user.dir") + "//repo.txt");

        addData();

        stage.setScene(scene);
        stage.show();
    }

    private void addData() {
        table.getItems().clear();

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

    private void makeRowEditable() {
        ObservableMap<Integer, Boolean> editable = FXCollections.observableHashMap();
        editable.put(selectedRow, Boolean.TRUE);

        Name.setCellFactory(StateTextFieldTableCell.forTableColumn(j -> Bindings.valueAt(editable, j).isEqualTo(Boolean.TRUE)));
        Measure.setCellFactory(StateTextFieldTableCell.forTableColumn(j -> Bindings.valueAt(editable, j).isEqualTo(Boolean.TRUE)));

        table.setRowFactory(tv -> new TableRow<Entries>() {
            @Override
            public void updateItem(Entries item, boolean empty) {
                System.out.println("INSIDE");
                super.updateItem(item, empty) ;
                System.out.println(selectedEntries.getName());
                if (item == null) {
                    System.out.println("1");
                    setStyle("");
                } else if (item.getName().equals(selectedEntries.getName())) {
                    System.out.println("2");
                    System.out.println("()()()()" + selectedEntries.getName());
                    setStyle("-fx-background-color: tomato;");
                } else {
                    System.out.println("3");
                    setStyle("");
                }
            }
        });
    }

    private void deleteFromFile(String toDelete) throws Exception {
        File tempFile = new File(System.getProperty("user.dir") + "//repo1.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String trimmed = currentLine.trim();
            if (trimmed.equals(toDelete)) continue;
            writer.write(trimmed + "\n");
        }
        writer.close();
        reader.close();

        System.out.println(file.delete());
        System.out.println(tempFile.renameTo(file));

        addData();
        deleteButton.setDisable(true);
    }

    private void updateToFile() {
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


        Measure.setOnEditCommit(
                new EventHandler<CellEditEvent<Entries, String>>() {
                    @Override
                    public void handle(CellEditEvent<Entries, String> t) {
                        ((Entries) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());

                    }
                }
        );

        Error.setOnEditCommit(
                new EventHandler<CellEditEvent<Entries, String>>() {
                    @Override
                    public void handle(CellEditEvent<Entries, String> t) {
                        ((Entries) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }
        );


        Units.setOnEditCommit(
                new EventHandler<CellEditEvent<Entries, String>>() {
                    @Override
                    public void handle(CellEditEvent<Entries, String> t) {
                        ((Entries) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }
        );

        Type.setOnEditCommit(
                new EventHandler<CellEditEvent<Entries, String>>() {
                    @Override
                    public void handle(CellEditEvent<Entries, String> t) {
                        ((Entries) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }
        );
        updateButton.setDisable(true);
    }

}