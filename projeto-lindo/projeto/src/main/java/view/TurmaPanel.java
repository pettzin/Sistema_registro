package view;

import controller.CursoController;
import controller.TurmaController;
import model.Curso;
import model.Turma;
import view.components.Button;
import view.components.Input;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class TurmaPanel extends BasePanel {
    private TurmaController turmaController;
    private CursoController cursoController;
    
    private JTextField nomeField;
    private JComboBox<Curso> cursoComboBox;
    private JTextField periodoField;
    private JTextField capacidadeField;
    private JTextField dataInicioField;
    private JTextField dataTerminoField;
    
    private Turma turmaAtual;
    
    private static final Pattern PADRAO_NOME_TURMA = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern PADRAO_PERIODO = Pattern.compile("^[a-zA-ZÀ-ú]+$");
    
    private Button salvarButton;
    private Button editarButton;
    private Button excluirButton;
    
    public TurmaPanel(MainFrame mainFrame) {
        super(mainFrame, "Cadastrar Turma");
        this.turmaController = new TurmaController();
        this.cursoController = new CursoController();
        
        initializeComponents();
        setupListeners();
    }
    
    @Override
    protected void initializeComponents() {
        // Nome (Código da Turma)
        form.addLabel("Nome", 0, 0);
        nomeField = form.addTextField(1, 0, 2);
        nomeField.setToolTipText("Ex: 1A, 1B, 2A, 2B");
        
        // Curso
        form.addLabel("Curso", 0, 1);
        cursoComboBox = form.addComboBox(1, 1, 2);
        atualizarCursos();
        
        // Período
        form.addLabel("Período", 0, 2);
        periodoField = form.addTextField(1, 2, 2);
        periodoField.setToolTipText("Ex: Manhã, Tarde, Noite");
        
        // Capacidade
        form.addLabel("Capacidade", 0, 3);
        capacidadeField = form.addTextField(1, 3, 2);
        capacidadeField.setToolTipText("Máximo: 40 alunos");
        
        // Data Início
        form.addLabel("Data início", 0, 4);
        dataInicioField = form.addTextField(1, 4, 2);
        dataInicioField.setToolTipText("Formato: dd/MM/yyyy");
        
        // Data Término
        form.addLabel("Data término", 0, 5);
        dataTerminoField = form.addTextField(1, 5, 2);
        dataTerminoField.setToolTipText("Formato: dd/MM/yyyy");
        
        // Botões
        salvarButton = createSaveButton();
        editarButton = createEditButton();
        excluirButton = createDeleteButton();
        
        painelBotoes.add(salvarButton);
        painelBotoes.add(editarButton);
        painelBotoes.add(excluirButton);
        
        // Aplicar validações de entrada
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
    
    @Override
    protected void setupListeners() {
        salvarButton.addActionListener(e -> salvarTurma());
        editarButton.addActionListener(e -> editarTurma());
        excluirButton.addActionListener(e -> excluirTurma());
    }
    
    public void atualizarListaCursos() {
        atualizarCursos();
    }
    
    public void atualizarCursos() {
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

            mainFrame.notificarTurmaSalva();
            
            clearFields();
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
                clearFields();
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
    
    @Override
    protected void clearFields() {
        turmaAtual = null;
        nomeField.setText("");
        periodoField.setText("");
        capacidadeField.setText("");
        dataInicioField.setText("");
        dataTerminoField.setText("");
        cursoComboBox.setSelectedIndex(-1);
    }
}
