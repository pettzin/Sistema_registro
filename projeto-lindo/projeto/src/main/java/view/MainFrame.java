package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private AlunoPanel alunoPanel;
    private TurmaPanel turmaPanel;
    private CursoPanel cursoPanel;
    private ProfessorPanel professorPanel;
    private PesquisaPanel pesquisaPanel;

    public MainFrame() {
        setTitle("Sistema de Matrícula de Alunos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Inicializa os painéis
        alunoPanel = new AlunoPanel(this);
        turmaPanel = new TurmaPanel(this);
        cursoPanel = new CursoPanel(this);
        professorPanel = new ProfessorPanel(this);
        pesquisaPanel = new PesquisaPanel(this);
        
        // Painel de conteúdo principal
        contentPanel = new JPanel(new BorderLayout());
        setContentPane(contentPanel);
        
        // Painel de navegação (menu lateral)
        JPanel menuPanel = createMenuPanel();
        contentPanel.add(menuPanel, BorderLayout.WEST);
        
        // Inicialmente, mostra o painel de alunos
        showPanel(alunoPanel);
    }
    
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1, 10, 10));
        menuPanel.setBackground(new Color(51, 51, 51));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Botões do menu
        JButton alunoButton = createMenuButton("Aluno");
        JButton turmaButton = createMenuButton("Turma");
        JButton cursoButton = createMenuButton("Curso");
        JButton professorButton = createMenuButton("Professor");
        JButton pesquisarButton = createMenuButton("Pesquisar");
        
        // Adiciona ação aos botões
        alunoButton.addActionListener(e -> showPanel(alunoPanel));
        turmaButton.addActionListener(e -> showPanel(turmaPanel));
        cursoButton.addActionListener(e -> showPanel(cursoPanel));
        professorButton.addActionListener(e -> showPanel(professorPanel));
        pesquisarButton.addActionListener(e -> showPanel(pesquisaPanel));
        
        // Adiciona botões ao painel
        menuPanel.add(alunoButton);
        menuPanel.add(turmaButton);
        menuPanel.add(cursoButton);
        menuPanel.add(professorButton);
        menuPanel.add(pesquisarButton);
        
        return menuPanel;
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(68, 68, 68));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 102, 0));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(68, 68, 68));
            }
        });
        
        return button;
    }
    
    public void showPanel(JPanel panel) {
        // Remove o painel atual
        Component[] components = contentPanel.getComponents();
        for (Component component : components) {
            if (component != contentPanel.getComponent(0)) { // Não remove o menu
                contentPanel.remove(component);
            }
        }
        
        // Adiciona o novo painel
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}