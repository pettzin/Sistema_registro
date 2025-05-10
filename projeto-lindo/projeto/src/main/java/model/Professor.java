package model;

import java.util.Date;

public class Professor {
    private int id;
    private String nome;
    private Date dataNascimento;
    private String cpf;
    private String endereco;
    private String carteirinhaLicenciatura;

    public Professor() {
    }

    public Professor(int id, String nome, Date dataNascimento, String cpf, String endereco, String carteirinhaLicenciatura) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.endereco = endereco;
        this.carteirinhaLicenciatura = carteirinhaLicenciatura;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCarteirinhaLicenciatura() {
        return carteirinhaLicenciatura;
    }

    public void setCarteirinhaLicenciatura(String carteirinhaLicenciatura) {
        this.carteirinhaLicenciatura = carteirinhaLicenciatura;
    }

    @Override
    public String toString() {
        return nome;
    }
}