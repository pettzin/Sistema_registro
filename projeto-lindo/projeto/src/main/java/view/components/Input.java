package view.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Input {
    // Padrões de regex para validação
    private static final Pattern PADRAO_EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PADRAO_CPF = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    private static final Pattern PADRAO_DATA = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
    private static final Pattern PADRAO_NUMERICO = Pattern.compile("\\d+");
    private static final Pattern PADRAO_ALFABETICO = Pattern.compile("[a-zA-ZÀ-ú\\s]+");
    private static final Pattern PADRAO_ALFANUMERICO = Pattern.compile("[a-zA-Z0-9\\s]+");

    /**
     * Aplica uma máscara a um campo de texto sem substituir o componente
     * @param campoTexto O campo de texto para aplicar a máscara
     * @param mascara O padrão da máscara (ex: "###.###.###-##" para CPF)
     */
    public static void aplicarMascara(JTextField campoTexto, String mascara) {
        try {
            MaskFormatter formatter = new MaskFormatter(mascara);
            formatter.setPlaceholderCharacter('_');
            DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
            
            if (campoTexto instanceof JFormattedTextField) {
                ((JFormattedTextField)campoTexto).setFormatterFactory(factory);
            } else {
                // Criar um novo JFormattedTextField com as mesmas propriedades
                JFormattedTextField novoField = new JFormattedTextField(factory);
                novoField.setFont(campoTexto.getFont());
                novoField.setBackground(campoTexto.getBackground());
                novoField.setForeground(campoTexto.getForeground());
                novoField.setBorder(campoTexto.getBorder());
                novoField.setEditable(campoTexto.isEditable());
                novoField.setEnabled(campoTexto.isEnabled());
                novoField.setToolTipText(campoTexto.getToolTipText());
                novoField.setPreferredSize(campoTexto.getPreferredSize());
                novoField.setMinimumSize(campoTexto.getMinimumSize());
                novoField.setMaximumSize(campoTexto.getMaximumSize());
                
                // Substituir o campo original pelo novo campo formatado
                Container parent = campoTexto.getParent();
                if (parent != null) {
                    GridBagConstraints gbc = null;
                    if (parent.getLayout() instanceof GridBagLayout) {
                        GridBagLayout layout = (GridBagLayout) parent.getLayout();
                        gbc = layout.getConstraints(campoTexto);
                    }
                    
                    int index = -1;
                    for (int i = 0; i < parent.getComponentCount(); i++) {
                        if (parent.getComponent(i) == campoTexto) {
                            index = i;
                            break;
                        }
                    }
                    
                    if (index >= 0) {
                        parent.remove(campoTexto);
                        if (gbc != null) {
                            parent.add(novoField, gbc);
                        } else {
                            parent.add(novoField, index);
                        }
                        parent.revalidate();
                        parent.repaint();
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aplica a máscara de CPF a um campo de texto (###.###.###-##)
     * @param campoTexto O campo de texto para aplicar a máscara
     */
    public static void aplicarMascaraCPF(JTextField campoTexto) {
        // Usar um DocumentFilter em vez de substituir o componente
        AbstractDocument document = (AbstractDocument) campoTexto.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String beforeOffset = currentText.substring(0, offset);
                String afterOffset = currentText.substring(offset + length);
                String newText = beforeOffset + text + afterOffset;
                
                // Remover caracteres não numéricos para verificação
                String plainText = newText.replaceAll("[^0-9]", "");
                
                if (plainText.length() <= 11) {
                    // Formatar o texto com a máscara de CPF
                    StringBuilder formattedText = new StringBuilder();
                    for (int i = 0; i < plainText.length(); i++) {
                        if (i == 3 || i == 6) {
                            formattedText.append('.');
                        } else if (i == 9) {
                            formattedText.append('-');
                        }
                        formattedText.append(plainText.charAt(i));
                    }
                    
                    // Substituir todo o texto pelo texto formatado
                    fb.replace(0, fb.getDocument().getLength(), formattedText.toString(), attrs);
                }
            }
            
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                replace(fb, offset, 0, string, attr);
            }
        });
    }

    /**
     * Aplica a máscara de data a um campo de texto (dd/MM/yyyy)
     * @param campoTexto O campo de texto para aplicar a máscara
     */
    public static void aplicarMascaraData(JTextField campoTexto) {
        // Usar um DocumentFilter em vez de substituir o componente
        AbstractDocument document = (AbstractDocument) campoTexto.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String beforeOffset = currentText.substring(0, offset);
                String afterOffset = currentText.substring(offset + length);
                String newText = beforeOffset + text + afterOffset;
                
                // Remover caracteres não numéricos para verificação
                String plainText = newText.replaceAll("[^0-9]", "");
                
                if (plainText.length() <= 8) {
                    // Formatar o texto com a máscara de data
                    StringBuilder formattedText = new StringBuilder();
                    for (int i = 0; i < plainText.length(); i++) {
                        if (i == 2 || i == 4) {
                            formattedText.append('/');
                        }
                        formattedText.append(plainText.charAt(i));
                    }
                    
                    // Substituir todo o texto pelo texto formatado
                    fb.replace(0, fb.getDocument().getLength(), formattedText.toString(), attrs);
                }
            }
            
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                replace(fb, offset, 0, string, attr);
            }
        });
    }

    /**
     * Aplica a máscara de telefone a um campo de texto ((##) #####-####)
     * @param campoTexto O campo de texto para aplicar a máscara
     */
    public static void aplicarMascaraTelefone(JTextField campoTexto) {
        // Usar um DocumentFilter em vez de substituir o componente
        AbstractDocument document = (AbstractDocument) campoTexto.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String beforeOffset = currentText.substring(0, offset);
                String afterOffset = currentText.substring(offset + length);
                String newText = beforeOffset + text + afterOffset;
                
                // Remover caracteres não numéricos para verificação
                String plainText = newText.replaceAll("[^0-9]", "");
                
                if (plainText.length() <= 11) {
                    // Formatar o texto com a máscara de telefone
                    StringBuilder formattedText = new StringBuilder();
                    for (int i = 0; i < plainText.length(); i++) {
                        if (i == 0) {
                            formattedText.append('(');
                        } else if (i == 2) {
                            formattedText.append(") ");
                        } else if (i == 7) {
                            formattedText.append('-');
                        }
                        formattedText.append(plainText.charAt(i));
                    }
                    
                    // Substituir todo o texto pelo texto formatado
                    fb.replace(0, fb.getDocument().getLength(), formattedText.toString(), attrs);
                }
            }
            
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                replace(fb, offset, 0, string, attr);
            }
        });
    }

    /**
     * Limita o número de caracteres em um campo de texto
     * @param campoTexto O campo de texto para limitar
     * @param limite O número máximo de caracteres
     */
    public static void definirLimiteCaracteres(JTextComponent campoTexto, int limite) {
        AbstractDocument documento = (AbstractDocument) campoTexto.getDocument();
        documento.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String texto, AttributeSet attrs) throws BadLocationException {
                String novoTexto = fb.getDocument().getText(0, fb.getDocument().getLength()) + texto;
                if (novoTexto.length() <= limite) {
                    super.replace(fb, offset, length, texto, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String novoTexto = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (novoTexto.length() <= limite) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        });
    }

    /**
     * Permite apenas entrada numérica em um campo de texto
     * @param campoTexto O campo de texto para restringir
     */
    public static void apenasNumeros(JTextField campoTexto) {
        AbstractDocument documento = (AbstractDocument) campoTexto.getDocument();
        documento.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String texto, AttributeSet attrs) throws BadLocationException {
                if (texto.matches("\\d*")) {
                    super.replace(fb, offset, length, texto, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        });
    }

    /**
     * Permite apenas entrada alfabética em um campo de texto
     * @param campoTexto O campo de texto para restringir
     */
    public static void apenasAlfabetico(JTextField campoTexto) {
        AbstractDocument documento = (AbstractDocument) campoTexto.getDocument();
        documento.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String texto, AttributeSet attrs) throws BadLocationException {
                if (texto.matches("[a-zA-ZÀ-ú\\s]*")) {
                    super.replace(fb, offset, length, texto, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[a-zA-ZÀ-ú\\s]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        });
    }

    /**
     * Permite apenas entrada alfanumérica em um campo de texto
     * @param campoTexto O campo de texto para restringir
     */
    public static void apenasAlfanumerico(JTextField campoTexto) {
        AbstractDocument documento = (AbstractDocument) campoTexto.getDocument();
        documento.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String texto, AttributeSet attrs) throws BadLocationException {
                if (texto.matches("[a-zA-Z0-9\\s]*")) {
                    super.replace(fb, offset, length, texto, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[a-zA-Z0-9\\s]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        });
    }

    /**
     * Valida um endereço de email
     * @param email O email para validar
     * @return Verdadeiro se o email for válido, falso caso contrário
     */
    public static boolean isEmailValido(String email) {
        return PADRAO_EMAIL.matcher(email).matches();
    }

    /**
     * Valida um CPF
     * @param cpf O CPF para validar (pode estar formatado)
     * @return Verdadeiro se o CPF for matematicamente válido, falso caso contrário
     */
    /*public static boolean isCPFValido(String cpf) {
        // Primeiro, verifica se corresponde ao padrão básico
        if (!PADRAO_CPF.matcher(cpf).matches()) {
            return false;
        }

        // Remove caracteres não numéricos
        String apenasDigitosCPF = cpf.replaceAll("[^0-9]", "");

        // Verifica se todos os dígitos são iguais
        boolean todosDigitosIguais = true;
        for (int i = 1; i < apenasDigitosCPF.length(); i++) {
            if (apenasDigitosCPF.charAt(i) != apenasDigitosCPF.charAt(0)) {
                todosDigitosIguais = false;
                break;
            }
        }
        if (todosDigitosIguais) {
            return false;
        }

        // Calcula e verifica o primeiro dígito verificador
        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += (apenasDigitosCPF.charAt(i) - '0') * (10 - i);
        }
        int resto1 = soma1 % 11;
        int digito1 = resto1 < 2 ? 0 : 11 - resto1;
        if ((apenasDigitosCPF.charAt(9) - '0') != digito1) {
            return false;
        }

        // Calcula e verifica o segundo dígito verificador
        int soma2 = 0;
        for (int i = 0; i < 10; i++) {
            soma2 += (apenasDigitosCPF.charAt(i) - '0') * (11 - i);
        }
        int resto2 = soma2 % 11;
        int digito2 = resto2 < 2 ? 0 : 11 - resto2;
        return (apenasDigitosCPF.charAt(10) - '0') == digito2;
    }

    /**
     * Valida uma data no formato dd/MM/yyyy
     * @param dataStr A string da data para validar
     * @return Verdadeiro se a data for válida, falso caso contrário
     */
    public static boolean isDataValida(String dataStr) {
        if (!PADRAO_DATA.matcher(dataStr).matches()) {
            return false;
        }

        SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
        formatadorData.setLenient(false);

        try {
            formatadorData.parse(dataStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Adiciona validação a um campo de texto (apenas quando o botão salvar for clicado)
     * @param campoTexto O campo de texto para validar
     * @param tipoValidacao O tipo de validação a aplicar
     * @param mensagemErro A mensagem de erro a exibir se a validação falhar
     * @return true se o campo for válido, false caso contrário
     */
    public static boolean validarCampo(JTextComponent campoTexto, TipoValidacao tipoValidacao, String mensagemErro) {
        String texto = campoTexto.getText().trim();
        boolean valido = true;

        switch (tipoValidacao) {
            case EMAIL:
                valido = isEmailValido(texto);
                break;
            /*case CPF:
                valido = isCPFValido(texto);
                break;*/
            case DATA:
                valido = isDataValida(texto);
                break;
            case NUMERICO:
                valido = PADRAO_NUMERICO.matcher(texto).matches();
                break;
            case ALFABETICO:
                valido = PADRAO_ALFABETICO.matcher(texto).matches();
                break;
            case ALFANUMERICO:
                valido = PADRAO_ALFANUMERICO.matcher(texto).matches();
                break;
            case REQUERIDO:
                valido = !texto.isEmpty();
                break;
        }

        if (!valido) {
            JOptionPane.showMessageDialog(campoTexto, mensagemErro, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            campoTexto.requestFocus();
        }
        
        return valido;
    }

    /**
     * Adiciona validação a um campo de texto com um padrão (regex) personalizado
     * @param campoTexto O campo de texto para validar
     * @param padrao O padrão (regex) para validar a entrada
     * @param mensagemErro A mensagem de erro a exibir se a validação falhar
     * @return true se o campo for válido, false caso contrário
     */
    public static boolean validarCampoPersonalizado(JTextComponent campoTexto, Pattern padrao, String mensagemErro) {
        String texto = campoTexto.getText().trim();
        boolean valido = padrao.matcher(texto).matches();

        if (!valido) {
            JOptionPane.showMessageDialog(campoTexto, mensagemErro, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            campoTexto.requestFocus();
        }
        
        return valido;
    }

    /**
     * Adiciona feedback visual em tempo real a um campo de texto
     * @param campoTexto O campo de texto para validar
     * @param tipoValidacao O tipo de validação a aplicar
     */
    public static void adicionarFeedbackVisual(JTextComponent campoTexto, TipoValidacao tipoValidacao) {
        campoTexto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validar();
            }

            private void validar() {
                String texto = campoTexto.getText().trim();
                boolean valido = true;

                switch (tipoValidacao) {
                    case EMAIL:
                        valido = texto.isEmpty() || isEmailValido(texto);
                        break;
                    /*case CPF:
                        valido = texto.isEmpty() || isCPFValido(texto);
                        break;*/
                    case DATA:
                        valido = texto.isEmpty() || isDataValida(texto);
                        break;
                    case NUMERICO:
                        valido = texto.isEmpty() || PADRAO_NUMERICO.matcher(texto).matches();
                        break;
                    case ALFABETICO:
                        valido = texto.isEmpty() || PADRAO_ALFABETICO.matcher(texto).matches();
                        break;
                    case ALFANUMERICO:
                        valido = texto.isEmpty() || PADRAO_ALFANUMERICO.matcher(texto).matches();
                        break;
                    case REQUERIDO:
                        valido = !texto.isEmpty();
                        break;
                }

                // Mudar a cor de fundo do campo
                if (valido) {
                    campoTexto.setBackground(Color.WHITE);
                } else {
                    campoTexto.setBackground(new Color(255, 220, 220)); // Rosa claro
                }
            }
        });
    }

    /**
     * Tipos de validação
     */
    public enum TipoValidacao {
        EMAIL,
        CPF,
        DATA,
        NUMERICO,
        ALFABETICO,
        ALFANUMERICO,
        REQUERIDO
    }

    /**
     * Adiciona validação a um campo de texto
     * @param campoTexto O campo de texto para validar
     * @param tipoValidacao O tipo de validação a aplicar
     * @param mensagemErro A mensagem de erro a exibir se a validação falhar
     */
    public static void adicionarValidacao(JTextField campoTexto, TipoValidacao tipoValidacao, String mensagemErro) {
        campoTexto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String texto = campoTexto.getText().trim();
                boolean valido = true;

                switch (tipoValidacao) {
                    case EMAIL:
                        valido = texto.isEmpty() || isEmailValido(texto);
                        break;
                    /*case CPF:
                        valido = texto.isEmpty() || isCPFValido(texto);
                        break;*/
                    case DATA:
                        valido = texto.isEmpty() || isDataValida(texto);
                        break;
                    case NUMERICO:
                        valido = texto.isEmpty() || PADRAO_NUMERICO.matcher(texto).matches();
                        break;
                    case ALFABETICO:
                        valido = texto.isEmpty() || PADRAO_ALFABETICO.matcher(texto).matches();
                        break;
                    case ALFANUMERICO:
                        valido = texto.isEmpty() || PADRAO_ALFANUMERICO.matcher(texto).matches();
                        break;
                    case REQUERIDO:
                        valido = !texto.isEmpty();
                        break;
                }

                // Apenas muda a cor de fundo, sem mostrar mensagem de erro
                if (valido) {
                    campoTexto.setBackground(Color.WHITE);
                } else {
                    campoTexto.setBackground(new Color(255, 220, 220)); // Rosa claro
                }
            }
        });
        
        // Adiciona também o feedback visual em tempo real
        adicionarFeedbackVisual(campoTexto, tipoValidacao);
    }

    /**
     * Adiciona validação personalizada a um campo de texto
     * @param campoTexto O campo de texto para validar
     * @param padrao O padrão (regex) para validar a entrada
     * @param mensagemErro A mensagem de erro a exibir se a validação falhar
     */
    public static void adicionarValidacaoPersonalizada(JTextField campoTexto, Pattern padrao, String mensagemErro) {
        campoTexto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String texto = campoTexto.getText().trim();
                boolean valido = texto.isEmpty() || padrao.matcher(texto).matches();

                // Apenas muda a cor de fundo, sem mostrar mensagem de erro
                if (valido) {
                    campoTexto.setBackground(Color.WHITE);
                } else {
                    campoTexto.setBackground(new Color(255, 220, 220)); // Rosa claro
                }
            }
        });
        
        // Adiciona também o feedback visual em tempo real
        campoTexto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validar();
            }

            private void validar() {
                String texto = campoTexto.getText().trim();
                boolean valido = texto.isEmpty() || padrao.matcher(texto).matches();

                // Mudar a cor de fundo do campo
                if (valido) {
                    campoTexto.setBackground(Color.WHITE);
                } else {
                    campoTexto.setBackground(new Color(255, 220, 220)); // Rosa claro
                }
            }
        });
    }

    /**
     * Adiciona validação personalizada a uma área de texto
     * @param areaTexto A área de texto para validar
     * @param tipoValidacao O tipo de validação a aplicar
     * @param mensagemErro A mensagem de erro a exibir se a validação falhar
     */
    public static void adicionarValidacaoPersonalizada(JTextArea areaTexto, TipoValidacao tipoValidacao, String mensagemErro) {
        areaTexto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String texto = areaTexto.getText().trim();
                boolean valido = true;

                switch (tipoValidacao) {
                    case REQUERIDO:
                        valido = !texto.isEmpty();
                        break;
                    default:
                        // Outros tipos de validação não se aplicam a áreas de texto
                        break;
                }

                // Apenas muda a cor de fundo, sem mostrar mensagem de erro
                if (valido) {
                    areaTexto.setBackground(Color.WHITE);
                } else {
                    areaTexto.setBackground(new Color(255, 220, 220)); // Rosa claro
                }
            }
        });
        
        // Adiciona também o feedback visual em tempo real
        areaTexto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validar();
            }

            private void validar() {
                String texto = areaTexto.getText().trim();
                boolean valido = true;

                switch (tipoValidacao) {
                    case REQUERIDO:
                        valido = !texto.isEmpty();
                        break;
                    default:
                        // Outros tipos de validação não se aplicam a áreas de texto
                        break;
                }

                // Mudar a cor de fundo do campo
                if (valido) {
                    areaTexto.setBackground(Color.WHITE);
                } else {
                    areaTexto.setBackground(new Color(255, 220, 220)); // Rosa claro
                }
            }
        });
    }
}