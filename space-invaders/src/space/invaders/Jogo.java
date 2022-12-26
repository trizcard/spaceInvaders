package space.invaders;

import static java.lang.Thread.sleep;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
    private Thread tMisseis; // movimentaçao dos misseis
    private Thread tAtaqueInv; // ataque do invasor
    private Thread tInvasor4; // movimento do ufo/invasor tipo 4
    private Thread tInvasores; // movimento de todos os invasores
    
    // animacao do jogo
    private AnimationTimer tAnimacao;
    
    // cria todas as entidades do jogo
    private Jogador jogador;
    private MatrizInvasores invasores;
    private ListaMisseis misseis;

    // variaveis de controle
    private int nivel; // nivel de dificuldade
    private long tmpTiroJog; // tempo desde o ultimo tiro do jogador
    private long tmpTiroInv; // tempo desde o ultimo tiro do invasor
    
    // variaveis de status
    private int vidas; // quantidade total de vidas
    private String vidaTotal = "Vidas: " + vidas;
    private int pontos; // quantidade total de pontos
    private String pontuacao = "Pontos: " + pontos;

    private int ptVelocidade; // pontos conquistados por terminar o jogo rapidamente
    // (inicia em 5000 e perde um ponto em toda repetiçao da animaçao
    
    private Image fundo = new Image(getClass().getResourceAsStream("imagens/space.png"));
    
    /**
     * O desenvolvimento do jogo
     * @param nivel o nivel de dificuldade escolhido pelo jogador
     */
    public Jogo(int nivel){
        // seta o nivel e o ponto inicial de velocidade
        this.nivel = nivel; 
        ptVelocidade = 5000;
        
        // inicializa entidades
        jogador = new Jogador();
        invasores = new MatrizInvasores();
        misseis = new ListaMisseis();
        
        // inicializa a parte grafica
        jStage = new Stage();
        
        // inicializa o jogo
        start();
        
        // inicializa a leitura do teclado p/ realizar o movimento do jogador
        movimentoJogador();
        
        // cria todas as threads
        criaThreads();
        
        // declara as variaveis de status
        vidas = jogador.getVida();
        vidaTotal = "Vidas: " + vidas;
        pontos = jogador.getPontos();
        pontuacao = "Pontos: " + pontos;
    }

    /**
     * inicializa o jogo, criando a tela
     */
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
    
    /**
     * realiza a leitura do teclado para saber os movimentos do jogador
     * a/A -> move o canhao para a esquerda
     * d/D -> move o canhao para a direita
     * espaço -> caso o tempo de cooldown tenha passado (200ms desde o ultimo tiro), o canhao atira
     * escape/esc -> fecha o jogo
     * enter -> reinicia o jogo
     */
    private void movimentoJogador() {		
	jScene.setOnKeyTyped((KeyEvent e) -> {
            String c = e.getCharacter();
            switch (c) {
                case "a":
                case "A":
                    jogador.moveCanhao(false);
                    break;
                case "d":
                case "D":
                    jogador.moveCanhao(true);
                    break;
            }
        });

        jScene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.SPACE && (System.currentTimeMillis() - tmpTiroJog) > 200 + nivel*100) {
                misseis = jogador.ataqueJogador(misseis);
                tmpTiroJog = System.currentTimeMillis();
            }
            else if (event.getCode() == KeyCode.ESCAPE){
                System.exit(0);
                jStage.close();
            }
            else if (event.getCode() == KeyCode.ENTER){
                jStage.close();
                Jogo j = new Jogo(this.nivel);
            }
        });
    }
    
    /**
     * cria as 4 threads e a animaçao, sendo elas:
     * tMisseis -> movimenta os misseis
     * tInvasores -> movimenta todos os invasores, exceto o ufo
     * tInvasor4 -> movimenta o ufo
     * tAtaqueInv -> coordena o ataque dos invasores
     * tAnimacao -> realiza o loop do jogo de desenhar na tela
     */
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

        this.tInvasores = new Thread(() -> {
            while (jogador.getVida() > 0 && !invasores.invasoresChegaram() && !invasores.invasoresDestruidos()) {
                try {
                    // delay para mover os invasores, o delay diminui conforme a quantidade de invasores diminui
                    // o delay eh 50 ms menor para o nivel dificil
                    sleep(300 + (invasores.quantidade()/56) * 20 - nivel*100);
                } catch (InterruptedException ex) {
                    System.out.println("Erro!");
                }
                invasores.move();
            }
        });

        this.tInvasor4 = new Thread(() -> {
            while (jogador.getVida() > 0 && !invasores.invasoresChegaram() && !invasores.invasoresDestruidos()) {
                try {
                    // delay fixo para mover o ufo
                    sleep(225);
                } catch (InterruptedException ex) {
                    System.out.println("Erro!");
                }
                invasores.moveI4();
            }
        });
        
        this.tAtaqueInv = new Thread(() -> {
            while (jogador.getVida() > 0 && !invasores.invasoresChegaram() && !invasores.invasoresDestruidos()) {
                // aguarda o tempo de cooldown entre os disparos dos invasores
                // para o nivel facil cooldown = 900, para o nivel dificil cooldown = 800
                while (System.currentTimeMillis() - tmpTiroInv < 900 - nivel*200){
                }
                tmpTiroInv = System.currentTimeMillis();
                misseis = invasores.AtacarRandom(misseis);
            }
        });

        tAnimacao = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // "limpar" tela
                gc.drawImage(fundo, 0, 0, 500, 600);
                
                // verificar se algum invasor/jogador foi atingido
                jogador = misseis.tiroInimigo(jogador);
                invasores = misseis.tiroJogador(invasores);
                
                // resgata os pontos dos invasores atingidos
                jogador.getCanhao().somaPontos(misseis.resgataPontos());
                
                // atualiza vida e pontos 
                vidas = jogador.getVida();
                vidaTotal = "Vidas: " + vidas;
                pontos = jogador.getPontos();
                pontuacao = "Pontos: " + pontos;
                
                // caso o jogo tenha acabado
                if (jogador.getVida() == 0 || invasores.invasoresChegaram() || invasores.invasoresDestruidos()) {
                    tAnimacao.stop();
                    
                    // desenha tela de fim
                    gc.drawImage(fundo, 0, 0, 500, 600);
                    gc.setFill( Color.WHITE );
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.setFont(javafx.scene.text.Font.font("Courier New", 50));
                    
                    if (invasores.invasoresDestruidos()){
                        // pontuacao final eh maior se voce termina o jogo mais rapido ou se esta no nivel dificil
                        pontos += ptVelocidade + nivel*500;
                        pontuacao = "Pontos: " + pontos;                        
                        gc.fillText("VOCÊ GANHOU!\n" + pontuacao, 250, 300);
                    }
                    else{
                        // pontuaçao maior para nivel mais dificil
                        pontos += nivel*500;
                        pontuacao = "Pontos: " + pontos;  
                        gc.fillText("VOCÊ PERDEU :c\n" + pontuacao, 250, 300);
                    }
                }
                // caso nao tenha acabado, atualiza a interface
                else{
                    atualizaInterface();
                }
                
                // perde ponto de velocidade toda vez que o loop repete
                ptVelocidade--;
            }
        };
        
        tMisseis.start();
        tAtaqueInv.start();
        tInvasores.start();
        tInvasor4.start();
        tAnimacao.start();
    }
    
    /**
     * atualiza toda a interface do jogo a cada loop de movimentaçao
     */
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