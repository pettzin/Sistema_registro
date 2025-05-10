package edu.br.cruzeirodosul;

import edu.br.cruzeirodosul.controller.AlunoController;
import edu.br.cruzeirodosul.controller.CursoController;
import edu.br.cruzeirodosul.controller.ProfessorController;
import edu.br.cruzeirodosul.controller.TurmaController;
import edu.br.cruzeirodosul.model.Registro;
import edu.br.cruzeirodosul.view.MainView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    
    // Códigos ANSI para cores (mantidos para compatibilidade com a versão console)
    private static final String RESET = "\033[0m";
    private static final String BLUE = "\033[0;34m";
    private static final String CYAN = "\033[0;36m";
    private static final String YELLOW = "\033[0;33m";
    private static final String GREEN = "\033[0;32m";
    private static final String PURPLE = "\033[0;35m";
    private static final String WHITE = "\033[0;37m";
    private static final String RED = "\033[0;31m";
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Configura o look and feel para Nimbus
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
                
                // Cria o modelo
                Registro registro = new Registro();
                
                // Cria a view principal
                MainView mainView = new MainView();
                
                // Cria os controladores
                AlunoController alunoController = new AlunoController(registro, mainView.getAlunoView());
                TurmaController turmaController = new TurmaController(registro, mainView.getTurmaView());
                CursoController cursoController = new CursoController(registro, mainView.getCursoView());
                ProfessorController professorController = new ProfessorController(registro, mainView.getProfessorView());
                
                // Configura os listeners da view principal
                mainView.addAlunoButtonListener(e -> mainView.showView("aluno"));
                mainView.addTurmaButtonListener(e -> mainView.showView("turma"));
                mainView.addCursoButtonListener(e -> mainView.showView("curso"));
                mainView.addMatriculaButtonListener(e -> mainView.showView("matricula"));
                mainView.addProfessorButtonListener(e -> mainView.showView("professor"));
                
                // Exibe a view principal
                mainView.setVisible(true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    // Métodos da versão console mantidos para compatibilidade
    public static void mostrarLogo() {
        // Código da logo mantido para compatibilidade
    }
    
    public static void limparConsole() {
        // Código para limpar console mantido para compatibilidade
    }
}