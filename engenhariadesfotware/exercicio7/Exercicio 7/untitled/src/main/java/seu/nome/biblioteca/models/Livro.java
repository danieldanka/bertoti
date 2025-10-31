package seu.nome.biblioteca.models;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private boolean disponivel;

    public Livro(String titulo, String autor, int id, boolean disponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.id = id;
        this.disponivel = disponivel;
    }

    public Livro(String titulo, String autor) {
        this(titulo, autor,0, true);
    }
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getId() {
        return id;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}

