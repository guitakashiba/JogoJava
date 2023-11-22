import java.awt.Color;
import java.awt.Graphics;

public class Projetil extends Objeto {
    private Direcao direcao;
    private static final int VELOCIDADE_PROJETIL = 5;

    public Projetil(int x, int y, int largura, int altura, Direcao direcao) {
        super(x, y, largura, altura);
        this.direcao = direcao;
    }

    public Direcao getDirecao() {
        return direcao;
    }

    public void moverParaCima() {
        y -= VELOCIDADE_PROJETIL;
    }

    public void moverParaBaixo() {
        y += VELOCIDADE_PROJETIL;
    }

    public void moverParaEsquerda() {
        x -= VELOCIDADE_PROJETIL;
    }

    public void moverParaDireita() {
        x += VELOCIDADE_PROJETIL;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, largura, altura);
    }

    public void mover() {
        switch (direcao) {
            case UP:
                moverParaCima();
                break;
            case DOWN:
                moverParaBaixo();
                break;
            case LEFT:
                moverParaEsquerda();
                break;
            case RIGHT:
                moverParaDireita();
                break;
        }
    }

}
