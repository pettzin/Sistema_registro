package view;

import controller.CursoController;
import controller.ProfessorController;
import model.Curso;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CursoPanel extends JPanel {
    private MainFrame mainFrame;
    private CursoController cursoController;
    private ProfessorController professorController;
    
    private JTextField nomeField;
    private JComboBox<Professor> professorComboBox;
    private JTextArea descricaoArea;
    
    private Curso cursoAtual;
    
    public CursoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.cursoController = new CursoController();
        this.professorController = new ProfessorController();
        
        setLayout(new BorderLayout());
        setBackground(new Color(68, 68, 68));
        
        // Painel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(68, 68, 68));
        JLabel titleLabel = new JLabel("Registrar Curso");
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
        
        // Professor
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel professorLabel = new JLabel("Professor");
        professorLabel.setForeground(Color.WHITE);
        formPanel.add(professorLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        professorComboBox = new JComboBox<>();
        atualizarProfessores();
        formPanel.add(professorComboBox, gbc);
        
        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JLabel descricaoLabel = new JLabel("Descrição");
        descricaoLabel.setForeground(Color.WHITE);
        formPanel.add(descricaoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        descricaoArea = new JTextArea(5, 20);
        descricaoArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(descricaoArea);
        formPanel.add(scrollPane, gbc);
        
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
        
        salvarButton.addActionListener(e -> salvarCurso());
        editarButton.addActionListener(e -> editarCurso());
        excluirButton.addActionListener(e -> excluirCurso());
        
        buttonPanel.add(salvarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(excluirButton);
        
        // Adiciona os painéis ao painel principal
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void atualizarProfessores() {
        professorComboBox.removeAllItems();
        List<Professor> professores = professorController.buscarTodosProfessores();
        for (Professor professor : professores) {
            professorComboBox.addItem(professor);
        }
    }
    
    private void salvarCurso() {
        try {
            String nome = nomeField.getText().trim();
            String descricao = descricaoArea.getText().trim();
            
            if (nome.isEmpty() || descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
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
            
            cursoController.salvarCurso(cursoAtual);
            
            limparCampos();
            JOptionPane.showMessageDialog(this, "Curso salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar curso: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarCurso() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do curso a ser editado:");
        if (nome != null && !nome.isEmpty()) {
            List<Curso> cursos = cursoController.buscarCursosPorNome(nome);
            if (!cursos.isEmpty()) {
                cursoAtual = cursos.get(0);
                preencherCampos(cursoAtual);
            } else {
                JOptionPane.showMessageDialog(this, "Curso não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void excluirCurso() {
        if (cursoAtual != null) {
            int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este curso?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                cursoController.excluirCurso(cursoAtual);
                limparCampos();
                JOptionPane.showMessageDialog(this, "Curso excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
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
    
    private void limparCampos() {
        cursoAtual = null;
        nomeField.setText("");
        descricaoArea.setText("");
        professorComboBox.setSelectedIndex(-1);
    }
}