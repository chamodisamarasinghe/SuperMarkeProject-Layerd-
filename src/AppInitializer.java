import entity.Customer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.io.IOException;
import java.security.acl.Owner;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);

        Customer customer = new Customer();
        customer.setCustomerId("C009");
        customer.setTitle("Mr");
        customer.setName("Nimal");
        customer.setAddress("Colombo");
        customer.setCity("Colombo");
        customer.setProvince("Western");
        customer.setPostalCode("P002");

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(customer);

        transaction.commit();
        session.close();



    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("views/DashBoardForm.fxml"))));
        primaryStage.show();
    }
}

