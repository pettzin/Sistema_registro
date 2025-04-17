package Atividade;

import java.util.ArrayList;
import java.util.List;

public class Turma {
 private String nome;
 private String codigo;
 private List<Aluno> alunos;

 public Turma(String nome, String codigo) {
     this.nome = nome;
     this.codigo = codigo;
     this.alunos = new ArrayList<>();
 }

 public String getNome() { return nome; }
 public String getCodigo() { return codigo; }
 public List<Aluno> getAlunos() { return alunos; }

 public void setNome(String nome) { this.nome = nome; }

 public void adicionarAluno(Aluno aluno) {
     alunos.add(aluno);
 }

 public void removerAluno(String matricula) {
     alunos.removeIf(a -> a.getMatricula().equals(matricula));
 }

 @Override
 public String toString() {
     return "Turma: " + nome + " (Código: " + codigo + ")";
 }
}
