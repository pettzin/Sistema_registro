package controller;

import model.Aluno;
import model.Turma;
import model.dao.AlunoDAO;
import model.dao.TurmaDAO;

import java.util.List;

public class AlunoController {
    private AlunoDAO alunoDAO;
    private TurmaDAO turmaDAO;

    public AlunoController() {
        this.alunoDAO = new AlunoDAO();
        this.turmaDAO = new TurmaDAO();
    }

    public void salvarAluno(Aluno aluno) {
        try {
            // Verificar se o aluno está sendo matriculado em uma turma
            if (aluno.getTurma() != null) {
                // Buscar a turma atualizada do banco de dados para ter a contagem correta de alunos
                Turma turmaAtualizada = turmaDAO.buscarPorCodigo(aluno.getTurma().getCodigo());
                
                // Verificar se a turma já atingiu sua capacidade máxima
                int alunosMatriculados = turmaAtualizada.getAlunos().size();
                int capacidadeMaxima = turmaAtualizada.getCapacidade();
                
                // Se o aluno já está na turma (caso de edição), não contar ele duas vezes
                boolean alunoJaMatriculado = false;
                for (Aluno a : turmaAtualizada.getAlunos()) {
                    if (a.getMatricula() != null && a.getMatricula().equals(aluno.getMatricula())) {
                        alunoJaMatriculado = true;
                        break;
                    }
                }
                
                // Se o aluno não está na turma e a turma já está cheia
                if (!alunoJaMatriculado && alunosMatriculados >= capacidadeMaxima) {
                    throw new RuntimeException("Não é possível matricular o aluno. A turma " + 
                                              turmaAtualizada.getNome() + " já atingiu sua capacidade máxima de " + 
                                              capacidadeMaxima + " alunos.");
                }
            }
            
            alunoDAO.salvar(aluno);
        } catch (Exception e) {
            System.err.println("Erro ao salvar aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar aluno: " + e.getMessage());
        }
    }

    public void excluirAluno(Aluno aluno) {
        try {
            alunoDAO.excluir(aluno);
        } catch (Exception e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir aluno: " + e.getMessage());
        }
    }

    public Aluno buscarAlunoPorId(int id) {
        try {
            return alunoDAO.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno por ID: " + e.getMessage());
            return null;
        }
    }
    
    public Aluno buscarAlunoPorMatricula(String matricula) {
        try {
            return alunoDAO.buscarPorMatricula(matricula);
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno por matrícula: " + e.getMessage());
            return null;
        }
    }

    public List<Aluno> buscarTodosAlunos() {
        try {
            return alunoDAO.buscarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os alunos: " + e.getMessage());
            return null;
        }
    }

    public List<Aluno> buscarAlunosPorNome(String nome) {
        try {
            return alunoDAO.buscarPorNome(nome);
        } catch (Exception e) {
            System.err.println("Erro ao buscar alunos por nome: " + e.getMessage());
            return null;
        }
    }

    public Aluno buscarAlunoPorCpf(String cpf) {
        try {
            return alunoDAO.buscarPorCpf(cpf);
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno por CPF: " + e.getMessage());
            return null;
        }
    }

    public List<Aluno> buscarAlunosPorTurma(Turma turma) {
        try {
            return alunoDAO.buscarPorTurma(turma);
        } catch (Exception e) {
            System.err.println("Erro ao buscar alunos por turma: " + e.getMessage());
            return null;
        }
    }

    public boolean matricularAlunoEmTurma(Aluno aluno, Turma turma) {
        try {
            // Buscar a turma atualizada do banco de dados
            TurmaDAO turmaDAO = new TurmaDAO();
            Turma turmaAtualizada = turmaDAO.buscarPorCodigo(turma.getCodigo());
            
            // Verificar se a turma já atingiu sua capacidade máxima
            if (turmaAtualizada.getAlunos().size() >= turmaAtualizada.getCapacidade()) {
                return false;
            }
            
            aluno.setTurma(turma);
            turma.adicionarAluno(aluno);
            alunoDAO.salvar(aluno);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao matricular aluno em turma: " + e.getMessage());
            return false;
        }
    }
}
