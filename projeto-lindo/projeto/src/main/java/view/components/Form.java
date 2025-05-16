package view.components;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Form extends JPanel {
    
    private GridBagConstraints gbc;
    private Color corFundo = new Color(220, 220, 220); // Cor de fundo cinza claro
    private Color corLabel = Color.BLACK;
    
    public Form() {
        setLayout(new GridBagLayout());
        setBackground(corFundo);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
    }
    
    public Form(Color corFundo, Color corLabel) {
        this();
        this.corFundo = corFundo;
        this.corLabel = corLabel;
        setBackground(corFundo);
    }
    
    public JLabel addLabel(String texto, int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        
        JLabel label = new JLabel(texto);
        label.setForeground(corLabel);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        add(label, gbc);
        
        return label;
    }
    
    public void addComponent(JComponent componente, int gridx, int gridy, int gridwidth) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        
        add(componente, gbc);
    }
    
    public JTextField addTextField(int gridx, int gridy, int gridwidth) {
        RoundedTextField campoTexto = new RoundedTextField(20);
        addComponent(campoTexto, gridx, gridy, gridwidth);
        return campoTexto;
    }
    
    public <E> JComboBox<E> addComboBox(int gridx, int gridy, int gridwidth) {
        RoundedComboBox<E> comboBox = new RoundedComboBox<>();
        addComponent(comboBox, gridx, gridy, gridwidth);
        return comboBox;
    }
    
    public JTextArea addTextArea(int gridx, int gridy, int gridwidth) {
        JTextArea areaTexto = new JTextArea(5, 20);
        areaTexto.setLineWrap(true);
        
        // Criar um painel com borda arredondada para o JTextArea
        JPanel panelTexto = new JPanel(new BorderLayout());
        panelTexto.setBackground(Color.WHITE);
        panelTexto.setBorder(new RoundedBorder(Color.BLACK, 1, 10));
        panelTexto.add(areaTexto, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        panelTexto.add(scrollPane, BorderLayout.CENTER);
        
        addComponent(panelTexto, gridx, gridy, gridwidth);
        return areaTexto;
    }
    
    public JPanel createButtonPanel(JButton... botoes) {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setBackground(corFundo);
        
        for (JButton botao : botoes) {
            painelBotoes.add(botao);
        }
        
        return painelBotoes;
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
