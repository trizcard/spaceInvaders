package space.invaders;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import static javafx.application.Platform.exit;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Jogo {
    // parte grafica do jogo
    private Stage jStage;
    private Scene jScene;
    private Group jGrupo;
    private Canvas jCanvas;
    private GraphicsContext gc;
    
    // threads
    private Thread tMisseis;
    private Thread tAtaqueInv;
    private Thread tInvasor4;
    private Thread tInvasores;
    
    // animacao
    private AnimationTimer tAnimacao;
    
    // cria todas as entidades do jogo
    private Jogador jogador;
    private MatrizInvasores invasores;
    private ListaMisseis misseis;

    // variaveis de controle
    private int nivel;
    private long tmpTiroJog;
    private long tmpTiroInv;
    private int vidas;
    private String vidaTotal = "Vidas: " + vidas;
    private int pontos = 0;
    private int ptVelocidade;
    private String pontuacao = "Pontos: " + pontos;

    private Image fundo = new Image(getClass().getResourceAsStream("imagens/space.png"));
    
    public Jogo(int nivel){
        this.nivel = nivel;
        ptVelocidade = 5000;
        // inicializa entidades
        jogador = new Jogador();
        invasores = new MatrizInvasores();
        misseis = new ListaMisseis();
        
        jStage = new Stage();
        start();
        movimentoJogador();
        criaThreads();
        
        // declara entidades
        vidas = jogador.getVida();
        vidaTotal = "Vidas: " + vidas;
        pontos = jogador.getPontos();
        pontuacao = "Pontos: " + pontos;
    }

    public void start() {
        jStage.setTitle ("Space Invader - Beatriz Cardoso");
        
        // cria grupo e adiciona na cena
        jGrupo = new Group();
        jScene = new Scene(jGrupo);
        jStage.setScene(jScene);
        
        jCanvas = new Canvas(500, 600);
        jGrupo.getChildren().add(jCanvas);
        gc = jCanvas.getGraphicsContext2D();

        jStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                System.exit(0);
            }
        });

        jStage.show();
    }
    
    private void movimentoJogador() {		
	jScene.setOnKeyTyped((KeyEvent e) -> {
            String c = e.getCharacter();
            switch (c) {
                case "a":
                case "A":
                    jogador.moveCanhao(false);
                    //System.out.print("esquerda");
                    break;
                case "d":
                case "D":
                    jogador.moveCanhao(true);
                    //System.out.print("direita");
                    break;
            }
        });

        jScene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.SPACE && (System.currentTimeMillis() - tmpTiroJog) > 200) {
                misseis = jogador.ataqueJogador(misseis);
                tmpTiroJog = System.currentTimeMillis();
            }
            else if (event.getCode() == KeyCode.ENTER){
                System.exit(0);
                jStage.close();
            }
        });
    }
    
    private void criaThreads() {

        this.tMisseis = new Thread(() -> {
            while (jogador.getVida() > 0 && !invasores.invasoresChegaram() && !invasores.invasoresDestruidos()) {
                try {
                    sleep(5);
                } catch (InterruptedException ex) {
                    System.out.println("Erro!");
                }
                misseis.movimentacao();
            }
        });
        
        this.tAtaqueInv = new Thread(() -> {
            while (jogador.getVida() > 0 && !invasores.invasoresChegaram() && !invasores.invasoresDestruidos()) {
                while (System.currentTimeMillis() - tmpTiroInv < 900 - nivel*100){
                }
                tmpTiroInv = System.currentTimeMillis();
                misseis = invasores.AtacarRandom(misseis);
            }
        });

        this.tInvasores = new Thread(() -> {
            while (jogador.getVida() > 0 && !invasores.invasoresChegaram() && !invasores.invasoresDestruidos()) {
                try {
                    sleep(300 + (invasores.quantidade()/56) * 20 - nivel*50);
                } catch (InterruptedException ex) {
                    System.out.println("Erro!");
                }
                invasores.move();
            }
        });

        this.tInvasor4 = new Thread(() -> {
            while (jogador.getVida() > 0 && !invasores.invasoresChegaram() && !invasores.invasoresDestruidos()) {
                try {
                    sleep(225);
                } catch (InterruptedException ex) {
                    System.out.println("Erro!");
                }
                invasores.moveI4();
            }
        });

        tAnimacao = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // "limpar" tela
                gc.drawImage(fundo, 0, 0, 500, 600);
                jogador = misseis.tiroInimigo(jogador);
                invasores = misseis.tiroJogador(invasores);
                jogador.getCanhao().somaPontos(misseis.resgataPontos());
                
                // atualiza vida e pontos 
                vidas = jogador.getVida();
                vidaTotal = "Vidas: " + vidas;
                pontos = jogador.getPontos();
                pontuacao = "Pontos: " + pontos;
                if (jogador.getVida() == 0 || invasores.invasoresChegaram() || invasores.invasoresDestruidos()) {
                    tAnimacao.stop();
                    gc.drawImage(fundo, 0, 0, 500, 600);
                    gc.setFill( Color.WHITE );
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.setFont(javafx.scene.text.Font.font("Courier New", 60));
                    // pontuacao final eh maior se voce termina o jogo mais rapido
                    if (invasores.invasoresDestruidos()){
                        pontos += ptVelocidade + nivel*500;
                        pontuacao = "Pontos: " + pontos;                        
                        gc.fillText("Voce ganhou!\n" + pontuacao, 250, 300);
                    }
                    else{
                        pontos += nivel*500;
                        pontuacao = "Pontos: " + pontos;  
                        gc.fillText("Game over\n" + pontuacao, 250, 300);
                    }
                }
                else{
                    atualizaInterface();
                }
                ptVelocidade--;
            }
        };
        tMisseis.start();
        tAtaqueInv.start();
        tInvasores.start();
        tInvasor4.start();
        tAnimacao.start();
    }
    
    private void atualizaInterface(){
        jogador.desenha(gc);
        invasores.desenha(gc);
        misseis.desenha(gc);
        gc.setFill( Color.LIGHTGRAY );
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(javafx.scene.text.Font.font("Segoe UI Semibold", 20));
        gc.fillText(pontuacao, 30, 30);
        gc.fillText(vidaTotal, 400, 30);
    }
}