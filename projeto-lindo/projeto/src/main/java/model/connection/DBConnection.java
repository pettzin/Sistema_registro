package model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados
 */
public class DBConnection {
    // Configurações de conexão
    private static final String URL = "jdbc:mysql://localhost:3306/SistemaAcademico";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Coloque sua senha aqui
    
    private static Connection connection = null;
    
    /**
     * Obtém uma conexão com o banco de dados
     * @return Connection - objeto de conexão
     * @throws SQLException - caso ocorra algum erro na conexão
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Carrega o driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Estabelece a conexão
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC não encontrado: " + e.getMessage());
            } catch (SQLException e) {
                throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return connection;
    }
    
    /**
     * Fecha a conexão com o banco de dados
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Conexão com o banco de dados fechada com sucesso!");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}