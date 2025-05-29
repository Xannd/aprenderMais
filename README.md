✅ Descrição do Projeto

Sistema em Java com persistência em MySQL para gerenciar:

    Cadastro de alunos e livros.

    Controle de empréstimos e devoluções.

    Gestão automática de estoque.

    Geração de relatórios detalhados.

✅ Funcionalidades
🟢 Cadastro

    Cadastrar Aluno → nome, matrícula, data de nascimento.

    Cadastrar Livro → título, autor, ano de publicação e estoque.

🟢 Empréstimo

    Registrar empréstimo:
    ➡️ Valida automaticamente o estoque.
    ➡️ Diminui a quantidade disponível.
    ➡️ Define status = EMPRESTADO.

🟢 Devolução

    Registrar devolução:
    ➡️ Aumenta o estoque do livro.
    ➡️ Atualiza o status para DEVOLVIDO.
    ➡️ Mostra o nome do aluno que fez a devolução.

🟢 Relatórios

    Listar todos os empréstimos:
    ➡️ Mostra: nome do aluno, título do livro, data de empréstimo, data de devolução, status.

✅ Estrutura do Projeto

    model → classes de domínio: Aluno, Livro, Emprestimo.

    dao → acesso ao banco de dados: AlunoDAO, LivroDAO, EmprestimoDAO.

    util → conexão com MySQL: Conexao.java.

    Main.java → interface de console.

✅ Tecnologias

    Java 11+

    MySQL 8.0+

    JDBC

✅ Estrutura do Banco de Dados

Tabelas:

    Alunos

    Livros

    Emprestimos → agora com status: EMPRESTADO ou DEVOLVIDO.

✅ Como executar

    Configure o banco de dados MySQL conforme o script de criação das tabelas.

    Configure o arquivo Conexao.java com os dados corretos de conexão.

    Compile e execute o Main.java.

✅ Diagrama ER

➡️ Gerado via MySQL Workbench (em anexo).

✅ Créditos

    Equipe:
    ➡️ Alexandre de Oliveira da Costa - 2323780

✅ Professor

    Pedro Pinheiro

✅ Data

    Abril/Maio 2025

✅ Observações

    Todos os empréstimos são registrados com prazo de 7 dias para devolução.

    O sistema bloqueia empréstimos quando não há estoque disponível.

    Relatório exibe o status de cada empréstimo.