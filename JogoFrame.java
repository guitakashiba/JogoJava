import javax.swing.*;

public class JogoFrame extends JFrame {
    public JogoFrame() {
        setTitle("Jogo POO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 768); // Tamanho da tela conforme especificado
        
        // Cria o painel do jogo e o adiciona ao frame
        JogoPanel jogoPanel = new JogoPanel();
        add(jogoPanel);
        
        // Define que a janela não pode ser redimensionada
        setResizable(false);
        
        // Centraliza a janela na tela
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        // Cria o frame na Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JogoFrame frame = new JogoFrame();
            frame.setVisible(true);
        });
    }
}
