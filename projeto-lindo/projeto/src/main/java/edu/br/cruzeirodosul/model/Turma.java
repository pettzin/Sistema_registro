package edu.br.cruzeirodosul.model;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Turma {
    private String nome;
    private String codigo;
    private String curso;         // Nome do curso
    private String periodo;       // Manhã, Tarde ou Noite
    private int capacidadeMaxima; // Limite de alunos na turma
    private List<Aluno> alunos;

    // Atualizar o construtor completo
    public Turma(String nome, String codigo, String curso, String periodo, int capacidadeMaxima) {
        this.nome = nome;
        this.codigo = codigo;
        this.curso = curso;
        this.periodo = periodo;
        this.capacidadeMaxima = capacidadeMaxima > 0 ? capacidadeMaxima : 40; // Valor padrão de 40 se for inválido
        this.alunos = new ArrayList<>();
    }
    
    // Atualizar o construtor simplificado
    public Turma(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.curso = "";
        this.periodo = "";
        this.capacidadeMaxima = 40; // Valor padrão
        this.alunos = new ArrayList<>();
    }

    // Getters
    public String getNome() { return nome; }
    public String getCodigo() { return codigo; }
    public String getCurso() { return curso; }
    public String getPeriodo() { return periodo; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public List<Aluno> getAlunos() { return alunos; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setCurso(String curso) { this.curso = curso; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public void setCapacidadeMaxima(int capacidadeMaxima) { 
        this.capacidadeMaxima = capacidadeMaxima > 0 ? capacidadeMaxima : this.capacidadeMaxima; 
    }

    // Métodos para gerenciar alunos
    public boolean adicionarAluno(Aluno aluno) {
        // Verifica se o aluno já está na turma
        if (alunoJaMatriculado(aluno.getMatricula())) {
            return false;
        }
        
        // Verifica se há vagas disponíveis
        if (estaCheia()) {
            return false;
        }
        
        alunos.add(aluno);
        return true;
    }

    public boolean alunoJaMatriculado(String matricula) {
        return alunos.stream().anyMatch(a -> a.getMatricula().equals(matricula));
    }

    public void removerAluno(String matricula) {
        alunos.removeIf(a -> a.getMatricula().equals(matricula));
    }

    // Adicionar método para verificar se a turma está cheia
    public boolean estaCheia() {
        return alunos.size() >= capacidadeMaxima;
    }

    // Adicionar método para obter o número de vagas disponíveis
    public int vagasDisponiveis() {
        return capacidadeMaxima - alunos.size();
    }

    // Atualizar o método toString para incluir informações de vagas
    @Override
    public String toString() {
        return "Turma: " + nome + " (Código: " + codigo + ")" +
               "\n  Curso: " + curso +
               "\n  Período: " + periodo +
               "\n  Capacidade: " + alunos.size() + "/" + capacidadeMaxima + 
               " (" + vagasDisponiveis() + " vagas disponíveis)";
    }
}
