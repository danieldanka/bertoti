package seu.nome.biblioteca.services;

import seu.nome.biblioteca.Conexao;
import seu.nome.biblioteca.models.Livro;
import seu.nome.biblioteca.models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class LivroService {
    public static void adicionarLivro(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, disponivel) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getAutor());
            pstmt.setInt(3, livro.isDisponivel() ? 1 : 0);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Usuario> listarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            var rs = pstmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                int id = rs.getInt("id");

                usuarios.add(new Usuario(nome, email, id));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }
}
