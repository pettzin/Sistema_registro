package view;

import controller.AlunoController;
import controller.TurmaController;
import model.Aluno;
import model.Turma;
import view.components.Button;
import view.components.Input;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AlunoPanel extends BasePanel {
    private AlunoController alunoController;
    private TurmaController turmaController;
    
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTextField emailField;
    private JComboBox<String> generoField;
    private JTextArea enderecoArea;
    private JComboBox<Turma> turmaComboBox;
    
    private Aluno alunoAtual;
    
    private Button salvarButton;
    private Button editarButton;
    private Button excluirButton;
    
    public AlunoPanel(MainFrame mainFrame) {
        super(mainFrame, "Cadastrar Aluno");
        this.alunoController = new AlunoController();
        this.turmaController = new TurmaController();
        
        initializeComponents();
        setupListeners();
    }
    
    @Override
    protected void initializeComponents() {
        // Nome
        form.addLabel("Nome", 0, 0);
        nomeField = form.addTextField(1, 0, 2);
        
        // Data de Nascimento
        form.addLabel("Data de Nascimento", 0, 1);
        dataNascimentoField = form.addTextField(1, 1, 2);
        dataNascimentoField.setToolTipText("Formato: dd/MM/yyyy");
        
        // CPF
        form.addLabel("CPF", 0, 2);
        cpfField = form.addTextField(1, 2, 2);
        
        // Telefone
        form.addLabel("Telefone", 0, 3);
        telefoneField = form.addTextField(1, 3, 2);
        
        // Email
        form.addLabel("Email", 0, 4);
        emailField = form.addTextField(1, 4, 2);
        
        // Gênero
        form.addLabel("Gênero", 0, 5);
        generoField = form.addComboBox(1, 5, 2);
        String[] generos = {"Masculino", "Feminino", "Outro"};
        for (String genero : generos) {
            generoField.addItem(genero);
        }
        
        // Endereço
        form.addLabel("Endereço", 0, 6);
        enderecoArea = form.addTextArea(1, 6, 2);
        
        // Turma
        form.addLabel("Turma", 0, 7);
        turmaComboBox = form.addComboBox(1, 7, 2);
        atualizarTurmas();
        
        // Botões
        salvarButton = createSaveButton();
        editarButton = createEditButton();
        excluirButton = createDeleteButton();
        
        painelBotoes.add(salvarButton);
        painelBotoes.add(editarButton);
        painelBotoes.add(excluirButton);
        
        // Aplicar validações de entrada
        Input.aplicarMascaraData(dataNascimentoField);
        Input.aplicarMascaraCPF(cpfField);
        Input.aplicarMascaraTelefone(telefoneField);
        Input.definirLimiteCaracteres(telefoneField, 15);
        Input.adicionarFeedbackVisual(telefoneField, Input.TipoValidacao.REQUERIDO);
        Input.definirLimiteCaracteres(nomeField, 50);
        Input.definirLimiteCaracteres(emailField, 100);
        Input.definirLimiteCaracteres(enderecoArea, 200);
        
        Input.adicionarFeedbackVisual(nomeField, Input.TipoValidacao.REQUERIDO);
        Input.adicionarFeedbackVisual(dataNascimentoField, Input.TipoValidacao.DATA);
        Input.adicionarFeedbackVisual(cpfField, Input.TipoValidacao.CPF);
        Input.adicionarFeedbackVisual(emailField, Input.TipoValidacao.EMAIL);
    }
    
    @Override
    protected void setupListeners() {
        salvarButton.addActionListener(e -> salvarAluno());
        editarButton.addActionListener(e -> editarAluno());
        excluirButton.addActionListener(e -> excluirAluno());
    }
    
    public void atualizarListaTurmas() {
        atualizarTurmas();
    }
    
    private void atualizarTurmas() {
        turmaComboBox.removeAllItems();
        List<Turma> turmas = turmaController.buscarTodasTurmas();
        for (Turma turma : turmas) {
            turmaComboBox.addItem(turma);
        }
    }
    
    private void salvarAluno() {
        try {
            String nome = nomeField.getText().trim();
            String dataNascimentoStr = dataNascimentoField.getText().trim();
            String cpf = cpfField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String email = emailField.getText().trim();
            String genero = (String) generoField.getSelectedItem();
            String endereco = enderecoArea.getText().trim();
            
            // Validação dos campos
            if (!Input.validarCampo(nomeField, Input.TipoValidacao.REQUERIDO, "O nome é obrigatório!")) {
                return;
            }
            
            if (!Input.validarCampo(dataNascimentoField, Input.TipoValidacao.REQUERIDO, "A data de nascimento é obrigatória!") ||
                !Input.validarCampo(dataNascimentoField, Input.TipoValidacao.DATA, "Formato de data inválido. Use dd/MM/yyyy")) {
                return;
            }
            
            if (!Input.validarCampo(cpfField, Input.TipoValidacao.REQUERIDO, "O CPF é obrigatório!")) {
                return;
            }

            if (!Input.validarCampo(telefoneField, Input.TipoValidacao.REQUERIDO, "O telefone é obrigatório!")) {
                return;
            }
            
            if (!Input.validarCampo(emailField, Input.TipoValidacao.REQUERIDO, "O email é obrigatório!") ||
                !Input.validarCampo(emailField, Input.TipoValidacao.EMAIL, "Formato de email inválido!")) {
                return;
            }
            
            if (generoField.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione o gênero!", "Erro", JOptionPane.ERROR_MESSAGE);
                generoField.requestFocus();
                return;
            }
            
            if (endereco.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O endereço é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                enderecoArea.requestFocus();
                return;
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNascimento = dateFormat.parse(dataNascimentoStr);
            
            Turma turma = (Turma) turmaComboBox.getSelectedItem();
            
            if (alunoAtual == null) {
                alunoAtual = new Aluno();
            }
            
            alunoAtual.setNome(nome);
            alunoAtual.setDataNascimento(dataNascimento);
            alunoAtual.setCpf(cpf);
            alunoAtual.setTelefone(telefone);
            alunoAtual.setEmail(email);
            alunoAtual.setGenero(genero);
            alunoAtual.setEndereco(endereco);
            alunoAtual.setTurma(turma);
            
            alunoController.salvarAluno(alunoAtual);
            
            if (turma != null) {
                alunoController.matricularAlunoEmTurma(alunoAtual, turma);
            }
            
            clearFields();
            JOptionPane.showMessageDialog(this, "Aluno salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aluno: Formato de data inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarAluno() {
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do aluno a ser editado:");
        if (cpf != null && !cpf.isEmpty()) {
            Aluno aluno = alunoController.buscarAlunoPorCpf(cpf);
            if (aluno != null) {
                alunoAtual = aluno;
                preencherCampos(aluno);
            } else {
                JOptionPane.showMessageDialog(this, "Aluno não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void excluirAluno() {
        if (alunoAtual != null) {
            int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este aluno?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                alunoController.excluirAluno(alunoAtual);
                clearFields();
                JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum aluno selecionado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preencherCampos(Aluno aluno) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        nomeField.setText(aluno.getNome());
        dataNascimentoField.setText(dateFormat.format(aluno.getDataNascimento()));
        cpfField.setText(aluno.getCpf());
        emailField.setText(aluno.getEmail());
        generoField.setSelectedItem(aluno.getGenero());
        telefoneField.setText(aluno.getTelefone());
        enderecoArea.setText(aluno.getEndereco());
        
        if (aluno.getTurma() != null) {
            for (int i = 0; i < turmaComboBox.getItemCount(); i++) {
                Turma turma = turmaComboBox.getItemAt(i);
                if (turma.getId() == aluno.getTurma().getId()) {
                    turmaComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    @Override
    protected void clearFields() {
        alunoAtual = null;
        nomeField.setText("");
        dataNascimentoField.setText("");
        cpfField.setText("");
        emailField.setText("");
        telefoneField.setText("");
        generoField.setSelectedIndex(-1);
        enderecoArea.setText("");
        turmaComboBox.setSelectedIndex(-1);
    }
}
