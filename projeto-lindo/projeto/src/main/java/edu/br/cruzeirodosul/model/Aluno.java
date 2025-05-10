package edu.br.cruzeirodosul.model;

import lombok.*;

@Data
@NoArgsConstructor
public class Aluno {
    private String nome;
    private String matricula;
    private String cpf;
    private String genero;
    private String email;
    private String telefone;
    private String endereco; // Adicionando campo de endereço
   
    // Construtor completo
    public Aluno(int id, String nome, String matricula, String cpf, String genero, String email, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.cpf = cpf;
        this.genero = genero;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }
    
    // Construtor simplificado (para compatibilidade com código existente)
    public Aluno(String nome, String matricula) {
        this.id = 0; // Será definido pelo sistema
        this.nome = nome;
        this.matricula = matricula;
        this.cpf = "";
        this.genero = "";
        this.email = "";
        this.telefone = "";
        this.endereco = "";
    }
   
    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getCpf() { return cpf; }
    public String getGenero() { return genero; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }
   
    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
   
    @Override
    public String toString() {
        return "Aluno: " + nome + " (ID: " + id + ", Matrícula: " + matricula + ")" +
               "\n  CPF: " + cpf +
               "\n  Gênero: " + genero +
               "\n  Email: " + email +
               "\n  Telefone: " + telefone +
               "\n  Endereço: " + endereco;
    }
}