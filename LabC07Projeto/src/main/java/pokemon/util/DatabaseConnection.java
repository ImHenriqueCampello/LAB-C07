package pokemon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //-------------------------------------------------------------------
    private static final String URL = "jdbc:mysql://localhost:3306/PokemonDB";
    private static final String USER = "root";
    private static final String PASSWORD = "#Ique2003";
    private static Connection connection;
    //-------------------------------------------------------------------



    //-------------------------------------------------------------------
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
    //-------------------------------------------------------------------



    //-------------------------------------------------------------------
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
    //-------------------------------------------------------------------
}