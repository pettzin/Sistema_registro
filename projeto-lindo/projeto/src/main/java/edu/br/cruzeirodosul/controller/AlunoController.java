package edu.br.cruzeirodosul.controller;

import edu.br.cruzeirodosul.model.Aluno;
import edu.br.cruzeirodosul.model.Registro;
import edu.br.cruzeirodosul.view.AlunoView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlunoController {
    
    private Registro modelo;
    private AlunoView view;
    private String alunoAtualMatricula;
    
    public AlunoController(Registro modelo, AlunoView view) {
        this.modelo = modelo;
        this.view = view;
        this.alunoAtualMatricula = null;
        
        // Adiciona os listeners aos botões
        this.view.addSalvarButtonListener(new SalvarAlunoListener());
        this.view.addEditarButtonListener(new EditarAlunoListener());
        this.view.addExcluirButtonListener(new ExcluirAlunoListener());
    }
    
    class SalvarAlunoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nome = view.getNome();
                String cpf = view.getCpf();
                String genero = view.getGenero();
                String email = view.getEmail();
                String telefone = view.getTelefone();
                String endereco = view.getEndereco();
                
                // Validações básicas
                if (nome.isEmpty()) {
                    view.exibirMensagem("O nome é obrigatório!");
                    return;
                }
                
                if (cpf.isEmpty() || !modelo.validarCpf(cpf)) {
                    view.exibirMensagem("CPF inválido!");
                    return;
                }
                
                if (!email.isEmpty() && !modelo.validarEmail(email)) {
                    view.exibirMensagem("Email inválido!");
                    return;
                }
                
                if (alunoAtualMatricula == null) {
                    // Cadastrar novo aluno
                    modelo.cadastrarAluno(nome, cpf, genero, email, telefone);
                    view.exibirMensagem("Aluno cadastrado com sucesso!");
                } else {
                    // Atualizar aluno existente
                    modelo.alterarAluno(alunoAtualMatricula, nome, cpf, genero, email, telefone);
                    view.exibirMensagem("Aluno atualizado com sucesso!");
                    alunoAtualMatricula = null;
                }
                
                view.limparCampos();
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao salvar aluno: " + ex.getMessage());
            }
        }
    }
    
    class EditarAlunoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String matricula = JOptionPane.showInputDialog(view, "Digite a matrícula do aluno:");
                
                if (matricula != null && !matricula.isEmpty()) {
                    Aluno aluno = modelo.getAlunos().get(matricula);
                    
                    if (aluno != null) {
                        view.setNome(aluno.getNome());
                        view.setCpf(aluno.getCpf());
                        view.setGenero(aluno.getGenero());
                        view.setEmail(aluno.getEmail());
                        view.setTelefone(aluno.getTelefone());
                        
                        alunoAtualMatricula = matricula;
                    } else {
                        view.exibirMensagem("Aluno não encontrado!");
                    }
                }
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao editar aluno: " + ex.getMessage());
            }
        }
    }
    
    class ExcluirAlunoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String matricula = JOptionPane.showInputDialog(view, "Digite a matrícula do aluno:");
                
                if (matricula != null && !matricula.isEmpty()) {
                    int confirmacao = JOptionPane.showConfirmDialog(
                        view, 
                        "Tem certeza que deseja excluir este aluno?", 
                        "Confirmação", 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (confirmacao == JOptionPane.YES_OPTION) {
                        modelo.excluirAluno(matricula);
                        view.exibirMensagem("Aluno excluído com sucesso!");
                        
                        if (alunoAtualMatricula != null && alunoAtualMatricula.equals(matricula)) {
                            view.limparCampos();
                            alunoAtualMatricula = null;
                        }
                    }
                }
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao excluir aluno: " + ex.getMessage());
            }
        }
    }
}