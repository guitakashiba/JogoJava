import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JogoPanel extends JPanel implements ActionListener {

    private List<Objeto> objetosFixos;
    private List<Guerreiro> guerreiros;
    private Timer timer;

    private boolean initialized = false;

    public JogoPanel() {
        objetosFixos = new ArrayList<>();
        guerreiros = new ArrayList<>();
        timer = new Timer(10, this);

        // Configura um listener para o evento de adição ao contêiner
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if (!initialized) {
                    inicializarObjetosFixos();
                    inicializarGuerreiros();
                    initialized = true;
                    timer.start();
                }
            }
        });

        // Configura um timer para atirar projéteis
        Timer projeteisTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atirarProjeteis();
            }
        });
        projeteisTimer.start();
    }

    private void inicializarObjetosFixos() {
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int larguraObjetoFixo = 50;
            int alturaObjetoFixo = 50;

            int x = random.nextInt(getWidth() - larguraObjetoFixo);
            int y = random.nextInt(getHeight() - alturaObjetoFixo);

            ObjetoFixo objetoFixo = new ObjetoFixo(x, y, larguraObjetoFixo, alturaObjetoFixo);
            adicionarObjetoFixo(objetoFixo);
        }
    }

    private void inicializarGuerreiros() {
        Random random = new Random();

        for (int i = 0; i < 15; i++) {
            int larguraGuerreiro = 20;
            int alturaGuerreiro = 20;

            int x = random.nextInt(getWidth() - larguraGuerreiro);
            int y = random.nextInt(getHeight() - alturaGuerreiro);

            Guerreiro guerreiro = new Guerreiro(x, y, 5, 10, larguraGuerreiro, alturaGuerreiro);
            guerreiros.add(guerreiro);
        }
    }

    public void adicionarObjetoFixo(Objeto objeto) {
        objetosFixos.add(objeto);
    }

    private void verificarColisoes() {
        for (Guerreiro guerreiro : guerreiros) {
            // Verifica colisão com bordas do painel
            Rectangle bounds = guerreiro.getBounds();
            if (bounds.x < 0 || bounds.y < 0 || bounds.x > getWidth() - bounds.width || bounds.y > getHeight() - bounds.height) {
                guerreiro.perderEnergia(5);
                guerreiro.escolherNovaDirecao();
            }

            // Verifica colisão com objetos fixos
            for (Objeto objeto : objetosFixos) {
                if (objeto instanceof ObjetoFixo) {
                    ObjetoFixo fixo = (ObjetoFixo) objeto;
                    if (bounds.intersects(fixo.getBounds())) {
                        guerreiro.perderEnergia(5);
                        guerreiro.escolherNovaDirecao();
                    }
                }
            }
        }
    }

    private void atirarProjeteis() {
        for (Guerreiro guerreiro : guerreiros) {
            guerreiro.atirar();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenha objetos fixos
        for (Objeto objeto : objetosFixos) {
            objeto.draw(g);
        }
        // Desenha guerreiros e a energia
        for (Guerreiro guerreiro : guerreiros) {
            guerreiro.draw(g);
            // Desenha a energia do guerreiro
            g.setColor(Color.WHITE);
            g.drawString("Energia: " + guerreiro.getEnergia(), guerreiro.getX(), guerreiro.getY() - 5);
        }
        verificarColisoes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        atualizarGuerreiros();
        verificarColisoes(); // Verifique as colisões após cada movimento
        repaint();
    }

    private void atualizarGuerreiros() {
        for (Guerreiro guerreiro : guerreiros) {
            guerreiro.mover();
        }
    }
}
