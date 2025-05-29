package dao;

import model.Emprestimo;
import util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {
    public static boolean registrarEmprestimo(int idAluno, int idLivro) throws SQLException {
        if (!LivroDAO.reduzirEstoque(idLivro)) {
            System.out.println("Livro indisponível.");
            return false;
        }

        String sql = "INSERT INTO Emprestimos (id_aluno, id_livro, data_devolucao, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idLivro);
            stmt.setDate(3, Date.valueOf(LocalDate.now().plusDays(7)));
            stmt.setString(4, "EMPRESTADO");
            stmt.executeUpdate();
        }
        return true;
    }


    public static void listarEmprestimosDetalhado() throws SQLException {
        String sql = "SELECT e.id_emprestimo, a.nome_aluno, l.titulo, e.data_emprestimo, e.data_devolucao, e.status " +
                "FROM Emprestimos e " +
                "JOIN Alunos a ON e.id_aluno = a.id_aluno " +
                "JOIN Livros l ON e.id_livro = l.id_livro";


        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idEmprestimo = rs.getInt("id_emprestimo");
                String nomeAluno = rs.getString("nome_aluno");
                String tituloLivro = rs.getString("titulo");
                Date dataEmprestimo = rs.getDate("data_emprestimo");
                Date dataDevolucao = rs.getDate("data_devolucao");
                String status = rs.getString("status");

                System.out.println("Empréstimo ID: " + idEmprestimo);
                System.out.println("Aluno: " + nomeAluno);
                System.out.println("Livro: " + tituloLivro);
                System.out.println("Data do Empréstimo: " + dataEmprestimo);
                System.out.println("Data da Devolução: " + dataDevolucao);
                System.out.println("Status: " + status);
                System.out.println("----------------------------");

            }
        }
    }

    public static void registrarDevolucao(int idEmprestimo) throws SQLException {
        String sqlSelect = "SELECT id_livro, id_aluno FROM Emprestimos WHERE id_emprestimo = ? AND status = 'EMPRESTADO'";
        String sqlUpdate = "UPDATE Emprestimos SET status = 'DEVOLVIDO' WHERE id_emprestimo = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {

            stmtSelect.setInt(1, idEmprestimo);
            try (ResultSet rs = stmtSelect.executeQuery()) {
                if (rs.next()) {
                    int idLivro = rs.getInt("id_livro");
                    int idAluno = rs.getInt("id_aluno");

                    // Atualiza status
                    try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                        stmtUpdate.setInt(1, idEmprestimo);
                        stmtUpdate.executeUpdate();
                    }

                    // Aumenta estoque
                    LivroDAO.aumentarEstoque(idLivro);

                    // Recupera nome do aluno
                    String nomeAluno = AlunoDAO.obterNomeAluno(idAluno);

                    System.out.println("Devolução realizada com sucesso pelo aluno: " + nomeAluno);
                } else {
                    System.out.println("Empréstimo não encontrado ou já devolvido.");
                }
            }
        }
    }

}
