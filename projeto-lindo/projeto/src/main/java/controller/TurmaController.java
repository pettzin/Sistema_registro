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
        turmaDAO.salvar(turma);
    }

    public void excluirTurma(Turma turma) {
        turmaDAO.excluir(turma);
    }

    public Turma buscarTurmaPorId(int id) {
        return turmaDAO.buscarPorId(id);
    }

    public List<Turma> buscarTodasTurmas() {
        return turmaDAO.buscarTodos();
    }

    public List<Turma> buscarTurmasPorCodigo(String codigo) {
        return turmaDAO.buscarPorCodigo(codigo);
    }

    public List<Turma> buscarTurmasPorCurso(Curso curso) {
        return turmaDAO.buscarPorCurso(curso);
    }

    public List<Turma> buscarTurmasPorPeriodo(String periodo) {
        return turmaDAO.buscarPorPeriodo(periodo);
    }
}