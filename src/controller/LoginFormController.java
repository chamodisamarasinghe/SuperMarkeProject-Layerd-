package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {
    public AnchorPane logInFormContext;
    public AnchorPane dashBoardContext;
    public TextField txtPassWord;
    public TextField txtUserName;



    public void LogInOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dashBoardContext.getScene().getWindow();
        stage.close();
        if (txtUserName.getText().equalsIgnoreCase("Manager") && txtPassWord.getText().equals("123")) {
            Stage window = (Stage) logInFormContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/ManagerLogInForm.fxml"))));
            stage.centerOnScreen();
        } else if (txtUserName.getText().equalsIgnoreCase("Cashier") && txtPassWord.getText().equals("456")) {
            Stage window = (Stage) logInFormContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/CashierLogInForm.fxml"))));
            stage.centerOnScreen();

        } else {
            new Alert(Alert.AlertType.WARNING, "Incorrect User Name, Password. Try again..", ButtonType.CLOSE).show();
        }
    }

            public void cancelOnAction (ActionEvent actionEvent){
            }


            public void newAccountOnAction (ActionEvent actionEvent) throws IOException {
                URL resource = getClass().getResource("../views/UserForm.fxml");
                Parent load = FXMLLoader.load(resource);
                Stage window = (Stage) logInFormContext.getScene().getWindow();
                window.setScene(new Scene(load));
            }


    public void closeStage(AnchorPane dashBoardContext) {
        this.dashBoardContext=dashBoardContext;
    }

    public void moveToPassWord(ActionEvent actionEvent) {
        txtPassWord.requestFocus();
    }
}






