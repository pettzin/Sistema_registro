package model;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private int id;
    private String nome;
    private String descricao;
    private Professor professor;
    private List<Turma> turmas;

    public Curso() {
        this.turmas = new ArrayList<>();
    }

    public Curso(int id, String nome, String descricao, Professor professor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.professor = professor;
        this.turmas = new ArrayList<>();
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public void adicionarTurma(Turma turma) {
        turmas.add(turma);
    }

    public boolean removerTurma(Turma turma) {
        return turmas.remove(turma);
    }

    @Override
    public String toString() {
        return nome;
    }
}