package centus;

import centus.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import centus.helpers.Db;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    public static User user;

    @Override
    public void start(Stage stage) throws IOException {
        Db.connect();

        this.user = null;
        this.stage = stage;

        scene = new Scene(loadFXML("loginform"));
        stage.setTitle("Centuś - Agata Gołębiowska");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static void switchTo(String fxml) throws IOException {
        stage.setScene(new Scene(loadFXML(fxml)));
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}