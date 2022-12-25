package space.invaders;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Instrucoes {
    /**
     * Indica todas as teclas utilizadas no jogo
     */
    public Instrucoes(){
        Stage jStage = new Stage();
        Scene jScene;
        Group jGrupo;
        Canvas jCanvas;
        GraphicsContext gc;
        
        jStage.setTitle ("Space Invader - Beatriz Cardoso");
        
        // cria grupo e adiciona na cena
        jGrupo = new Group();
        jScene = new Scene(jGrupo);
        jStage.setScene(jScene);
        
        jCanvas = new Canvas(500, 600);
        jGrupo.getChildren().add(jCanvas);
        gc = jCanvas.getGraphicsContext2D();
        
        // configuraçoes visuais
        Image fundo = new Image(getClass().getResourceAsStream("imagens/space.png"));
        gc.drawImage(fundo, 0, 0, 500, 600);
        gc.setFill( Color.WHITE );
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(javafx.scene.text.Font.font("Courier New", 16));
        
        gc.fillText("a -> move nave para direita\nd -> move nave para esquerda\nspace -> atira\nesc -> sai do jogo\nenter -> reinicia o jogo", 250, 100);
        
        // configura o botao de instruçoes do jogo
        Button instr = new Button("VOLTAR");
        instr.setStyle("-fx-background-color: grey;"
                + "-fx-font-size: 8pt;"
                + "-fx-font-family: Courier New;"
                + "-fx-text-fill: lightGrey;"
                + "-fx-background-radius: 20px;");
        instr.setMinHeight(30);
        instr.setMinWidth(80);
        instr.setLayoutX(210);
        instr.setLayoutY(475);
        instr.setOnAction((event) -> {
            System.out.println("Voltando");
            jStage.close();
            SpaceInvaders jogo = new SpaceInvaders();
            jogo.start(jStage);
        });
        
        jGrupo.getChildren().add(instr);
        jStage.show();
    }
}
