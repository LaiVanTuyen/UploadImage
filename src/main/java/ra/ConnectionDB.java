package ra;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/Upload_Image";
    private static final String USER="root";
    private static final String PASS="240301";

    public static Connection openConnection(){
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(Connection conn, CallableStatement callSt){
        if(conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (callSt != null) {
            try {
                callSt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
