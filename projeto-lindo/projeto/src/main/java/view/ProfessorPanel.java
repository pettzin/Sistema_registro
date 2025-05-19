package view;

import controller.ProfessorController;
import model.Professor;
import view.components.Button;
import view.components.Input;
import javax.swing.*;

public class ProfessorPanel extends BasePanel {
    private ProfessorController professorController;
    
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField emailField;
    private JTextField telefoneField;
    private JTextArea enderecoArea;
    
    private Professor professorAtual;
    
    private Button salvarButton;
    private Button editarButton;
    private Button excluirButton;
    
    public ProfessorPanel(MainFrame mainFrame) {
        super(mainFrame, "Cadastrar Professor");
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
    
    // CPF
    form.addLabel("CPF", 0, 1);
    cpfField = form.addTextField(1, 1, 2);
    configurarCampoTexto(cpfField);
    
    // Email
    form.addLabel("Email", 0, 2);
    emailField = form.addTextField(1, 2, 2);
    configurarCampoTexto(emailField);
    
    // Telefone
    form.addLabel("Telefone", 0, 3);
    telefoneField = form.addTextField(1, 3, 2);
    configurarCampoTexto(telefoneField);
    
    // Endereço
    form.addLabel("Endereço", 0, 4);
    enderecoArea = form.addTextArea(1, 4, 2);
    
    // Botões
    salvarButton = createSaveButton();
    editarButton = createEditButton();
    excluirButton = createDeleteButton();
    
    // Usar o método setupButtonPanel para posicionar os botões no canto direito
    setupButtonPanel(salvarButton, editarButton, excluirButton);
    
    // Aplicar validações de entrada
    Input.aplicarMascaraCPF(cpfField);
    Input.aplicarMascaraTelefone(telefoneField);
    Input.definirLimiteCaracteres(nomeField, 100);
    Input.definirLimiteCaracteres(emailField, 100);
    Input.definirLimiteCaracteres(enderecoArea, 100);
    Input.apenasAlfabetico(nomeField);
    
    Input.adicionarValidacao(nomeField, Input.TipoValidacao.REQUERIDO, "O nome é obrigatório!");
    Input.adicionarValidacao(cpfField, Input.TipoValidacao.REQUERIDO, "O CPF é obrigatório!");
    Input.adicionarValidacao(emailField, Input.TipoValidacao.EMAIL, "Email inválido!");
    Input.adicionarValidacaoPersonalizada(enderecoArea, Input.TipoValidacao.REQUERIDO, "O endereço é obrigatório!");
}
    
    @Override
    protected void setupListeners() {
        salvarButton.addActionListener(e -> salvarProfessor());
        editarButton.addActionListener(e -> editarProfessor());
        excluirButton.addActionListener(e -> excluirProfessor());
    }
    
    private void salvarProfessor() {
        try {
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim().replaceAll("[^0-9]", "");
            String email = emailField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String endereco = enderecoArea.getText().trim();
            
            if (nome.isEmpty() || cpf.isEmpty() || endereco.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome, CPF e Endereço são campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (professorAtual == null) {
                professorAtual = new Professor();
            }
            
            professorAtual.setNome(nome);
            professorAtual.setCpf(cpf);
            professorAtual.setEmail(email);
            professorAtual.setTelefone(telefone);
            professorAtual.setEndereco(endereco);
            
            professorController.salvarProfessor(professorAtual);
            
            mainFrame.notificarProfessorSalvo();

            clearFields();
            JOptionPane.showMessageDialog(this, "Professor salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar professor: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarProfessor() {
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do professor a ser editado:");
        if (cpf != null && !cpf.isEmpty()) {
            try {
                cpf = cpf.replaceAll("[^0-9]", "");
                Professor professor = professorController.buscarProfessorPorCpf(cpf);
                if (professor != null) {
                    professorAtual = professor;
                    preencherCampos(professor);
                } else {
                    JOptionPane.showMessageDialog(this, "Professor não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar professor: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void excluirProfessor() {
        if (professorAtual != null) {
            int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este professor?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    professorController.excluirProfessor(professorAtual);
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Professor excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (RuntimeException e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir professor: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum professor selecionado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preencherCampos(Professor professor) {
        nomeField.setText(professor.getNome());
        cpfField.setText(professor.getCpf());
        emailField.setText(professor.getEmail());
        telefoneField.setText(professor.getTelefone());
        enderecoArea.setText(professor.getEndereco());
    }
    
    @Override
    protected void clearFields() {
        professorAtual = null;
        nomeField.setText("");
        cpfField.setText("");
        emailField.setText("");
        telefoneField.setText("");
        enderecoArea.setText("");
    }
}