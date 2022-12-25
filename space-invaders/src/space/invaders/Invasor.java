/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders;

import javafx.scene.image.Image;

/**
 *
 * @author beatr
 */
public class Invasor extends Nave{
    // caracteristicas de nave
    protected int velocidade; // velocidade da nave

    /**
     * cria o invasor do tipo t na coordenada x e y
     * @param t tipo de invasor, pode ser 1, 2, 3 e 4
     * @param x valor da coordenada x
     * @param y valor da coordanada y
     */
    public Invasor(int t, int x, int y) {
        // caracteristicas espaciais
        this.x = x;
        this.y = y;

        // caracteristicas de nave
        this.atacado = false;
        this.tipo = t;
        this.tamanho = 30;
        this.velocidade = 10;
        switch (t) {
            case 1:
                this.imagem = new Image(getClass().getResourceAsStream("imagens/invasor1.png"));
                break;
            case 2:
                this.imagem = new Image(getClass().getResourceAsStream("imagens/invasor2.png"));
                break;
            case 3:
                this.imagem = new Image(getClass().getResourceAsStream("imagens/invasor3.png"));
                break;
            case 4:
                this.imagem = new Image(getClass().getResourceAsStream("imagens/ufo.png"));
                break;
        }
    }
    
    /**
     * @return true se o invasor esta vivo, false se nao esta
     */
    public boolean getVivo(){
        return !(this.atacado);
    }

    /**
     * move o invasor em y
     */
    public void MoverY() {
        if (this.tipo != 4) { // o invasor 4 nao se move em y
            this.y += 20;
        }
    }

    @Override
    /**
     * move o invasor em x, sendo direc a direçao do movimento true = direita
     * e false = esquerda
     * @param direc direçao do movimento
     */
    public void MoverX(boolean direc) {
        if (this.tipo == 4){ // nave especial muda a direcao
            direc = !direc;
        }
        if (direc) {
            this.x += (velocidade);
        } else {
            this.x -= (velocidade);
        }
    }

    /**
     * muda a velocidade de movimento do invasor
     */
    public void mudarVelocidade(){
        this.velocidade += 2;
    }

    @Override
    /**
     * verifica se o invasor foi atacado pelo tiro, caso acerte, retorna os pontos
     * e altera o status de atacado do invasor
     * @param tiro tiro que esta atacando o invasor
     * @return os pontos daquele ataque
     */
    public int Atacado(Missil tiro) {
        int x = tiro.getCoordX();
        int y = tiro.getCoordY();
        
        if (this.atacado == true){return 0;}
        
        if ((this.x <= x) && (x < (this.x + this.tamanho)) && ((this.y - this.tamanho/2) <= y) && (y < (this.y + this.tamanho/2))) {
            this.atacado = true;
            return (this.tipo * 10);
        }
        return 0;
    }
}
