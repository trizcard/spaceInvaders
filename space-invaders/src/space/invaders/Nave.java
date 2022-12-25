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
     * desenha a nave na tela
     * @param gc o contexto grafico
     * @return o contexto grafico com a nave desenhada
     */
    public void desenha(GraphicsContext gc) {
        if (atacado){return;}
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
    
    public int Atacado(Missil tiro) {
        return 0;
    }
    
    public void MoverX(boolean direc) {

    }

}
