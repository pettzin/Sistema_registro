package view;

import controller.AlunoController;
import controller.TurmaController;
import model.Aluno;
import model.Turma;
import view.components.Button;
import view.components.Input;
import view.components.RoundedComboBox;
import javax.swing.*;
import java.util.List;

public class AlunoPanel extends BasePanel {
    private AlunoController alunoController;
    private TurmaController turmaController;
    
    private JTextField nomeField;
    private JTextField cpfField;
    private JComboBox<String> generoComboBox;
    private JTextField emailField;
    private JTextField telefoneField;
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
    // Nome (agora é o primeiro campo)
    form.addLabel("Nome", 0, 0);
    nomeField = form.addTextField(1, 0, 2);
    configurarCampoTexto(nomeField);
    
    // CPF
    form.addLabel("CPF", 0, 1);
    cpfField = form.addTextField(1, 1, 2);
    configurarCampoTexto(cpfField);
    
    // Gênero
    form.addLabel("Gênero", 0, 2);
    generoComboBox = new RoundedComboBox<>();
    generoComboBox.addItem("Masculino");
    generoComboBox.addItem("Feminino");
    generoComboBox.addItem("Outro");
    configurarComboBox(generoComboBox);
    form.addComponent(generoComboBox, 1, 2, 2);
    
    // Email
    form.addLabel("Email", 0, 3);
    emailField = form.addTextField(1, 3, 2);
    configurarCampoTexto(emailField);
    
    // Telefone
    form.addLabel("Telefone", 0, 4);
    telefoneField = form.addTextField(1, 4, 2);
    configurarCampoTexto(telefoneField);
    
    // Endereço
    form.addLabel("Endereço", 0, 5);
    enderecoArea = form.addTextArea(1, 5, 2);
    
    // Turma
    form.addLabel("Turma", 0, 6);
    turmaComboBox = new RoundedComboBox<>();
    configurarComboBox(turmaComboBox);
    form.addComponent(turmaComboBox, 1, 6, 2);
    
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
    // Carregar turmas
    carregarTurmas();
}
    
    @Override
    protected void setupListeners() {
        salvarButton.addActionListener(e -> salvarAluno());
        editarButton.addActionListener(e -> editarAluno());
        excluirButton.addActionListener(e -> excluirAluno());
    }
    
    private void carregarTurmas() {
        try {
            List<Turma> turmas = turmaController.buscarTodasTurmas();
            turmaComboBox.removeAllItems();
            
            // Adicionar item vazio
            turmaComboBox.addItem(null);
            
            for (Turma turma : turmas) {
                turmaComboBox.addItem(turma);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar turmas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarAluno() {
        try {
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim().replaceAll("[^0-9]", "");
            String genero = (String) generoComboBox.getSelectedItem();
            String email = emailField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String endereco = enderecoArea.getText().trim();
            
            if (nome.isEmpty() || cpf.isEmpty() || endereco.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome, CPF e Endereço são campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (alunoAtual == null) {
                alunoAtual = new Aluno();
                // Gerar matrícula automaticamente (ano atual + 5 dígitos aleatórios)
                String ano = String.valueOf(java.time.Year.now().getValue());
                String random = String.format("%05d", (int)(Math.random() * 100000));
                alunoAtual.setMatricula(ano + random);
            }
            
            alunoAtual.setNome(nome);
            alunoAtual.setCpf(cpf);
            alunoAtual.setGenero(genero);
            alunoAtual.setEmail(email);
            alunoAtual.setTelefone(telefone);
            alunoAtual.setEndereco(endereco);
            
            Turma turmaSelecionada = (Turma) turmaComboBox.getSelectedItem();
            alunoAtual.setTurma(turmaSelecionada);
            
            alunoController.salvarAluno(alunoAtual);
            
            // Mostrar a matrícula gerada para o usuário
            JOptionPane.showMessageDialog(this, 
                "Aluno salvo com sucesso!\nMatrícula gerada: " + alunoAtual.getMatricula(), 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            clearFields();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarAluno() {
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do aluno a ser editado:");
        if (cpf != null && !cpf.isEmpty()) {
            try {
                // Remover formatação do CPF
                cpf = cpf.replaceAll("[^0-9]", "");
                Aluno aluno = alunoController.buscarAlunoPorCpf(cpf);
                if (aluno != null) {
                    alunoAtual = aluno;
                    preencherCampos(aluno);
                } else {
                    JOptionPane.showMessageDialog(this, "Aluno não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void excluirAluno() {
        if (alunoAtual != null) {
            int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este aluno?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    alunoController.excluirAluno(alunoAtual);
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum aluno selecionado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preencherCampos(Aluno aluno) {
        nomeField.setText(aluno.getNome());
        cpfField.setText(aluno.getCpf());
        
        if (aluno.getGenero() != null) {
            for (int i = 0; i < generoComboBox.getItemCount(); i++) {
                if (generoComboBox.getItemAt(i).equals(aluno.getGenero())) {
                    generoComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        emailField.setText(aluno.getEmail());
        telefoneField.setText(aluno.getTelefone());
        enderecoArea.setText(aluno.getEndereco());
        
        // Selecionar a turma
        if (aluno.getTurma() != null) {
            for (int i = 0; i < turmaComboBox.getItemCount(); i++) {
                Turma turma = turmaComboBox.getItemAt(i);
                if (turma != null && turma.getCodigo().equals(aluno.getTurma().getCodigo())) {
                    turmaComboBox.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            turmaComboBox.setSelectedIndex(0);
        }
    }
    
    @Override
    protected void clearFields() {
        alunoAtual = null;
        nomeField.setText("");
        cpfField.setText("");
        generoComboBox.setSelectedIndex(0);
        emailField.setText("");
        telefoneField.setText("");
        enderecoArea.setText("");
        turmaComboBox.setSelectedIndex(0);
    }
    
    // Método adicionado para resolver o erro
    public void atualizarListaTurmas() {
        carregarTurmas();
    }
}
