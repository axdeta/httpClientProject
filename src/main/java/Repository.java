import java.sql.*;

public class Repository {

    private static final String DB_URL = Props.getProperties("DB_URL");
    private static final String USER = Props.getProperties("USER");
    private static final String PASS = Props.getProperties("PASS");
    private static final String tableName = Props.getProperties("TABLE_NAME");

    public Repository() {
        createTableIfNotExist();
        if (isEmptyTable()) { // IF TABLE EMPTY
            //INSERT FIRST RECORD [ id: 1 count: 0 ]
            Connection connection = getConnection();
            String sql = "INSERT INTO " + tableName + " (ID, COUNT) VALUES (1, 0)";
            try {
                assert connection != null;
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
                connection.close();
            } catch (SQLException c) {
                c.printStackTrace();
                System.out.println("Error insert first record");
            }
        }
    }

    private static Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) { //ERROR: driver not found
            System.out.println("PostgreSQL JDBC Driver is not found");
            e.printStackTrace();
            return null;
        }

        Connection connection = null;


        try {
            if (DB_URL == null) return null;
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) { //ERROR connection failed
            System.out.println("Connection Failed");
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    private void createTableIfNotExist() {
        Connection connection = getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(ID INT PRIMARY KEY NOT NULL, COUNT INT NOT NULL)";
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error create table");
            e.printStackTrace();
        }
    }

    private void dropTable() {
        Connection connection = getConnection();
        if (connection != null) {
            String sql = "DROP TABLE IF EXISTS " + tableName;
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
                connection.close();
            } catch (SQLException c) {
                System.out.println("ERROR drop table");
                c.printStackTrace();
            }

        }
    }

    public int getCount() {
        try {
            int result = 0;
            Connection connection = getConnection();
            String sql = "SELECT * FROM " + tableName;
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
                result = resultSet.getInt("count");
            statement.close();
            connection.close();
            return result;
        } catch (SQLException c) {
            c.printStackTrace();
            System.out.println("Error get first record");
        }
        return 0;
    }

    public boolean countUp() {
        int count = 0;

        //GET CURRENT COUNT NUMBER
        count = getCount();
        //COUNT UP
        count++;
        //UPDATE COUNT NUMBER IN TABLE
        try {
            Connection connection = getConnection();
            String sql = "UPDATE " + tableName + " set count = " + count + " where ID=1;";
            assert connection != null;
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (SQLException c) {
            c.printStackTrace();
            System.out.println("Error update record");
            return false;
        }
        return true;
    }

    private boolean isEmptyTable() {
        int row_count = 0;
        String sql = "SELECT COUNT(*) FROM " + tableName; // GET NUMBER OF ROWS IN TABLE
        try {
            Connection connection = getConnection();
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            row_count = resultSet.getInt(1);
            statement.close();
            connection.close();
        } catch (SQLException c) {
            c.printStackTrace();
            System.out.println("Error get rows count");
        }
        return row_count == 0;
    }
}
