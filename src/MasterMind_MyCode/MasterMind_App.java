import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MasterMind_App extends Application {

    private BorderPane borderPane = new BorderPane();
    private TextArea readMessagesArea = new TextArea();
    private TextField sendMessagesField = new TextField("Type in message...");
    private Button sendMessage = new Button("send");

    @Override
    public void start(Stage primaryStage){
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(sendMessagesField, sendMessage);

        borderPane.setCenter(readMessagesArea);
        borderPane.setBottom(hBox);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MasterMind");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(MasterMind_App.class);
    }
}



/*
Thread guiThread = new Thread(() -> {
                try {
                    int player = in.readInt();
                    if (player == PLAYER1) {
                        Platform.runLater(() -> {
                            readMessagesArea.appendText("Waiting for player two");
                        });

                        sendMessage.setOnAction(event -> {
                            codeToBreak = sendMessagesField.getText();
                        });

                        //TODO implement code to make a code from the characters B, Y, G, R, P, O

                    } else if (player == PLAYER2) {
                        if (codeToBreak.equals("")) {
                            Platform.runLater(() -> {
                                readMessagesArea.appendText("Waiting on player one to make a code...");
                            });
                        }

                        if (!codeToBreak.equals("")) {
                            //TODO implement code to guess the code with the characters B, Y, G, R, P, O
                            sendMessage.setOnAction(event -> {
                                tryingCode = sendMessagesField.getText();
                                readMessagesArea.appendText(tryingCode);
                            });
                        }
                    }

                    while (continuePlay) {
                        if (player == PLAYER1) {
                            //TODO maybe implement like tictactoe example?
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            guiThread.start();
 */