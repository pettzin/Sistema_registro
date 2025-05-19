// Form.java modifications

package view.components;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Form extends JPanel {
    
    private GridBagConstraints gbc;
    private Color corFundo = new Color(220, 220, 220); // Cor de fundo cinza claro
    private Color corLabel = Color.BLACK; // Cor do label já é preta
    
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
        gbc.anchor = GridBagConstraints.WEST; // Alinha o título à esquerda
        
        JLabel label = new JLabel(texto);
        label.setForeground(Color.BLACK); // Garante que a cor do texto seja preta
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        // Sem background para o título
        label.setOpaque(false);
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
        // Modificar para usar um tamanho fixo maior
        RoundedTextField campoTexto = new RoundedTextField(20);
        
        // Definir um tamanho preferido maior para todos os campos
        campoTexto.setPreferredSize(new Dimension(350, 40)); // Aumentado de 300x30 para 350x40
        
        addComponent(campoTexto, gridx, gridy, gridwidth);
        return campoTexto;
    }

    public <E> JComboBox<E> addComboBox(int gridx, int gridy, int gridwidth) {
        RoundedComboBox<E> comboBox = new RoundedComboBox<>();
        
        // Definir o mesmo tamanho preferido maior para os comboboxes
        comboBox.setPreferredSize(new Dimension(350, 40)); // Aumentado de 300x30 para 350x40
        
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
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        panelTexto.add(scrollPane, BorderLayout.CENTER);
        
        // Definir um tamanho preferido maior para o painel do textarea
        panelTexto.setPreferredSize(new Dimension(350, 120)); // Aumentado de 300x100 para 350x120
        
        addComponent(panelTexto, gridx, gridy, gridwidth);
        return areaTexto;
    }
    
    public JPanel createButtonPanel(JButton... botoes) {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setBackground(corFundo);
        
        for (JButton botao : botoes) {
            painelBotoes.add(botao);
        }
        
        // Para garantir que os botões fiquem no canto inferior direito
        // Adicione este painel usando constraints específicas no seu layout principal
        // Por exemplo: addComponent(painelBotoes, 0, ultimaLinha, 2);
        // onde ultimaLinha é a última linha do seu formulário
        
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