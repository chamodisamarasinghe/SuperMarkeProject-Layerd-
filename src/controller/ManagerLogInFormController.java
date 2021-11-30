package controller;

import db.DbConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import views.tdm.OrderDetailTM;
import views.tdm.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class ManagerLogInFormController {
    public AnchorPane managerFormContext;
    public TableView tblOrder;


    public void openManageItems(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/AddNewOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) managerFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openIncomeReports(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/views/reports/IncomeChart.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

        }




    public void openMovableItems(ActionEvent actionEvent) {
    }

    public void backToHome(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) managerFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openJasperEvent(MouseEvent mouseEvent) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/views/reports/MovableItems.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
