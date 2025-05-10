package controller;

import model.Professor;
import model.dao.ProfessorDAO;

import java.util.List;

public class ProfessorController {
    private ProfessorDAO professorDAO;

    public ProfessorController() {
        this.professorDAO = new ProfessorDAO();
    }

    public void salvarProfessor(Professor professor) {
        professorDAO.salvar(professor);
    }

    public void excluirProfessor(Professor professor) {
        professorDAO.excluir(professor);
    }

    public Professor buscarProfessorPorId(int id) {
        return professorDAO.buscarPorId(id);
    }

    public List<Professor> buscarTodosProfessores() {
        return professorDAO.buscarTodos();
    }

    public List<Professor> buscarProfessoresPorNome(String nome) {
        return professorDAO.buscarPorNome(nome);
    }

    public Professor buscarProfessorPorCpf(String cpf) {
        return professorDAO.buscarPorCpf(cpf);
    }
}