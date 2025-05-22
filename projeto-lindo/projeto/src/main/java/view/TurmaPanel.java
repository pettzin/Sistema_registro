package view;

import controller.AlunoController;
import controller.CursoController;
import controller.TurmaController;
import model.Aluno;
import model.Curso;
import model.Turma;
import view.components.Button;
import view.components.Input;
import view.components.RoundedComboBox;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaPanel extends BasePanel {
    private TurmaController turmaController;
    private CursoController cursoController;
    private AlunoController alunoController;
    
    private JTextField nomeField;
    private JTextField periodoField;
    private JTextField capacidadeField;
    private JTextField dataInicioField;
    private JTextField dataTerminoField;
    private JComboBox<Curso> cursoComboBox;
    private JList<Aluno> alunosList;
    private DefaultListModel<Aluno> alunosListModel;
    
    private Turma turmaAtual;
    
    private Button salvarButton;
    private Button editarButton;
    private Button excluirButton;
    
    public TurmaPanel(MainFrame mainFrame) {
        super(mainFrame, "Cadastrar Turma");
        this.turmaController = new TurmaController();
        this.cursoController = new CursoController();
        this.alunoController = new AlunoController();
        
        initializeComponents();
        setupListeners();
    }
    
    @Override
protected void initializeComponents() {
    // Nome (agora é o primeiro campo)
    form.addLabel("Nome", 0, 0);
    nomeField = form.addTextField(1, 0, 2);
    configurarCampoTexto(nomeField);
    
    // Período
    form.addLabel("Período", 0, 1);
    periodoField = form.addTextField(1, 1, 2);
    configurarCampoTexto(periodoField);
    
    // Capacidade
    form.addLabel("Capacidade", 0, 2);
    capacidadeField = form.addTextField(1, 2, 2);
    configurarCampoTexto(capacidadeField);
    
    // Data de Início
    form.addLabel("Data de Início", 0, 3);
    dataInicioField = form.addTextField(1, 3, 2);
    configurarCampoTexto(dataInicioField);
    dataInicioField.setToolTipText("Formato: dd/MM/yyyy");
    
    // Data de Término
    form.addLabel("Data de Término", 0, 4);
    dataTerminoField = form.addTextField(1, 4, 2);
    configurarCampoTexto(dataTerminoField);
    dataTerminoField.setToolTipText("Formato: dd/MM/yyyy");
    
    // Curso
    form.addLabel("Curso", 0, 5);
    cursoComboBox = new RoundedComboBox<>();
    configurarComboBox(cursoComboBox);
    form.addComponent(cursoComboBox, 1, 5, 2);
    
    // Alunos
    form.addLabel("Alunos", 0, 6);
    alunosListModel = new DefaultListModel<>();
    alunosList = new JList<>(alunosListModel);
    alunosList.setFont(new Font("Arial", Font.PLAIN, 16)); 
    JScrollPane alunosScrollPane = new JScrollPane(alunosList);
    alunosScrollPane.setPreferredSize(new Dimension(150, 100)); 
    alunosScrollPane.setMinimumSize(new Dimension(150, 100));
    form.addComponent(alunosScrollPane, 1, 6, 2);
    
    // Botão para adicionar alunos
    Button adicionarAlunoButton = Button.createActionButton("Adicionar Aluno", new Color(51, 51, 51));
    adicionarAlunoButton.setPreferredSize(new Dimension(150, 45));
    adicionarAlunoButton.setFont(new Font("Arial", Font.BOLD, 14));
    form.addComponent(adicionarAlunoButton, 1, 7, 1);
    
    // Botão para remover alunos
    Button removerAlunoButton = Button.createActionButton("Remover Aluno", new Color(51, 51, 51));
    removerAlunoButton.setPreferredSize(new Dimension(150, 45));
    removerAlunoButton.setFont(new Font("Arial", Font.BOLD, 14));
    form.addComponent(removerAlunoButton, 2, 7, 1);
    
    // Botões
    salvarButton = createSaveButton();
    editarButton = createEditButton();
    excluirButton = createDeleteButton();
    
    // Configurar o painel de botões para ficar no canto direito
    setupButtonPanel(salvarButton, editarButton, excluirButton);
    
    // Aplicar validações de entrada
    Input.aplicarMascaraData(dataInicioField);
    Input.aplicarMascaraData(dataTerminoField);
    Input.apenasNumeros(capacidadeField);
    Input.definirLimiteCaracteres(nomeField, 100);
    Input.definirLimiteCaracteres(periodoField, 20);
    
    Input.adicionarValidacao(nomeField, Input.TipoValidacao.REQUERIDO, "O nome é obrigatório!");
    Input.adicionarValidacao(periodoField, Input.TipoValidacao.REQUERIDO, "O período é obrigatório!");
    Input.adicionarValidacao(capacidadeField, Input.TipoValidacao.REQUERIDO, "A capacidade é obrigatória!");
    Input.adicionarValidacao(dataInicioField, Input.TipoValidacao.DATA, "A data de início é obrigatória!");
    Input.adicionarValidacao(dataTerminoField, Input.TipoValidacao.DATA, "A data de término é obrigatória!");
    
    // Carregar cursos
    carregarCursos();
    
    // Configurar listeners para os botões de adicionar e remover alunos
    adicionarAlunoButton.addActionListener(e -> adicionarAluno());
    removerAlunoButton.addActionListener(e -> removerAluno());
}
    
    // Método para configurar campos de texto com tamanho e fonte maiores
    protected void configurarCampoTexto(JTextField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 16)); // Aumentar tamanho da fonte
        campo.setPreferredSize(new Dimension(450, 45)); // Aumentar tamanho do campo
        
        // Forçar o tamanho mínimo também
        campo.setMinimumSize(new Dimension(450, 45));
    }
    
    // Método para configurar combobox com tamanho e fonte maiores
    protected void configurarComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16)); // Aumentar tamanho da fonte
        comboBox.setPreferredSize(new Dimension(450, 45)); // Aumentar tamanho do combobox
        comboBox.setMinimumSize(new Dimension(450, 45)); // Forçar tamanho mínimo
        
        // Configurar o renderer para usar fonte maior
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setFont(new Font("Arial", Font.PLAIN, 16));
                return c;
            }
        });
    }
    
    @Override
    protected void setupListeners() {
        salvarButton.addActionListener(e -> salvarTurma());
        editarButton.addActionListener(e -> editarTurma());
        excluirButton.addActionListener(e -> excluirTurma());
    }
    
    private void carregarCursos() {
        try {
            List<Curso> cursos = cursoController.buscarTodosCursos();
            cursoComboBox.removeAllItems();
            
            for (Curso curso : cursos) {
                cursoComboBox.addItem(curso);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cursos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void adicionarAluno() {
        // Criar um JTextField para entrada do CPF com formatação
        JTextField cpfField = new JTextField(14);
        Input.aplicarMascaraCPF(cpfField);
        
        // Criar um painel para o diálogo
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Digite o CPF do aluno:"), BorderLayout.NORTH);
        panel.add(cpfField, BorderLayout.CENTER);
        
        // Mostrar o diálogo com o campo formatado
        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Aluno", 
                                                  JOptionPane.OK_CANCEL_OPTION, 
                                                  JOptionPane.PLAIN_MESSAGE);
        
        // Processar o resultado
        if (result == JOptionPane.OK_OPTION) {
            String cpf = cpfField.getText();
            if (cpf != null && !cpf.isEmpty()) {
                try {
                    // Remover formatação do CPF para busca
                    String cpfSemFormatacao = cpf.replaceAll("[^0-9]", "");
                    Aluno aluno = alunoController.buscarAlunoPorCpf(cpfSemFormatacao);
                    if (aluno != null) {
                        // Verificar se o aluno já está na lista
                        boolean alunoJaAdicionado = false;
                        for (int i = 0; i < alunosListModel.size(); i++) {
                            if (alunosListModel.getElementAt(i).getMatricula().equals(aluno.getMatricula())) {
                                alunoJaAdicionado = true;
                                break;
                            }
                        }
                        
                        if (!alunoJaAdicionado) {
                            alunosListModel.addElement(aluno);
                        } else {
                            JOptionPane.showMessageDialog(this, "Este aluno já foi adicionado à turma!", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Aluno não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void removerAluno() {
        int selectedIndex = alunosList.getSelectedIndex();
        if (selectedIndex != -1) {
            alunosListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para remover!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void salvarTurma() {
        try {
            String nome = nomeField.getText().trim();
            String periodo = periodoField.getText().trim();
            String capacidadeStr = capacidadeField.getText().trim();
            String dataInicio = dataInicioField.getText().trim();
            String dataTermino = dataTerminoField.getText().trim();
            
            if (nome.isEmpty() || periodo.isEmpty() || capacidadeStr.isEmpty() || dataInicio.isEmpty() || dataTermino.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int capacidade;
            try {
                capacidade = Integer.parseInt(capacidadeStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Capacidade deve ser um número!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (alunosListModel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A turma deve ter pelo menos um aluno!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Curso cursoSelecionado = (Curso) cursoComboBox.getSelectedItem();
            if (cursoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um curso para a turma!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (turmaAtual == null) {
                turmaAtual = new Turma();
                // Gerar código automaticamente (sigla do curso + ano atual + número sequencial)
                String siglaCurso = gerarSiglaCurso(cursoSelecionado.getNome());
                String ano = String.valueOf(java.time.Year.now().getValue()).substring(2); // Últimos 2 dígitos do ano
                String sequencial = String.format("%03d", (int)(Math.random() * 1000));
                turmaAtual.setCodigo(siglaCurso + ano + sequencial);
            }
            
            turmaAtual.setNome(nome);
            turmaAtual.setPeriodo(periodo);
            turmaAtual.setCapacidade(capacidade);
            turmaAtual.setDataInicio(dataInicio);
            turmaAtual.setDataTermino(dataTermino);
            turmaAtual.setCurso(cursoSelecionado);
            
            // Adicionar alunos à turma
            List<Aluno> alunos = new ArrayList<>();
            for (int i = 0; i < alunosListModel.size(); i++) {
                alunos.add(alunosListModel.getElementAt(i));
            }
            turmaAtual.setAlunos(alunos);
            
            turmaController.salvarTurma(turmaAtual);
            
            // Mostrar o código gerado para o usuário
            JOptionPane.showMessageDialog(this, 
                "Turma salva com sucesso!\nCódigo gerado: " + turmaAtual.getCodigo(), 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            clearFields();
            
            // Notificar o MainFrame que uma turma foi salva
            mainFrame.notificarTurmaSalva();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar turma: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para gerar sigla a partir do nome do curso
    private String gerarSiglaCurso(String nomeCurso) {
        StringBuilder sigla = new StringBuilder();
        String[] palavras = nomeCurso.split(" ");
        
        for (String palavra : palavras) {
            if (!palavra.isEmpty() && !palavra.equalsIgnoreCase("de") && 
                !palavra.equalsIgnoreCase("da") && !palavra.equalsIgnoreCase("do") && 
                !palavra.equalsIgnoreCase("e")) {
                sigla.append(palavra.toUpperCase().charAt(0));
            }
        }
        
        // Se a sigla for muito curta, adicionar mais caracteres
        if (sigla.length() < 2) {
            if (nomeCurso.length() > 1) {
                sigla.append(nomeCurso.charAt(1));
            }
            if (nomeCurso.length() > 2) {
                sigla.append(nomeCurso.charAt(2));
            }
        }
        
        return sigla.toString();
    }
    
    private void editarTurma() {
        // Agora vamos buscar por nome em vez de código
        String nome = JOptionPane.showInputDialog(this, "Digite o nome da turma a ser editada:");
        if (nome != null && !nome.isEmpty()) {
            try {
                // Buscar todas as turmas e filtrar pelo nome
                List<Turma> turmas = turmaController.buscarTodasTurmas();
                List<Turma> turmasFiltradas = new ArrayList<>();
                
                for (Turma t : turmas) {
                    if (t.getNome().toLowerCase().contains(nome.toLowerCase())) {
                        turmasFiltradas.add(t);
                    }
                }
                
                if (turmasFiltradas.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhuma turma encontrada com esse nome!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Se encontrou mais de uma turma, mostrar uma lista para seleção
                Turma turmaSelecionada;
                if (turmasFiltradas.size() > 1) {
                    Object[] options = turmasFiltradas.toArray();
                    turmaSelecionada = (Turma) JOptionPane.showInputDialog(
                        this,
                        "Selecione a turma:",
                        "Múltiplas turmas encontradas",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                    );
                    
                    if (turmaSelecionada == null) {
                        return; // Usuário cancelou
                    }
                } else {
                    turmaSelecionada = turmasFiltradas.get(0);
                }
                
                turmaAtual = turmaSelecionada;
                preencherCampos(turmaAtual);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar turma: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void excluirTurma() {
        if (turmaAtual != null) {
            int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esta turma?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    turmaController.excluirTurma(turmaAtual);
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Turma excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir turma: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma turma selecionada!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preencherCampos(Turma turma) {
        nomeField.setText(turma.getNome());
        periodoField.setText(turma.getPeriodo());
        capacidadeField.setText(String.valueOf(turma.getCapacidade()));
        dataInicioField.setText(turma.getDataInicio());
        dataTerminoField.setText(turma.getDataTermino());
        
        // Selecionar o curso
        if (turma.getCurso() != null) {
            for (int i = 0; i < cursoComboBox.getItemCount(); i++) {
                Curso curso = cursoComboBox.getItemAt(i);
                if (curso.getId() == turma.getCurso().getId()) {
                    cursoComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        // Adicionar alunos à lista
        alunosListModel.clear();
        if (turma.getAlunos() != null) {
            for (Aluno aluno : turma.getAlunos()) {
                alunosListModel.addElement(aluno);
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
        if (cursoComboBox.getItemCount() > 0) {
            cursoComboBox.setSelectedIndex(0);
        }
        alunosListModel.clear();
    }
    
    // Método para atualizar a lista de cursos
    public void atualizarListaCursos() {
        carregarCursos();
    }
}