package space.invaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;

public class MatrizInvasores {
    // invasores que se movem no eixo y tambem
    private List <Invasor> linhaUm;
    private List <Invasor> linhaDois;
    private List <Invasor> linhaTres;
    private List <Invasor> linhaQuatro;
    private List <Invasor> linhaCinco;
    
    private Invasor invasor; // nave mae/ufo
    private boolean direc; // direcao de movimento invasores
    private boolean direcI4; // direcao de movimento do invasor 4/ufo
    
    private int ataqueUfo; // invasor 4 (ufo) ataca a cada 15 ataques
    
    /**
     * cria os invasores
     */
    MatrizInvasores(){
        // cria as listas de cada linha de invasores
        linhaUm = new ArrayList <> ();
        linhaDois = new ArrayList <> ();
        linhaTres = new ArrayList <> ();
        linhaQuatro = new ArrayList <> ();
        linhaCinco = new ArrayList <> ();
        
        // cria invasor da nave-mae
        invasor = new Invasor(4, 50, 60);
        
        // inicializa o ataque do ufo em 0
        ataqueUfo = 0;
        
        // criar invasores de cada linha
        for (int i = 0; i < 11; i++) {
            Invasor invasor1 = new Invasor(1, (i*35)+15,280);
            linhaUm.add(invasor1);
            
            Invasor invasor2 = new Invasor(1, (i*35)+15, 235);
            linhaDois.add(invasor2);
            
            Invasor invasor3 = new Invasor(2, (i*35)+15, 190);
            linhaTres.add(invasor3);
            
            Invasor invasor4 = new Invasor(2, (i*35)+15, 145);
            linhaQuatro.add(invasor4);
            
            Invasor invasor5 = new Invasor(3, (i*35)+15, 100);
            linhaCinco.add(invasor5);
        }
        direc = true;
        direcI4 = true;
    }
    
    /**
     * move todos os invasores, com exceçao da ufo
     */
    public void move(){        
        if (linhaCinco.get(10).getCoordX() + 30 + linhaCinco.get(10).velocidade > 499 && direc){
            for (int i = 0; i < 11; i++){
                linhaUm.get(i).MoverY();
                linhaDois.get(i).MoverY();
                linhaTres.get(i).MoverY();
                linhaQuatro.get(i).MoverY();
                linhaCinco.get(i).MoverY();
            }
            direc = false;
        }
        else if (linhaCinco.get(0).getCoordX() - linhaCinco.get(10).velocidade < 0 && !direc){
            for (int i = 0; i < 11; i++){
                linhaUm.get(i).MoverY();
                linhaDois.get(i).MoverY();
                linhaTres.get(i).MoverY();
                linhaQuatro.get(i).MoverY();
                linhaCinco.get(i).MoverY();
            }
            direc = true;
        }
        else{
            for (int i = 0; i < 11; i++){
                linhaUm.get(i).MoverX(direc);
                linhaDois.get(i).MoverX(direc);
                linhaTres.get(i).MoverX(direc);
                linhaQuatro.get(i).MoverX(direc);
                linhaCinco.get(i).MoverX(direc);
            }
        }
    }
    
    /**
     * move a ufo/invasor tipo 4, na unica direcao possivel (eixo x)
     */
    public void moveI4(){
        if ((invasor.getCoordX() + 30 + invasor.velocidade == 500 && !direcI4) || (invasor.getCoordX() - invasor.velocidade == 0 && direcI4)){
            direcI4 = !direcI4;
            invasor.MoverX(direcI4);
        }
        else{
            invasor.MoverX(direcI4);
        }
    }

    /**
     * desenha todos os invasores que nao foram atacados na tela
     * @param gc recebe o contexto grafico
     */
    public void desenha (GraphicsContext gc){
        for (int i = 0; i < linhaUm.size(); i++){
            linhaUm.get(i).desenha(gc);
        }
        for (int i = 0; i < linhaDois.size(); i++){
            linhaDois.get(i).desenha(gc);
        }
        for (int i = 0; i < linhaTres.size(); i++){
            linhaTres.get(i).desenha(gc);
        }
        for (int i = 0; i < linhaQuatro.size(); i++){
            linhaQuatro.get(i).desenha(gc);
        }
        for (int i = 0; i < linhaCinco.size(); i++){
            linhaCinco.get(i).desenha(gc);
        }
        invasor.desenha(gc);
    }
    
    /**
     * funcao que determina a quantidade de invasores vivos
     * @return a quantidade de invasores vivos do jogo
     */
    public int quantidade(){
        int qtd = 0;
        for (int i = 0; i < 11; i++){
            if (linhaUm.get(i).getVivo()){
                qtd++;
            }
            if (linhaDois.get(i).getVivo()){
                qtd++;
            }
            if (linhaTres.get(i).getVivo()){
                qtd++;
            }
            if (linhaQuatro.get(i).getVivo()){
                qtd++;
            }
            if (linhaCinco.get(i).getVivo()){
                qtd++;
            }
        }
        if (invasor.getVivo()){ qtd++; }
        return qtd;
    }

    /**
     * Se ainda existir invasores essa funcao vai retornar false, caso todos os
     * invasores tenham sidos destruidos, ela retorna true
     * @return true para todos os invasores destruidos
     */
    public boolean invasoresDestruidos(){
        int qtdInv = 0;
        if(invasor.getVivo()){
            return false;
        }
        for (int i = 0; i < 11; i++){
            if (linhaUm.get(i).getVivo()){
                qtdInv++;
            }
            if (linhaDois.get(i).getVivo()){
                qtdInv++;
            }
            if (linhaTres.get(i).getVivo()){
                qtdInv++;
            }
            if (linhaQuatro.get(i).getVivo()){
                qtdInv++;
            }
            if (linhaCinco.get(i).getVivo()){
                qtdInv++;
            }
        }
        
        return qtdInv == 0;
    }
    
    /**
     * Funcao que determina se os invasores chegaram na base (altura das barreiras)
     * @return true se os invasores chegaram na base
     */
    public boolean invasoresChegaram(){
        int qtdLinha[] = new int[5];
        for (int i = 0; i < 5; i++){
            qtdLinha[i] = 0;
        }
        for (int i = 0; i < 11; i++){
            if (linhaUm.get(i).getVivo()){
                qtdLinha[0]++;
            }
            if (linhaDois.get(i).getVivo()){
                qtdLinha[1]++;
            }
            if (linhaTres.get(i).getVivo()){
                qtdLinha[2]++;
            }
            if (linhaQuatro.get(i).getVivo()){
                qtdLinha[3]++;
            }
            if (linhaCinco.get(i).getVivo()){
                qtdLinha[4]++;
            }
        }
        if(qtdLinha[0] > 0){
            return (linhaUm.get(0).getCoordY() > 514);
        }
        if(qtdLinha[1] > 0){
            return (linhaDois.get(0).getCoordY() > 514);
        }
        if(qtdLinha[2] > 0){
            return (linhaTres.get(0).getCoordY() > 514);
        }
        if(qtdLinha[3] > 0){
            return (linhaQuatro.get(0).getCoordY() > 514);
        }
        if(qtdLinha[4] > 0){
            return (linhaCinco.get(0).getCoordY() > 514);
        }
        return false;
    }
    
    /**
     * @param i index do invasor
     * @param linha linha em que o invasor esta localizado
     * @return uma lista com as informaçoes daquele invasor (posiçao e existencia)
     */
    public int[] infoInvasor(int i, int linha){
        int infos[] = new int[3];
        switch (linha){
            case 1:
                infos[0] = linhaUm.get(i).getCoordX();
                infos[1] = linhaUm.get(i).getCoordY();
                if (linhaUm.get(i).getVivo()){
                    infos[2] = 1;
                }
                else{
                    infos[2] = 0;
                }
                break;
            case 2:
                infos[0] = linhaDois.get(i).getCoordX();
                infos[1] = linhaDois.get(i).getCoordY();
                if (linhaDois.get(i).getVivo()){
                    infos[2] = 1;
                }
                else{
                    infos[2] = 0;
                }
                break;
            case 3:
                infos[0] = linhaTres.get(i).getCoordX();
                infos[1] = linhaTres.get(i).getCoordY();
                if (linhaTres.get(i).getVivo()){
                    infos[2] = 1;
                }
                else{
                    infos[2] = 0;
                }
                break;
            case 4:
                infos[0] = linhaQuatro.get(i).getCoordX();
                infos[1] = linhaQuatro.get(i).getCoordY();
                if (linhaQuatro.get(i).getVivo()){
                    infos[2] = 1;
                }
                else{
                    infos[2] = 0;
                }
                break;
            case 5:
                infos[0] = linhaCinco.get(i).getCoordX();
                infos[1] = linhaCinco.get(i).getCoordY();
                if (linhaCinco.get(i).getVivo()){
                    infos[2] = 1;
                }
                else{
                    infos[2] = 0;
                }
                break;
            default:
                // caso recebe informaçoes invalidas retorna tudo -1
                infos[0] = -1;
                infos[1] = -1;
                infos[2] = -1;
                break;
        }
        return infos;
    }
    
    /**
     * funçao de gerar ataque do invasor e atualizaçao da lista de misseis
     * @param lista recebe a lista de misseis para incluir ataque
     * @return lista de misseis atualizada
     */
    public ListaMisseis AtacarRandom(ListaMisseis lista){
        Random rand = new Random(); // gerador de aleatorios

        Missil tiro;
        boolean flag = true;
        int infos[] = new int[3];
        
        ataqueUfo++; // a cada ataque soma 1 para, ao atingir 15 ataques, a ufo atacar
        
        // a ufo ataca a cada 15 ataques OU quando todas as naves foram destruidas
        if ((ataqueUfo%15 == 0 || quantidade() == 1) && invasor.getVivo()){
            tiro = invasor.Atacar();
            lista.adicionaMissil(tiro, 1);
            return lista;
        }
        
        while(flag){
            int n = rand.nextInt(11); // numero aleatorio de 0 a 10
            for (int i = 1; i < 6; i++){ // analisa as 5 linhas, buscando qual o
                // invasor mais a frente daquela coluna para realizar o ataque
                infos = infoInvasor(n, i);
                if (infos[2] == 1){
                    switch (i){
                        case 1:
                            tiro = linhaUm.get(n).Atacar();
                            lista.adicionaMissil(tiro, 1);
                            flag = false;
                            break;
                        case 2:
                            tiro = linhaDois.get(n).Atacar();
                            lista.adicionaMissil(tiro, 1);
                            flag = false;
                            break;
                        case 3:
                            tiro = linhaTres.get(n).Atacar();
                            lista.adicionaMissil(tiro, 2);
                            flag = false;
                            break;
                        case 4:
                            tiro = linhaQuatro.get(n).Atacar();
                            lista.adicionaMissil(tiro, 2);
                            flag = false;
                            break;
                        case 5:
                            tiro = linhaCinco.get(n).Atacar();
                            lista.adicionaMissil(tiro, 3);
                            flag = false;
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }
        }
        
        return lista;
    }
    
    /**
     * verifica se algum invasor foi atingido pelo tiro e, caso sim, altera a
     * existencia dele e retorna os pontos daquele ataque
     * @param tiro o tiro do ataque atual
     * @return os pontos do ataque
     */
    public int Atacado(Missil tiro){
        int pontos = 0;
        pontos = invasor.Atacado(tiro);
        if (pontos != 0){
            return pontos;
        }
        
        // compara
        for (int i = 0; i < 11; i++) {
            pontos = linhaUm.get(i).Atacado(tiro);
            if (pontos != 0){
                return pontos;
            }
            pontos = linhaDois.get(i).Atacado(tiro);
            if (pontos != 0){
                return pontos;
            }
            
            pontos = linhaTres.get(i).Atacado(tiro);
            if (pontos != 0){
                return pontos;
            }
            pontos = linhaQuatro.get(i).Atacado(tiro);
            if (pontos != 0){
                return pontos;
            }
            
            pontos = linhaCinco.get(i).Atacado(tiro);
            if (pontos != 0){
                return pontos;
            }
        }
        pontos = invasor.Atacado(tiro);
        return pontos;
    }
}
