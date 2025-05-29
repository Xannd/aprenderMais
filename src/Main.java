import dao.AlunoDAO;
import dao.LivroDAO;
import dao.EmprestimoDAO;
import model.Aluno;
import model.Livro;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("=== Biblioteca Escolar ===");
            System.out.println("1 - Cadastrar aluno");
            System.out.println("2 - Cadastrar livro");
            System.out.println("3 - Registrar empréstimo");
            System.out.println("4 - Listar empréstimos");
            System.out.println("5 - Registrar devolução");
            System.out.println("0 - Sair");
            opcao = sc.nextInt();
            sc.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Matrícula: ");
                        String matricula = sc.nextLine();
                        System.out.print("Data nascimento (AAAA-MM-DD): ");
                        LocalDate data = LocalDate.parse(sc.nextLine());
                        AlunoDAO.inserir(new Aluno(nome, matricula, data));
                        System.out.println("Aluno cadastrado.");
                        break;

                    case 2:
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Autor: ");
                        String autor = sc.nextLine();
                        System.out.print("Ano publicação: ");
                        int ano = sc.nextInt();
                        System.out.print("Estoque: ");
                        int estoque = sc.nextInt();
                        LivroDAO.inserir(new Livro(titulo, autor, ano, estoque));
                        System.out.println("Livro cadastrado.");
                        break;

                    case 3:
                        System.out.print("ID do aluno: ");
                        int idAluno = sc.nextInt();
                        System.out.print("ID do livro: ");
                        int idLivro = sc.nextInt();

                        int estoqueAtual = LivroDAO.consultarEstoque(idLivro);
                        if (estoqueAtual <= 0) {
                            System.out.println("Erro: Livro indisponível no estoque.");
                        } else {
                            if (EmprestimoDAO.registrarEmprestimo(idAluno, idLivro)) {
                                System.out.println("Empréstimo registrado com sucesso!");
                            } else {
                                System.out.println("Erro: Não foi possível registrar o empréstimo.");
                            }
                        }
                        break;

                    case 4:
                        EmprestimoDAO.listarEmprestimosDetalhado();
                        break;
                    case 5:
                        System.out.print("ID do empréstimo: ");
                        int idEmprestimo = sc.nextInt();
                        EmprestimoDAO.registrarDevolucao(idEmprestimo);
                        break;

                }
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);

        sc.close();
    }
}
