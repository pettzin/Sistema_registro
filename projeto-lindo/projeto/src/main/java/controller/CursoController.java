package controller;

import model.Curso;
import model.Professor;
import model.dao.CursoDAO;

import java.util.List;

public class CursoController {
    private CursoDAO cursoDAO;

    public CursoController() {
        this.cursoDAO = new CursoDAO();
    }

    public void salvarCurso(Curso curso) {
        cursoDAO.salvar(curso);
    }

    public void excluirCurso(Curso curso) {
        cursoDAO.excluir(curso);
    }

    public Curso buscarCursoPorId(int id) {
        return cursoDAO.buscarPorId(id);
    }

    public List<Curso> buscarTodosCursos() {
        return cursoDAO.buscarTodos();
    }

    public List<Curso> buscarCursosPorNome(String nome) {
        return cursoDAO.buscarPorNome(nome);
    }

    public List<Curso> buscarCursosPorProfessor(Professor professor) {
        return cursoDAO.buscarPorProfessor(professor);
    }
}