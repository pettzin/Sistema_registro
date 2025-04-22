package Atividade;

public class Periodos {
    private String nome;
    private String codigo;
    
    public Periodos(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }
    
    public String getNome() { return nome; }
    public String getCodigo() { return codigo; }
    
    public void setNome(String nome) { this.nome = nome; }
    
    @Override
    public String toString() {
        return "Período: " + nome + " (Código: " + codigo + ")";
    }
}