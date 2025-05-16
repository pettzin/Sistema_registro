package view;

import view.components.Button;
import view.components.Form;

import javax.swing.*;
import java.awt.*;

public abstract class BasePanel extends JPanel {
    protected MainFrame mainFrame;
    protected Form form;
    protected JPanel titlePanel;
    protected JPanel buttonPanel;
    
    public BasePanel(MainFrame mainFrame, String title) {
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout());
        setBackground(new Color(68, 68, 68));
        
        // Create title panel
        titlePanel = new JPanel();
        titlePanel.setBackground(new Color(68, 68, 68));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        
        // Create form
        form = new Form();
        
        // Create button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(68, 68, 68));
        
        // Add components to panel
        add(titlePanel, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    protected Button createSaveButton() {
        Button saveButton = Button.createActionButton("Salvar", new Color(51, 51, 51));
        return saveButton;
    }
    
    protected Button createEditButton() {
        Button editButton = Button.createActionButton("Editar", new Color(51, 51, 51));
        return editButton;
    }
    
    protected Button createDeleteButton() {
        Button deleteButton = Button.createActionButton("Excluir", Color.RED);
        return deleteButton;
    }
    
    protected abstract void initializeComponents();
    protected abstract void setupListeners();
    protected abstract void clearFields();
}
