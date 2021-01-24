package Angel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private static String path;
    public AngelController angelController;
    public Stage mascotStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = new FXMLLoader(getClass().getResource("Search.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);

        FXMLLoader mascotLoader = new FXMLLoader(getClass().getResource("Angel.fxml"));
        Parent root = mascotLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(null);
        primaryStage.setScene(scene);
        angelController = mascotLoader.getController();
        primaryStage.setTitle("Loli Snatcher");
        primaryStage.show();
        primaryStage.setHeight(500);
        angelController.setStage(primaryStage,path);


    }
    public static void main(String[] args){
        if (args.length != 0){path=args[0];}
        launch(args);

    }
}
