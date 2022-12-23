package space.invaders;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.image.BufferStrategy;


public class Jogo extends Canvas {
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
    private BufferStrategy bufferStrategy;	
    private long tempoCooldown = 300; // tempo de cooldown entre tiros
    private long tempoUltimoTiro = 0; // tempo do ultimo tiro
    private String mensagem = ""; // mensagem enquanto o jogador nao aperta alguma tecla

    // variaveis de controle de teclas
    private boolean teclaEsq;
    private boolean teclaDir;
    private boolean teclaEspaco;
    private boolean aguardando = true; // aguarda o jogador apertar alguma tecla
    
    Jogo(Stage secondaryStage){
        Pane root = new Pane();
        root.setPrefSize(500, 600);
        Canvas canvas = new Canvas();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // declara entidades
        jogador = new Jogador();
        invasores = new MatrizInvasores();
        misseis = new ListaMisseis();

        vidas = jogador.getVida();
        vidaTotal = "Vidas: " + vidas;
        pontos = jogador.getPontos();
        pontuacao = "Pontos: " + pontos;

        Image fundo = new Image(getClass().getResourceAsStream("imagens/space.png"));
        root.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.getWidth());
            canvas.setHeight(newValue.getHeight());
            
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
        });
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        secondaryStage.setScene(scene);
        secondaryStage.show();
    }

    public void start(Stage secondaryStage){
        
        addKeyListener(new entradaTeclado());
        
        while(vidas > 0){
            new Thread(atualizaJogador).start(); // roda movimentos da naves inimigas
            new Thread(atualizaInvasor).start(); // roda movimentos do canhao
            new Thread(atualizaTiros).start(); // roda tiros
        }
    }
    
    private class entradaTeclado extends KeyAdapter {
    
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                teclaEsq = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                teclaDir = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                teclaEspaco = true;
            }
        }
    }
    
    private static Runnable atualizaJogador = new Runnable() {
        public void run() {
        }
    };
    private static Runnable atualizaInvasor = new Runnable() {
        public void run() {
        }
    };
    private static Runnable atualizaTiros = new Runnable() {
        public void run() {
        }
    };
}
