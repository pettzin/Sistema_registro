package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {
    
    private Color corPadrao = new Color(68, 68, 68);
    private Color corHover = new Color(0, 174, 239);  // Cor de destaque azul
    private Color corSelecionado = new Color(0, 174, 239);
    private Color corTexto = Color.WHITE;
    private boolean estaSelecionado = false;
    
    public Button(String texto) {
        super(texto);
        configurarBotao();
    }
    
    public Button(String texto, Color corPadrao, Color corHover, Color corSelecionado, Color corTexto) {
        super(texto);
        this.corPadrao = corPadrao;
        this.corHover = corHover;
        this.corSelecionado = corSelecionado;
        this.corTexto = corTexto;
        configurarBotao();
    }
    
    private void configurarBotao() {
        setBackground(corPadrao);
        setForeground(corTexto);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, 14));
        setContentAreaFilled(false);
        setOpaque(true);
        
        // Borda arredondada personalizada
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Efeito hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!estaSelecionado) {
                    setBackground(corHover);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!estaSelecionado) {
                    setBackground(corPadrao);
                }
            }
        });
    }
    
    public void setSelected(boolean selecionado) {
        estaSelecionado = selecionado;
        if (selecionado) {
            setBackground(corSelecionado);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 174, 239), 2),
                BorderFactory.createEmptyBorder(8, 13, 8, 13)
            ));
        } else {
            setBackground(corPadrao);
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }
    }
    
    public boolean isButtonSelected() {
        return estaSelecionado;
    }
    
    // Método para criar um botão de menu para a barra lateral
    public static Button createMenuButton(String texto) {
        Button botao = new Button(texto);
        botao.setPreferredSize(new Dimension(200, 50));
        botao.setMaximumSize(new Dimension(200, 50));
        botao.setMinimumSize(new Dimension(200, 50));
        botao.setHorizontalAlignment(SwingConstants.CENTER);
        return botao;
    }
    
    // Método para criar um botão de ação padrão
    public static Button createActionButton(String texto, Color corFundo) {
        Button botao = new Button(texto, corFundo, corFundo.darker(), corFundo, Color.WHITE);
        botao.setBorderPainted(true);
        botao.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return botao;
    }
}
