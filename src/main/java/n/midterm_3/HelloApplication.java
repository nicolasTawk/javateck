package n.midterm_3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.io.IOException;



public class HelloApplication extends Application {


    private static Stage stg;
    @Override
    public void start(Stage stage) throws IOException {

        stg = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public void changeScene (String FXML) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(FXML));
        stg.getScene().setRoot(pane);
    }



    public static void main(String[] args) {
        launch();
    }
}