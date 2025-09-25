package seu.nome.biblioteca;

import seu.nome.biblioteca.models.Livro;
import seu.nome.biblioteca.models.Usuario;
import seu.nome.biblioteca.services.EmprestimoService;
import seu.nome.biblioteca.services.LivroService;
import seu.nome.biblioteca.services.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Conexao.inicializarBanco();
        try (Scanner sc = new Scanner(System.in)) {
            OUTER:
            while (true) {
                System.out.println("\n--- MENU DA BIBLIOTECA ---");
                System.out.println("1 - Adicionar Usuário");
                System.out.println("2 - Listar Usuários");
                System.out.println("3 - Adicionar Livro");
                System.out.println("4 - Listar Livros");
                System.out.println("5 - Emprestar Livro");
                System.out.println("6 - Devolver Livro");
                System.out.println("7 - Listar Empréstimos");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                int op = sc.nextInt();
                sc.nextLine();

                switch (op) {
                    case 1 -> {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        UserService.adicionarUsuario(new Usuario(nome, email));
                        break;
                    }
                    case 2 -> {
                        UserService.listarUsuarios().forEach(user -> {
                            System.out.println("ID: " + user.getUsuarioId() + " | Nome: " + user.getNome() + " | Email: " + user.getEmail());
                        });
                        break;
                    }
                    case 3 -> {
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Autor: ");
                        String autor = sc.nextLine();
                        LivroService.adicionarLivro(new Livro(titulo, autor));
                        break;
                    }
                    case 4 -> {
                        LivroService.listarLivros().forEach(livro -> {
                            System.out.println("ID: " + livro.getId() + " | Título: " + livro.getTitulo() + " | Autor: " + livro.getAutor() + " | Disponível: " + (livro.isDisponivel() ? "Sim" : "Não"));
                        });
                        break;
                    }
                    case 5 -> {
                        System.out.print("ID do Usuário: ");
                        int userId = sc.nextInt();
                        System.out.print("ID do Livro: ");
                        int livroId = sc.nextInt();
                        EmprestimoService.emprestarLivro(userId, livroId);
                        break;
                    }
                    case 6 -> {
                        System.out.print("ID do Empréstimo: ");
                        int emprestimoId = sc.nextInt();
                        EmprestimoService.devolverLivro(emprestimoId);
                        break;
                    }
                    case 7 -> {
                        EmprestimoService.listarEmprestimos();
                        break;
                    }
                    case 0 -> {
                        System.out.println("Saindo do sistema...");
                        break OUTER;
                    }
                    default -> {
                        System.out.println("Opção inválida! Tente novamente.");
                    }
                }
            }
        }
    }
}