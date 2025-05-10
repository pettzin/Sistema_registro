package edu.br.cruzeirodosul.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    
    // Painéis principais
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    
    // Botões do menu
    private JButton btnAluno;
    private JButton btnTurma;
    private JButton btnCurso;
    private JButton btnMatricula;
    private JButton btnProfessor;
    
    // Painéis de conteúdo
    private AlunoView alunoView;
    private TurmaView turmaView;
    private CursoView cursoView;
    private MatriculaView matriculaView;
    private ProfessorView professorView;
    
    public MainView() {
        initComponents();
        setupLayout();
        this.setTitle("Sistema de Gestão Acadêmica");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        // Inicializa o layout principal
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(30, 30, 30));
        
        // Inicializa o painel lateral
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(25, 25, 25));
        sidebarPanel.setPreferredSize(new Dimension(200, 600));
        
        // Inicializa os botões do menu
        btnAluno = createMenuButton("Aluno", "person");
        btnTurma = createMenuButton("Turma", "groups");
        btnCurso = createMenuButton("Curso", "menu_book");
        btnMatricula = createMenuButton("Matrícula", "assignment");
        btnProfessor = createMenuButton("Professor", "school");
        
        // Inicializa as views
        alunoView = new AlunoView();
        turmaView = new TurmaView();
        cursoView = new CursoView();
        matriculaView = new MatriculaView();
        professorView = new ProfessorView();
        
        // Adiciona as views ao painel de conteúdo
        contentPanel.add(alunoView, "aluno");
        contentPanel.add(turmaView, "turma");
        contentPanel.add(cursoView, "curso");
        contentPanel.add(matriculaView, "matricula");
        contentPanel.add(professorView, "professor");
    }
    
    private void setupLayout() {
        // Configura o layout principal
        this.setLayout(new BorderLayout());
        
        // Adiciona os botões ao painel lateral
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(btnAluno);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnTurma);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnCurso);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnMatricula);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnProfessor);
        
        // Adiciona os painéis ao frame
        this.add(sidebarPanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);
    }
    
    private JButton createMenuButton(String text, String iconName) {
        JButton button = new JButton(text);
        // Você pode adicionar ícones depois
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(25, 25, 25));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(200, 50));
        button.setPreferredSize(new Dimension(200, 50));
        return button;
    }
    
    // Métodos para adicionar action listeners aos botões
    public void addAlunoButtonListener(ActionListener listener) {
        btnAluno.addActionListener(e -> {
            cardLayout.show(contentPanel, "aluno");
            listener.actionPerformed(e);
        });
    }
    
    public void addTurmaButtonListener(ActionListener listener) {
        btnTurma.addActionListener(e -> {
            cardLayout.show(contentPanel, "turma");
            listener.actionPerformed(e);
        });
    }
    
    public void addCursoButtonListener(ActionListener listener) {
        btnCurso.addActionListener(e -> {
            cardLayout.show(contentPanel, "curso");
            listener.actionPerformed(e);
        });
    }
    
    public void addMatriculaButtonListener(ActionListener listener) {
        btnMatricula.addActionListener(e -> {
            cardLayout.show(contentPanel, "matricula");
            listener.actionPerformed(e);
        });
    }
    
    public void addProfessorButtonListener(ActionListener listener) {
        btnProfessor.addActionListener(e -> {
            cardLayout.show(contentPanel, "professor");
            listener.actionPerformed(e);
        });
    }
    
    // Método para mostrar a view específica
    public void showView(String viewName) {
        cardLayout.show(contentPanel, viewName);
    }
    
    // Getters para as views
    public AlunoView getAlunoView() {
        return alunoView;
    }
    
    public TurmaView getTurmaView() {
        return turmaView;
    }
    
    public CursoView getCursoView() {
        return cursoView;
    }
    
    public MatriculaView getMatriculaView() {
        return matriculaView;
    }
    
    public ProfessorView getProfessorView() {
        return professorView;
    }
}