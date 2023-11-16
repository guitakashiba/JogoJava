import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public abstract class Objeto {
    protected int x, y;
    protected int largura, altura;

    // Adicionamos dois novos parâmetros: largura e altura.
    public Objeto(int x, int y, int largura, int altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public abstract void draw(Graphics g);
    
    // Este método agora é necessário para lidar com colisões em Guerreiro e ObjetoFixo.
    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }

    // Métodos getters para largura e altura.
    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    // Métodos getters para x e y.
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
