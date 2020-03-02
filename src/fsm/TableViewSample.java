//package fsm;
//
//import javafx.application.Application;
//import javafx.beans.binding.Bindings;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.ObservableMap;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableColumn.CellEditEvent;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//import javafx.util.Callback;
//
//public class TableViewSample extends Application {
//
//    @Override
//    public void start(Stage primaryStage) {
//        TableView<Item<String>> tableView = new TableView<>(FXCollections.observableArrayList(
//                new Item<>("0"),
//                new Item<>("1")));
//        tableView.setEditable(true);
//
//        ObservableMap<Integer, Boolean> editable = FXCollections.observableHashMap();
//        editable.put(1, Boolean.TRUE);
//
//        TableColumn<Item<String>, String> column = new TableColumn<>();
//        column.setOnEditCommit(evt -> {
//            editable.remove(evt.getTablePosition().getRow());
//        });
//        column.setCellValueFactory(new PropertyValueFactory<>("value"));
//        column.setCellFactory(StateTextFieldTableCell.forTableColumn(i -> Bindings.valueAt(editable, i).isEqualTo(Boolean.TRUE)));
//        tableView.getColumns().add(column);
//
//        Scene scene = new Scene(tableView);
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//}