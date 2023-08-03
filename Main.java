import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import Chess.*;

public class Main extends Application {
    public void start(Stage primaryStage){
        Game game = new Game();
        game.display();

        Scene scene = new Scene(game.getLayout(), primaryStage.getWidth(), primaryStage.getHeight());

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
