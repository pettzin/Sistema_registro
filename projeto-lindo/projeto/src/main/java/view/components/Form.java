package view.components;

import javax.swing.*;
import java.awt.*;

public class Form extends JPanel {
    
    private GridBagConstraints gbc;
    private Color corFundo = new Color(68, 68, 68);
    private Color corLabel = Color.WHITE;
    
    public Form() {
        setLayout(new GridBagLayout());
        setBackground(corFundo);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
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
        JTextField campoTexto = new JTextField(20);
        addComponent(campoTexto, gridx, gridy, gridwidth);
        return campoTexto;
    }
    
    public JComboBox addComboBox(int gridx, int gridy, int gridwidth) {
        JComboBox comboBox = new JComboBox();
        addComponent(comboBox, gridx, gridy, gridwidth);
        return comboBox;
    }
    
    public JTextArea addTextArea(int gridx, int gridy, int gridwidth) {
        JTextArea areaTexto = new JTextArea(5, 20);
        areaTexto.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        addComponent(scrollPane, gridx, gridy, gridwidth);
        return areaTexto;
    }
    
    public JPanel createButtonPanel(JButton... botoes) {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(corFundo);
        
        for (JButton botao : botoes) {
            painelBotoes.add(botao);
        }
        
        return painelBotoes;
    }
}
