import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

// An attempt to determine JavaFX not being loaded properly
public class JavaFXTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("JavaFX is working!");
        primaryStage.setScene(new Scene(label, 200, 100));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
