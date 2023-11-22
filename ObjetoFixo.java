import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class ObjetoFixo extends Objeto {

    public ObjetoFixo(int x, int y, int largura, int altura) {
        super(x, y, largura, altura);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x, y, largura, altura);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }
}
