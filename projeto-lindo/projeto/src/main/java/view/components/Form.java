package view.components;

import javax.swing.*;
import java.awt.*;

public class Form extends JPanel {
    
    private GridBagConstraints gbc;
    private Color backgroundColor = new Color(68, 68, 68);
    private Color labelColor = Color.WHITE;
    
    public Form() {
        setLayout(new GridBagLayout());
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    }
    
    public Form(Color backgroundColor, Color labelColor) {
        this();
        this.backgroundColor = backgroundColor;
        this.labelColor = labelColor;
        setBackground(backgroundColor);
    }
    
    public JLabel addLabel(String text, int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        
        JLabel label = new JLabel(text);
        label.setForeground(labelColor);
        add(label, gbc);
        
        return label;
    }
    
    public void addComponent(JComponent component, int gridx, int gridy, int gridwidth) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        
        add(component, gbc);
    }
    
    public JTextField addTextField(int gridx, int gridy, int gridwidth) {
        JTextField textField = new JTextField(20);
        addComponent(textField, gridx, gridy, gridwidth);
        return textField;
    }
    
    public JComboBox addComboBox(int gridx, int gridy, int gridwidth) {
        JComboBox comboBox = new JComboBox();
        addComponent(comboBox, gridx, gridy, gridwidth);
        return comboBox;
    }
    
    public JTextArea addTextArea(int gridx, int gridy, int gridwidth) {
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        addComponent(scrollPane, gridx, gridy, gridwidth);
        return textArea;
    }
    
    public JPanel createButtonPanel(JButton... buttons) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(backgroundColor);
        
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        
        return buttonPanel;
    }
}
