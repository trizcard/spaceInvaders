package space.invaders;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public class Jogador {
    private List <Barreira> barreiras; // lista das 4 barreiras do jogador
    private Canhao canhao;
    
    /**
     * cria o Jogador (barreiras e canhao)
     */
    public Jogador(){
        barreiras = new ArrayList <> ();
        canhao = new Canhao();
        // cria as quatros barreiras
        for (int i = 0; i < 4; i++) {
            // barreira de largura 6 com espacamento entre elas de 3
            Barreira barreira = new Barreira(52+(112*i)); // cria as 4 barreiras
            barreiras.add(barreira);
        }
    }

    /**
     * desenha as barreiras e o canhao na tela
     * @param gc recebe o contexto grafico
     */
    public void desenha (GraphicsContext gc){
        canhao.desenha(gc);
        for (int i = 0; i < 4; i++){
            barreiras.get(i).desenha(gc);
        }
    }
    
    /**
     * @param direc indica a direcao do movimento, direita=true e esquerda=false
     */
    public void moveCanhao(boolean direc){
        canhao.MoverX(direc);
    }
    
    /**
     * @param tiro o tiro que ataca a barreira
     * @return 1 caso a barreira seja atingida
     */
    public int ataqueBarreira(Missil tiro){
        boolean acertou;
        for (int i = 0; i < 4; i++){
            acertou = barreiras.get(i).atacada(tiro);
            if(acertou){
                return 1;
            }
        }
        return 0;
    }
    
    /**
     * cria um tiro quando jogador decide disparar, adiciona ele na lista e
     * retorna a lista de misseis atualizada
     * @param lista a lista de tiros do jogador
     * @return a lista de misseis atualizada
     */
    public ListaMisseis ataqueJogador(ListaMisseis lista){
        Missil tiro;
        tiro = canhao.Atacar();
        lista.adicionaMissil(tiro, 0);
        return lista;
    }

    /**
     * @return quantas vidas o jogador tem
     */
    public int getVida(){
        return canhao.getVida();
    }
    
    /**
     * @return quantos pontos o jogador fez
     */
    public int getPontos(){
        return canhao.getPontos();
    }
    
    /**
     * @return o canhao do jogador
     */
    public Canhao getCanhao(){
        return canhao;
    }
    
    /**
     * @return a lista de barreiras
     */
    public List <Barreira> getBarreiras(){
        return barreiras;
    }
}