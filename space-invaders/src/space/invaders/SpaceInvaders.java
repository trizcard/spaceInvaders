/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author beatr
 */

public class SpaceInvaders extends Application {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        Pane root = new Pane();
        root.setPrefSize(500, 600);
        Canvas canvas = new Canvas();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Image original = new Image(getClass().getResourceAsStream("imagens/space.png"));
        root.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.getWidth());
            canvas.setHeight(newValue.getHeight());
            
            // configura o fundo do jogo
            gc.drawImage(original, 0, 0, 500, 600);
            
            // configura o titulo do jogo
            gc.setFill( Color.LIGHTGRAY );
            gc.setStroke( Color.GRAY );
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(javafx.scene.text.Font.font("Segoe UI Semibold", 50));
            gc.setLineWidth(1);
            gc.fillText("SPACE INVADERS", 250, 150);
            gc.strokeText("SPACE INVADERS", 250, 150);
        });

        // configura o botao de inicio do jogo
        Button inicio = new Button("INICIAR");
        inicio.setStyle("-fx-background-color: grey;"
                + "-fx-background-opacity: 0.7;"
                + "-fx-font-size: 12pt;"
                + "-fx-font-family: Segoe UI Semibold;"
                + "-fx-text-fill: lightGrey;"
                + "-fx-background-radius: 20px;");
        inicio.setMinHeight(30);
        inicio.setMinWidth(100);
        inicio.setLayoutX(200);
        inicio.setLayoutY(400);
        inicio.setOnAction((event) -> {
            System.out.println("Iniciando o jogo");
            Jogo jogo = new Jogo();
            jogo.start();
        });
        
        root.getChildren().add(canvas);
        root.getChildren().add(inicio);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }    
}
