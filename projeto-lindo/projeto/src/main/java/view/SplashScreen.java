package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SplashScreen extends JDialog {
    
    private JLabel mensagemLabel;
    private Timer timer;
    
    public SplashScreen(Frame owner) {
        super(owner, true); 
        setTitle("EDUCA CLASS");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setUndecorated(true); 
        
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(Color.BLACK);
        
        JLabel tituloLabel = new JLabel("EDUCA CLASS");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 48));
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        mensagemLabel = new JLabel("Pressione uma tecla para continuar...");
        mensagemLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        mensagemLabel.setForeground(Color.WHITE);
        mensagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mensagemLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        
        painelPrincipal.add(tituloLabel, BorderLayout.CENTER);
        painelPrincipal.add(mensagemLabel, BorderLayout.SOUTH);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (timer != null) {
                    timer.stop();
                }
                dispose();
            }
        });

        setFocusable(true);
        
        setContentPane(painelPrincipal);
    }
    
    public void mostrarSplash() {
        setVisible(true);
    }
}
