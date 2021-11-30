package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UserFormController {
    public AnchorPane userFormContext;
    public TextField txtUserName;
    public ComboBox cmbRoleType;
    public TextField txtFullName;
    public TextField txtEmail;
    public TextField txtPassWord;
    private String selectedRoleType;

    public void initialize(){
        cmbRoleType.getItems().addAll("Doctor", "Reception");
        cmbRoleType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedRoleType = String.valueOf(newValue);
        });
    }


    public void addOnAction(ActionEvent actionEvent) {
    }
}
