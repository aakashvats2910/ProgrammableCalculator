package calculator;

import Table.TableMain;
import javafx.scene.control.TableRow;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import calculator.Entries;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class Entries {
    public String name, measuredValue, errorValue, unit, type;

    public Entries(String name, String measuredValue, String errorValue, String unit, String type) {
        this.name = name;
        this.measuredValue = measuredValue;
        this.errorValue = errorValue;
        this.unit = unit;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getMeasuredValue() {
        return measuredValue;
    }

    public String getErrorValue() {
        return errorValue;
    }

    public String getUnit() {
        return unit;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeasuredValue(String measuredValue) {
        this.measuredValue = measuredValue;
    }

    public void setErrorValue(String errorValue) {
        this.errorValue = errorValue;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<Entries> readRepo() {
        ArrayList<Entries> toReturn = new ArrayList<>();
        try {
            File file = new File(System.getProperty("user.dir") + "//repo.txt");
            FileReader reader = new FileReader(file);
            int i;
            String[] arr = new String[5];
            arr[0] = ""; arr[1] = ""; arr[2] = ""; arr[3] = ""; arr[4] = "";
            int arrIndx = 0;
            while ((i = reader.read()) != -1) {
                if ((char)i == ',') {
                    arrIndx++;
                    continue;
                }
                if ((char)i == '\n') {
                    arrIndx = 0;
                    toReturn.add(new Entries(arr[0], arr[1], arr[2], arr[3], arr[4]));
                    arr = new String[5];
                    arr[0] = ""; arr[1] = ""; arr[2] = ""; arr[3] = ""; arr[4] = "";
                    continue;
                }
                arr[arrIndx] += "" + (char)i;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public static void removeEntry(String name) {
    }

    public static Entries searchEntry(String name) {
        ArrayList<Entries> list = readRepo();
        for (int i = 0; i <= list.size()-1; i++) {
            if (list.get(i).getName().equals(name)) {
                return list.get(i);
            }
        }
        return null;
    }

    public static void updateEntry(String name) {

    }

    public static void hilight(TableView<Entries> tableView, String name) {
        System.out.println("FIRST");
        tableView.setRowFactory(tv -> new TableRow<Entries>() {
            @Override
            public void updateItem(Entries item, boolean empty) {
                System.out.println("INSIDE");
                super.updateItem(item, empty) ;
                if (item == null) {
                    System.out.println("1");
                    setStyle("");
                } else if (item.getName().equals(name)) {
                    System.out.println("2");
                    System.out.println("()()()()" + name);
                    setStyle("-fx-background-color: tomato;");
                } else {
                    System.out.println("3");
                    setStyle("");
                }
            }
        });
    }

}
