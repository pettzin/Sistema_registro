package view;

import controller.CursoController;
import controller.TurmaController;
import model.Curso;
import model.Turma;
import view.components.Input;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class TurmaPanel extends JPanel {
    private MainFrame mainFrame;
    private TurmaController turmaController;
    private CursoController cursoController;
    
    private JTextField nomeField;
    private JComboBox<Curso> cursoComboBox;
    private JTextField periodoField;
    private JTextField capacidadeField;
    private JTextField dataInicioField;
    private JTextField dataTerminoField;
    
    private Turma turmaAtual;
    
    // Padrões de regex para validação
    private static final Pattern PADRAO_NOME_TURMA = Pattern.compile("^[a-zA-Z0-9]+$"); // Apenas alfanumérico para o nome da turma
    private static final Pattern PADRAO_PERIODO = Pattern.compile("^[a-zA-ZÀ-ú]+$"); // Apenas letras e acentos para o período
    
    public TurmaPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.turmaController = new TurmaController();
        this.cursoController = new CursoController();
        
        setLayout(new BorderLayout());
        setBackground(new Color(68, 68, 68));
        
        // Painel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(68, 68, 68));
        JLabel titleLabel = new JLabel("Registrar Turma");
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
        
        // Nome (Código da Turma)
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nomeLabel = new JLabel("Nome");
        nomeLabel.setForeground(Color.WHITE);
        formPanel.add(nomeLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        nomeField = new JTextField(20);
        nomeField.setToolTipText("Ex: 1A, 1B, 2A, 2B");
        formPanel.add(nomeField, gbc);
        
        // Curso
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel cursoLabel = new JLabel("Curso");
        cursoLabel.setForeground(Color.WHITE);
        formPanel.add(cursoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        cursoComboBox = new JComboBox<>();
        atualizarCursos();
        formPanel.add(cursoComboBox, gbc);
        
        // Período
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JLabel periodoLabel = new JLabel("Período");
        periodoLabel.setForeground(Color.WHITE);
        formPanel.add(periodoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        periodoField = new JTextField(20);
        periodoField.setToolTipText("Ex: Manhã, Tarde, Noite");
        formPanel.add(periodoField, gbc);
        
        // Capacidade
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        JLabel capacidadeLabel = new JLabel("Capacidade");
        capacidadeLabel.setForeground(Color.WHITE);
        formPanel.add(capacidadeLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        capacidadeField = new JTextField(20);
        capacidadeField.setToolTipText("Máximo: 40 alunos");
        formPanel.add(capacidadeField, gbc);
        
        // Data Início
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        JLabel dataInicioLabel = new JLabel("Data início");
        dataInicioLabel.setForeground(Color.WHITE);
        formPanel.add(dataInicioLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        dataInicioField = new JTextField(20);
        dataInicioField.setToolTipText("Formato: dd/MM/yyyy");
        formPanel.add(dataInicioField, gbc);
        
        // Data Término
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        JLabel dataTerminoLabel = new JLabel("Data término");
        dataTerminoLabel.setForeground(Color.WHITE);
        formPanel.add(dataTerminoLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        dataTerminoField = new JTextField(20);
        dataTerminoField.setToolTipText("Formato: dd/MM/yyyy");
        formPanel.add(dataTerminoField, gbc);
        
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
        
        salvarButton.addActionListener(e -> salvarTurma());
        editarButton.addActionListener(e -> editarTurma());
        excluirButton.addActionListener(e -> excluirTurma());
        
        buttonPanel.add(salvarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(excluirButton);
        
        // Adiciona os painéis ao painel principal
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Aplica as máscaras e validações aos campos
        Input.aplicarMascaraData(dataInicioField);
        Input.aplicarMascaraData(dataTerminoField);
        Input.definirLimiteCaracteres(nomeField, 10);
        Input.definirLimiteCaracteres(periodoField, 20);
        Input.apenasNumeros(capacidadeField);
        Input.apenasAlfabetico(periodoField);
        Input.apenasAlfanumerico(nomeField);
        
        Input.adicionarValidacao(nomeField, Input.TipoValidacao.REQUERIDO, "O nome da turma é obrigatório!");
        Input.adicionarValidacaoPersonalizada(nomeField, PADRAO_NOME_TURMA, "Nome da turma inválido (apenas letras e números).");
        Input.adicionarValidacao(periodoField, Input.TipoValidacao.REQUERIDO, "O período é obrigatório!");
        Input.adicionarValidacaoPersonalizada(periodoField, PADRAO_PERIODO, "Período inválido (apenas letras).");
        Input.adicionarValidacao(capacidadeField, Input.TipoValidacao.REQUERIDO, "A capacidade é obrigatória!");
        Input.adicionarValidacao(capacidadeField, Input.TipoValidacao.NUMERICO, "Capacidade inválida (digite um número).");
        Input.adicionarValidacao(dataInicioField, Input.TipoValidacao.REQUERIDO, "A data de início é obrigatória!");
        Input.adicionarValidacao(dataInicioField, Input.TipoValidacao.DATA, "Formato de data inválido. Use dd/MM/yyyy");
        Input.adicionarValidacao(dataTerminoField, Input.TipoValidacao.REQUERIDO, "A data de término é obrigatória!");
        Input.adicionarValidacao(dataTerminoField, Input.TipoValidacao.DATA, "Formato de data inválido. Use dd/MM/yyyy");
    }
    
    private void atualizarCursos() {
        cursoComboBox.removeAllItems();
        List<Curso> cursos = cursoController.buscarTodosCursos();
        for (Curso curso : cursos) {
            cursoComboBox.addItem(curso);
        }
    }
    
    private void salvarTurma() {
        try {
            String nome = nomeField.getText().trim();
            String periodo = periodoField.getText().trim();
            String capacidadeStr = capacidadeField.getText().trim();
            String dataInicioStr = dataInicioField.getText().trim();
            String dataTerminoStr = dataTerminoField.getText().trim();
            
            if (nome.isEmpty() || periodo.isEmpty() || capacidadeStr.isEmpty() || dataInicioStr.isEmpty() || dataTerminoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!PADRAO_NOME_TURMA.matcher(nome).matches()) {
                JOptionPane.showMessageDialog(this, "Nome da turma inválido (apenas letras e números).", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!PADRAO_PERIODO.matcher(periodo).matches()) {
                JOptionPane.showMessageDialog(this, "Período inválido (apenas letras).", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                int capacidade = Integer.parseInt(capacidadeStr);
                if (capacidade <= 0 || capacidade > 40) {
                    JOptionPane.showMessageDialog(this, "A capacidade deve ser entre 1 e 40 alunos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Capacidade inválida. Digite um número!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!Input.isDataValida(dataInicioStr) || !Input.isDataValida(dataTerminoStr)) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInicio = dateFormat.parse(dataInicioStr);
            Date dataTermino = dateFormat.parse(dataTerminoStr);
            
            if (dataTermino.before(dataInicio)) {
                JOptionPane.showMessageDialog(this, "A data de término não pode ser anterior à data de início!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Curso curso = (Curso) cursoComboBox.getSelectedItem();
            if (curso == null) {
                JOptionPane.showMessageDialog(this, "Selecione um curso!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (turmaAtual == null) {
                turmaAtual = new Turma();
            }
            
            turmaAtual.setCodigo(nome);
            turmaAtual.setPeriodo(periodo);
            turmaAtual.setCapacidade(Integer.parseInt(capacidadeStr));
            turmaAtual.setDataInicio(dataInicio);
            turmaAtual.setDataTermino(dataTermino);
            turmaAtual.setCurso(curso);
            
            turmaController.salvarTurma(turmaAtual);
            curso.adicionarTurma(turmaAtual);
            
            limparCampos();
            JOptionPane.showMessageDialog(this, "Turma salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar turma: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarTurma() {
        String codigo = JOptionPane.showInputDialog(this, "Digite o código da turma a ser editada:");
        if (codigo != null && !codigo.isEmpty()) {
            List<Turma> turmas = turmaController.buscarTurmasPorCodigo(codigo);
            if (!turmas.isEmpty()) {
                turmaAtual = turmas.get(0);
                preencherCampos(turmaAtual);
            } else {
                JOptionPane.showMessageDialog(this, "Turma não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void excluirTurma() {
        if (turmaAtual != null) {
            int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esta turma?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                turmaController.excluirTurma(turmaAtual);
                limparCampos();
                JOptionPane.showMessageDialog(this, "Turma excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma turma selecionada!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preencherCampos(Turma turma) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        nomeField.setText(turma.getCodigo());
        periodoField.setText(turma.getPeriodo());
        capacidadeField.setText(String.valueOf(turma.getCapacidade()));
        dataInicioField.setText(dateFormat.format(turma.getDataInicio()));
        dataTerminoField.setText(dateFormat.format(turma.getDataTermino()));
        
        if (turma.getCurso() != null) {
            for (int i = 0; i < cursoComboBox.getItemCount(); i++) {
                Curso curso = cursoComboBox.getItemAt(i);
                if (curso.getId() == turma.getCurso().getId()) {
                    cursoComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    private void limparCampos() {
        turmaAtual = null;
        nomeField.setText("");
        periodoField.setText("");
        capacidadeField.setText("");
        dataInicioField.setText("");
        dataTerminoField.setText("");
        cursoComboBox.setSelectedIndex(-1);
    }
}