package view;

import controller.AlunoController;
import controller.CursoController;
import controller.ProfessorController;
import controller.TurmaController;
import model.Aluno;
import model.Curso;
import model.Professor;
import model.Turma;
import view.components.Button;
import view.components.RoundedTextField;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PesquisaPanel extends BasePanel {
    private AlunoController alunoController;
    private TurmaController turmaController;
    private CursoController cursoController;
    private ProfessorController professorController;

    private JTextField pesquisaField;
    private JComboBox<String> tipoComboBox;
    private JTextArea resultadoArea;
    private Button pesquisarButton;

    public PesquisaPanel(MainFrame mainFrame) {
        super(mainFrame, "Pesquisar");
        this.alunoController = new AlunoController();
        this.turmaController = new TurmaController();
        this.cursoController = new CursoController();
        this.professorController = new ProfessorController();
        
        initializeComponents();
        setupListeners();
    }

    @Override
    protected void initializeComponents() {
        
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(220, 220, 220));
        
        // Painel para os componentes de pesquisa com GridBagLayout para responsividade
        JPanel componentsPanel = new JPanel(new GridBagLayout());
        componentsPanel.setBackground(new Color(220, 220, 220));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 5);
        
       
        pesquisaField = new RoundedTextField(20);
      
        
        // Combo box de tipo
        tipoComboBox = new JComboBox<>(new String[]{"Aluno", "Turma", "Curso", "Professor"});
        tipoComboBox.setBorder(new RoundedBorder(Color.BLACK, 1, 10));
        tipoComboBox.setPreferredSize(new Dimension(200, 45));
        tipoComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Botão de pesquisa
        pesquisarButton = Button.createActionButton("Pesquisar", new Color(51, 51, 51));
        searchPanel.add(pesquisaField);
        searchPanel.add(tipoComboBox);
        searchPanel.add(pesquisarButton);
        
        // Área de resultado
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(new Color(220, 220, 220));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        resultadoArea = new JTextArea(25, 40);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);
        resultadoArea.setBackground(Color.WHITE);
        resultadoArea.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        scrollPane.setBorder(new RoundedBorder(Color.BLACK, 1, 10));
        
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Adicionar componentes ao painel principal
        add(searchPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);

        // Adicionar validação
        adicionarValidacao(pesquisaField, TipoValidacao.REQUERIDO, "Digite um termo para pesquisar!");
        
    }

    @Override
    protected void setupListeners() {
        pesquisarButton.addActionListener(e -> realizarPesquisa());
    }

    private void realizarPesquisa() {
        String termo = pesquisaField.getText().trim();
        String tipo = (String) tipoComboBox.getSelectedItem();

        if (termo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um termo para pesquisar!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultadoArea.setText("");

        try {
            switch (tipo) {
                case "Aluno":
                    pesquisarAlunos(termo);
                    break;
                case "Turma":
                    pesquisarTurmas(termo);
                    break;
                case "Curso":
                    pesquisarCursos(termo);
                    break;
                case "Professor":
                    pesquisarProfessores(termo);
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar pesquisa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            resultadoArea.setText("Ocorreu um erro durante a pesquisa. Por favor, tente novamente.");
        }
    }

    // Método auxiliar para formatar CPF
    private String formatarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf; // Retorna o CPF original se for nulo ou não tiver 11 dígitos
        }
        
        // Formata o CPF no padrão 111.111.111-11
        return cpf.substring(0, 3) + "." + 
               cpf.substring(3, 6) + "." + 
               cpf.substring(6, 9) + "-" + 
               cpf.substring(9);
    }

    private void pesquisarAlunos(String termo) {
        try {
            List<Aluno> alunos = alunoController.buscarAlunosPorNome(termo);

            if (alunos.isEmpty()) {
                // Tenta buscar por CPF
                Aluno aluno = alunoController.buscarAlunoPorCpf(termo);
                if (aluno != null) {
                    alunos.add(aluno);
                }
            }

            if (alunos.isEmpty()) {
                // Tenta buscar por matrícula
                try {
                    Aluno aluno = alunoController.buscarAlunoPorMatricula(termo);
                    if (aluno != null) {
                        alunos.add(aluno);
                    }
                } catch (NumberFormatException e) {
                    // Ignora se o termo não for um número válido
                }
            }

            if (alunos.isEmpty()) {
                resultadoArea.setText("Nenhum aluno encontrado.");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (Aluno aluno : alunos) {
                sb.append("Matrícula: ").append(aluno.getMatricula() != null ? aluno.getMatricula() : "Não informada").append("\n");
                sb.append("Nome: ").append(aluno.getNome()).append("\n");
                sb.append("CPF: ").append(formatarCPF(aluno.getCpf())).append("\n");
                sb.append("Gênero: ").append(aluno.getGenero() != null ? aluno.getGenero() : "Não informado").append("\n");
                sb.append("Email: ").append(aluno.getEmail() != null ? aluno.getEmail() : "Não informado").append("\n");
                sb.append("Telefone: ").append(aluno.getTelefone() != null ? aluno.getTelefone() : "Não informado").append("\n");
                sb.append("Endereço: ").append(aluno.getEndereco() != null ? aluno.getEndereco() : "Não informado").append("\n");
                
                // Adicionar a data de criação
                sb.append("Data de Cadastro: ").append(aluno.getDataCriacao() != null ? aluno.getDataCriacao() : "Não informada").append("\n");

                if (aluno.getTurma() != null) {
                    sb.append("Turma: ").append(aluno.getTurma().getCodigo()).append(" - ").append(aluno.getTurma().getNome()).append("\n");

                    if (aluno.getTurma().getCurso() != null) {
                        sb.append("Curso: ").append(aluno.getTurma().getCurso().getNome()).append("\n");
                    }
                }

                sb.append("\n");
            }

            resultadoArea.setText(sb.toString());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pesquisar alunos: " + e.getMessage(), e);
        }
    }

    private void pesquisarTurmas(String termo) {
        try {
            // Primeiro, buscar por código
            List<Turma> turmas = turmaController.buscarTurmasPorCodigo(termo);
            
            // Se não encontrou pelo código, tentar buscar pelo nome
            if (turmas.isEmpty()) {
                turmas = buscarTurmasPorNome(termo);
            }

            if (turmas.isEmpty()) {
                resultadoArea.setText("Nenhuma turma encontrada.");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (Turma turma : turmas) {
                sb.append("Código: ").append(turma.getCodigo()).append("\n");
                sb.append("Nome: ").append(turma.getNome()).append("\n");
                sb.append("Período: ").append(turma.getPeriodo()).append("\n");
                sb.append("Capacidade: ").append(turma.getCapacidade()).append("\n");
                sb.append("Data Início: ").append(turma.getDataInicio()).append("\n");
                sb.append("Data Término: ").append(turma.getDataTermino()).append("\n");

                if (turma.getCurso() != null) {
                    sb.append("Curso: ").append(turma.getCurso().getNome()).append("\n");
                }
                List<Aluno> alunos = turma.getAlunos();

                sb.append("Alunos Matriculados: ").append(turma.getAlunos().size()).append("\n");

                for (Aluno aluno : alunos) {
                    sb.append(" - ").append(aluno.getNome()).append("\n");
                }
                
                sb.append("\n");
            }
            
            resultadoArea.setText(sb.toString());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pesquisar turmas: " + e.getMessage(), e);
        }
    }
    
    // Método auxiliar para buscar turmas por nome
    private List<Turma> buscarTurmasPorNome(String nome) {
        try {
            // Buscar todas as turmas
            List<Turma> todasTurmas = turmaController.buscarTodasTurmas();
            List<Turma> turmasFiltradas = new ArrayList<>();
            
            // Filtrar as turmas pelo nome (case insensitive)
            for (Turma turma : todasTurmas) {
                if (turma.getNome() != null && turma.getNome().toLowerCase().contains(nome.toLowerCase())) {
                    turmasFiltradas.add(turma);
                }
            }
            
            return turmasFiltradas;
        } catch (Exception e) {
            System.err.println("Erro ao buscar turmas por nome: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void pesquisarCursos(String termo) {
        try {
            List<Curso> cursos = cursoController.buscarCursosPorNome(termo);

            if (cursos.isEmpty()) {
                resultadoArea.setText("Nenhum curso encontrado.");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (Curso curso : cursos) {
                sb.append("Nome: ").append(curso.getNome()).append("\n");
                sb.append("Descrição: ").append(curso.getDescricao()).append("\n");

                if (curso.getProfessor() != null) {
                    sb.append("Professor: ").append(curso.getProfessor().getNome()).append("\n");
                }

                List<Turma> turmas = curso.getTurmas();
                sb.append("Turmas: ").append(turmas.size()).append("\n");
                
                if (!turmas.isEmpty()) {
                    sb.append("Lista de Turmas:\n");
                    for (Turma turma : turmas) {
                        sb.append(" - ").append(turma.getNome()).append(" (Código: ").append(turma.getCodigo()).append(")\n");
                    }
                } else {
                    sb.append("Nenhuma turma cadastrada para este curso.\n");
                }
                
                sb.append("\n");
            }

            resultadoArea.setText(sb.toString());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pesquisar cursos: " + e.getMessage(), e);
        }
    }

    private void pesquisarProfessores(String termo) {
        try {
            List<Professor> professores = professorController.buscarProfessoresPorNome(termo);

            if (professores.isEmpty()) {
                Professor professor = professorController.buscarProfessorPorCpf(termo);
                if (professor != null) {
                    professores.add(professor);
                }
            }

            if (professores.isEmpty()) {
                resultadoArea.setText("Nenhum professor encontrado.");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (Professor professor : professores) {
                sb.append("Nome: ").append(professor.getNome()).append("\n");
                sb.append("CPF: ").append(formatarCPF(professor.getCpf())).append("\n");
                sb.append("Email: ").append(professor.getEmail() != null ? professor.getEmail() : "Não informado").append("\n");
                sb.append("Telefone: ").append(professor.getTelefone() != null ? professor.getTelefone() : "Não informado").append("\n");
                sb.append("Endereço: ").append(professor.getEndereco()).append("\n");
                sb.append("\n");
            }

            resultadoArea.setText(sb.toString());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao pesquisar professores: " + e.getMessage(), e);
        }
    }

    /**
     * Adiciona validação a um campo de texto
     * @param campoTexto O campo de texto para validar
     * @param tipoValidacao O tipo de validação a aplicar
     * @param mensagemErro A mensagem de erro a exibir se a validação falhar
     */
    public static void adicionarValidacao(JTextComponent campoTexto, TipoValidacao tipoValidacao, String mensagemErro) {
        campoTexto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String texto = campoTexto.getText().trim();
                boolean valido = true;

                switch (tipoValidacao) {
                    case REQUERIDO:
                        valido = !texto.isEmpty();
                        break;
                    case PADRAO: // Este caso requer que o padrão (regex) seja passado ao chamar adicionarValidacao
                        break;
                }

                if (!valido) {
                    JOptionPane.showMessageDialog(campoTexto, mensagemErro, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    campoTexto.requestFocus();
                }
            }
        });
    }

    /**
     * @param campoTexto O campo de texto para validar
     * @param tipoValidacao O tipo de validação a aplicar (use PADRAO)
     * @param mensagemErro A mensagem de erro a exibir se a validação falhar
     * @param padrao O padrão (regex) para validar a entrada
     */
    public static void adicionarValidacao(JTextComponent campoTexto, TipoValidacao tipoValidacao, String mensagemErro, Pattern padrao) {
        campoTexto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String texto = campoTexto.getText().trim();
                boolean valido = true;

                switch (tipoValidacao) {
                    case REQUERIDO:
                        valido = !texto.isEmpty();
                        break;
                    case PADRAO:
                        valido = padrao.matcher(texto).matches();
                        break;
                }

                if (!valido) {
                    JOptionPane.showMessageDialog(campoTexto, mensagemErro, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    campoTexto.requestFocus();
                }
            }
        });
    }

    public enum TipoValidacao {
        REQUERIDO,
        PADRAO
    }
    
    @Override
    protected void clearFields() {
        pesquisaField.setText("");
        resultadoArea.setText("");
    }
    
    // Classe interna para criar uma borda arredondada
    private static class RoundedBorder extends AbstractBorder {
        private final Color cor;
        private final int espessura;
        private final int raio;
        
        public RoundedBorder(Color cor, int espessura, int raio) {
            this.cor = cor;
            this.espessura = espessura;
            this.raio = raio;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(cor);
            g2.setStroke(new BasicStroke(espessura));
            g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, raio, raio));
            
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(espessura + 2, espessura + 2, espessura + 2, espessura + 2);
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = espessura + 2;
            return insets;
        }
    }
}