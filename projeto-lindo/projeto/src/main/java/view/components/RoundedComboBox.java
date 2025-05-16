package view.components;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedComboBox<E> extends JComboBox<E> {
    
    private int raio = 10;
    
    public RoundedComboBox() {
        super();
        setOpaque(false);
        setBorder(new RoundedBorder(Color.BLACK, 1, raio));
        setPreferredSize(new Dimension(getPreferredSize().width, 35));
        setBackground(Color.WHITE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), raio, raio));
        
        super.paintComponent(g2);
        g2.dispose();
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
