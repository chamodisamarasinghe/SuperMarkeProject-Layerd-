package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashBoardFormController {
    public AnchorPane dashBoardContext;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        loadDateAndTime();
    }

    private void loadDateAndTime() {
        Date date=new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void openLogInFormOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginForm.fxml"));
        Parent parent = loader.load();
        LoginFormController controller = loader.getController();
        Scene scene= new Scene(parent);
        Stage stage = new Stage();
        controller.closeStage(dashBoardContext);
        stage.setScene(scene);
        // stage.setTitle("Dash Board");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
