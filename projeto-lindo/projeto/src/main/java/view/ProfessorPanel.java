package view;

import controller.ProfessorController;
import model.Professor;
import view.components.Input;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfessorPanel extends JPanel {
    private MainFrame mainFrame;
    private ProfessorController professorController;
    
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField cpfField;
    private JTextArea enderecoArea;
    private JTextField carteirinhaField;
    
    private Professor professorAtual;
    
    public ProfessorPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.professorController = new ProfessorController();
        
        setLayout(new BorderLayout());
        setBackground(new Color(68, 68, 68));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(68, 68, 68));
        JLabel titleLabel = new JLabel("Registrar Professor");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        
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
        
        // Endereço
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        JLabel enderecoLabel = new JLabel("Endereço");
        enderecoLabel.setForeground(Color.WHITE);
        formPanel.add(enderecoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        enderecoArea = new JTextArea(5, 20);
        enderecoArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(enderecoArea);
        formPanel.add(scrollPane, gbc);
        
        // Carteirinha de Licenciatura
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        JLabel carteirinhaLabel = new JLabel("Carteirinha de Licenciatura");
        carteirinhaLabel.setForeground(Color.WHITE);
        formPanel.add(carteirinhaLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        carteirinhaField = new JTextField(20);
        formPanel.add(carteirinhaField, gbc);
        
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
        
        salvarButton.addActionListener(e -> salvarProfessor());
        editarButton.addActionListener(e -> editarProfessor());
        excluirButton.addActionListener(e -> excluirProfessor());
        
        buttonPanel.add(salvarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(excluirButton);
        
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Aplica as máscaras e validações aos campos
        Input.aplicarMascaraData(dataNascimentoField);
        Input.aplicarMascaraCPF(cpfField);
        Input.definirLimiteCaracteres(nomeField, 50);
        Input.definirLimiteCaracteres(carteirinhaField, 20);
        Input.definirLimiteCaracteres(enderecoArea, 200);
        Input.apenasAlfabetico(nomeField);
        
        Input.adicionarValidacao(nomeField, Input.TipoValidacao.REQUERIDO, "O nome é obrigatório!");
        Input.adicionarValidacao(dataNascimentoField, Input.TipoValidacao.REQUERIDO, "A data de nascimento é obrigatória!");
        Input.adicionarValidacao(dataNascimentoField, Input.TipoValidacao.DATA, "Formato de data inválido. Use dd/MM/yyyy");
        Input.adicionarValidacao(cpfField, Input.TipoValidacao.REQUERIDO, "O CPF é obrigatório!");
        Input.adicionarValidacao(cpfField, Input.TipoValidacao.CPF, "Formato de CPF inválido!");
        Input.adicionarValidacaoPersonalizada(enderecoArea, Input.TipoValidacao.REQUERIDO, "O endereço é obrigatório!");
        Input.adicionarValidacao(carteirinhaField, Input.TipoValidacao.REQUERIDO, "A carteirinha de licenciatura é obrigatória!");
    }
    
    private void salvarProfessor() {
        try {
            String nome = nomeField.getText().trim();
            String dataNascimentoStr = dataNascimentoField.getText().trim();
            String cpf = cpfField.getText().trim();
            String endereco = enderecoArea.getText().trim();
            String carteirinha = carteirinhaField.getText().trim();
            
            if (nome.isEmpty() || dataNascimentoStr.isEmpty() || cpf.isEmpty() || endereco.isEmpty() || carteirinha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!Input.isDataValida(dataNascimentoStr)) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            /*if (!Input.isCPFValido(cpf)) {
                JOptionPane.showMessageDialog(this, "Formato de CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }*/
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNascimento = dateFormat.parse(dataNascimentoStr);
            
            if (professorAtual == null) {
                professorAtual = new Professor();
            }
            
            professorAtual.setNome(nome);
            professorAtual.setDataNascimento(dataNascimento);
            professorAtual.setCpf(cpf);
            professorAtual.setEndereco(endereco);
            professorAtual.setCarteirinhaLicenciatura(carteirinha);
            
            professorController.salvarProfessor(professorAtual);
            
            mainFrame.notificarProfessorSalvo();

            limparCampos();
            JOptionPane.showMessageDialog(this, "Professor salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar professor: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarProfessor() {
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do professor a ser editado:");
        if (cpf != null && !cpf.isEmpty()) {
            Professor professor = professorController.buscarProfessorPorCpf(cpf);
            if (professor != null) {
                professorAtual = professor;
                preencherCampos(professor);
            } else {
                JOptionPane.showMessageDialog(this, "Professor não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void excluirProfessor() {
        if (professorAtual != null) {
            int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este professor?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                professorController.excluirProfessor(professorAtual);
                limparCampos();
                JOptionPane.showMessageDialog(this, "Professor excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum professor selecionado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preencherCampos(Professor professor) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        nomeField.setText(professor.getNome());
        dataNascimentoField.setText(dateFormat.format(professor.getDataNascimento()));
        cpfField.setText(professor.getCpf());
        enderecoArea.setText(professor.getEndereco());
        carteirinhaField.setText(professor.getCarteirinhaLicenciatura());
    }
    
    private void limparCampos() {
        professorAtual = null;
        nomeField.setText("");
        dataNascimentoField.setText("");
        cpfField.setText("");
        enderecoArea.setText("");
        carteirinhaField.setText("");
    }
}