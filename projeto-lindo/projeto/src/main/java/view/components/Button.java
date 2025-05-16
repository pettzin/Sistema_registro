package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {
    
    private Color defaultBackground = new Color(68, 68, 68);
    private Color hoverBackground = new Color(0, 174, 239);  // Blue highlight color
    private Color selectedBackground = new Color(0, 174, 239);
    private Color textColor = Color.WHITE;
    private boolean isSelected = false;
    
    public Button(String text) {
        super(text);
        setupButton();
    }
    
    public Button(String text, Color defaultBg, Color hoverBg, Color selectedBg, Color textColor) {
        super(text);
        this.defaultBackground = defaultBg;
        this.hoverBackground = hoverBg;
        this.selectedBackground = selectedBg;
        this.textColor = textColor;
        setupButton();
    }
    
    private void setupButton() {
        setBackground(defaultBackground);
        setForeground(textColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, 14));
        setContentAreaFilled(false);
        setOpaque(true);
        
        // Custom rounded border
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isSelected) {
                    setBackground(hoverBackground);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!isSelected) {
                    setBackground(defaultBackground);
                }
            }
        });
    }
    
    public void setSelected(boolean selected) {
        isSelected = selected;
        if (selected) {
            setBackground(selectedBackground);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 174, 239), 2),
                BorderFactory.createEmptyBorder(8, 13, 8, 13)
            ));
        } else {
            setBackground(defaultBackground);
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }
    }
    
    public boolean isButtonSelected() {
        return isSelected;
    }
    
    // Method to create a menu button for the sidebar
    public static Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setMaximumSize(new Dimension(200, 50));
        button.setMinimumSize(new Dimension(200, 50));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        return button;
    }
    
    // Method to create a standard action button
    public static Button createActionButton(String text, Color bgColor) {
        Button button = new Button(text, bgColor, bgColor.darker(), bgColor, Color.WHITE);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }
}
