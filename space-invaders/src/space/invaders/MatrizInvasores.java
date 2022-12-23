/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author beatr
 */
public class MatrizInvasores {
    // invasores que se movem
    private List <Invasor> linhaUm;
    private List <Invasor> linhaDois;
    private List <Invasor> linhaTres;
    private List <Invasor> linhaQuatro;
    private List <Invasor> linhaCinco;
    
    private Invasor invasor;
    private boolean direc; // direcao de movimento invasores
    private boolean direcI4; // direcao de movimento do invasor 4
    
    /**
     * cria os invasores
     */
    MatrizInvasores(){
        // cria as listas e variaveis
        linhaUm = new ArrayList <> ();
        linhaDois = new ArrayList <> ();
        linhaTres = new ArrayList <> ();
        linhaQuatro = new ArrayList <> ();
        linhaCinco = new ArrayList <> ();
        
        invasor = new Invasor(4, 45, 60);
        
        // criar invasores
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
     * Se ainda existir invasores essa funcao vai retornar false, caso todos os
     * invasores tenham sidos destruidos, ela retorna true
     * @return true para invasores completamente destruidos
     */
    public boolean invasoresDestruidos(){
        int qtdInv = 0;
        if(invasor.getVivo()){
            return false;
        }
        qtdInv += linhaUm.size();
        qtdInv += linhaDois.size();
        qtdInv += linhaTres.size();
        qtdInv += linhaQuatro.size();
        qtdInv += linhaCinco.size();
        
        return qtdInv == 0;
    }
    
    /**
     * @return true se os invasores chegaram na base
     */
    public boolean invasoresChegaram(){
        if(!linhaUm.isEmpty()){
            return (linhaUm.get(0).getCoordY() > 546);
        }
        if(!linhaDois.isEmpty()){
            return (linhaDois.get(0).getCoordY() > 546);
        }
        if(!linhaTres.isEmpty()){
            return (linhaTres.get(0).getCoordY() > 546);
        }
        if(!linhaQuatro.isEmpty()){
            return (linhaQuatro.get(0).getCoordY() > 546);
        }
        if(!linhaCinco.isEmpty()){
            return (linhaCinco.get(0).getCoordY() > 546);
        }
        return false;
    }
    
    /**
     * @param i index do invasor
     * @param linha tipo do invasor
     * @return as informaçoes daquele invasor (posiçao e existencia)
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

        while(flag){
            int n = rand.nextInt(11); // numero aleatorio de 0 a 10

            for (int i = 1; i < 6; i++){
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
    
    /**
     * move todos os invasores
     */
    public void move(){
        if ((invasor.getCoordX() == 499 && !direcI4) || (invasor.getCoordX() == 0 && direcI4)){
            direcI4 = !direcI4;
            invasor.MoverX(direcI4);
        }
        else{
            invasor.MoverX(direcI4);
        }
        
        if (linhaCinco.get(10).getCoordX() == 499 && direc){
            for (int i = 0; i < 11; i++){
                linhaUm.get(i).MoverY();
                linhaDois.get(i).MoverY();
                linhaTres.get(i).MoverY();
                linhaQuatro.get(i).MoverY();
                linhaCinco.get(i).MoverY();
            }
            direc = false;
        }
        else if (linhaCinco.get(0).getCoordX() == 0 && !direc){
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
}
