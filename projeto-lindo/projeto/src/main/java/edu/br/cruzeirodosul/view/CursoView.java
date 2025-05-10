package edu.br.cruzeirodosul.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class CursoView extends JPanel {
    
    private JLabel titleLabel;
    private JTextField nomeField;
    private JTextField codigoField;
    private JTextArea descricaoArea;
    private JFormattedTextField dataInicioField;
    private JFormattedTextField dataFimField;
    
    private JButton salvarButton;
    private JButton editarButton;
    private JButton excluirButton;
    
    public CursoView() {
        initComponents();
        setupLayout();
    }
    
    private void initComponents() {
        // Configura o painel
        this.setBackground(new Color(30, 30, 30));
        
        // Inicializa os componentes
        titleLabel = new JLabel("Gerenciar Curso");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        // Campos de texto
        nomeField = createTextField();
        codigoField = createTextField();
        
        descricaoArea = new JTextArea();
        descricaoArea.setBackground(new Color(40, 40, 40));
        descricaoArea.setForeground(Color.WHITE);
        descricaoArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        descricaoArea.setLineWrap(true);
        descricaoArea.setWrapStyleWord(true);
        
        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataInicioField = new JFormattedTextField(dataMask);
            dataInicioField.setBackground(new Color(40, 40, 40));
            dataInicioField.setForeground(Color.WHITE);
            dataInicioField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            
            dataFimField = new JFormattedTextField(dataMask);
            dataFimField.setBackground(new Color(40, 40, 40));
            dataFimField.setForeground(Color.WHITE);
            dataFimField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
        } catch (ParseException e) {
            dataInicioField = new JFormattedTextField();
            dataFimField = new JFormattedTextField();
        }
        
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
        JLabel nomeLabel = createLabel("Nome do Curso");
        formPanel.add(nomeLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(nomeField, gbc);
        
        // Código
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel codigoLabel = createLabel("Código");
        formPanel.add(codigoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(codigoField, gbc);
        
        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel descricaoLabel = createLabel("Descrição");
        formPanel.add(descricaoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        gbc.gridheight = 2;
        JScrollPane scrollPane = new JScrollPane(descricaoArea);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        formPanel.add(scrollPane, gbc);
        
        // Data de Início
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        JLabel dataInicioLabel = createLabel("Data de Início");
        formPanel.add(dataInicioLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(dataInicioField, gbc);
        
        // Data de Fim
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel dataFimLabel = createLabel("Data de Fim");
        formPanel.add(dataFimLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        formPanel.add(dataFimField, gbc);
        
        // Botões
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.33;
        formPanel.add(salvarButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(editarButton, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 6;
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
    
    public String getCodigo() {
        return codigoField.getText();
    }
    
    public String getDescricao() {
        return descricaoArea.getText();
    }
    
    public String getDataInicio() {
        return dataInicioField.getText();
    }
    
    public String getDataFim() {
        return dataFimField.getText();
    }
    
    // Métodos para definir os valores dos campos
    public void setNome(String nome) {
        nomeField.setText(nome);
    }
    
    public void setCodigo(String codigo) {
        codigoField.setText(codigo);
    }
    
    public void setDescricao(String descricao) {
        descricaoArea.setText(descricao);
    }
    
    public void setDataInicio(String dataInicio) {
        dataInicioField.setText(dataInicio);
    }
    
    public void setDataFim(String dataFim) {
        dataFimField.setText(dataFim);
    }
    
    // Método para limpar os campos
    public void limparCampos() {
        nomeField.setText("");
        codigoField.setText("");
        descricaoArea.setText("");
        dataInicioField.setText("");
        dataFimField.setText("");
    }
    
    // Método para exibir mensagem
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}