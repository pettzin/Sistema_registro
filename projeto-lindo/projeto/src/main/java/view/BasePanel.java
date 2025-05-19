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
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alterado para LEFT
        painelTitulo.setBackground(new Color(51, 51, 51));
        painelTitulo.setPreferredSize(new Dimension(getWidth(), 60));
        
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setForeground(Color.WHITE);
        painelTitulo.add(Box.createHorizontalStrut(20)); // Adiciona espaço à esquerda
        painelTitulo.add(tituloLabel);
        
        // Criar o painel de formulário
        form = new Form();
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Criar o painel de botões com alinhamento à direita
        painelBotoes = new JPanel();
        painelBotoes.setLayout(new BorderLayout());
        painelBotoes.setBackground(new Color(220, 220, 220));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 20)); // Adiciona margem à direita
        
        // Adicionar os painéis ao painel principal
        add(painelTitulo, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    // Método para configurar o painel de botões com alinhamento à direita
    protected void setupButtonPanel(Button... botoes) {
        // Limpar o painel de botões existente
        painelBotoes.removeAll();
        
        // Criar um painel com FlowLayout alinhado à direita
        JPanel botoesAlinhados = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        botoesAlinhados.setBackground(new Color(220, 220, 220));
        
        // Adicionar os botões ao painel
        for (Button botao : botoes) {
            if (botao != null) {
                // Configurar o botão com tamanho e fonte maiores
                botao.setFont(new Font("Arial", Font.BOLD, 14));
                botao.setPreferredSize(new Dimension(150, 45));
                botoesAlinhados.add(botao);
            }
        }
        
        // Adicionar o painel alinhado ao painel de botões principal
        painelBotoes.add(botoesAlinhados, BorderLayout.EAST);
        
        // Atualizar o painel
        painelBotoes.revalidate();
        painelBotoes.repaint();
    }
    
    protected Button createSaveButton() {
        Button botao = Button.createActionButton("Salvar", new Color(0, 128, 0));
        // Tornar a cor mais vívida
        botao = Button.createActionButton("Salvar", new Color(0, 180, 0)); // Verde mais vívido
        botao.setPreferredSize(new Dimension(150, 45));
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        return botao;
    }
    
    protected Button createEditButton() {
        Button botao = Button.createActionButton("Editar", new Color(0, 0, 180)); // Azul mais vívido
        botao.setPreferredSize(new Dimension(150, 45));
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        return botao;
    }
    
    protected Button createDeleteButton() {
        Button botao = Button.createActionButton("Excluir", new Color(180, 0, 0)); // Vermelho mais vívido
        botao.setPreferredSize(new Dimension(150, 45));
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        return botao;
    }
    
    // Método para criar um campo de pesquisa com largura maior
    protected JTextField createSearchField() {
        JTextField campoTexto = form.createSearchField();
        return campoTexto;
    }
    
    // Método para configurar campos de texto com tamanho e fonte maiores
    protected void configurarCampoTexto(JTextField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
        campo.setPreferredSize(new Dimension(450, 45));
        campo.setMinimumSize(new Dimension(450, 45));
    }
    
    // Método para configurar combobox com tamanho e fonte maiores
    protected void configurarComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setPreferredSize(new Dimension(450, 45));
        comboBox.setMinimumSize(new Dimension(450, 45));
        
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setFont(new Font("Arial", Font.PLAIN, 16));
                return c;
            }
        });
    }
    
    protected abstract void initializeComponents();
    
    protected abstract void setupListeners();
    
    protected abstract void clearFields();
}