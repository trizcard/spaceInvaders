package space.invaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Nave {
    // atributos
    protected int tipo; // indica se e canhao (0) ou invasor (1, 2, 3 ou 4)
    // tipo 1: 10 pontos
    // tipo 2: 20 pontos
    // tipo 3: 30 pontos
    // tipo 4: 40 pontos (nave que aparece em cima e se move opostamente)
    protected int x;
    protected int y;
    protected int tamanho;
    protected Image imagem;
    protected boolean atacado;

    /**
     * a nave ataca, criando um missil
     * @return o missil na coordenada da nave
     */
    public Missil Atacar() {
        //cria um missil na posicao da nave
        Missil tiro = new Missil(this.x, this.y, this.tipo);
        return tiro;
    }
    
    /**
     * desenha a nave na tela, por meio do contexto grafico
     * @param gc recebe o contexto grafico
     */
    public void desenha(GraphicsContext gc) {
        if (atacado){return;} // se foi atacado nao desenha a nave
        gc.drawImage(this.imagem, (this.x), (this.y - (tamanho/2)), this.tamanho, this.tamanho);
    }

    /** 
     * @return a coordenada em x da nave
     */
    public int getCoordX() {
        return this.x;
    }

    /**
     * @return a coordenada em y da nave
     */
    public int getCoordY() {
        return this.y;
    }

    /**
     * @return o tipo da nave (canhao ou o tipo de invasor) 
     */
    public int getTipo() {
        return this.tipo;
    }
    
    /**
     * recebe o missil e retorna se foi atacado ou nao, para invasores, retorna a quantidade de pontos
     * @param tiro o tiro qu esta atacando a nave
     * @return um inteiro que corresponde a 1 ou 0 (para barreira e canhao) ou a quantidade de pontos (para invasores)
     */
    public int Atacado(Missil tiro) {
        return 0;
    }
    
    /**
     * move a nave no eixo x
     * @param direc true corresponde a direita e false a esquerda
     */
    public void MoverX(boolean direc) {

    }

}
