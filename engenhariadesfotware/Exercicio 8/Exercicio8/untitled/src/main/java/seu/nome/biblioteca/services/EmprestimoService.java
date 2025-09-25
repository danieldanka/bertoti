package seu.nome.biblioteca.services;

import seu.nome.biblioteca.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmprestimoService {

    /**
     * Realiza o empréstimo de um livro para um usuário.
     * Esta operação é uma transação: ou tudo funciona, ou nada é alterado.
     */
    public static void emprestarLivro(int usuarioId, int livroId) {
        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);
            String checkSql = "SELECT disponivel FROM livros WHERE id = ?";

            try (PreparedStatement pstmtCheck = conn.prepareStatement(checkSql)) {
                pstmtCheck.setInt(1, livroId);
                ResultSet rs = pstmtCheck.executeQuery();

                if (rs.next()) {
                    boolean disponivel = rs.getInt("disponivel") == 1;
                    if (!disponivel) {
                        System.out.println("ERRO: O livro não está disponível para empréstimo.");
                        conn.rollback(); // Desfaz a transação
                        return;
                    }
                } else {
                    System.out.println("ERRO: Livro com ID " + livroId + " não encontrado.");
                    conn.rollback();
                    return;
                }
            }

            String emprestimoSql = "INSERT INTO emprestimos (usuario_id, livro_id) VALUES (?, ?)";
            try (PreparedStatement pstmtEmprestimo = conn.prepareStatement(emprestimoSql)) {
                pstmtEmprestimo.setInt(1, usuarioId);
                pstmtEmprestimo.setInt(2, livroId);
                pstmtEmprestimo.executeUpdate();
            }

            String updateSql = "UPDATE livros SET disponivel = 0 WHERE id = ?";
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql)) {
                pstmtUpdate.setInt(1, livroId);
                pstmtUpdate.executeUpdate();
            }

            conn.commit();
            System.out.println("Empréstimo realizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao realizar o empréstimo. A operação foi cancelada.");
            throw new RuntimeException(e);
        }
    }

    public static void devolverLivro(int emprestimoId) {
        int livroId = -1;

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);

            String findLivroSql = "SELECT livro_id FROM emprestimos WHERE id = ?";
            try(PreparedStatement pstmtFind = conn.prepareStatement(findLivroSql)) {
                pstmtFind.setInt(1, emprestimoId);
                ResultSet rs = pstmtFind.executeQuery();
                if (rs.next()) {
                    livroId = rs.getInt("livro_id");
                } else {
                    System.out.println("ERRO: Empréstimo com ID " + emprestimoId + " não encontrado.");
                    conn.rollback();
                    return;
                }
            }

            String deleteSql = "DELETE FROM emprestimos WHERE id = ?";
            try(PreparedStatement pstmtDelete = conn.prepareStatement(deleteSql)) {
                pstmtDelete.setInt(1, emprestimoId);
                pstmtDelete.executeUpdate();
            }

            String updateSql = "UPDATE livros SET disponivel = 1 WHERE id = ?";
            try(PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql)) {
                pstmtUpdate.setInt(1, livroId);
                pstmtUpdate.executeUpdate();
            }

            conn.commit();
            System.out.println("Livro devolvido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao devolver o livro. A operação foi cancelada.");
            throw new RuntimeException(e);
        }
    }
    public static void listarEmprestimos() {
        String sql = "SELECT e.id, u.nome, l.titulo " +
                "FROM emprestimos e " +
                "JOIN usuarios u ON e.usuario_id = u.id " +
                "JOIN livros l ON e.livro_id = l.id";

        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- Empréstimos Ativos ---");
            boolean encontrouEmprestimos = false;

            while (rs.next()) {
                encontrouEmprestimos = true;
                int emprestimoId = rs.getInt("id");
                String nomeUsuario = rs.getString("nome");
                String tituloLivro = rs.getString("titulo");

                System.out.println(
                        "ID do Empréstimo: " + emprestimoId +
                                " | Usuário: " + nomeUsuario +
                                " | Livro: " + tituloLivro
                );
            }

            if (!encontrouEmprestimos) {
                System.out.println("Nenhum livro emprestado no momento.");
            }
            System.out.println("--------------------------");

        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao listar os empréstimos.");
            throw new RuntimeException(e);
        }
    }
}