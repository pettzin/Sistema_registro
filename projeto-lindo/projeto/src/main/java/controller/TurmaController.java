package controller;

import model.Curso;
import model.Turma;
import model.dao.TurmaDAO;

import java.util.List;

public class TurmaController {
    private TurmaDAO turmaDAO;

    public TurmaController() {
        this.turmaDAO = new TurmaDAO();
    }

    public void salvarTurma(Turma turma) {
        try {
            turmaDAO.salvar(turma);
        } catch (Exception e) {
            System.err.println("Erro ao salvar turma: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar turma: " + e.getMessage());
        }
    }

    public void excluirTurma(Turma turma) {
        try {
            turmaDAO.excluir(turma);
        } catch (Exception e) {
            System.err.println("Erro ao excluir turma: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir turma: " + e.getMessage());
        }
    }

    public Turma buscarTurmaPorId(int id) {
        try {
            return turmaDAO.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar turma por ID: " + e.getMessage());
            return null;
        }
    }

    public List<Turma> buscarTodasTurmas() {
        try {
            return turmaDAO.buscarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao buscar todas as turmas: " + e.getMessage());
            return null;
        }
    }

    public List<Turma> buscarTurmasPorCodigo(String codigo) {
        try {
            return turmaDAO.buscarPorCodigo(codigo);
        } catch (Exception e) {
            System.err.println("Erro ao buscar turmas por código: " + e.getMessage());
            return null;
        }
    }

    public List<Turma> buscarTurmasPorCurso(Curso curso) {
        try {
            return turmaDAO.buscarPorCurso(curso);
        } catch (Exception e) {
            System.err.println("Erro ao buscar turmas por curso: " + e.getMessage());
            return null;
        }
    }

    public List<Turma> buscarTurmasPorPeriodo(String periodo) {
        try {
            return turmaDAO.buscarPorPeriodo(periodo);
        } catch (Exception e) {
            System.err.println("Erro ao buscar turmas por período: " + e.getMessage());
            return null;
        }
    }
}