/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author san21
 */
public class SQLServer {
    private static SQLServer instance = null;
    private Connection connection;

    // Datos de la conexión
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=dbIntranet"; // Cambia localhost y base de datos
    private String user = "sa"; // Usuario de la base de datos
    private String password = "1234"; // Contraseña de la base de datos

    // Constructor privado para evitar instanciación externa
    private SQLServer() {
        try {
            // Cargar el driver JDBC de SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Establecer la conexión
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error de conexión con la base de datos", e);
        }
    }

    // Obtener la instancia única
    public static SQLServer getInstance() {
        if (instance == null) {
            instance = new SQLServer();
        }
        return instance;
    }

    // Obtener la conexión
    public Connection getConnection() {
        return connection;
    }

    // Método para cerrar la conexión
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
