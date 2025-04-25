package Atividade;

import java.util.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registro {
    private Map<String, Turma> turmas;
    private Map<String, Periodos> periodos;
    private Map<String, Aluno> alunos;

    public Registro() {
        this.turmas = new HashMap<>();
        this.periodos = new HashMap<>();
        this.alunos = new HashMap<>();
    }

    // Métodos para Turma
    public void cadastrarTurma(String nome, String codigo) {
        if (turmas.containsKey(codigo)) {
            System.out.println("Turma com esse código já existe.");
        } else {
            turmas.put(codigo, new Turma(nome, codigo));
            System.out.println("Turma cadastrada com sucesso.");
        }
    }

    public void alterarTurma(String codigo, String novoNome) {
        Turma turma = turmas.get(codigo);
        if (turma != null) {
            turma.setNome(novoNome);
            System.out.println("Turma alterada com sucesso.");
        } else {
            System.out.println("Turma não encontrada.");
        }
    }

    public void excluirTurma(String codigo) {
        if (turmas.remove(codigo) != null) {
            System.out.println("Turma excluída com sucesso.");
        } else {
            System.out.println("Turma não encontrada.");
        }
    }

    public void listarTurmas() {
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
        } else {
            System.out.println("\n--- TURMAS CADASTRADAS ---");
            for (Turma t : turmas.values()) {
                System.out.println(t);
            }
        }
    }

    // Métodos para Aluno
    public void cadastrarAluno(String nome, String matricula) {
        if (alunos.containsKey(matricula)) {
            System.out.println("Aluno com esta matrícula já existe.");
        } else {
            alunos.put(matricula, new Aluno(nome, matricula));
            System.out.println("Aluno cadastrado com sucesso.");
        }
    }

    public void matricularAluno(String codigoTurma, String matricula) {
        Turma turma = turmas.get(codigoTurma);
        Aluno aluno = alunos.get(matricula);
        
        if (turma == null) {
            System.out.println("Turma não encontrada.");
        } else if (aluno == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            turma.adicionarAluno(aluno);
            System.out.println("Aluno matriculado com sucesso na turma " + turma.getNome());
        }
    }

    public void alterarAluno(String matricula, String novoNome) {
        Aluno aluno = alunos.get(matricula);
        if (aluno != null) {
            aluno.setNome(novoNome);
            System.out.println("Aluno alterado com sucesso.");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void excluirAluno(String matricula) {
        // Remove o aluno de todas as turmas primeiro
        for (Turma t : turmas.values()) {
            t.removerAluno(matricula);
        }
        
        if (alunos.remove(matricula) != null) {
            System.out.println("Aluno excluído com sucesso.");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void listarAlunos(String codigoTurma) {
        Turma turma = turmas.get(codigoTurma);
        if (turma != null) {
            System.out.println("\n--- ALUNOS DA TURMA " + turma.getNome() + " ---");
            for (Aluno a : turma.getAlunos()) {
                System.out.println(a);
            }
        } else {
            System.out.println("Turma não encontrada.");
        }
    }

    // Métodos para Período
    public void cadastrarPeriodo(String nome, String codigo) {
        if (periodos.containsKey(codigo)) {
            System.out.println("Período com esse código já existe.");
        } else {
            periodos.put(codigo, new Periodos(nome, codigo));
            System.out.println("Período cadastrado com sucesso.");
        }
    }

    public void listarPeriodos() {
        if (periodos.isEmpty()) {
            System.out.println("Nenhum período cadastrado.");
        } else {
            System.out.println("\n--- PERÍODOS CADASTRADOS ---");
            for (Periodos p : periodos.values()) {
                System.out.println(p);
            }
        }
    }

    // Métodos para Relatórios
    public void gerarRelatorios() {
        System.out.println("\n--- RELATÓRIO GERAL ---");
        System.out.println("Total de Turmas: " + turmas.size());
        System.out.println("Total de Períodos: " + periodos.size());
        System.out.println("Total de Alunos Cadastrados: " + alunos.size());
        
        System.out.println("\nAlunos por Turma:");
        for (Turma t : turmas.values()) {
            System.out.println("- " + t.getNome() + ": " + t.getAlunos().size() + " alunos");
        }
    }

    // Métodos para Busca
    public void buscarAluno(String matricula) {
        Aluno aluno = alunos.get(matricula);
        if (aluno != null) {
            System.out.println("\n--- ALUNO ENCONTRADO ---");
            System.out.println(aluno);
            
            System.out.println("\nTurmas matriculadas:");
            boolean encontrado = false;
            for (Turma t : turmas.values()) {
                if (t.getAlunos().contains(aluno)) {
                    System.out.println("- " + t.getNome());
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("O aluno não está matriculado em nenhuma turma.");
            }
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void buscarTurma(String codigo) {
        Turma turma = turmas.get(codigo);
        if (turma != null) {
            System.out.println("\n--- TURMA ENCONTRADA ---");
            System.out.println(turma);
            listarAlunos(codigo);
        } else {
            System.out.println("Turma não encontrada.");
        }
    }
}