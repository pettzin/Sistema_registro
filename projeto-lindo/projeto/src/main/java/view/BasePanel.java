package view;

import view.components.Button;
import view.components.Form;

import javax.swing.*;
import java.awt.*;

public abstract class BasePanel extends JPanel {
    protected MainFrame mainFrame;
    protected String titulo;
    protected Form form;
    protected JPanel painelBotoes;
    
    public BasePanel(MainFrame mainFrame, String titulo) {
        this.mainFrame = mainFrame;
        this.titulo = titulo;
        
        setLayout(new BorderLayout());
        setBackground(new Color(220, 220, 220));
        
        // Criar o painel de título
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelTitulo.setBackground(new Color(51, 51, 51));
        painelTitulo.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setForeground(Color.WHITE);
        painelTitulo.add(tituloLabel);
        
        // Criar o painel de formulário
        form = new Form();
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Criar o painel de botões
        painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBackground(new Color(220, 220, 220));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Adicionar os painéis ao painel principal
        add(painelTitulo, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    protected Button createSaveButton() {
        return Button.createActionButton("Salvar", new Color(0, 128, 0));
    }
    
    protected Button createEditButton() {
        return Button.createActionButton("Editar", new Color(0, 0, 128));
    }
    
    protected Button createDeleteButton() {
        return Button.createActionButton("Excluir", new Color(128, 0, 0));
    }
    
    protected abstract void initializeComponents();
    
    protected abstract void setupListeners();
    
    protected abstract void clearFields();
}
