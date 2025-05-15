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
        
        alunoPanel = new AlunoPanel(this);
        turmaPanel = new TurmaPanel(this);
        cursoPanel = new CursoPanel(this);
        professorPanel = new ProfessorPanel(this);
        pesquisaPanel = new PesquisaPanel(this);

        contentPanel = new JPanel(new BorderLayout());
        setContentPane(contentPanel);
        

        JPanel menuPanel = createMenuPanel();
        contentPanel.add(menuPanel, BorderLayout.WEST);
        
        showPanel(alunoPanel);
    }
    
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1, 10, 10));
        menuPanel.setBackground(new Color(51, 51, 51));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton alunoButton = createMenuButton("Aluno");
        JButton turmaButton = createMenuButton("Turma");
        JButton cursoButton = createMenuButton("Curso");
        JButton professorButton = createMenuButton("Professor");
        JButton pesquisarButton = createMenuButton("Pesquisar");
        
        alunoButton.addActionListener(e -> showPanel(alunoPanel));
        turmaButton.addActionListener(e -> showPanel(turmaPanel));
        cursoButton.addActionListener(e -> showPanel(cursoPanel));
        professorButton.addActionListener(e -> showPanel(professorPanel));
        pesquisarButton.addActionListener(e -> showPanel(pesquisaPanel));
        

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

        Component[] components = contentPanel.getComponents();
        for (Component component : components) {
            if (component != contentPanel.getComponent(0)) {
                contentPanel.remove(component);
            }
        }
        
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
        public void notificarProfessorSalvo() {
            cursoPanel.atualizarListaProfessores();

        }
        public void notificarCursoSalvo() {
            turmaPanel.atualizarListaCursos();
    }
        public void notificarTurmaSalva() {
        alunoPanel.atualizarListaTurmas();
    }
}