package seu.nome.biblioteca.models;

public class Emprestimo {
    private int usuarioId;
    private int livroId;

    public Emprestimo(int usuarioId, int livroId) {
        this.usuarioId = usuarioId;
        this.livroId = livroId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }
}
