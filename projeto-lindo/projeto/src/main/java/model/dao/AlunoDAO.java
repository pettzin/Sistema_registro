package model.dao;

import model.Aluno;
import model.Turma;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlunoDAO {
    private static List<Aluno> alunos = new ArrayList<>();
    private static int nextId = 1;

    public void salvar(Aluno aluno) {
        if (aluno.getId() == 0) {
            // Novo aluno
            aluno.setId(nextId++);
            alunos.add(aluno);
        } else {
            // Atualizar aluno existente
            for (int i = 0; i < alunos.size(); i++) {
                if (alunos.get(i).getId() == aluno.getId()) {
                    alunos.set(i, aluno);
                    break;
                }
            }
        }
    }

    public void excluir(Aluno aluno) {
        alunos.removeIf(a -> a.getId() == aluno.getId());
    }

    public Aluno buscarPorId(int id) {
        for (Aluno aluno : alunos) {
            if (aluno.getId() == id) {
                return aluno;
            }
        }
        return null;
    }

    public List<Aluno> buscarTodos() {
        return new ArrayList<>(alunos);
    }

    public List<Aluno> buscarPorNome(String nome) {
        List<Aluno> resultado = new ArrayList<>();
        for (Aluno aluno : alunos) {
            if (aluno.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(aluno);
            }
        }
        return resultado;
    }

    public Aluno buscarPorCpf(String cpf) {
        for (Aluno aluno : alunos) {
            if (aluno.getCpf().equals(cpf)) {
                return aluno;
            }
        }
        return null;
    }

    public List<Aluno> buscarPorTurma(Turma turma) {
        List<Aluno> resultado = new ArrayList<>();
        for (Aluno aluno : alunos) {
            if (aluno.getTurma() != null && aluno.getTurma().getId() == turma.getId()) {
                resultado.add(aluno);
            }
        }
        return resultado;
    }
}