package seu.nome.biblioteca;
import seu.nome.biblioteca.models.Emprestimo;
import seu.nome.biblioteca.models.Livro;
import seu.nome.biblioteca.models.Usuario;
import seu.nome.biblioteca.services.LivroService;
import seu.nome.biblioteca.services.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Conexao.inicializarBanco();
        UserService userService = new UserService();
        LivroService livroService = new LivroService();

        try (Scanner sc = new Scanner(System.in)) {
            OUTER:
            while (true) {
                System.out.println("\n1 - Adicionar Usuário");
                System.out.println("2 - Listar Usuários");
                System.out.println("3 - Adicionar Livro");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1 -> {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        UserService.adicionarUsuario(new Usuario(nome, email));
                    }
                    case 2 -> {
                        userService.listarUsuarios().forEach(user -> {
                            System.out.println("ID: " + user.getUsuarioId() + " | Nome: " + user.getNome() + " | Email: " + user.getEmail());
                        });
                    }
                    case 3 -> {
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Autor: ");
                        String autor = sc.nextLine();
                        LivroService.adicionarLivro(new Livro(titulo, autor));
                    }
                    case 0 -> {
                        break OUTER;
                    }
                    default -> {
                        System.out.println("Opção inválida!");
                        continue;
                    }
                    }
                }
            }
        }
    }

