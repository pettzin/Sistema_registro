package model.dao;

import model.Curso;
import model.Professor;

import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    private static List<Curso> cursos = new ArrayList<>();
    private static int nextId = 1;

    public void salvar(Curso curso) {
        if (curso.getId() == 0) {
            curso.setId(nextId++);
            cursos.add(curso);
        } else {
            for (int i = 0; i < cursos.size(); i++) {
                if (cursos.get(i).getId() == curso.getId()) {
                    cursos.set(i, curso);
                    break;
                }
            }
        }
    }

    public void excluir(Curso curso) {
        cursos.removeIf(c -> c.getId() == curso.getId());
    }

    public Curso buscarPorId(int id) {
        for (Curso curso : cursos) {
            if (curso.getId() == id) {
                return curso;
            }
        }
        return null;
    }

    public List<Curso> buscarTodos() {
        return new ArrayList<>(cursos);
    }

    public List<Curso> buscarPorNome(String nome) {
        List<Curso> resultado = new ArrayList<>();
        for (Curso curso : cursos) {
            if (curso.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(curso);
            }
        }
        return resultado;
    }

    public List<Curso> buscarPorProfessor(Professor professor) {
        List<Curso> resultado = new ArrayList<>();
        for (Curso curso : cursos) {
            if (curso.getProfessor() != null && curso.getProfessor().getId() == professor.getId()) {
                resultado.add(curso);
            }
        }
        return resultado;
    }
}