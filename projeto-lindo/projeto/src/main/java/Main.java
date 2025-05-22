public class Main {
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            view.MainFrame frame = new view.MainFrame();

            view.SplashScreen splashScreen = new view.SplashScreen(frame);
            splashScreen.mostrarSplash();
            
            frame.setVisible(true);
        });
    }
}
