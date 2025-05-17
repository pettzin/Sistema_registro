package controller;

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

    public Turma buscarTurmaPorCodigo(String codigo) {
        return turmaDAO.buscarPorCodigo(codigo);
    }

    public List<Turma> buscarTodasTurmas() {
        return turmaDAO.buscarTodos();
    }

    public List<Turma> buscarTurmasPorCodigo(String codigo) {
        return turmaDAO.buscarTurmasPorCodigo(codigo);
    }
}