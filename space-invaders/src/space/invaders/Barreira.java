package space.invaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Barreira {
    // a barreira tem a area de altura x (fim - começo) 
    private int comeco; 
    private int largura;
    private int altura;
    private Image imagem;
    
    private boolean[][] existe; // verifica se barreira existe no ponto

    /**
     * Cria a barreira com esse parametros de inicio e fim com todas as suas
     * posiçoes iguais a verdadeiro (true)
     * @param c começo
     */
    public Barreira(int c) {
        this.comeco = c;
        this.largura = 60; // largura da barreira, eh destruida de 10 em 10
        this.altura = 40; // altura da barreira, eh destruida de 10 em 10
        this.existe = new boolean[6][4];
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 4; j++){
                this.existe[i][j] = true;
            }
        }
        this.imagem = new Image(getClass().getResourceAsStream("imagens/barreira.png"));
    }
    
    /**
     * Desenha a barreira na tela
     * @param gc recebe o contexto grafico
     */
    public void desenha(GraphicsContext gc){
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 4; j++){
                if (this.existe[i][j]){
                    gc.drawImage(this.imagem, (this.comeco + (i*10)), (514 + (j*10)), 10, 10);
                }
            }
        }
    }
    
    /**
     * compara se o tiro acertou a barreira, caso sim, a barreira deixa de existir
     * naquele ponto, retornando true e, caso nao, a funçao apenas retorna false
     * @param tiro o missil de ataque
     * @return se o ataque teve sucesso ou nao
     */
    public boolean atacada(Missil tiro) {
        int x = tiro.getCoordX();
        int y = tiro.getCoordY();

        int localRelativoX = x - this.comeco;
        int localRelativoY = y - 514;
        
        // se o tiro esta antes da barreira retorna falso
        if (localRelativoY < 0 || localRelativoX < 0) {return false;}
        
        // se o tiro esta depois da barreira retorna falso
        if (localRelativoX >= this.largura || localRelativoY >= this.altura) {return false;}

        double parteX = Math.floor(localRelativoX / 10);
        double parteY = Math.floor(localRelativoY / 10);
        
        // se a barreira ja foi apagada retorna falso
        if (this.existe[(int)parteX][(int)parteY] == false){return false;}
        
        this.existe[(int)parteX][(int)parteY] = false;
        return true;
    }
    
    /**
     * verifica se a barreira existe naquele ponto
     * @param x coordenada x
     * @param y coordenada y
     * @return se existe ou nao a barreira
     */
    public boolean existe(int x, int y){
        int localRelativoX = x - this.comeco;
        int localRelativoY = y - 514;
        if (localRelativoX > this.largura || localRelativoY > this.altura) {return false;}

        double parteX = Math.floor(localRelativoX / 10);
        double parteY = Math.floor(localRelativoY / 10);

        if (this.existe[(int)parteX][(int)parteY]){
            return true;
        }
        else{
            return false;
        }
    }
}
