package seu.nome.biblioteca.models;

public class Usuario {

    private String nome;
    private String email;
    private int usuarioId;

    public Usuario(String nome, String email, int usuarioId) {
        this.nome = nome;
        this.usuarioId = usuarioId;
        this.email = email;
    }
    public Usuario(String nome, String email) {
        this(nome, email,0);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
   public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }
}

