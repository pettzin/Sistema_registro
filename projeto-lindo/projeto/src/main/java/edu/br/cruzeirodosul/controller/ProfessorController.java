package edu.br.cruzeirodosul.controller;

import edu.br.cruzeirodosul.model.Professor;
import edu.br.cruzeirodosul.model.Registro;
import edu.br.cruzeirodosul.view.ProfessorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ProfessorController {
    
    private Registro modelo;
    private ProfessorView view;
    private String professorAtualId;
    private Map<String, Professor> professores; // Simulação de armazenamento de professores
    
    public ProfessorController(Registro modelo, ProfessorView view) {
        this.modelo = modelo;
        this.view = view;
        this.professorAtualId = null;
        this.professores = new HashMap<>(); // Simulação de armazenamento
        
        // Adiciona os listeners aos botões
        this.view.addSalvarButtonListener(new SalvarProfessorListener());
        this.view.addEditarButtonListener(new EditarProfessorListener());
        this.view.addExcluirButtonListener(new ExcluirProfessorListener());
    }
    
    class SalvarProfessorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nome = view.getNome();
                String licenciatura = view.getLicenciatura();
                String cpf = view.getCpf();
                String genero = view.getGenero();
                String email = view.getEmail();
                String telefone = view.getTelefone();
                
                // Validações básicas
                if (nome.isEmpty()) {
                    view.exibirMensagem("O nome é obrigatório!");
                    return;
                }
                
                if (licenciatura.isEmpty()) {
                    view.exibirMensagem("A licenciatura é obrigatória!");
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
                
                if (professorAtualId == null) {
                    // Cadastrar novo professor
                    // Como não há método específico no modelo, vamos simular
                    int id = professores.size() + 1;
                    
                    Professor professor = new Professor(id, nome, licenciatura, cpf, genero, email, telefone);
                    professores.put(String.valueOf(id), professor);
                    view.exibirMensagem("Professor cadastrado com sucesso!");
                } else {
                    // Atualizar professor existente
                    Professor professor = professores.get(professorAtualId);
                    if (professor != null) {
                        professor.setNome(nome);
                        professor.setLicenciatura(licenciatura);
                        professor.setCpf(cpf);
                        professor.setGenero(genero);
                        professor.setEmail(email);
                        professor.setTelefone(telefone);
                        view.exibirMensagem("Professor atualizado com sucesso!");
                    }
                    professorAtualId = null;
                }
                
                view.limparCampos();
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao salvar professor: " + ex.getMessage());
            }
        }
    }
    
    class EditarProfessorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String id = JOptionPane.showInputDialog(view, "Digite o ID do professor:");
                
                if (id != null && !id.isEmpty()) {
                    Professor professor = professores.get(id);
                    
                    if (professor != null) {
                        view.setNome(professor.getNome());
                        view.setLicenciatura(professor.getLicenciatura());
                        view.setCpf(professor.getCpf());
                        view.setGenero(professor.getGenero());
                        view.setEmail(professor.getEmail());
                        view.setTelefone(professor.getTelefone());
                        
                        professorAtualId = id;
                    } else {
                        view.exibirMensagem("Professor não encontrado!");
                    }
                }
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao editar professor: " + ex.getMessage());
            }
        }
    }
    
    class ExcluirProfessorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String id = JOptionPane.showInputDialog(view, "Digite o ID do professor:");
                
                if (id != null && !id.isEmpty()) {
                    int confirmacao = JOptionPane.showConfirmDialog(
                        view, 
                        "Tem certeza que deseja excluir este professor?", 
                        "Confirmação", 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (confirmacao == JOptionPane.YES_OPTION) {
                        professores.remove(id);
                        view.exibirMensagem("Professor excluído com sucesso!");
                        
                        if (professorAtualId != null && professorAtualId.equals(id)) {
                            view.limparCampos();
                            professorAtualId = null;
                        }
                    }
                }
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao excluir professor: " + ex.getMessage());
            }
        }
    }
}