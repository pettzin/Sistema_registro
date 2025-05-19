package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class Button extends JButton {
    
    // Cores mais vívidas
    private Color corPadrao = new Color(50, 50, 50); // Cinza mais escuro
    private Color corBorda = new Color(100, 100, 100);
    private Color corBordaHover = new Color(0, 200, 255);  // Azul mais vívido
    private Color corTexto = Color.WHITE;
    private boolean estaSelecionado = false;
    private int raio = 10; // Raio para as bordas arredondadas
    
    public Button(String texto) {
        super(texto);
        configurarBotao();
    }
    
    public Button(String texto, Color corPadrao, Color corBorda, Color corBordaHover, Color corTexto) {
        super(texto);
        this.corPadrao = corPadrao;
        this.corBorda = corBorda;
        this.corBordaHover = corBordaHover;
        this.corTexto = corTexto;
        configurarBotao();
    }
    
    private void configurarBotao() {
        setBackground(corPadrao);
        setForeground(corTexto);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, 14));
        setContentAreaFilled(false);
        setOpaque(false);
        
        // Efeito hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!estaSelecionado) {
                    corBorda = corBordaHover;
                    repaint();
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!estaSelecionado) {
                    corBorda = new Color(100, 100, 100);
                    repaint();
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Desenhar o fundo arredondado
        g2.setColor(corPadrao);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), raio, raio));
        
        // Desenhar a borda arredondada
        g2.setColor(corBorda);
        g2.setStroke(new BasicStroke(estaSelecionado ? 2 : 1));
        g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, raio, raio));
        
        super.paintComponent(g2);
        g2.dispose();
    }
    
    public void setSelected(boolean selecionado) {
        estaSelecionado = selecionado;
        if (selecionado) {
            corBorda = corBordaHover;
        } else {
            corBorda = new Color(100, 100, 100);
        }
        repaint();
    }
    
    public boolean isButtonSelected() {
        return estaSelecionado;
    }
    
    // Método para criar um botão de menu para a barra lateral
    public static Button createMenuButton(String texto) {
        Button botao = new Button(texto);
        botao.setPreferredSize(new Dimension(200, 50));
        botao.setMaximumSize(new Dimension(200, 50));
        botao.setMinimumSize(new Dimension(200, 50));
        botao.setHorizontalAlignment(SwingConstants.CENTER);
        botao.setBackground(new Color(50, 50, 50)); // Cinza mais escuro
        botao.setForeground(Color.WHITE);
        return botao;
    }
    
    // Método para criar um botão de ação padrão com cores mais vívidas
    public static Button createActionButton(String texto, Color corFundo) {
        // Tornar a cor mais vívida
        float[] hsb = Color.RGBtoHSB(corFundo.getRed(), corFundo.getGreen(), corFundo.getBlue(), null);
        Color corMaisVivida = Color.getHSBColor(hsb[0], Math.min(1.0f, hsb[1] * 1.3f), Math.min(1.0f, hsb[2] * 1.2f));
        
        Button botao = new Button(texto, corMaisVivida, new Color(0, 0, 0), new Color(0, 200, 255), Color.WHITE);
        botao.setPreferredSize(new Dimension(120, 40)); // Botões um pouco maiores
        return botao;
    }
}