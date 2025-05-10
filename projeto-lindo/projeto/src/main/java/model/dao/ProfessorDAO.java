package model.dao;

import model.Professor;

import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
    private static List<Professor> professores = new ArrayList<>();
    private static int nextId = 1;

    public void salvar(Professor professor) {
        if (professor.getId() == 0) {
            // Novo professor
            professor.setId(nextId++);
            professores.add(professor);
        } else {
            // Atualizar professor existente
            for (int i = 0; i < professores.size(); i++) {
                if (professores.get(i).getId() == professor.getId()) {
                    professores.set(i, professor);
                    break;
                }
            }
        }
    }

    public void excluir(Professor professor) {
        professores.removeIf(p -> p.getId() == professor.getId());
    }

    public Professor buscarPorId(int id) {
        for (Professor professor : professores) {
            if (professor.getId() == id) {
                return professor;
            }
        }
        return null;
    }

    public List<Professor> buscarTodos() {
        return new ArrayList<>(professores);
    }

    public List<Professor> buscarPorNome(String nome) {
        List<Professor> resultado = new ArrayList<>();
        for (Professor professor : professores) {
            if (professor.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(professor);
            }
        }
        return resultado;
    }

    public Professor buscarPorCpf(String cpf) {
        for (Professor professor : professores) {
            if (professor.getCpf().equals(cpf)) {
                return professor;
            }
        }
        return null;
    }
}