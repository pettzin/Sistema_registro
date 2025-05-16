package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SplashScreen extends JDialog {
    
    private JLabel mensagemLabel;
    private boolean teclaPrecionada = false;
    private Timer timer;
    
    public SplashScreen(Frame owner) {
        super(owner, true); // Modal dialog
        setTitle("Sistema de Matrícula de Alunos");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove decorações da janela
        
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(34, 34, 34));
        painelPrincipal.setBorder(BorderFactory.createLineBorder(new Color(0, 174, 239), 2));
        
        // Título
        JLabel tituloLabel = new JLabel("Sistema de Matrícula de Alunos");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        
        // Subtítulo
        JLabel subtituloLabel = new JLabel("Gerenciamento Acadêmico");
        subtituloLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtituloLabel.setForeground(new Color(180, 180, 180));
        subtituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Mensagem
        mensagemLabel = new JLabel("Pressione qualquer tecla para continuar...");
        mensagemLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mensagemLabel.setForeground(new Color(0, 174, 239));
        mensagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mensagemLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Versão
        JLabel versaoLabel = new JLabel("Versão 1.0");
        versaoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        versaoLabel.setForeground(new Color(150, 150, 150));
        versaoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        versaoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        
        // Painel central com logo ou ícone
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBackground(new Color(34, 34, 34));
        
        // Criar um logo simples (pode ser substituído por uma imagem)
        JPanel logoPainel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int tamanho = Math.min(getWidth(), getHeight()) / 2;
                int x = getWidth() / 2 - tamanho / 2;
                int y = getHeight() / 2 - tamanho / 2;
                
                // Desenhar círculo
                g2d.setColor(new Color(0, 174, 239));
                g2d.fillOval(x, y, tamanho, tamanho);
                
                // Desenhar ícone de livro
                g2d.setColor(new Color(34, 34, 34));
                int livroLargura = tamanho * 3 / 4;
                int livroAltura = tamanho / 2;
                int livroX = x + tamanho / 2 - livroLargura / 2;
                int livroY = y + tamanho / 2 - livroAltura / 2;
                
                g2d.fillRect(livroX, livroY, livroLargura, livroAltura);
                
                // Desenhar páginas do livro
                g2d.setColor(Color.WHITE);
                g2d.fillRect(livroX + 2, livroY + 2, livroLargura - 4, livroAltura - 4);
                
                // Desenhar linhas para páginas
                g2d.setColor(new Color(200, 200, 200));
                for (int i = 1; i < 5; i++) {
                    int linhaY = livroY + i * livroAltura / 5;
                    g2d.drawLine(livroX + 5, linhaY, livroX + livroLargura - 5, linhaY);
                }
                
                g2d.dispose();
            }
        };
        logoPainel.setBackground(new Color(34, 34, 34));
        
        painelCentral.add(logoPainel, BorderLayout.CENTER);
        
        // Adicionar componentes ao painel principal
        JPanel cabecalhoPainel = new JPanel(new BorderLayout());
        cabecalhoPainel.setBackground(new Color(34, 34, 34));
        cabecalhoPainel.add(tituloLabel, BorderLayout.NORTH);
        cabecalhoPainel.add(subtituloLabel, BorderLayout.CENTER);
        
        painelPrincipal.add(cabecalhoPainel, BorderLayout.NORTH);
        painelPrincipal.add(painelCentral, BorderLayout.CENTER);
        painelPrincipal.add(mensagemLabel, BorderLayout.SOUTH);
        painelPrincipal.add(versaoLabel, BorderLayout.SOUTH);
        
        // Adicionar key listener para continuar ao pressionar uma tecla
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                teclaPrecionada = true;
                if (timer != null) {
                    timer.stop();
                }
                dispose(); // Fechar a tela de splash
            }
        });
        
        // Garantir que o frame possa receber eventos de teclado
        setFocusable(true);
        
        setContentPane(painelPrincipal);
    }
    
    public void mostrarSplash() {
        // Piscar a mensagem
        timer = new Timer(500, e -> {
            mensagemLabel.setVisible(!mensagemLabel.isVisible());
        });
        timer.start();
        
        // Mostrar o diálogo modal - isso bloqueia até que o diálogo seja fechado
        setVisible(true);
    }
}
