package edu.br.cruzeirodosul.controller;

import edu.br.cruzeirodosul.model.Aluno;
import edu.br.cruzeirodosul.model.Registro;
import edu.br.cruzeirodosul.model.Turma;
import edu.br.cruzeirodosul.view.TurmaView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TurmaController {
    
    private Registro modelo;
    private TurmaView view;
    private String turmaAtualCodigo;
    
    public TurmaController(Registro modelo, TurmaView view) {
        this.modelo = modelo;
        this.view = view;
        this.turmaAtualCodigo = null;
        
        // Adiciona os listeners aos botões
        this.view.addSalvarButtonListener(new SalvarTurmaListener());
        this.view.addEditarButtonListener(new EditarTurmaListener());
        this.view.addExcluirButtonListener(new ExcluirTurmaListener());
        this.view.addAdicionarAlunoButtonListener(new AdicionarAlunoListener());
        this.view.addRemoverAlunoButtonListener(new RemoverAlunoListener());
    }
    
    class SalvarTurmaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nome = view.getNome();
                String codigo = view.getCodigo();
                String curso = view.getCurso();
                String periodo = view.getPeriodo();
                int capacidade = view.getCapacidade();
                
                // Validações básicas
                if (nome.isEmpty()) {
                    view.exibirMensagem("O nome é obrigatório!");
                    return;
                }
                
                if (codigo.isEmpty()) {
                    view.exibirMensagem("O código é obrigatório!");
                    return;
                }
                
                if (turmaAtualCodigo == null) {
                    // Cadastrar nova turma
                    modelo.cadastrarTurma(nome, codigo, curso, periodo, capacidade);
                    view.exibirMensagem("Turma cadastrada com sucesso!");
                } else {
                    // Atualizar turma existente
                    modelo.alterarTurma(turmaAtualCodigo, nome, curso, periodo, capacidade);
                    view.exibirMensagem("Turma atualizada com sucesso!");
                    turmaAtualCodigo = null;
                }
                
                view.limparCampos();
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao salvar turma: " + ex.getMessage());
            }
        }
    }
    
    class EditarTurmaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codigo = JOptionPane.showInputDialog(view, "Digite o código da turma:");
                
                if (codigo != null && !codigo.isEmpty()) {
                    Turma turma = modelo.getTurmas().get(codigo);
                    
                    if (turma != null) {
                        view.setNome(turma.getNome());
                        view.setCodigo(turma.getCodigo());
                        view.setCurso(turma.getCurso());
                        view.setPeriodo(turma.getPeriodo());
                        view.setCapacidade(turma.getCapacidadeMaxima());
                        
                        // Atualiza a lista de alunos
                        view.limparListaAlunos();
                        for (Aluno aluno : turma.getAlunos()) {
                            view.adicionarAlunoNaLista(aluno.getNome() + " (" + aluno.getMatricula() + ")");
                        }
                        
                        turmaAtualCodigo = codigo;
                    } else {
                        view.exibirMensagem("Turma não encontrada!");
                    }
                }
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao editar turma: " + ex.getMessage());
            }
        }
    }
    
    class ExcluirTurmaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codigo = JOptionPane.showInputDialog(view, "Digite o código da turma:");
                
                if (codigo != null && !codigo.isEmpty()) {
                    int confirmacao = JOptionPane.showConfirmDialog(
                        view, 
                        "Tem certeza que deseja excluir esta turma?", 
                        "Confirmação", 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (confirmacao == JOptionPane.YES_OPTION) {
                        modelo.excluirTurma(codigo);
                        view.exibirMensagem("Turma excluída com sucesso!");
                        
                        if (turmaAtualCodigo != null && turmaAtualCodigo.equals(codigo)) {
                            view.limparCampos();
                            turmaAtualCodigo = null;
                        }
                    }
                }
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao excluir turma: " + ex.getMessage());
            }
        }
    }
    
    class AdicionarAlunoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (turmaAtualCodigo == null) {
                    view.exibirMensagem("Selecione uma turma primeiro!");
                    return;
                }
                
                String matricula = JOptionPane.showInputDialog(view, "Digite a matrícula do aluno:");
                
                if (matricula != null && !matricula.isEmpty()) {
                    modelo.matricularAluno(turmaAtualCodigo, matricula);
                    
                    // Atualiza a lista de alunos
                    Turma turma = modelo.getTurmas().get(turmaAtualCodigo);
                    view.limparListaAlunos();
                    for (Aluno aluno : turma.getAlunos()) {
                        view.adicionarAlunoNaLista(aluno.getNome() + " (" + aluno.getMatricula() + ")");
                    }
                }
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao adicionar aluno: " + ex.getMessage());
            }
        }
    }
    
    class RemoverAlunoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (turmaAtualCodigo == null) {
                    view.exibirMensagem("Selecione uma turma primeiro!");
                    return;
                }
                
                String alunoSelecionado = view.getAlunoSelecionado();
                if (alunoSelecionado == null) {
                    view.exibirMensagem("Selecione um aluno da lista!");
                    return;
                }
                
                // Extrai a matrícula do aluno selecionado
                String matricula = alunoSelecionado.substring(
                    alunoSelecionado.lastIndexOf("(") + 1, 
                    alunoSelecionado.lastIndexOf(")")
                );
                
                Turma turma = modelo.getTurmas().get(turmaAtualCodigo);
                turma.removerAluno(matricula);
                
                view.exibirMensagem("Aluno removido da turma com sucesso!");
                
                // Atualiza a lista de alunos
                view.limparListaAlunos();
                for (Aluno aluno : turma.getAlunos()) {
                    view.adicionarAlunoNaLista(aluno.getNome() + " (" + aluno.getMatricula() + ")");
                }
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao remover aluno: " + ex.getMessage());
            }
        }
    }
}