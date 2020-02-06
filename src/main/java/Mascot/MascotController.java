package Mascot;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.File;


public class MascotController {
    private Stage stage;
    double xOffset = 0;
    double yOffset = 0;
    @FXML
    public VBox main;
    @FXML
    ImageView mascotView;



    public void setStage(Stage stage, String path) {
        this.stage = stage;
        //Things to do on keypresses
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                switch (e.getCode()) {
                    case CLOSE_BRACKET:
                        stage.setHeight(stage.getHeight() * 1.05);
                        stage.setWidth(stage.getWidth() * 1.05);
                        break;
                    case OPEN_BRACKET:
                        stage.setHeight(stage.getHeight() * 0.95);
                        stage.setWidth(stage.getWidth() * 0.95);
                        break;
                    case UP:
                        stage.setY(stage.getY() - 20);
                        break;
                    case DOWN:
                        stage.setY(stage.getY() + 20);
                        break;
                    case LEFT:
                        stage.setX(stage.getX() - 20);
                        break;
                    case RIGHT:
                        stage.setX(stage.getX() + 20);
                        break;
                    case Q:
                        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                        break;
                    case O:
                        openImage();
                        break;
                 }

                }
            });
            openImage();
         }

            private void openImage() {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Image");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif", "*.jpeg"));
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    setImg(selectedFile.getAbsolutePath());
                } else {
                    openImage();
                }
            }

            @FXML
            public void imgClicked(MouseEvent e) {
                xOffset = e.getSceneX();
                yOffset = e.getSceneY();
            }

            @FXML
            public void imgDragged(MouseEvent e) {
                stage.setX(e.getScreenX() - xOffset);
                stage.setY(e.getScreenY() - yOffset);
            }


            public boolean setImg(String path) {
                File tmp = new File(path);
                if (tmp.exists()) {
                    Image mascotImage = new Image("file://" + path);
                    stage.setHeight(mascotImage.getHeight() * 0.25);
                    stage.setWidth(mascotImage.getWidth() * 0.25);
                    mascotView.setImage(mascotImage);
                    mascotView.setPreserveRatio(true);
                    mascotView.fitHeightProperty().bind(stage.heightProperty());
                    tmp = null;
                    return true;
                }
                tmp = null;
                return false;
            }

        }

