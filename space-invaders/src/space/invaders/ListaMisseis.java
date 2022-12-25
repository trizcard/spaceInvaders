package space.invaders;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class ListaMisseis {
    // lista de tiros
    private List <Missil> tirosInv; // lista de disparos do invasor
    private List <Missil> tirosJog; // lista de disparos do jogador
    private int pontosObtidos; // pontos obtidos com o acerto
    
    /**
     * Cria a lista de misseis
     */
    public ListaMisseis(){
        // lista de tiros
        tirosInv = new ArrayList<> ();
        tirosJog = new ArrayList<> ();
        pontosObtidos = 0;
    }
    
    public void desenha (GraphicsContext gc){
        int tam = tirosInv.size();
        for (int i = 0; i < tam; i++){
            tirosInv.get(i).desenha(gc);
        }
        tam = tirosJog.size();
        for (int i = 0; i < tam; i++){
            tirosJog.get(i).desenha(gc);
        }
    }

    /**
     * Recebe qual o tipo de lista de disparos esta sendo solicitada 0 para lista
     * de tiros do Jogador e qualquer valor para lista de tiros do Invasor
     * seleciona pelo index n passado para a funçao e retorna o tiro desse index
     * @param tipo invasor ou jogador
     * @param n index do tiro
     * @return Missil do tiro daquele index
     */
    public Missil retornaMissil(int tipo, int n){
        if (tipo != 0){
            return tirosInv.get(n);
        }
        else{
            return tirosJog.get(n);
        }
    }
    
    /**
     * Adiciona um missil na lista de Misseis
     * @param tiro disparo que vai ser adicionado
     * @param tipo indica a lista em que o tiro sera adicionado
     */
    public void adicionaMissil(Missil tiro, int tipo){
        if (tipo != 0){
            tirosInv.add(tiro);
        }
        else{
            tirosJog.add(tiro);
        }
    }
    
    /**
     * @param index o index do tiro
     * @param tipo 0 para jogador e qualquer valor para invasor
     * @return a posiçao do missil de acordo com a sua lista e index
     */
    public int[] retornaPosi(int index, int tipo){
        int [] posi = new int[2];
        if (tipo != 0){
            posi[0] = tirosInv.get(index).getCoordX();
            posi[1] = tirosInv.get(index).getCoordY();
        }
        else{
            posi[0] = tirosJog.get(index).getCoordX();
            posi[1] = tirosJog.get(index).getCoordY();
        }
        return posi;
    }
    
    /**
     * movimenta todos os misseis usando a funçao mover da classe Missil e depois
     * verifica se o missil ainda existe (ou seja, ainda esta na area da tela)
     */
    public void movimentacao(){
        int tam = tirosInv.size();
        for (int i = 0; i < tam; i++){
            tirosInv.get(i).Mover();
        }
        tam = tirosJog.size();
        for (int i = 0; i < tam; i++){
            tirosJog.get(i).Mover();
        }
        verificaMisseis();
    }
    
    /**
     * verifica se o missil ainda esta na area da tela e, caso nao esteja, remove
     * o missil da lista de misseis
     */
    public void verificaMisseis(){
        tirosInv.removeIf(Missil -> (Missil.getCoordY() < 0));
        tirosJog.removeIf(Missil -> (Missil.getCoordY() > 599));
    }
    
    /**
     * verifica o tamanho da lista de misseis
     * @param tipo o tipo da lista de tiro (jogador ou invasor)
     * @return o tamanho daquela lista de misseis
     */
    public int tamanhoLista(int tipo){
        if (tipo != 0){
            return tirosInv.size();
        }
        else{
            return tirosJog.size();
        }
    }
    
    /**
     * remove o tiro da lista por meio de seu index
     * @param tipo o tipo de tiro a ser removido
     * @param index o index do tiro a ser removido
     */
    public void removeIndex(int tipo, int index){
        if (tipo != 0){
            tirosInv.remove(index);
        }
        else{
            tirosJog.remove(index);
        }
    }
    
    /**
     * verifica se o jogador foi atingido pelo disparo do invasor, isso inclui
     * a barreira e o canhao
     * @param jogador a classe do jogador
     * @return jogador com as informacoes atualizadas
     */
    public Jogador tiroInimigo(Jogador jogador){
        int qtd = tirosInv.size(); // verifica quantos tiros do invasor serao comparados
        
        for (int i = 0; i < qtd; i++){
            // verifica se o tiro atingiu o canhao
            if (jogador.getCanhao().Atacado(tirosInv.get(i)) != 0){
                jogador.getCanhao().reinicia();
                removeIndex(1, i);
                i--;
                qtd--;
                continue;
            }
            
            // verifica se o tiro atingiu a barreira
            if (jogador.ataqueBarreira((tirosInv.get(i))) != 0){
                removeIndex(1, i);
                i--;
                qtd--;
            }
            
        }
        qtd = tirosJog.size();
        for (int i = 0; i < qtd; i++){
            // verifica se o tiro do jogador atingiu a barreira
            if (jogador.ataqueBarreira((tirosJog.get(i))) != 0){
                removeIndex(0, i);
                i--;
                qtd--;
            }
        }
        return jogador;
    }
    
    /**
     * verifica se os invasores foram atingidos pelo disparo do canhao
     * @param invasores a classe dos invasores
     * @return matriz de invasores com as informacoes atualizadas
     */
    public MatrizInvasores tiroJogador(MatrizInvasores invasores){
        int qtd = tirosJog.size(); // verifica quantos tiros serao comparados
        int acertou = 0;
        for (int i = 0; i < qtd; i++){
            acertou = invasores.Atacado(tirosJog.get(i));
            if (acertou != 0){
                removeIndex(0, i);
                pontosObtidos += acertou;
                i--;
                qtd--;
            }
        }
        return invasores;
    }
    
    /**
     * resgata os pontos feitos pelo jogador
     * @return os pontos feitos
     */
    public int resgataPontos(){
        int temp = pontosObtidos;
        pontosObtidos = 0;
        return temp;
    }
}