package view;

import controller.AlunoController;
import controller.CursoController;
import controller.ProfessorController;
import controller.TurmaController;
import model.Aluno;
import model.Curso;
import model.Professor;
import model.Turma;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

public class PesquisaPanel extends JPanel {
    private MainFrame mainFrame;
    private AlunoController alunoController;
    private TurmaController turmaController;
    private CursoController cursoController;
    private ProfessorController professorController;

    private JTextField pesquisaField;
    private JComboBox<String> tipoComboBox;
    private JTextArea resultadoArea;

    private static final Pattern PADRAO_PESQUISA = Pattern.compile("^[a-zA-Z0-9\\s\\.\\-]+$");

    public PesquisaPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.alunoController = new AlunoController();
        this.turmaController = new TurmaController();
        this.cursoController = new CursoController();
        this.professorController = new ProfessorController();

        setLayout(new BorderLayout());
        setBackground(new Color(68, 68, 68));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(68, 68, 68));
        JLabel titleLabel = new JLabel("Pesquisar");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(68, 68, 68));

        pesquisaField = new JTextField(20);
        tipoComboBox = new JComboBox<>(new String[]{"Aluno", "Turma", "Curso", "Professor"});
        JButton pesquisarButton = new JButton("Pesquisar");
        pesquisarButton.setBackground(new Color(51, 51, 51));
        pesquisarButton.setForeground(Color.WHITE);

        pesquisarButton.addActionListener(e -> realizarPesquisa());

        searchPanel.add(pesquisaField);
        searchPanel.add(tipoComboBox);
        searchPanel.add(pesquisarButton);

        // Painel de resultado
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(new Color(68, 68, 68));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        resultadoArea = new JTextArea(15, 40);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);
        resultadoArea.setBackground(Color.WHITE);
        resultadoArea.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);

        adicionarValidacao(pesquisaField, TipoValidacao.REQUERIDO, "Digite um termo para pesquisar!");

    }

    private void realizarPesquisa() {
        String termo = pesquisaField.getText().trim();
        String tipo = (String) tipoComboBox.getSelectedItem();

        if (termo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um termo para pesquisar!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultadoArea.setText("");

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
    }

    private void pesquisarAlunos(String termo) {
        List<Aluno> alunos = alunoController.buscarAlunosPorNome(termo);

        if (alunos.isEmpty()) {
            Aluno aluno = alunoController.buscarAlunoPorCpf(termo);
            if (aluno != null) {
                alunos.add(aluno);
            }
        }

        if (alunos.isEmpty()) {
            resultadoArea.setText("Nenhum aluno encontrado.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();

        for (Aluno aluno : alunos) {
            sb.append("Nome: ").append(aluno.getNome()).append("\n");
            sb.append("Data Nascimento: ").append(dateFormat.format(aluno.getDataNascimento())).append("\n");
            sb.append("CPF: ").append(aluno.getCpf()).append("\n");
            sb.append("Email: ").append(aluno.getEmail()).append("\n");
            sb.append("Gênero: ").append(aluno.getGenero()).append("\n");
            sb.append("Endereço: ").append(aluno.getEndereco()).append("\n");

            if (aluno.getTurma() != null) {
                sb.append("Turma: ").append(aluno.getTurma().getCodigo()).append(" - ").append(aluno.getTurma().getPeriodo()).append("\n");

                if (aluno.getTurma().getCurso() != null) {
                    sb.append("Curso: ").append(aluno.getTurma().getCurso().getNome()).append("\n");
                }
            }

            sb.append("\n");
        }

        resultadoArea.setText(sb.toString());
    }

    private void pesquisarTurmas(String termo) {
        List<Turma> turmas = turmaController.buscarTurmasPorCodigo(termo);

        if (turmas.isEmpty()) {
            resultadoArea.setText("Nenhuma turma encontrada.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();

        for (Turma turma : turmas) {
            sb.append("Código: ").append(turma.getCodigo()).append("\n");
            sb.append("Período: ").append(turma.getPeriodo()).append("\n");
            sb.append("Capacidade: ").append(turma.getCapacidade()).append("\n");
            sb.append("Data Início: ").append(dateFormat.format(turma.getDataInicio())).append("\n");
            sb.append("Data Término: ").append(dateFormat.format(turma.getDataTermino())).append("\n");

            if (turma.getCurso() != null) {
                sb.append("Curso: ").append(turma.getCurso().getNome()).append("\n");
            }

            sb.append("Alunos Matriculados: ").append(turma.getAlunos().size()).append("\n");
            sb.append("\n");
        }

        resultadoArea.setText(sb.toString());
    }

    private void pesquisarCursos(String termo) {
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

            sb.append("Turmas: ").append(curso.getTurmas().size()).append("\n");
            sb.append("\n");
        }

        resultadoArea.setText(sb.toString());
    }

    private void pesquisarProfessores(String termo) {
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();

        for (Professor professor : professores) {
            sb.append("Nome: ").append(professor.getNome()).append("\n");
            sb.append("Data Nascimento: ").append(dateFormat.format(professor.getDataNascimento())).append("\n");
            sb.append("CPF: ").append(professor.getCpf()).append("\n");
            sb.append("Endereço: ").append(professor.getEndereco()).append("\n");
            sb.append("Carteirinha de Licenciatura: ").append(professor.getCarteirinhaLicenciatura()).append("\n");
            sb.append("\n");
        }

        resultadoArea.setText(sb.toString());
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
}