package model;

import java.util.Date;

public class Aluno {
    private int id;
    private String matricula; // Adicionado para representar a chave primária no banco
    private String nome;
    private Date dataNascimento;
    private String cpf;
    private String telefone;
    private String email;
    private String genero;
    private String endereco;
    private Turma turma;

    public Aluno() {
        this.matricula = "";
    }

    public Aluno(int id, String nome, Date dataNascimento, String cpf, String telefone, String email, String genero, String endereco, Turma turma) {
        this.id = id;
        this.matricula = String.valueOf(id); // Inicializa matricula com o id
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.genero = genero;
        this.endereco = endereco;
        this.turma = turma;
    }

    // Getters e Setters originais
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    // Novos getters e setters para matricula
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    @Override
    public String toString() {
        return nome + (matricula != null && !matricula.isEmpty() ? " (Matrícula: " + matricula + ")" : "");
    }
}