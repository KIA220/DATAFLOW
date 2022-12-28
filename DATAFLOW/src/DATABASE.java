import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.JDBC;

public class DATABASE {
    private static final String url1 = "jdbc:sqlite:src/DB/StorageDB.db";



    DATABASE() throws SQLException {
        DriverManager.registerDriver(new JDBC());

    }
    public String passer() {
        try {
            Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url1)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Storage");
                StringBuilder data= new StringBuilder();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String numberItems = resultSet.getString(3);
                    data.append(id).append(" \t").append(name).append(" \t").append(numberItems).append("\n");
                }
                conn.close();
                return data.toString();
            }

        } catch (Exception ex) {
            return ex.toString();
        }
    }
    public String LimitSpaceLeft() {
        try {
            Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url1)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT SUM (numberItems) FROM Storage");
                String data="";
                while (resultSet.next()) {
                    String numberItems = resultSet.getString(1);
                    data = numberItems.concat("/10000 items currently in storage");
                }
                conn.close();
                return data;
            }

        } catch (Exception ex) {
            return ex.toString();
        }
    }
    public String numberOfPositions() {
        try {
            Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url1)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT COUNT (name) FROM Storage");
                String data="";
                while (resultSet.next()) {
                    String numberPositions = resultSet.getString(1);
                    data = numberPositions.concat(" - number of all positions in storage");
                }
                conn.close();
                return data;
            }

        } catch (Exception ex) {
            return ex.toString();
        }
    }

}
