package controller;

import model.Aluno;
import model.Turma;
import model.dao.AlunoDAO;

import java.util.List;

public class AlunoController {
    private AlunoDAO alunoDAO;

    public AlunoController() {
        this.alunoDAO = new AlunoDAO();
    }

    public void salvarAluno(Aluno aluno) {
        alunoDAO.salvar(aluno);
    }

    public void excluirAluno(Aluno aluno) {
        alunoDAO.excluir(aluno);
    }

    public Aluno buscarAlunoPorId(int id) {
        return alunoDAO.buscarPorId(id);
    }

    public List<Aluno> buscarTodosAlunos() {
        return alunoDAO.buscarTodos();
    }

    public List<Aluno> buscarAlunosPorNome(String nome) {
        return alunoDAO.buscarPorNome(nome);
    }

    public Aluno buscarAlunoPorCpf(String cpf) {
        return alunoDAO.buscarPorCpf(cpf);
    }

    public List<Aluno> buscarAlunosPorTurma(Turma turma) {
        return alunoDAO.buscarPorTurma(turma);
    }

    public boolean matricularAlunoEmTurma(Aluno aluno, Turma turma) {
        if (turma.getAlunos().size() < turma.getCapacidade()) {
            aluno.setTurma(turma);
            turma.adicionarAluno(aluno);
            alunoDAO.salvar(aluno);
            return true;
        }
        return false;
    }
}