package view;

import view.components.Button;
import view.components.Form;

import javax.swing.*;
import java.awt.*;

public abstract class BasePanel extends JPanel {
    protected MainFrame mainFrame;
    protected Form form;
    protected JPanel painelTitulo;
    protected JPanel painelBotoes;
    
    public BasePanel(MainFrame mainFrame, String titulo) {
        this.mainFrame = mainFrame;
        
        setLayout(new BorderLayout());
        setBackground(new Color(220, 220, 220)); // Cor de fundo cinza claro
        
        // Criar painel de título
        painelTitulo = new JPanel();
        painelTitulo.setBackground(new Color(220, 220, 220));
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setForeground(Color.BLACK);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        painelTitulo.add(tituloLabel);
        
        // Criar formulário
        form = new Form();
        
        // Criar painel de botões
        painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(new Color(220, 220, 220));
        
        // Adicionar componentes ao painel
        add(painelTitulo, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    protected Button createSaveButton() {
        Button salvarButton = new Button("Salvar");
        salvarButton.setBackground(new Color(51, 51, 51));
        salvarButton.setForeground(Color.WHITE);
        salvarButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        salvarButton.setPreferredSize(new Dimension(120, 35));
        return salvarButton;
    }
    
    protected Button createEditButton() {
        Button editarButton = new Button("Editar");
        editarButton.setBackground(new Color(51, 51, 51));
        editarButton.setForeground(Color.WHITE);
        editarButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        editarButton.setPreferredSize(new Dimension(120, 35));
        return editarButton;
    }
    
    protected Button createDeleteButton() {
        Button excluirButton = new Button("Excluir");
        excluirButton.setBackground(Color.RED);
        excluirButton.setForeground(Color.WHITE);
        excluirButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        excluirButton.setPreferredSize(new Dimension(120, 35));
        return excluirButton;
    }
    
    protected abstract void initializeComponents();
    protected abstract void setupListeners();
    protected abstract void clearFields();
}
