public class Main {
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Criar o frame principal primeiro (mas não mostrar ainda)
            view.MainFrame frame = new view.MainFrame();
            
            // Mostrar a tela de splash primeiro
            view.SplashScreen splashScreen = new view.SplashScreen(frame);
            splashScreen.mostrarSplash();
            
            // Depois mostrar o frame principal
            frame.setVisible(true);
        });
    }
}
