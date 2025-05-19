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
        setTitle("EDUCA CLASS");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove decorações da janela
        
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(Color.BLACK);
        
        // Título "EDUCA CLASS" no centro
        JLabel tituloLabel = new JLabel("EDUCA CLASS");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 48));
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Mensagem na parte inferior
        mensagemLabel = new JLabel("Pressione uma tecla para continuar...");
        mensagemLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        mensagemLabel.setForeground(Color.WHITE);
        mensagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mensagemLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        
        // Adicionar componentes ao painel principal
        painelPrincipal.add(tituloLabel, BorderLayout.CENTER);
        painelPrincipal.add(mensagemLabel, BorderLayout.SOUTH);
        
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
        // Mostrar o diálogo modal - isso bloqueia até que o diálogo seja fechado
        setVisible(true);
    }
}
