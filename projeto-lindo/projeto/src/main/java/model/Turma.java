package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Turma {
    private int id;
    private String codigo; // Ex: 1A, 1B, 2A, 2B
    private String periodo; // Manhã, Tarde ou Noite
    private int capacidade;
    private Date dataInicio;
    private Date dataTermino;
    private Curso curso;
    private List<Aluno> alunos;

    public Turma() {
        this.alunos = new ArrayList<>();
    }

    public Turma(int id, String codigo, String periodo, int capacidade, Date dataInicio, Date dataTermino, Curso curso) {
        this.id = id;
        this.codigo = codigo;
        this.periodo = periodo;
        this.capacidade = capacidade;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.curso = curso;
        this.alunos = new ArrayList<>();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
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

    public boolean adicionarAluno(Aluno aluno) {
        if (alunos.size() < capacidade) {
            alunos.add(aluno);
            return true;
        }
        return false;
    }

    public boolean removerAluno(Aluno aluno) {
        return alunos.remove(aluno);
    }

    @Override
    public String toString() {
        return codigo + " - " + periodo;
    }
}