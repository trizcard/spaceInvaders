package space.invaders;

import javafx.scene.image.Image;

public class Canhao extends Nave{
    private int vida;
    private int pontos;

    /**
     * cria o canhao na posiçao x = 100 e y = 0 com 3 vidas e 0 pontos
     */
    public Canhao() {
        this.x = 100; // de 0 a 500, inicia em 20%
        this.vida = 3;
        this.y = 582; // fica preso no final da tela
        this.pontos = 0;
        this.atacado = false;
        this.tamanho = 36;
        this.imagem = new Image(getClass().getResourceAsStream("imagens/canhao.png"));
    }
    
    /**
     * reinicia o canhao na sua posicao inicial
     */
    public void reinicia() {
        this.atacado = false;
        this.x = 100;
    }

    @Override
    /**
     * move o canhao caso ele nao esteja no limite da tela
     * @param direc mover para a direita true e mover para esquerda false
     */
    public void MoverX(boolean direc) {
        if (direc) {
            if (this.x < 499) {
                this.x += 10;
            }
        } else {
            if (this.x > 0) {
                this.x -= 10;
            }
        }
    }

    @Override
    /**
     * @return se o canhao foi atingido ou nao
     */
    public int Atacado(Missil tiro) {
        int x = tiro.getCoordX();
        int y = tiro.getCoordY();
        
        if ((this.x <= x) && (this.x + this.tamanho > x) && (this.y <= y) && (this.y + this.tamanho > y)) {
            vida -= 1;
            atacado = true;
            return 1;
        }
        return 0;
    }
    
    /**
     * adiciona mais pontos ao canhao
     * @param pontos pontos a serem adicionados
     */
    public void somaPontos(int pontos) {
        this.pontos += pontos;
    }
    
    /**
     * @return os pontos do jogador
     */
    public int getPontos(){
        return pontos;
    }
    
    /**
     * @return quantidade de vidas
     */
    public int getVida(){
        return vida;
    }
    
    /**
     * @return se o canhao foi atacado ou nao
     */
    public boolean getAtacado() {
        return this.atacado;
    }
}
