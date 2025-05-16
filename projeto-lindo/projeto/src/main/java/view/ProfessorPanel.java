package view;

import controller.ProfessorController;
import model.Professor;
import view.components.Button;
import view.components.Input;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfessorPanel extends BasePanel {
    private ProfessorController professorController;
    
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField cpfField;
    private JTextArea enderecoArea;
    private JTextField carteirinhaField;
    
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
        
        // Data de Nascimento
        form.addLabel("Data de Nascimento", 0, 1);
        dataNascimentoField = form.addTextField(1, 1, 2);
        dataNascimentoField.setToolTipText("Formato: dd/MM/yyyy");
        
        // CPF
        form.addLabel("CPF", 0, 2);
        cpfField = form.addTextField(1, 2, 2);
        
        // Endereço
        form.addLabel("Endereço", 0, 3);
        enderecoArea = form.addTextArea(1, 3, 2);
        
        // Carteirinha de Licenciatura
        form.addLabel("Carteirinha de Licenciatura", 0, 4);
        carteirinhaField = form.addTextField(1, 4, 2);
        
        // Botões
        salvarButton = createSaveButton();
        editarButton = createEditButton();
        excluirButton = createDeleteButton();
        
        // Configurar o painel de botões
        JPanel botoesPainel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botoesPainel.setBackground(new Color(220, 220, 220));
        botoesPainel.add(salvarButton);
        botoesPainel.add(editarButton);
        botoesPainel.add(excluirButton);
        
        painelBotoes.add(botoesPainel);
        
        // Aplicar validações de entrada
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
        Input.adicionarValidacaoPersonalizada(enderecoArea, Input.TipoValidacao.REQUERIDO, "O endereço é obrigatório!");
        Input.adicionarValidacao(carteirinhaField, Input.TipoValidacao.REQUERIDO, "A carteirinha de licenciatura é obrigatória!");
    }
    
    // O restante do código permanece o mesmo
    @Override
    protected void setupListeners() {
        salvarButton.addActionListener(e -> salvarProfessor());
        editarButton.addActionListener(e -> editarProfessor());
        excluirButton.addActionListener(e -> excluirProfessor());
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

            clearFields();
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
                clearFields();
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
    
    @Override
    protected void clearFields() {
        professorAtual = null;
        nomeField.setText("");
        dataNascimentoField.setText("");
        cpfField.setText("");
        enderecoArea.setText("");
        carteirinhaField.setText("");
    }
}
