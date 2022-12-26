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
 * @author Beatriz Cardoso de Oliveira
 * NUSP: 12566400
 */

public class SpaceInvaders extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        // Configura a pagina inicial do jogo, com o tamanho 500x600
        Pane root = new Pane();
        root.setPrefSize(500, 600);
        Canvas canvas = new Canvas(500, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Image fundo = new Image(getClass().getResourceAsStream("imagens/space.png"));
        root.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.getWidth());
            canvas.setHeight(newValue.getHeight());
            
            // configura o fundo do jogo
            gc.drawImage(fundo, 0, 0, 500, 600);
            
            // configura o titulo do jogo
            gc.setFill( Color.LIGHTGRAY );
            gc.setStroke( Color.GRAY );
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(javafx.scene.text.Font.font("Courier New", 50));
            gc.setLineWidth(2);
            gc.fillText("SPACE INVADERS", 250, 150);
            gc.strokeText("SPACE INVADERS", 250, 150);
        });

        // configura o botao de inicio facil do jogo
        Button facil = new Button("FÁCIL");
        facil.setStyle("-fx-background-color: grey;"
                + "-fx-font-size: 12pt;"
                + "-fx-font-family: Courier New;"
                + "-fx-text-fill: lightGrey;"
                + "-fx-background-radius: 20px;");
        facil.setMinHeight(30);
        facil.setMinWidth(100);
        facil.setLayoutX(200);
        facil.setLayoutY(375);
        facil.setOnAction((event) -> {
            System.out.println("Iniciando o jogo");
            primaryStage.close();
            // quando clica no botao de inicio, chama o loop do jogo
            Jogo jogo = new Jogo(0);
        });
        
        // configura o botao de inicio dificil do jogo
        Button dificil = new Button("DIFÍCIL");
        dificil.setStyle("-fx-background-color: grey;"
                + "-fx-font-size: 12pt;"
                + "-fx-font-family: Courier New;"
                + "-fx-text-fill: lightGrey;"
                + "-fx-background-radius: 20px;");
        dificil.setMinHeight(30);
        dificil.setMinWidth(100);
        dificil.setLayoutX(200);
        dificil.setLayoutY(425);
        dificil.setOnAction((event) -> {
            System.out.println("Iniciando o jogo");
            primaryStage.close();
            // quando clica no botao de inicio, chama o loop do jogo
            Jogo jogo = new Jogo(1);
        });
        
        // configura o botao de instruçoes do jogo
        Button instr = new Button("INSTRUÇÕES");
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
            System.out.println("Instruçoes do jogo");
            primaryStage.close();
            Instrucoes inst = new Instrucoes();
        });
        
        root.getChildren().add(canvas);
        root.getChildren().add(instr);
        root.getChildren().add(dificil);
        root.getChildren().add(facil);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }    
}
