package view;

import controller.AlunoController;
import controller.TurmaController;
import model.Aluno;
import model.Turma;
import view.components.Input;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AlunoPanel extends JPanel {
    private MainFrame mainFrame;
    private AlunoController alunoController;
    private TurmaController turmaController;
    
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField cpfField;
    private JTextField emailField;
    private JTextField generoField;
    private JTextArea enderecoArea;
    private JComboBox<Turma> turmaComboBox;
    
    private Aluno alunoAtual;
    
    public AlunoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.alunoController = new AlunoController();
        this.turmaController = new TurmaController();
        
        setLayout(new BorderLayout());
        setBackground(new Color(68, 68, 68));
        
        // Painel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(68, 68, 68));
        JLabel titleLabel = new JLabel("Registrar de Aluno");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(68, 68, 68));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nomeLabel = new JLabel("Nome");
        nomeLabel.setForeground(Color.WHITE);
        formPanel.add(nomeLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        nomeField = new JTextField(20);
        formPanel.add(nomeField, gbc);
        
        // Data de Nascimento
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel dataNascimentoLabel = new JLabel("Data de Nascimento");
        dataNascimentoLabel.setForeground(Color.WHITE);
        formPanel.add(dataNascimentoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        dataNascimentoField = new JTextField(20);
        dataNascimentoField.setToolTipText("Formato: dd/MM/yyyy");
        formPanel.add(dataNascimentoField, gbc);
        
        // CPF
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JLabel cpfLabel = new JLabel("CPF");
        cpfLabel.setForeground(Color.WHITE);
        formPanel.add(cpfLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        cpfField = new JTextField(20);
        formPanel.add(cpfField, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        formPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);
        
        // Gênero
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        JLabel generoLabel = new JLabel("Genero");
        generoLabel.setForeground(Color.WHITE);
        formPanel.add(generoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        generoField = new JTextField(20);
        formPanel.add(generoField, gbc);
        
        // Endereço
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        JLabel enderecoLabel = new JLabel("Endereço");
        enderecoLabel.setForeground(Color.WHITE);
        formPanel.add(enderecoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        enderecoArea = new JTextArea(5, 20);
        enderecoArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(enderecoArea);
        formPanel.add(scrollPane, gbc);
        
        // Turma
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        JLabel turmaLabel = new JLabel("Turma");
        turmaLabel.setForeground(Color.WHITE);
        formPanel.add(turmaLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        turmaComboBox = new JComboBox<>();
        atualizarTurmas();
        formPanel.add(turmaComboBox, gbc);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(68, 68, 68));
        
        JButton salvarButton = new JButton("Salvar");
        salvarButton.setBackground(new Color(51, 51, 51));
        salvarButton.setForeground(Color.WHITE);
        
        JButton editarButton = new JButton("Editar");
        editarButton.setBackground(new Color(51, 51, 51));
        editarButton.setForeground(Color.WHITE);
        
        JButton excluirButton = new JButton("Excluir");
        excluirButton.setBackground(Color.RED);
        excluirButton.setForeground(Color.WHITE);
        
        salvarButton.addActionListener(e -> salvarAluno());
        editarButton.addActionListener(e -> editarAluno());
        excluirButton.addActionListener(e -> excluirAluno());
        
        buttonPanel.add(salvarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(excluirButton);
        
        // Adiciona os painéis ao painel principal
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Aplica as máscaras e validações aos campos
        Input.aplicarMascaraData(dataNascimentoField);
        Input.aplicarMascaraCPF(cpfField);
        Input.definirLimiteCaracteres(nomeField, 50);
        Input.definirLimiteCaracteres(emailField, 100);
        Input.definirLimiteCaracteres(generoField, 20);
        Input.definirLimiteCaracteres(enderecoArea, 200);
        Input.apenasAlfabetico(generoField);
        
        // Adiciona feedback visual em tempo real
        Input.adicionarFeedbackVisual(nomeField, Input.TipoValidacao.REQUERIDO);
        Input.adicionarFeedbackVisual(dataNascimentoField, Input.TipoValidacao.DATA);
        Input.adicionarFeedbackVisual(cpfField, Input.TipoValidacao.CPF);
        Input.adicionarFeedbackVisual(emailField, Input.TipoValidacao.EMAIL);
        Input.adicionarFeedbackVisual(generoField, Input.TipoValidacao.ALFABETICO);
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
            String email = emailField.getText().trim();
            String genero = generoField.getText().trim();
            String endereco = enderecoArea.getText().trim();
            
            // Validação dos campos
            if (!Input.validarCampo(nomeField, Input.TipoValidacao.REQUERIDO, "O nome é obrigatório!")) {
                return;
            }
            
            if (!Input.validarCampo(dataNascimentoField, Input.TipoValidacao.REQUERIDO, "A data de nascimento é obrigatória!") ||
                !Input.validarCampo(dataNascimentoField, Input.TipoValidacao.DATA, "Formato de data inválido. Use dd/MM/yyyy")) {
                return;
            }
            
            if (!Input.validarCampo(cpfField, Input.TipoValidacao.REQUERIDO, "O CPF é obrigatório!") ||
                !Input.validarCampo(cpfField, Input.TipoValidacao.CPF, "Formato de CPF inválido!")) {
                return;
            }
            
            if (!Input.validarCampo(emailField, Input.TipoValidacao.REQUERIDO, "O email é obrigatório!") ||
                !Input.validarCampo(emailField, Input.TipoValidacao.EMAIL, "Formato de email inválido!")) {
                return;
            }
            
            if (!Input.validarCampo(generoField, Input.TipoValidacao.REQUERIDO, "O gênero é obrigatório!") ||
                !Input.validarCampo(generoField, Input.TipoValidacao.ALFABETICO, "O gênero deve conter apenas letras!")) {
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
            alunoAtual.setEmail(email);
            alunoAtual.setGenero(genero);
            alunoAtual.setEndereco(endereco);
            alunoAtual.setTurma(turma);
            
            alunoController.salvarAluno(alunoAtual);
            
            if (turma != null) {
                alunoController.matricularAlunoEmTurma(alunoAtual, turma);
            }
            
            limparCampos();
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
                limparCampos();
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
        generoField.setText(aluno.getGenero());
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
    
    private void limparCampos() {
        alunoAtual = null;
        nomeField.setText("");
        dataNascimentoField.setText("");
        cpfField.setText("");
        emailField.setText("");
        generoField.setText("");
        enderecoArea.setText("");
        turmaComboBox.setSelectedIndex(-1);
    }
}