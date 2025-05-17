package controller;

import model.Aluno;
import model.Turma;
import model.dao.AlunoDAO;

import java.util.List;

public class AlunoController {
    private AlunoDAO alunoDAO;

    public AlunoController() {
        this.alunoDAO = new AlunoDAO();
    }

    public void salvarAluno(Aluno aluno) {
        try {
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
            if (turma.getAlunos().size() < turma.getCapacidade()) {
                aluno.setTurma(turma);
                turma.adicionarAluno(aluno);
                alunoDAO.salvar(aluno);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao matricular aluno em turma: " + e.getMessage());
            return false;
        }
    }
}
