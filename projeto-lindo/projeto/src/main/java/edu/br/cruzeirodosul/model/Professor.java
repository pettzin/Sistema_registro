package edu.br.cruzeirodosul.model;

import lombok.*;

@Data
@NoArgsConstructor
public class Professor {
    private int id;           // ID único para o aluno
    private String nome;
    private String cpf;
    private String genero;
    private String email;
    private String telefone;

    // Construtor completo
    public Professor(int id, String nome, String licenciatura, String cpf, String genero, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.genero = genero;
        this.email = email;
        this.telefone = telefone;
    }

    // Construtor simplificado (para compatibilidade com código existente)
    public Professor(String nome, String licenciatura) {
        this.id = 0; // Será definido pelo sistema
        this.nome = nome;
        this.cpf = "";
        this.email = "";
        this.telefone = "";
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getGenero() { return genero; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
   
    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() {
        return "Professor: " + nome + " (ID: " + id + ", Licenciatura: " + ")" +
               "\n  CPF: " + cpf +
               "\n  Gênero: " + genero +
               "\n  Email: " + email +
               "\n  Telefone: " + telefone;
    }
}
