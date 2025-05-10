package edu.br.cruzeirodosul.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class AlunoView extends JPanel {
    
    private JLabel titleLabel;
    private JTextField nomeField;
    private JFormattedTextField dataNascimentoField;
    private JFormattedTextField cpfField;
    private JTextArea enderecoArea;
    private JTextField emailField;
    private JFormattedTextField telefoneField;
    private JComboBox<String> generoCombo;
    
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    
    public AlunoView() {
        initComponents();
        setupLayout();
    }
    
    private void initComponents() {
        // Configura o painel
        this.setBackground(new Color(30, 30, 30));
        
        // Inicializa os componentes
        titleLabel = new JLabel("Registrar Aluno");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        // Campos de texto
        nomeField = createTextField();
        
        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataNascimentoField = new JFormattedTextField(dataMask);
            dataNascimentoField.setBackground(new Color(40, 40, 40));
            dataNascimentoField.setForeground(Color.WHITE);
            dataNascimentoField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfField = new JFormattedTextField(cpfMask);
            cpfField.setBackground(new Color(40, 40, 40));
            cpfField.setForeground(Color.WHITE);
            cpfField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            
            MaskFormatter telefoneMask = new MaskFormatter("(##) #####-####");
            telefoneField = new JFormattedTextField(telefoneMask);
            telefoneField.setBackground(new Color(40, 40, 40));
            telefoneField.setForeground(Color.WHITE);
            telefoneField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
        } catch (ParseException e) {
            dataNascimentoField = new JFormattedTextField();
            cpfField = new JFormattedTextField();
            telefoneField = new JFormattedTextField();
        }
        
        emailField = createTextField();
        
        enderecoArea = new JTextArea();
        enderecoArea.setBackground(new Color(40, 40, 40));
        enderecoArea.setForeground(Color.WHITE);
        enderecoArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        enderecoArea.setLineWrap(true);
        enderecoArea.setWrapStyleWord(true);
        
        // ComboBox para gênero
        generoCombo = new JComboBox<>(new String[]{"Masculino", "Feminino", "Outro"});
        generoCombo.setBackground(new Color(40, 40, 40));
        generoCombo.setForeground(Color.WHITE);
        
        // Botões
        salvarButton = createButton("Salvar");
        editarButton = createButton("Editar");
        excluirButton = createButton("Excluir");
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
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel nomeLabel = createLabel("Nome");
        formPanel.add(nomeLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(nomeField, gbc);
        
        // Data de Nascimento
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel dataLabel = createLabel("Data de Nascimento");
        formPanel.add(dataLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(dataNascimentoField, gbc);
        
        // CPF
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel cpfLabel = createLabel("CPF");
        formPanel.add(cpfLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(cpfField, gbc);
        
        // Gênero
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel generoLabel = createLabel("Gênero");
        formPanel.add(generoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(generoCombo, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel emailLabel = createLabel("Email");
        formPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(emailField, gbc);
        
        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel telefoneLabel = createLabel("Telefone");
        formPanel.add(telefoneLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(telefoneField, gbc);
        
        // Endereço
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel enderecoLabel = createLabel("Endereço");
        formPanel.add(enderecoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        gbc.gridheight = 2;
        JScrollPane scrollPane = new JScrollPane(enderecoArea);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        formPanel.add(scrollPane, gbc);
        
        // Botões
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.33;
        formPanel.add(salvarButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 8;
        formPanel.add(editarButton, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 8;
        formPanel.add(excluirButton, gbc);
        
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
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }
    
    // Métodos para adicionar action listeners aos botões
    public void addSalvarButtonListener(ActionListener listener) {
        salvarButton.addActionListener(listener);
    }
    
    public void addEditarButtonListener(ActionListener listener) {
        editarButton.addActionListener(listener);
    }
    
    public void addExcluirButtonListener(ActionListener listener) {
        excluirButton.addActionListener(listener);
    }
    
    // Métodos para obter os valores dos campos
    public String getNome() {
        return nomeField.getText();
    }
    
    public String getDataNascimento() {
        return dataNascimentoField.getText();
    }
    
    public String getCpf() {
        return cpfField.getText().replaceAll("[^0-9]", "");
    }
    
    public String getGenero() {
        return (String) generoCombo.getSelectedItem();
    }
    
    public String getEmail() {
        return emailField.getText();
    }
    
    public String getTelefone() {
        return telefoneField.getText().replaceAll("[^0-9]", "");
    }
    
    public String getEndereco() {
        return enderecoArea.getText();
    }
    
    // Métodos para definir os valores dos campos
    public void setNome(String nome) {
        nomeField.setText(nome);
    }
    
    public void setDataNascimento(String dataNascimento) {
        dataNascimentoField.setText(dataNascimento);
    }
    
    public void setCpf(String cpf) {
        cpfField.setText(cpf);
    }
    
    public void setGenero(String genero) {
        generoCombo.setSelectedItem(genero);
    }
    
    public void setEmail(String email) {
        emailField.setText(email);
    }
    
    public void setTelefone(String telefone) {
        telefoneField.setText(telefone);
    }
    
    public void setEndereco(String endereco) {
        enderecoArea.setText(endereco);
    }
    
    // Método para limpar os campos
    public void limparCampos() {
        nomeField.setText("");
        dataNascimentoField.setText("");
        cpfField.setText("");
        generoCombo.setSelectedIndex(0);
        emailField.setText("");
        telefoneField.setText("");
        enderecoArea.setText("");
    }
    
    // Método para exibir mensagem
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}