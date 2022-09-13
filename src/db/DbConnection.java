package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection=null;
    private Connection connection;

    private DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/SuperMarket",
                "root",
                "Chamodi@123"
        );
    }

    public static DbConnection getInstance() throws ClassNotFoundException, SQLException {
        if (dbConnection==null){
            dbConnection= new DbConnection();
        }
        return dbConnection;
    }

    public static DbConnection getDbConnection() throws SQLException, ClassNotFoundException {
        return dbConnection == null ? dbConnection = new DbConnection() : dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}
