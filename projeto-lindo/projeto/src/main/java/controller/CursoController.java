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
        try {
            cursoDAO.salvar(curso);
        } catch (Exception e) {
            System.err.println("Erro ao salvar curso: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar curso: " + e.getMessage());
        }
    }

    public void excluirCurso(Curso curso) {
        try {
            cursoDAO.excluir(curso);
        } catch (Exception e) {
            System.err.println("Erro ao excluir curso: " + e.getMessage());
            throw new RuntimeException("Erro ao excluir curso: " + e.getMessage());
        }
    }

    public Curso buscarCursoPorId(int id) {
        try {
            return cursoDAO.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar curso por ID: " + e.getMessage());
            return null;
        }
    }

    public List<Curso> buscarTodosCursos() {
        try {
            return cursoDAO.buscarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os cursos: " + e.getMessage());
            return null;
        }
    }

    public List<Curso> buscarCursosPorNome(String nome) {
        try {
            return cursoDAO.buscarPorNome(nome);
        } catch (Exception e) {
            System.err.println("Erro ao buscar cursos por nome: " + e.getMessage());
            return null;
        }
    }

    public List<Curso> buscarCursosPorProfessor(Professor professor) {
        try {
            return cursoDAO.buscarPorProfessor(professor);
        } catch (Exception e) {
            System.err.println("Erro ao buscar cursos por professor: " + e.getMessage());
            return null;
        }
    }
}