package model.dao;

import model.Curso;
import model.Turma;

import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {
    private static List<Turma> turmas = new ArrayList<>();
    private static int nextId = 1;

    public void salvar(Turma turma) {
        if (turma.getId() == 0) {
            turma.setId(nextId++);
            turmas.add(turma);
        } else {
            for (int i = 0; i < turmas.size(); i++) {
                if (turmas.get(i).getId() == turma.getId()) {
                    turmas.set(i, turma);
                    break;
                }
            }
        }
    }

    public void excluir(Turma turma) {
        turmas.removeIf(t -> t.getId() == turma.getId());
    }

    public Turma buscarPorId(int id) {
        for (Turma turma : turmas) {
            if (turma.getId() == id) {
                return turma;
            }
        }
        return null;
    }

    public List<Turma> buscarTodos() {
        return new ArrayList<>(turmas);
    }

    public List<Turma> buscarPorCodigo(String codigo) {
        List<Turma> resultado = new ArrayList<>();
        for (Turma turma : turmas) {
            if (turma.getCodigo().toLowerCase().contains(codigo.toLowerCase())) {
                resultado.add(turma);
            }
        }
        return resultado;
    }

    public List<Turma> buscarPorCurso(Curso curso) {
        List<Turma> resultado = new ArrayList<>();
        for (Turma turma : turmas) {
            if (turma.getCurso() != null && turma.getCurso().getId() == curso.getId()) {
                resultado.add(turma);
            }
        }
        return resultado;
    }

    public List<Turma> buscarPorPeriodo(String periodo) {
        List<Turma> resultado = new ArrayList<>();
        for (Turma turma : turmas) {
            if (turma.getPeriodo().equalsIgnoreCase(periodo)) {
                resultado.add(turma);
            }
        }
        return resultado;
    }
}