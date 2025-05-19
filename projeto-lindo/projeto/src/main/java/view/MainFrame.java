package view;

import view.components.Button;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private JPanel painelConteudo;
    private JPanel painelLateral;
    private AlunoPanel alunoPanel;
    private TurmaPanel turmaPanel;
    private CursoPanel cursoPanel;
    private ProfessorPanel professorPanel;
    private PesquisaPanel pesquisaPanel;
    
    private List<Button> botoesMenu;
    private int botaoAtivoIndex = 0;

    public MainFrame() {
        setTitle("Sistema de Matrícula de Alunos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 760);
        setLocationRelativeTo(null);
        
        alunoPanel = new AlunoPanel(this);
        turmaPanel = new TurmaPanel(this);
        cursoPanel = new CursoPanel(this);
        professorPanel = new ProfessorPanel(this);
        pesquisaPanel = new PesquisaPanel(this);

        painelConteudo = new JPanel(new BorderLayout());
        setContentPane(painelConteudo);
        
        painelLateral = criarPainelLateral();
        painelConteudo.add(painelLateral, BorderLayout.WEST);
        
        // Definir o primeiro botão como selecionado por padrão
        botoesMenu.get(botaoAtivoIndex).setSelected(true);
        mostrarPainel(alunoPanel);
    }
    
    private JPanel criarPainelLateral() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(34, 34, 34)); // Cor de fundo escura
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        
        botoesMenu = new ArrayList<>();
        
        // Criar botões do menu
        Button alunoButton = Button.createMenuButton("Aluno");
        Button cursoButton = Button.createMenuButton("Curso");
        Button turmaButton = Button.createMenuButton("Turma");
        Button professorButton = Button.createMenuButton("Professor");
        Button pesquisarButton = Button.createMenuButton("Pesquisar");
        
        // Adicionar botões à lista para rastreamento
        botoesMenu.add(alunoButton);
        botoesMenu.add(cursoButton);
        botoesMenu.add(turmaButton);
        botoesMenu.add(professorButton);
        botoesMenu.add(pesquisarButton);
        
        // Adicionar action listeners
        alunoButton.addActionListener(e -> selecionarBotao(0, alunoPanel));
        cursoButton.addActionListener(e -> selecionarBotao(1, cursoPanel));
        turmaButton.addActionListener(e -> selecionarBotao(2, turmaPanel));
        professorButton.addActionListener(e -> selecionarBotao(3, professorPanel));
        pesquisarButton.addActionListener(e -> selecionarBotao(4, pesquisaPanel));
        
        // Adicionar botões à barra lateral com espaçamento
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(alunoButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(cursoButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(turmaButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(professorButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(pesquisarButton);
        sidebar.add(Box.createVerticalGlue());
        
        return sidebar;
    }
    
    private void selecionarBotao(int index, JPanel panel) {
        // Desselecionar o botão atual
        botoesMenu.get(botaoAtivoIndex).setSelected(false);
        
        // Selecionar o novo botão
        botaoAtivoIndex = index;
        botoesMenu.get(botaoAtivoIndex).setSelected(true);
        
        // Mostrar o painel correspondente
        mostrarPainel(panel);
    }
    
    public void mostrarPainel(JPanel panel) {
        // Remover todos os componentes exceto a barra lateral
        Component[] components = painelConteudo.getComponents();
        for (Component component : components) {
            if (component != painelLateral) {
                painelConteudo.remove(component);
            }
        }
        
        // Adicionar o novo painel
        painelConteudo.add(panel, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
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
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            // Criar o frame principal primeiro (mas não mostrar ainda)
            MainFrame frame = new MainFrame();
            
            // Mostrar a tela de splash primeiro
            SplashScreen splashScreen = new SplashScreen(frame);
            splashScreen.mostrarSplash();
            
            // Depois mostrar o frame principal
            frame.setVisible(true);
        });
    }
}
