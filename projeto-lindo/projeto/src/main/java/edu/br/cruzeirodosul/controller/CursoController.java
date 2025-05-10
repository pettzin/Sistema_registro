package edu.br.cruzeirodosul.controller;

import edu.br.cruzeirodosul.model.Curso;
import edu.br.cruzeirodosul.model.Registro;
import edu.br.cruzeirodosul.view.CursoView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CursoController {
    
    private Registro modelo;
    private CursoView view;
    private String cursoAtualCodigo;
    
    public CursoController(Registro modelo, CursoView view) {
        this.modelo = modelo;
        this.view = view;
        this.cursoAtualCodigo = null;
        
        // Adiciona os listeners aos botões
        this.view.addSalvarButtonListener(new SalvarCursoListener());
        this.view.addEditarButtonListener(new EditarCursoListener());
        this.view.addExcluirButtonListener(new ExcluirCursoListener());
    }
    
    class SalvarCursoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nome = view.getNome();
                String codigo = view.getCodigo();
                String descricao = view.getDescricao();
                String dataInicio = view.getDataInicio();
                String dataFim = view.getDataFim();
                
                // Validações básicas
                if (nome.isEmpty()) {
                    view.exibirMensagem("O nome é obrigatório!");
                    return;
                }
                
                if (codigo.isEmpty()) {
                    view.exibirMensagem("O código é obrigatório!");
                    return;
                }
                
                if (cursoAtualCodigo == null) {
                    // Cadastrar novo curso/período
                    modelo.cadastrarPeriodo(nome, codigo, descricao, dataInicio, dataFim);
                    view.exibirMensagem("Curso cadastrado com sucesso!");
                } else {
                    // Atualizar curso existente (não implementado no modelo original)
                    // Aqui você precisaria implementar um método para atualizar o curso
                    view.exibirMensagem("Atualização de curso não implementada!");
                    cursoAtualCodigo = null;
                }
                
                view.limparCampos();
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao salvar curso: " + ex.getMessage());
            }
        }
    }
    
    class EditarCursoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codigo = JOptionPane.showInputDialog(view, "Digite o código do curso:");
                
                if (codigo != null && !codigo.isEmpty()) {
                    Curso curso = modelo.getCursos().get(codigo);
                    
                    if (curso != null) {
                        view.setNome(curso.getNome());
                        view.setCodigo(curso.getCodigo());
                        view.setDescricao(curso.getDescricao());
                        view.setDataInicio(curso.getDataInicio());
                        view.setDataFim(curso.getDataFim());
                        
                        cursoAtualCodigo = codigo;
                    } else {
                        view.exibirMensagem("Curso não encontrado!");
                    }
                }
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao editar curso: " + ex.getMessage());
            }
        }
    }
    
    class ExcluirCursoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String codigo = JOptionPane.showInputDialog(view, "Digite o código do curso:");
                
                if (codigo != null && !codigo.isEmpty()) {
                    int confirmacao = JOptionPane.showConfirmDialog(
                        view, 
                        "Tem certeza que deseja excluir este curso?", 
                        "Confirmação", 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (confirmacao == JOptionPane.YES_OPTION) {
                        // Excluir curso (não implementado no modelo original)
                        // Aqui você precisaria implementar um método para excluir o curso
                        view.exibirMensagem("Exclusão de curso não implementada!");
                        
                        if (cursoAtualCodigo != null && cursoAtualCodigo.equals(codigo)) {
                            view.limparCampos();
                            cursoAtualCodigo = null;
                        }
                    }
                }
            } catch (Exception ex) {
                view.exibirMensagem("Erro ao excluir curso: " + ex.getMessage());
            }
        }
    }
}