package view;

import controller.CursoController;
import controller.ProfessorController;
import model.Curso;
import model.Professor;
import view.components.Button;
import view.components.Input;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CursoPanel extends BasePanel {
    private CursoController cursoController;
    private ProfessorController professorController;
    
    private JTextField nomeField;
    private JComboBox<Professor> professorComboBox;
    private JTextArea descricaoArea;
    
    private Curso cursoAtual;
    
    private Button salvarButton;
    private Button editarButton;
    private Button excluirButton;
    
    public CursoPanel(MainFrame mainFrame) {
        super(mainFrame, "Cadastrar Curso");
        this.cursoController = new CursoController();
        this.professorController = new ProfessorController();
        
        initializeComponents();
        setupListeners();
    }
    
    @Override
protected void initializeComponents() {
    // Nome
    form.addLabel("Nome", 0, 0);
    nomeField = form.addTextField(1, 0, 2);
    configurarCampoTexto(nomeField);
    
    // Professor
    form.addLabel("Professor", 0, 1);
    professorComboBox = form.addComboBox(1, 1, 2);
    configurarComboBox(professorComboBox);
    atualizarProfessores();
    
    // Descrição
    form.addLabel("Descrição", 0, 2);
    descricaoArea = form.addTextArea(1, 2, 2);
    
    // Botões
    salvarButton = createSaveButton();
    editarButton = createEditButton();
    excluirButton = createDeleteButton();
    
    // Usar o método setupButtonPanel para posicionar os botões no canto direito
    setupButtonPanel(salvarButton, editarButton, excluirButton);
    
    // Aplicar validações de entrada
    Input.definirLimiteCaracteres(nomeField, 50);
    Input.definirLimiteCaracteres(descricaoArea, 500);
    
    Input.adicionarValidacao(nomeField, Input.TipoValidacao.REQUERIDO, "O nome do curso é obrigatório!");
    Input.adicionarValidacaoPersonalizada(descricaoArea, Input.TipoValidacao.REQUERIDO, "A descrição do curso é obrigatória!");
    }
    
    @Override
    protected void setupListeners() {
        salvarButton.addActionListener(e -> salvarCurso());
        editarButton.addActionListener(e -> editarCurso());
        excluirButton.addActionListener(e -> excluirCurso());
    }
    
    public void atualizarListaProfessores() {
        atualizarProfessores();
    }

    public void atualizarProfessores() {
        professorComboBox.removeAllItems();
        try {
            List<Professor> professores = professorController.buscarTodosProfessores();
            if (professores != null) {
                for (Professor professor : professores) {
                    professorComboBox.addItem(professor);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar professores: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarCurso() {
        try {
            String nome = nomeField.getText().trim();
            String descricao = descricaoArea.getText().trim();
            
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome do curso é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A descrição do curso é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Professor professor = (Professor) professorComboBox.getSelectedItem();
            if (professor == null) {
                JOptionPane.showMessageDialog(this, "Selecione um professor!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (cursoAtual == null) {
                cursoAtual = new Curso();
            }
            
            cursoAtual.setNome(nome);
            cursoAtual.setDescricao(descricao);
            cursoAtual.setProfessor(professor);
            
            try {
                cursoController.salvarCurso(cursoAtual);
                mainFrame.notificarCursoSalvo();
                
                clearFields();
                JOptionPane.showMessageDialog(this, "Curso salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar curso no banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar curso: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarCurso() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do curso a ser editado:");
        if (nome != null && !nome.isEmpty()) {
            try {
                List<Curso> cursos = cursoController.buscarCursosPorNome(nome);
                if (cursos != null && !cursos.isEmpty()) {
                    cursoAtual = cursos.get(0);
                    preencherCampos(cursoAtual);
                } else {
                    JOptionPane.showMessageDialog(this, "Curso não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar curso: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void excluirCurso() {
        if (cursoAtual != null) {
            int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este curso?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    cursoController.excluirCurso(cursoAtual);
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Curso excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir curso: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum curso selecionado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preencherCampos(Curso curso) {
        nomeField.setText(curso.getNome());
        descricaoArea.setText(curso.getDescricao());
        
        if (curso.getProfessor() != null) {
            for (int i = 0; i < professorComboBox.getItemCount(); i++) {
                Professor professor = professorComboBox.getItemAt(i);
                if (professor.getId() == curso.getProfessor().getId()) {
                    professorComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    @Override
    protected void clearFields() {
        cursoAtual = null;
        nomeField.setText("");
        descricaoArea.setText("");
        professorComboBox.setSelectedIndex(-1);
    }
}