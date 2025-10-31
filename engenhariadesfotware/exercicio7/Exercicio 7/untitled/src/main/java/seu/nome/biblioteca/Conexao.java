package seu.nome.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
   private static final String url = "jdbc:sqlite:banco.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public static void inicializarBanco() {

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nome TEXT NOT NULL, " +
                        "email TEXT NOT NULL UNIQUE)";
                stmt.execute(sqlUsuarios);

                String sqlLivros = "CREATE TABLE IF NOT EXISTS livros (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "titulo TEXT NOT NULL, " +
                        "autor TEXT NOT NULL, " +
                        "disponivel INTEGER NOT NULL)";
                stmt.execute(sqlLivros);

                String sqlEmprestimos = "CREATE TABLE IF NOT EXISTS emprestimos (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "usuario_id INTEGER NOT NULL, " +
                        "livro_id INTEGER NOT NULL, " +
                        "FOREIGN KEY(usuario_id) REFERENCES usuarios(id), " +
                        "FOREIGN KEY(livro_id) REFERENCES livros(id))";
                stmt.execute(sqlEmprestimos);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}