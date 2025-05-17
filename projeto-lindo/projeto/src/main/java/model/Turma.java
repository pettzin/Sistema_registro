package model;

import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String codigo;
    private String nome;
    private String periodo;
    private int capacidade;
    private String dataInicio;
    private String dataTermino;
    private Curso curso;
    private List<Aluno> alunos;

    public Turma() {
        this.alunos = new ArrayList<>();
    }

    public Turma(String codigo, String nome, String periodo, int capacidade, String dataInicio, String dataTermino) {
        this.codigo = codigo;
        this.nome = nome;
        this.periodo = periodo;
        this.capacidade = capacidade;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.alunos = new ArrayList<>();
    }

    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public void adicionarAluno(Aluno aluno) {
        if (this.alunos == null) {
            this.alunos = new ArrayList<>();
        }
        this.alunos.add(aluno);
    }

    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}