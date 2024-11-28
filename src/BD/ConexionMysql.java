package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMysql {
    // Objeto de la conexión con la base de datos.
    private Connection conn;

    // Parámetros de conexión
    private static final String USER = "root";
    private static final String PASSWORD = "quegay";
    private static final String URL = "jdbc:mysql://localhost:3306/zarape";
    private static final String PARAMS = "?useSSL=false&useUnicode=true&characterEncoding=utf-8";

    /**
     * Abre una conexión a la base de datos.
     * @return Connection - Objeto de conexión a la base de datos.
     */
    public Connection open() {
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            conn = DriverManager.getConnection(URL + PARAMS, USER, PASSWORD);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectar a la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Cierra la conexión actual a la base de datos.
     */
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al cerrar la conexión: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Cierra una conexión específica a la base de datos.
     * @param conn - Objeto Connection a cerrar.
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al cerrar la conexión: " + e.getMessage(), e);
            }
        }
    }
}