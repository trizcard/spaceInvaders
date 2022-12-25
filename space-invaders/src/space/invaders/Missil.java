/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author beatr
 */
public class Missil {
    private int tipo; // indica se eh canhao ou qual invasor eh

    // posicoes de missil
    private int coordX;
    private int coordY;
    private int largura;

    // imagem
    private Image imagem;

    /**
     * cria o tiro na frente daquele que fez o disparo
     * @param x coordenada em x
     * @param y coordenada em y
     * @param t tipo da nave que disparou
     */
    public Missil(int x, int y, int t) {
        this.coordX = x;
        this.largura = 10;

        // verifica a direcao do tiro em y
        if (t == 0) {
            this.coordY = y;
        } else {
            this.coordY = y;
        }
        
        
        this.imagem = new Image(getClass().getResourceAsStream("imagens/missil.png"));

        this.tipo = t;
    }

    /**
     * desenha o tiro na tela
     * @param gc recebe o contexto grafico
     * @return o contexto grafico com o tiro desenhado
     */
    public void desenha(GraphicsContext gc) {
        gc.drawImage(this.imagem, this.coordX - (this.largura/2), this.coordY - (this.largura/2), this.largura, this.largura);
    }
    
    /**
     * move o tiro para frente ou para tras, dependendo do tipo
     */
    public void Mover() {
        if (this.tipo != 0) {
            this.coordY++;
        } else {
            this.coordY--;
        }
    }

    /**
     * @return a coordenada em x do tiro 
     */
    public int getCoordX() {
        return this.coordX;
    }

    /**
     * @return a coordanada em y do tiro 
     */
    public int getCoordY() {
        return this.coordY;
    }
}
