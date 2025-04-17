package Atividade;

import java.util.*;

public class Registro {
 private Map<String, Turma> turmas;

 public Registro() {
     this.turmas = new HashMap<>();
 }

 public void cadastrarTurma(String nome, String codigo) {
     if (turmas.containsKey(codigo)) {
         System.out.println("Turma com esse código já existe.");
     } else {
         turmas.put(codigo, new Turma(nome, codigo));
         System.out.println("Turma cadastrada.");
     }
 }

 public void alterarTurma(String codigo, String novoNome) {
     Turma turma = turmas.get(codigo);
     if (turma != null) {
         turma.setNome(novoNome);
         System.out.println("Turma alterada.");
     } else {
         System.out.println("Turma não encontrada.");
     }
 }

 public void excluirTurma(String codigo) {
     if (turmas.remove(codigo) != null) {
         System.out.println("Turma excluída.");
     } else {
         System.out.println("Turma não encontrada.");
     }
 }

 public void listarTurmas() {
     if (turmas.isEmpty()) {
         System.out.println("Nenhuma turma cadastrada.");
     } else {
         for (Turma t : turmas.values()) {
             System.out.println(t);
         }
     }
 }

 public void cadastrarAluno(String codigoTurma, String nome, String matricula) {
     Turma turma = turmas.get(codigoTurma);
     if (turma != null) {
         turma.adicionarAluno(new Aluno(nome, matricula));
         System.out.println("Aluno cadastrado.");
     } else {
         System.out.println("Turma não encontrada.");
     }
 }

 public void alterarAluno(String codigoTurma, String matricula, String novoNome) {
     Turma turma = turmas.get(codigoTurma);
     if (turma != null) {
         for (Aluno a : turma.getAlunos()) {
             if (a.getMatricula().equals(matricula)) {
                 a.setNome(novoNome);
                 System.out.println("Aluno alterado.");
                 return;
             }
         }
         System.out.println("Aluno não encontrado.");
     } else {
         System.out.println("Turma não encontrada.");
     }
 }

 public void excluirAluno(String codigoTurma, String matricula) {
     Turma turma = turmas.get(codigoTurma);
     if (turma != null) {
         turma.removerAluno(matricula);
         System.out.println("Aluno excluído (se existia).");
     } else {
         System.out.println("Turma não encontrada.");
     }
 }

 public void listarAlunos(String codigoTurma) {
     Turma turma = turmas.get(codigoTurma);
     if (turma != null) {
         System.out.println("Alunos da " + turma.getNome() + ":");
         for (Aluno a : turma.getAlunos()) {
             System.out.println(a);
         }
     } else {
         System.out.println("Turma não encontrada.");
     }
 }
}
