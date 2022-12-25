package space.invaders;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.jar.Manifest;

import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Jogo extends Canvas {
    private Stage mainStage;
    private Pane root;
    private Canvas mainCanvas;
    private GraphicsContext gc;
    private Image fundo;

    // cria todas as entidades do jogo
    private Jogador jogador;
    private MatrizInvasores invasores;
    private ListaMisseis misseis;

    // variaveis de controle
    private int vidas;
    private String vidaTotal = "Vidas: " + vidas;
    private int pontos = 0;
    private String pontuacao = "Pontos: " + pontos;

    // variaveis de jogabilidade
    private long tempoCooldown = 300; // tempo de cooldown entre tiros
    private long tempoUltimoTiro = 0; // tempo do ultimo tiro
    private String mensagem = ""; // mensagem enquanto o jogador nao aperta alguma tecla

    // variaveis de controle de teclas
    private boolean teclaEsq;
    private boolean teclaDir;
    private boolean teclaEspaco;
    private boolean aguardando = true; // aguarda o jogador apertar alguma tecla
    
    Jogo(Stage secondaryStage){
        mainStage = secondaryStage;
        root = new Pane();
        root.setPrefSize(500, 600);
        mainCanvas = new Canvas(500, 600);
		
        gc = mainCanvas.getGraphicsContext2D();

        // declara entidades
        jogador = new Jogador();
        invasores = new MatrizInvasores();
        misseis = new ListaMisseis();

        vidas = jogador.getVida();
        vidaTotal = "Vidas: " + vidas;
        pontos = jogador.getPontos();
        pontuacao = "Pontos: " + pontos;

        fundo = new Image(getClass().getResourceAsStream("imagens/space.png"));
        // configura o fundo do jogo
        gc.drawImage(fundo, 0, 0, 500, 600);
        
        // configura o titulo do jogo
        gc.setFill( Color.LIGHTGRAY );
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(javafx.scene.text.Font.font("Segoe UI Semibold", 20));
        gc.fillText(pontuacao, 30, 30);
        gc.fillText(vidaTotal, 400, 30);

        // desenha as entidades
        jogador.desenha(gc);
        invasores.desenha(gc);
        misseis.desenha(gc);
        
        root.getChildren().add(mainCanvas);
        Scene scene = new Scene(root);
        secondaryStage.setScene(scene);
        secondaryStage.show();
    }

    public void start() {
        while(vidas > 0 && !invasores.invasoresDestruidos()){
            // atualiza o jogo
            gc.clearRect(0, 0, 500, 600);
            gc.drawImage(fundo, 0, 0, 500, 600);

            misseis.movimentacao();
            
            // confere danos
            jogador = misseis.tiroInimigo(jogador);
            invasores = misseis.tiroJogador(invasores);

            // atualiza cabecalho
            vidas = jogador.getVida();
            vidaTotal = "Vidas: " + vidas;
            pontos = jogador.getPontos();
            pontuacao = "Pontos: " + pontos;

            // movimento inimigos
            invasores.move();
            if (invasores.invasoresChegaram()){
                vidas = 0;
            }

            // ataque inimigo
            misseis = invasores.AtacarRandom(misseis);

            // movimento jogador
            if (teclaEsq){
                jogador.moveCanhao(false);
            }
            if (teclaDir){
                jogador.moveCanhao(true);
            }
            if (teclaEspaco){
                if (System.currentTimeMillis() - tempoUltimoTiro > tempoCooldown){
                    misseis = jogador.ataqueJogador(misseis);
                    tempoUltimoTiro = System.currentTimeMillis();
                }
            }

            // configura o titulo do jogo
            gc.setFill( Color.LIGHTGRAY );
            gc.setTextAlign(TextAlignment.LEFT);
            gc.setFont(javafx.scene.text.Font.font("Segoe UI Semibold", 20));
            gc.fillText(pontuacao, 30, 30);
            gc.fillText(vidaTotal, 400, 30);

            // desenha as entidades
            jogador.desenha(gc);
            invasores.desenha(gc);
            misseis.desenha(gc);
            
            root.getChildren().add(mainCanvas);
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        }
    }
    
    private class entradaTeclado extends KeyAdapter {		
		public void keyPressed(KeyEvent e) {
			if (aguardando) {
				return;
			}
			
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT: {
					teclaEsq = true;
					break;
				}
				
				case KeyEvent.VK_RIGHT: {
					teclaDir = true;
					break;
				}
				
				case KeyEvent.VK_SPACE: {
					teclaEspaco = true;
					break;
				}			
			}
            if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}
    /*
    private static Runnable atualizaJogador = new Runnable() {
        public void run() {
        }
    };
    private static Runnable atualizaInvasor = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() - tempoUltimoTiro > tempoCooldown){
                misseis.add(invasores.atira());
                tempoUltimoTiro = System.currentTimeMillis();
            }
            invasores.move();
        }
    };
    private static Runnable atualizaTiros = new Runnable() {
        public void run() {
        }
    };*/
}
