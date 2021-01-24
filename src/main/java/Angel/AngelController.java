package Angel;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
public class AngelController {
    private Stage stage;
    double xOffset = 0;
    double yOffset = 0;
    double mascotWidth,mascotHeight;
    bibleReader reader = new bibleReader();
    @FXML
    public VBox main,bibleContainer;
    @FXML
    ImageView mascotView;
    @FXML
    Text bookName,biblePassage;
    @FXML
    TextFlow textFlowPassage,textFlowTitle,textFlowNext,textFlowRandom,textFlowBookPrev,textFlowBookNext;


    public void setStage(final Stage stage, String path) {
        this.stage = stage;
        textFlowTitle.setTextAlignment(TextAlignment.CENTER);
        textFlowTitle.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        bibleContainer.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, new CornerRadii(10), Insets.EMPTY)));
        textFlowPassage.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        textFlowNext.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        textFlowRandom.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        textFlowBookNext.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        textFlowBookPrev.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        //Things to do on keypresses
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                switch (e.getCode()) {
                    case CLOSE_BRACKET:
                        stage.setHeight(stage.getHeight() * 1.05);
                        stage.setWidth(stage.getWidth() * 1.05);
                        updateHeight();
                        break;
                    case OPEN_BRACKET:
                        stage.setHeight(stage.getHeight() * 0.95);
                        stage.setWidth(stage.getWidth() * 0.95);
                        updateHeight();
                        break;
                    case W:
                        stage.setY(stage.getY() - 20);
                        break;
                    case S:
                        stage.setY(stage.getY() + 20);
                        break;
                    case A:
                        stage.setX(stage.getX() - 20);
                        break;
                    case D:
                        stage.setX(stage.getX() + 20);
                        break;
                    case Q:
                        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                        break;
                    case O:
                        openImage();
                        break;
                    case RIGHT:
                        nextButton();
                        break;
                    case LEFT:
                        randButton();
                        break;
                    case UP:
                        nextBook();
                        break;
                    case DOWN:
                        prevBook();
                        break;
                 }

                }
            });
            setImg();
            nextButton();
            loadPassage();
    }

            private void openImage() {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Image");
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    setImg(selectedFile.getPath());
                    System.out.println(selectedFile.getAbsolutePath());
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
                System.out.println(path);
                File tmp = new File(path);
                if (tmp.exists()) {
                    Image mascotImage = new Image("file://" + path);
                    tmp = null;
                    return loadImage(mascotImage);
                }
                tmp = null;
                return false;
            }
            public boolean setImg() {
                Image mascotImage = new Image("Angel/angel.png");
                return loadImage(mascotImage);
            }
            public boolean loadImage(Image mascotImage){
                mascotWidth = mascotImage.getWidth();
                mascotHeight = mascotImage.getHeight();
                stage.setHeight(mascotHeight * 0.25);
                stage.setWidth(mascotWidth  * 0.25);
                mascotView.setImage(mascotImage);
                mascotView.setFitWidth(stage.getWidth());
                mascotView.setPreserveRatio(true);
                mascotView.fitWidthProperty().bind(stage.widthProperty());
                updateHeight();
                return true;
            }
            public void loadPassage(){
                bookName.textProperty().setValue(reader.bookName);
                biblePassage.textProperty().setValue(reader.passage);
                updateHeight();
            }
            public void updateHeight(){
                // Layout is called because getheight always changes late causing height value to be the previous not the new
                bibleContainer.layout();
                main.layout();
                stage.setHeight(mascotHeight * (mascotView.getFitWidth() / mascotWidth) + bibleContainer.getHeight() + main.spacingProperty().getValue());
            }
            public void prevBook(){
                reader.prevBook();
                loadPassage();
            }
            public void nextBook(){
                reader.nextBook();
                loadPassage();
            }
            public void randButton(){
                reader.randRead();
                loadPassage();
            }
            public void nextButton(){
                reader.read();
                loadPassage();
            }
        }

