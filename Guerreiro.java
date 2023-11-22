import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Guerreiro extends Objeto {

    private Direcao direcaoAtual;
    private static final int VELOCIDADE = 3;
    private Random random = new Random();
    private int energia = 100;
    private int resistencia; // Resistência do guerreiro
    private int ataque; // Capacidade de ataque do guerreiro
    private static int proximoId = 1;
    private int id;

    private List<Projetil> projeteis;

    public Guerreiro(int x, int y, int resistencia, int ataque, int largura, int altura) {
        super(x, y, largura, altura);
        this.id = proximoId++;
        this.resistencia = resistencia;
        this.ataque = ataque;
        escolherNovaDirecao();
        projeteis = new ArrayList<>();
    }

    public static void resetProximoId() {
        proximoId = 1;
    }

    public void escolherNovaDirecao() {
        Direcao[] direcoes = Direcao.values();
        direcaoAtual = direcoes[random.nextInt(direcoes.length)];
    }

    public void atirar() {
        Projetil projetil = new Projetil(x + largura / 2, y, 5, 5, direcaoAtual);
        projeteis.add(projetil);
    }
    
    private void moverProjeteis() {
        for (Projetil projetil : projeteis) {
            projetil.mover();
        }
    }
    

    private void desenharProjeteis(Graphics g) {
        for (Projetil projetil : projeteis) {
            projetil.draw(g);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, largura, altura);
        desenharProjeteis(g);
        desenharEnergia(g);
    }

    public void mover() {
        switch (direcaoAtual) {
            case UP:
                y -= VELOCIDADE;
                break;
            case DOWN:
                y += VELOCIDADE;
                break;
            case LEFT:
                x -= VELOCIDADE;
                break;
            case RIGHT:
                x += VELOCIDADE;
                break;
        }
        if (x < 0 || x + largura > 1200 || y < 0 || y + altura > 768) {
            escolherNovaDirecao();
        }
        moverProjeteis();
    }

    private void desenharEnergia(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Energia: " + energia, x, y - 10);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }

    public int getEnergia() {
        return energia;
    }

    public void perderEnergia(int dano) {
        energia -= dano;
        if (energia < 0) energia = 0; // Para garantir que a energia não seja negativa.
    }

    public void colidir(Guerreiro outro) {
        this.energia -= Math.max(outro.ataque - this.resistencia, 0);
        this.energia = Math.max(this.energia, 0); // Garante que a energia não fique negativa
    }

    public void colidirComObjetoFixo() {
        this.energia -= Math.max(1 - this.resistencia, 0);
        this.energia = Math.max(this.energia, 0); // Garante que a energia não fique negativa
    }

    public List<Projetil> getProjeteis() {
        return projeteis;
    }

    public int getId() {
        return id;
    }
}
