import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;


public class JogoPanel extends JPanel implements ActionListener {

    private List<Objeto> objetosFixos;
    private List<Guerreiro> guerreiros;
    private Timer timer;

    private boolean jogoEncerrado = false;
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

    /*private void verificarColisoes() {
        for (int i = 0; i < guerreiros.size(); i++) {
            Guerreiro guerreiroA = guerreiros.get(i);
            Rectangle boundsA = guerreiroA.getBounds();
    
            // Verifica colisão com bordas do painel
            if (boundsA.x < 0 || boundsA.y < 0 || boundsA.x > getWidth() - boundsA.width || boundsA.y > getHeight() - boundsA.height) {
                guerreiroA.perderEnergia(5);
                guerreiroA.escolherNovaDirecao();
            }
    
            // Verifica colisão com objetos fixos
            for (Objeto objeto : objetosFixos) {
                if (objeto instanceof ObjetoFixo) {
                    ObjetoFixo fixo = (ObjetoFixo) objeto;
                    if (boundsA.intersects(fixo.getBounds())) {
                        guerreiroA.perderEnergia(5);
                        guerreiroA.escolherNovaDirecao();
                    }
                }
            }
    
            // Verifica colisão com outros guerreiros
            for (int j = i + 1; j < guerreiros.size(); j++) {
                Guerreiro guerreiroB = guerreiros.get(j);
                Rectangle boundsB = guerreiroB.getBounds();
    
                if (boundsA.intersects(boundsB)) {
                    guerreiroA.escolherNovaDirecao();
                    guerreiroB.escolherNovaDirecao();
                }
            }
        }
    } */

    private void verificarColisoes() {
        Iterator<Guerreiro> iterator = guerreiros.iterator();
    
        while (iterator.hasNext()) {
            Guerreiro guerreiroA = iterator.next();
            Rectangle boundsA = guerreiroA.getBounds();
    
            // Verifica colisão com bordas do painel
            if (boundsA.x < 0 || boundsA.y < 0 || boundsA.x > getWidth() - boundsA.width || boundsA.y > getHeight() - boundsA.height) {
                guerreiroA.perderEnergia(5);
                guerreiroA.escolherNovaDirecao();
            }
    
            // Verifica colisão com objetos fixos
            for (Objeto objeto : objetosFixos) {
                if (objeto instanceof ObjetoFixo) {
                    ObjetoFixo fixo = (ObjetoFixo) objeto;
                    if (boundsA.intersects(fixo.getBounds())) {
                        guerreiroA.perderEnergia(5);
                        guerreiroA.escolherNovaDirecao();
                    }
                }
            }
    
            // Verifica colisão com outros guerreiros
            for (Guerreiro guerreiroB : guerreiros) {
                if (guerreiroA != guerreiroB) { // Certifica-se de não comparar um guerreiro com ele mesmo
                    Rectangle boundsB = guerreiroB.getBounds();
                    if (boundsA.intersects(boundsB)) {
                        guerreiroA.escolherNovaDirecao();
                        guerreiroB.escolherNovaDirecao();
                    }
                }
            }
    
            // Verifica se o guerreiro está morto
            if (guerreiroA.getEnergia() <= 0) {
                iterator.remove(); // Remove guerreiro morto da lista
            }

            // Verifica se não há guerreiros vivos
            if (guerreiros.isEmpty()) {
                jogoEncerrado = true;
            }
        }
    }
    
    private void exibirMensagemJogoEncerrado(Graphics g) {
        if (jogoEncerrado) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            String mensagem = "Jogo Encerrado";
            int larguraTexto = g.getFontMetrics().stringWidth(mensagem);
            int alturaTexto = g.getFontMetrics().getHeight();
            int x = (getWidth() - larguraTexto) / 2;
            int y = (getHeight() - alturaTexto) / 2;
            g.drawString(mensagem, x, y);
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
        exibirMensagemJogoEncerrado(g);
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
