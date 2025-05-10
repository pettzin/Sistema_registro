package edu.br.cruzeirodosul.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MatriculaView extends JPanel {
    
    private JLabel titleLabel;
    private JComboBox<String> turmaCombo;
    private JTextField matriculaField;
    private JTextField nomeAlunoField;
    private JList<String> alunosList;
    private DefaultListModel<String> alunosListModel;
    
    private JButton matricularButton;
    private JButton cancelarMatriculaButton;
    private JButton buscarAlunoButton;
    
    public MatriculaView() {
        initComponents();
        setupLayout();
    }
    
    private void initComponents() {
        // Configura o painel
        this.setBackground(new Color(30, 30, 30));
        
        // Inicializa os componentes
        titleLabel = new JLabel("Matrícula de Alunos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        // ComboBox para turmas
        turmaCombo = new JComboBox<>();
        turmaCombo.setBackground(new Color(40, 40, 40));
        turmaCombo.setForeground(Color.WHITE);
        
        // Campos de texto
        matriculaField = createTextField();
        nomeAlunoField = createTextField();
        nomeAlunoField.setEditable(false);
        
        // Lista de alunos matriculados
        alunosListModel = new DefaultListModel<>();
        alunosList = new JList<>(alunosListModel);
        alunosList.setBackground(new Color(40, 40, 40));
        alunosList.setForeground(Color.WHITE);
        alunosList.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        
        // Botões
        matricularButton = createButton("Matricular");
        cancelarMatriculaButton = createButton("Cancelar Matrícula");
        buscarAlunoButton = createButton("Buscar Aluno");
    }
    
    private void setupLayout() {
        this.setLayout(new BorderLayout());
        
        // Painel de título
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(30, 30, 30));
        titlePanel.add(titleLabel);
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(30, 30, 30));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Turma
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel turmaLabel = createLabel("Turma");
        formPanel.add(turmaLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(turmaCombo, gbc);
        
        // Matrícula do Aluno
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel matriculaLabel = createLabel("Matrícula do Aluno");
        formPanel.add(matriculaLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.6;
        formPanel.add(matriculaField, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        formPanel.add(buscarAlunoButton, gbc);
        
        // Nome do Aluno
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel nomeAlunoLabel = createLabel("Nome do Aluno");
        formPanel.add(nomeAlunoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(nomeAlunoField, gbc);
        
        // Botões de matrícula
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        formPanel.add(matricularButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        formPanel.add(cancelarMatriculaButton, gbc);
        
        // Lista de Alunos Matriculados
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel alunosMatriculadosLabel = createLabel("Alunos Matriculados");
        formPanel.add(alunosMatriculadosLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPane = new JScrollPane(alunosList);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        formPanel.add(scrollPane, gbc);
        
        // Adiciona os painéis ao painel principal
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(formPanel, BorderLayout.CENTER);
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setBackground(new Color(40, 40, 40));
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
    
    // Métodos para adicionar action listeners aos botões
    public void addMatricularButtonListener(ActionListener listener) {
        matricularButton.addActionListener(listener);
    }
    
    public void addCancelarMatriculaButtonListener(ActionListener listener) {
        cancelarMatriculaButton.addActionListener(listener);
    }
    
    public void addBuscarAlunoButtonListener(ActionListener listener) {
        buscarAlunoButton.addActionListener(listener);
    }
    
    // Métodos para gerenciar o ComboBox de turmas
    public void limparTurmas() {
        turmaCombo.removeAllItems();
    }
    
    public void adicionarTurma(String turmaInfo) {
        turmaCombo.addItem(turmaInfo);
    }
    
    public String getTurmaSelecionada() {
        return (String) turmaCombo.getSelectedItem();
    }
    
    // Métodos para obter os valores dos campos
    public String getMatricula() {
        return matriculaField.getText();
    }
    
    // Métodos para definir os valores dos campos
    public void setNomeAluno(String nome) {
        nomeAlunoField.setText(nome);
    }
    
    // Métodos para gerenciar a lista de alunos
    public void limparListaAlunos() {
        alunosListModel.clear();
    }
    
    public void adicionarAlunoNaLista(String alunoInfo) {
        alunosListModel.addElement(alunoInfo);
    }
    
    public String getAlunoSelecionado() {
        return alunosList.getSelectedValue();
    }
    
    // Método para limpar os campos
    public void limparCampos() {
        matriculaField.setText("");
        nomeAlunoField.setText("");
    }
    
    // Método para exibir mensagem
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}