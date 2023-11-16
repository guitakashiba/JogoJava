import java.awt.*;

public class Projetil extends Objeto {
    private static final int VELOCIDADE = 5; // Defina a velocidade desejada

    public Projetil(int x, int y, int largura, int altura) {
        super(x, y, largura, altura);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, largura, altura);
    }

    public void mover() {
        y -= VELOCIDADE;
    }
}
